package cn.ecut.lrj.modules.ums.service;


import cn.ecut.lrj.modules.ums.dto.ResourceRoleDto;
import cn.ecut.lrj.modules.ums.model.UmsResource;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;


import java.util.List;

/**
 * 后台资源管理Service
 * Created by macro on 2020/2/2.
 */
public interface UmsResourceService extends IService<UmsResource> {
    /**
     * 添加资源
     */
    boolean create(UmsResource umsResource);

    /**
     * 修改资源
     */
    boolean update(Long id, UmsResource umsResource);

    /**
     * 删除资源
     */
    boolean delete(Long id);

    /**
     * 分页查询资源
     */
    Page<UmsResource> list(Long categoryId, String nameKeyword, String urlKeyword, Integer pageSize, Integer pageNum);

    /**
     * 查询资源对应角色信息
     * @return
     */
    List<ResourceRoleDto> getAllResourceRole();
}
