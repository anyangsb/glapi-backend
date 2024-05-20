package com.gl.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gl.glapicommon.model.entity.InterfaceInfo;
import com.gl.glapicommon.service.InnerInterfaceInfoService;
import com.gl.project.common.ErrorCode;
import com.gl.project.exception.BusinessException;
import com.gl.project.mapper.InterfaceInfoMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

@DubboService
public class InnerInterfaceInfoServiceImpl implements InnerInterfaceInfoService {

    @Resource
    private InterfaceInfoMapper interfaceInfoMapper;

    @Override
    public InterfaceInfo getInterfaceInfo(String path, String method) {
        if(StringUtils.isAnyBlank(path,method)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("url",path);
        queryWrapper.eq("method",method);
        return interfaceInfoMapper.selectOne(queryWrapper);
    }
}
