package cn.ecut.lrj.modules.ums.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
public class UmsAdminLogDto {

    private Integer id;

    private String userName;

    private Date createTime;

    private String ip;

    private String address;

    private String userAgent;
}
