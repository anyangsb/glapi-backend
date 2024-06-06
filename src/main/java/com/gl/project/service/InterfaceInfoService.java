package com.gl.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gl.glapicommon.model.entity.InterfaceInfo;
import com.gl.project.model.vo.InterfaceInfoVO;

import java.util.List;

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
     * 测试用，用于查询指定id范围内的接口信息
     * @param minId
     * @param maxId
     * @return
     */
    List<InterfaceInfoVO> getInterFaces(Long minId, Long maxId);

    /**
     * 测试用复杂sql更新语句
     * @param minId
     * @param maxId
     * @return
     */
    Boolean updateInterFaces(Long minId, Long maxId);

    /**
     * 获取查询条件
     *
     * @param postQueryRequest
     * @return
     */

}
