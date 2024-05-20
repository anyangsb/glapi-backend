package com.gl.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gl.glapicommon.model.entity.UserInterfaceInfo;

/**
* @author 19328
* @description 针对表【user_interface_info(用户调用接口关系)】的数据库操作Service
* @createDate 2024-05-15 21:00:31
*/
public interface UserInterfaceInfoService extends IService<UserInterfaceInfo> {

    void validUserInterfaceInfo(UserInterfaceInfo userInterfaceInfo, boolean add);

    /**
     * 统计接口调用次数
     * @param interfaceInfoId
     * @param userId
     * @return
     */
    boolean invokeCount(long interfaceInfoId,long userId);
    
}
