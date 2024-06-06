package com.gl.project.mapper;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gl.glapicommon.model.entity.InterfaceInfo;
import com.gl.glapicommon.model.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
* @author 19328
* @description 针对表【user(用户)】的数据库操作Mapper
* @createDate 2024-05-14 11:39:48
* @Entity com.gl.project.model.entity.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

    boolean updateDesc(@Param("ew") UpdateWrapper<InterfaceInfo> wrapper, @Param("newDesc") String desc);
}




