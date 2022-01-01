package cn.ecut.lrj.modules.ums.dto;


import cn.ecut.lrj.modules.ums.model.UmsResource;
import cn.ecut.lrj.modules.ums.model.UmsRole;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class ResourceRoleDto extends UmsResource {
    List<UmsRole> roleList;
}
