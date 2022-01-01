package cn.ecut.lrj.modules.ums.service.impl;

import cn.ecut.lrj.common.api.CommonResult;
import cn.ecut.lrj.common.util.ComConstants;
import cn.ecut.lrj.modules.ums.service.UmsAdminLoginLogService;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.BCrypt;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.ecut.lrj.common.exception.ApiException;
import cn.ecut.lrj.common.exception.Asserts;
import cn.ecut.lrj.modules.ums.dto.UmsAdminParam;
import cn.ecut.lrj.modules.ums.dto.UpdateAdminPasswordParam;
import cn.ecut.lrj.modules.ums.mapper.UmsAdminLoginLogMapper;
import cn.ecut.lrj.modules.ums.mapper.UmsAdminMapper;
import cn.ecut.lrj.modules.ums.mapper.UmsResourceMapper;
import cn.ecut.lrj.modules.ums.mapper.UmsRoleMapper;
import cn.ecut.lrj.modules.ums.model.*;
import cn.ecut.lrj.modules.ums.service.UmsAdminCacheService;
import cn.ecut.lrj.modules.ums.service.UmsAdminRoleRelationService;
import cn.ecut.lrj.modules.ums.service.UmsAdminService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 后台管理员管理Service实现类
 * Created by macro on 2018/4/26.
 */
@Service
@Slf4j
public class UmsAdminServiceImpl extends ServiceImpl<UmsAdminMapper, UmsAdmin> implements UmsAdminService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UmsAdminServiceImpl.class);

    @Resource
    private UmsAdminLoginLogMapper loginLogMapper;

    @Resource
    private UmsAdminCacheService adminCacheService;

    @Resource
    private UmsAdminRoleRelationService adminRoleRelationService;

    @Resource
    private UmsRoleMapper roleMapper;

    @Resource
    private UmsResourceMapper resourceMapper;

    @Resource
    private UmsAdminLoginLogService adminLoginLogService;


    @Override
    public UmsAdmin getAdminByUsername(String username) {
        UmsAdmin admin = adminCacheService.getAdmin(username);
        if(admin!=null) return  admin;
        QueryWrapper<UmsAdmin> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UmsAdmin::getUsername,username);
        List<UmsAdmin> adminList = list(wrapper);

        if (adminList != null && adminList.size() > 0) {
            admin = adminList.get(0);
            adminCacheService.setAdmin(admin);
            return admin;
        }
        return null;
    }

    @Override
    public UmsAdmin register(UmsAdminParam umsAdminParam) {
        UmsAdmin umsAdmin = new UmsAdmin();
        BeanUtils.copyProperties(umsAdminParam, umsAdmin);
        umsAdmin.setCreateTime(new Date());
        umsAdmin.setStatus(1);

        //查询是否有相同用户名的用户
        QueryWrapper<UmsAdmin> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UmsAdmin::getUsername,umsAdmin.getUsername());
        List<UmsAdmin> umsAdminList = list(wrapper);
        if (umsAdminList.size() > 0) {
            throw new ApiException("该用户已注册过！");
        }


        //将密码进行加密操作
        String encodePassword = BCrypt.hashpw(umsAdmin.getPassword());
        umsAdmin.setPassword(encodePassword);
        baseMapper.insert(umsAdmin);
        return umsAdmin;
    }

    @Override
    public UmsAdmin login(String username, String password, String kaptcha, HttpSession session) {

        //判断验证码是否正确
        String sessionKaptcha = (String) session.getAttribute(ComConstants.KAPTCHA);
        if (StrUtil.isBlank(sessionKaptcha) || !kaptcha.equalsIgnoreCase(sessionKaptcha)){
            throw new ApiException("验证码不正确！");
        }

        //先从缓存中获取
        UmsAdmin admin = adminCacheService.getAdmin(username);

        try {
            //要是获取不到从数据库中获取，并加入缓存
            if (admin == null){
                QueryWrapper<UmsAdmin> wrapper = new QueryWrapper();
                wrapper.lambda().eq(UmsAdmin::getUsername,username);
                List<UmsAdmin> list = list(wrapper);
                if (list.isEmpty()){
                    Asserts.fail("账号错误！");
                }
                admin = list.get(0);
            }
            //判断密码是否正确
            if (!BCrypt.checkpw(password, admin.getPassword())){
                Asserts.fail("密码错误！");
            }
            //验证账号状态是否正常
            if (admin.getStatus() != 1){
                Asserts.fail("账号异常，请联系管理员");
            }
            //加入缓存
            adminCacheService.setAdmin(admin);

            //记录登入日志
            insertLoginLog(admin.getUsername());

        } catch (Exception e) {
            log.info("登入异常："+e.getMessage());
            Asserts.fail("登录异常："+e.getMessage());
        }

        return admin;
    }

    /**
     * 添加登录记录
     * @param username 用户名
     */
    private void insertLoginLog(String username) {
        UmsAdmin admin = getAdminByUsername(username);
        if(admin==null) return;
        UmsAdminLoginLog loginLog = new UmsAdminLoginLog();
        loginLog.setAdminId(admin.getId());
        loginLog.setCreateTime(new Date());
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        loginLog.setIp(request.getRemoteAddr());
        loginLogMapper.insert(loginLog);
    }

    /**
     * 根据用户名修改登录时间
     */
    private void updateLoginTimeByUsername(String username) {
        UmsAdmin record = new UmsAdmin();
        record.setLoginTime(new Date());
        QueryWrapper<UmsAdmin> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UmsAdmin::getUsername,username);
        update(record,wrapper);
    }


    @Override
    public Page<UmsAdmin> list(String keyword, Integer pageSize, Integer pageNum) {
        Page<UmsAdmin> page = new Page<>(pageNum,pageSize);
        QueryWrapper<UmsAdmin> wrapper = new QueryWrapper<>();
        LambdaQueryWrapper<UmsAdmin> lambda = wrapper.lambda();
        if(StrUtil.isNotEmpty(keyword)){
            lambda.like(UmsAdmin::getUsername,keyword);
            lambda.or().like(UmsAdmin::getNickName,keyword);
        }
        return page(page,wrapper);
    }

    @Override
    public boolean update(Long id, UmsAdmin admin) {
        admin.setId(id);
        UmsAdmin rawAdmin = getById(id);
        if(rawAdmin.getPassword().equals(admin.getPassword())){
            //与原加密密码相同的不需要修改
            admin.setPassword(null);
        }else{
            //与原加密密码不同的需要加密修改
            if(StrUtil.isEmpty(admin.getPassword())){
                admin.setPassword(null);
            }else{
                admin.setPassword(BCrypt.hashpw(admin.getPassword()));
            }
        }
        boolean success = updateById(admin);
        adminCacheService.delAdmin(id);
        return success;
    }

    @Override
    public boolean delete(Long id) {
        adminCacheService.delAdmin(id);
        boolean success = removeById(id);
        adminCacheService.delResourceList(id);
        return success;
    }

    @Override
    public int updateRole(Long adminId, List<Long> roleIds) {
        int count = roleIds == null ? 0 : roleIds.size();
        //先删除原来的关系
        QueryWrapper<UmsAdminRoleRelation> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UmsAdminRoleRelation::getAdminId,adminId);
        adminRoleRelationService.remove(wrapper);
        //建立新关系
        if (!CollectionUtils.isEmpty(roleIds)) {
            List<UmsAdminRoleRelation> list = new ArrayList<>();
            for (Long roleId : roleIds) {
                UmsAdminRoleRelation roleRelation = new UmsAdminRoleRelation();
                roleRelation.setAdminId(adminId);
                roleRelation.setRoleId(roleId);
                list.add(roleRelation);
            }
            adminRoleRelationService.saveBatch(list);
        }
        adminCacheService.delResourceList(adminId);
        return count;
    }

    @Override
    public List<UmsRole> getRoleList(Long adminId) {
        return roleMapper.getRoleList(adminId);
    }

    @Override
    public List<UmsResource> getResourceList(Long adminId) {
        List<UmsResource> resourceList = adminCacheService.getResourceList(adminId);
        if(CollUtil.isNotEmpty(resourceList)){
            return  resourceList;
        }
        resourceList = resourceMapper.getResourceList(adminId);
        if(CollUtil.isNotEmpty(resourceList)){
            adminCacheService.setResourceList(adminId,resourceList);
        }
        return resourceList;
    }

    @Override
    public int updatePassword(UpdateAdminPasswordParam param) {
        if(StrUtil.isEmpty(param.getUsername())
                ||StrUtil.isEmpty(param.getOldPassword())
                ||StrUtil.isEmpty(param.getNewPassword())){
            return -1;
        }
        QueryWrapper<UmsAdmin> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UmsAdmin::getUsername,param.getUsername());
        List<UmsAdmin> adminList = list(wrapper);
        if(CollUtil.isEmpty(adminList)){
            return -2;
        }
        UmsAdmin umsAdmin = adminList.get(0);
        if(!BCrypt.checkpw(param.getOldPassword(),umsAdmin.getPassword())){
            return -3;
        }
        umsAdmin.setPassword(BCrypt.hashpw(param.getNewPassword()));
        updateById(umsAdmin);
        adminCacheService.delAdmin(umsAdmin.getId());
        return 1;
    }

    @Override
    public UmsAdmin loadUserByUsername(String username){
        //获取用户信息
        UmsAdmin admin = getAdminByUsername(username);
        if (admin != null) {
            // 查询用户访问资源，暂留， 后续改动
            // List<UmsResource> resourceList = getResourceList(admin.getId());
            return admin;
        }
        throw new ApiException("用户不存在");
    }

//    @Override
//    public UserDetails loadUserByUsername1(String username) {
//        UmsAdmin adminByUsername = getAdminByUsername(username);
//        if (adminByUsername!=null){
//            return new AdminDetail(adminByUsername,getRoleList(adminByUsername.getId()));
//        }
//        throw new ApiException("用户不存在");
//    }
//
//    /**
//     * 获取当前用户
//     * @return
//     */
//    @Override
//    public UmsAdmin getCurrentAdmin() {
//        AdminDetail adminDetail = (AdminDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        return adminDetail.getUmsAdmin();
//    }
}
