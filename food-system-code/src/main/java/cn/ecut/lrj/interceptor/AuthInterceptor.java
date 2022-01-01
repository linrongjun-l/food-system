package cn.ecut.lrj.interceptor;



import cn.ecut.lrj.common.api.ResultCode;
import cn.ecut.lrj.common.exception.ApiException;
import cn.ecut.lrj.common.exception.Asserts;
import cn.ecut.lrj.utils.JwtTokenUtil2;
import cn.ecut.lrj.modules.ums.model.UmsAdmin;
import cn.ecut.lrj.modules.ums.model.UmsResource;
import cn.ecut.lrj.modules.ums.service.UmsAdminService;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 作用： 验证 用户是否登录、菜单资源权限
 * 作者：徐庶
 */
@Slf4j
public class AuthInterceptor implements HandlerInterceptor {


    // 配置文件中的白名单secure.ignored.urls
    private List<String> urls;

    @Autowired
    private JwtTokenUtil2 jwtTokenUtil2;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;

    @Autowired
    private UmsAdminService umsAdminService;





    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //1、不需要登录就可以访问的路径——白名单
        // 获取当前请求   /admin/login
        String requestURI = request.getRequestURI();
        // Ant方式路径匹配 /**  ？  _
        PathMatcher matcher = new AntPathMatcher();
        for (String ignoredUrl : urls) {
            if(matcher.match(ignoredUrl,requestURI)){
                return  true;
            }
        }

        //拿到jwt令牌
        String jwt = request.getHeader(tokenHeader);

        //log.info("jwt令牌： "+jwt);

        //判断是否存在 判断开头是否加了tokenHead
        if (StrUtil.isBlank(jwt) || !jwt.startsWith(tokenHead)){
            throw new ApiException(ResultCode.UNAUTHORIZED);
        }
        //存在就进行解密
        jwt=jwt.substring(tokenHead.length());
        String userName = jwtTokenUtil2.getUserNameFromToken(jwt);

        //log.info("userName: " + userName);

        if (StrUtil.isBlank(userName)){
            throw new ApiException(ResultCode.UNAUTHORIZED);
        }

        //将userName存入ThreadLocal中(方便下面获取当前用户)
        JwtTokenUtil2.currentAdmin.set(userName);

        //得到userName就进行查询用户是否存在和 token是否过期
        UmsAdmin umsAdmin = umsAdminService.getAdminByUsername(userName);

        boolean result = jwtTokenUtil2.isTokenExpired(jwt);

        if (result){
            Asserts.fail(ResultCode.UNAUTHORIZED);
        }
        //如果登入的用户进行权限验证
        if (umsAdmin != null){
            // 获取用户所有可访问资源
            List<UmsResource> resourceList = umsAdminService.getResourceList(umsAdmin.getId());
            for (UmsResource umsResource : resourceList) {
                if("/admin/info".equals(requestURI) || matcher.match( umsResource.getUrl(),requestURI)){
                    return  true;
                }
            }
            throw new ApiException(ResultCode.FORBIDDEN);
        }else {
            Asserts.fail(ResultCode.UNAUTHORIZED);
        }


        return true;

//        //2、未登录用户，直接拒绝访问
//        if (null == request.getSession().getAttribute(ComConstants.FLAG_CURRENT_USER)) {
//            throw new ApiException(ResultCode.UNAUTHORIZED);
//        } else {
//            //3、已登录用户，判断是否有资源访问权限  Todo:到时候用spring security实现
//            UmsAdmin umsAdmin = (UmsAdmin) request.getSession().getAttribute(ComConstants.FLAG_CURRENT_USER);
//            // 获取用户所有可访问资源
//            UmsAdminService umsAdminService = null;
//            List<UmsResource> resourceList = umsAdminService.getResourceList(umsAdmin.getId());
//            for (UmsResource umsResource : resourceList) {
//                if(matcher.match( umsResource.getUrl(),requestURI)){
//                    return  true;
//                }
//            }
//            throw new ApiException(ResultCode.FORBIDDEN);
//        }

    }


    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }

}
