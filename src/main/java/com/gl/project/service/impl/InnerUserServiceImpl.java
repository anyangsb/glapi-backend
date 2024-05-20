package com.gl.project.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gl.glapicommon.model.entity.User;
import com.gl.glapicommon.service.InnerUserService;
import com.gl.project.common.ErrorCode;
import com.gl.project.exception.BusinessException;
import com.gl.project.mapper.UserMapper;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;


@DubboService
public class InnerUserServiceImpl implements InnerUserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public User getInvokeUser(String accessKey) {
        //参数校验
        if(StrUtil.isEmpty(accessKey)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("accessKey",accessKey);

        return userMapper.selectOne(queryWrapper);
    }
}
