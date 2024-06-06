package com.gl.project.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gl.glapicommon.model.entity.InterfaceInfo;
import com.gl.project.common.ErrorCode;
import com.gl.project.constant.CommonConstant;
import com.gl.project.exception.BusinessException;
import com.gl.project.exception.ThrowUtils;
import com.gl.project.mapper.UserMapper;
import com.gl.project.model.dto.interfaceinfo.InterfaceInfoQueryRequest;

import com.gl.project.model.vo.InterfaceInfoVO;
import com.gl.project.model.vo.UserVO;
import com.gl.project.service.InterfaceInfoService;
import com.gl.project.mapper.InterfaceInfoMapper;
import com.gl.project.service.UserService;
import com.gl.project.utils.SqlUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
* @author 19328
* @description 针对表【interface_info(接口信息)】的数据库操作Service实现
* @createDate 2024-05-14 11:37:55
*/
@Service
public class InterfaceInfoServiceImpl extends ServiceImpl<InterfaceInfoMapper, InterfaceInfo>
    implements InterfaceInfoService{

    @Resource
    private UserService userService;

    @Resource
    private UserMapper userMapper;

    @Override
    public void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean add) {
        if (interfaceInfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        String name = interfaceInfo.getName();


        // 创建时，参数不能为空
        if (add) {
            ThrowUtils.throwIf(StringUtils.isAnyBlank(name), ErrorCode.PARAMS_ERROR);
        }
        // 有参数则校验
        if (StringUtils.isNotBlank(name) && name.length() > 80) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "名称过长");
        }
    }

    @Override
    public List<InterfaceInfoVO> getInterFaces(Long minId, Long maxId) {
        LambdaQueryWrapper<InterfaceInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.ge(InterfaceInfo::getId, minId)
                .le(InterfaceInfo::getId, maxId);
        List<InterfaceInfo> list = this.list(wrapper);
        List<InterfaceInfoVO> returnList = list.stream().map(e -> {
            InterfaceInfoVO interfaceInfoVO = new InterfaceInfoVO();
            BeanUtils.copyProperties(e, interfaceInfoVO);
            return interfaceInfoVO;
        }).collect(Collectors.toList());

        return returnList;
    }

    @Override
    public Boolean updateInterFaces(Long minId, Long maxId) {
        UpdateWrapper<InterfaceInfo> wrapper = new UpdateWrapper<>();
        wrapper.ge("id", minId)
                .le("id", maxId);
        return userMapper.updateDesc(wrapper,"test");
    }

    /**
     * 获取查询包装类
     *
     * @param interfaceInfoQueryRequest
     * @return
     */







}




