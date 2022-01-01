package cn.ecut.lrj.config;

import org.springframework.context.annotation.Configuration;

@Configuration
//@EnableWebSecurity //启动
public class MallSecurityConfig {//extends SecurityConfig {

//    @Autowired
//    private UmsAdminService umsAdminService;
//
//    @Autowired
//    private UmsResourceService umsResourceService;
//
//    @Bean
//    public UserDetailsService userDetailsService(){
//        return username->umsAdminService.loadUserByUsername1(username);
//    }
//
//    @Bean
//    public SecurityResourceRoleSource securityResourceRoleSource(){
//        return () -> {
//            //调用业务逻辑类查询资源对应角色信息
//            List<ResourceRoleDto> list = umsResourceService.getAllResourceRole();
//            Map<String,List<String>> map = new HashMap<>();
//            for (ResourceRoleDto roleDto : list) {
//                List<String> roleNameList = roleDto.getRoleList()
//                        .stream()
//                        .map(role -> role.getName())
//                        .collect(Collectors.toList());
//                map.put(roleDto.getUrl(),roleNameList);
//            }
//            return map;
//        };
//    }
}
