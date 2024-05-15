package com.gl.project.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gl.project.model.dto.interfaceinfo.InterfaceInfoQueryRequest;
import com.gl.project.model.entity.InterfaceInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gl.project.model.entity.InterfaceInfo;
import com.gl.project.model.vo.InterfaceInfoVO;

import javax.servlet.http.HttpServletRequest;

/**
* @author 19328
* @description 针对表【interface_info(接口信息)】的数据库操作Service
* @createDate 2024-05-14 11:37:55
*/
public interface InterfaceInfoService extends IService<InterfaceInfo> {
    /**
     * 校验
     *
     * @param post
     * @param add
     */
    void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean add);

    /**
     * 获取查询条件
     *
     * @param postQueryRequest
     * @return
     */

}
