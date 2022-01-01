package cn.ecut.lrj.modules.ums.service;


import cn.ecut.lrj.modules.ums.model.UmsResourceCategory;
import com.baomidou.mybatisplus.extension.service.IService;


import java.util.List;

/**
 * 后台资源分类管理Service
 * Created by macro on 2020/2/5.
 */
public interface UmsResourceCategoryService extends IService<UmsResourceCategory> {

    /**
     * 获取所有资源分类
     */
    List<UmsResourceCategory> listAll();

    /**
     * 创建资源分类
     */
    boolean create(UmsResourceCategory umsResourceCategory);
}
