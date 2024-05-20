package com.gl.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gl.glapicommon.model.entity.UserInterfaceInfo;

import java.util.List;

/**
* @author 19328
* @description 针对表【user_interface_info(用户调用接口关系)】的数据库操作Mapper
* @createDate 2024-05-15 21:00:31
* @Entity com.gl.project.model.entity.UserInterfaceInfo
*/
public interface UserInterfaceInfoMapper extends BaseMapper<UserInterfaceInfo> {

    List<UserInterfaceInfo> listTopInvokeInterfaceInfo(int limit);
}




