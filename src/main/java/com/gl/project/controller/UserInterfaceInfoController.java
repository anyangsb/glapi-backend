package com.gl.project.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gl.glapiclientsdk.client.GlApiClient;
import com.gl.glapicommon.model.entity.User;
import com.gl.glapicommon.model.entity.UserInterfaceInfo;
import com.gl.project.annotation.AuthCheck;
import com.gl.project.common.*;
import com.gl.project.constant.CommonConstant;
import com.gl.project.constant.UserConstant;
import com.gl.project.exception.BusinessException;
import com.gl.project.model.dto.userinterfaceinfo.UserInterfaceInfoAddRequest;
import com.gl.project.model.dto.userinterfaceinfo.UserInterfaceInfoQueryRequest;
import com.gl.project.model.dto.userinterfaceinfo.UserInterfaceInfoUpdateRequest;
import com.gl.project.service.UserInterfaceInfoService;
import com.gl.project.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 接口管理
 *
 
 */
@RestController
@RequestMapping("/userUserInterfaceInfo")
@Slf4j
public class UserInterfaceInfoController {

    @Resource
    private UserInterfaceInfoService userUserInterfaceInfoService;

    @Resource
    private UserService userService;

    @Resource
    private GlApiClient glApiClient;

    // region 增删改查

    /**
     * 创建
     *
     * @param userUserInterfaceInfoAddRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Long> addUserInterfaceInfo(@RequestBody UserInterfaceInfoAddRequest userUserInterfaceInfoAddRequest, HttpServletRequest request) {
        if (userUserInterfaceInfoAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        UserInterfaceInfo userUserInterfaceInfo = new UserInterfaceInfo();
        BeanUtils.copyProperties(userUserInterfaceInfoAddRequest, userUserInterfaceInfo);
        // 校验
        userUserInterfaceInfoService.validUserInterfaceInfo(userUserInterfaceInfo, true);
        User loginUser = userService.getLoginUser(request);
        userUserInterfaceInfo.setUserId(loginUser.getId());
        boolean result = userUserInterfaceInfoService.save(userUserInterfaceInfo);
        if (!result) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        long newUserInterfaceInfoId = userUserInterfaceInfo.getId();
        return ResultUtils.success(newUserInterfaceInfoId);
    }

    /**
     * 删除
     *
     * @param deleteRequest
     * @param request
     * @return
     */
    @PostMapping("/delete")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> deleteUserInterfaceInfo(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.getLoginUser(request);
        long id = deleteRequest.getId();
        // 判断是否存在
        UserInterfaceInfo oldUserInterfaceInfo = userUserInterfaceInfoService.getById(id);
        if (oldUserInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 仅本人或管理员可删除
        if (!oldUserInterfaceInfo.getUserId().equals(user.getId()) && !userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean b = userUserInterfaceInfoService.removeById(id);
        return ResultUtils.success(b);
    }

    /**
     * 更新
     *
     * @param userUserInterfaceInfoUpdateRequest
     * @param request
     * @return
     */
    @PostMapping("/update")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> updateUserInterfaceInfo(@RequestBody UserInterfaceInfoUpdateRequest userUserInterfaceInfoUpdateRequest,
                                                     HttpServletRequest request) {
        if (userUserInterfaceInfoUpdateRequest == null || userUserInterfaceInfoUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        UserInterfaceInfo userUserInterfaceInfo = new UserInterfaceInfo();
        BeanUtils.copyProperties(userUserInterfaceInfoUpdateRequest, userUserInterfaceInfo);
        // 参数校验
        userUserInterfaceInfoService.validUserInterfaceInfo(userUserInterfaceInfo, false);
        User user = userService.getLoginUser(request);
        long id = userUserInterfaceInfoUpdateRequest.getId();
        // 判断是否存在
        UserInterfaceInfo oldUserInterfaceInfo = userUserInterfaceInfoService.getById(id);
        if (oldUserInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 仅本人或管理员可修改
        if (!oldUserInterfaceInfo.getUserId().equals(user.getId()) && !userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean result = userUserInterfaceInfoService.updateById(userUserInterfaceInfo);
        return ResultUtils.success(result);
    }

    /**
     * 根据 id 获取
     *
     * @param id
     * @return
     */
    @GetMapping("/get")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<UserInterfaceInfo> getUserInterfaceInfoById(long id) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        UserInterfaceInfo userUserInterfaceInfo = userUserInterfaceInfoService.getById(id);
        return ResultUtils.success(userUserInterfaceInfo);
    }

    /**
     * 获取列表（仅管理员可使用）
     *
     * @param userUserInterfaceInfoQueryRequest
     * @return
     */
    @AuthCheck(mustRole = "admin")
    @GetMapping("/list")
    public BaseResponse<List<UserInterfaceInfo>> listUserInterfaceInfo(UserInterfaceInfoQueryRequest userUserInterfaceInfoQueryRequest) {
        UserInterfaceInfo userUserInterfaceInfoQuery = new UserInterfaceInfo();
        if (userUserInterfaceInfoQueryRequest != null) {
            BeanUtils.copyProperties(userUserInterfaceInfoQueryRequest, userUserInterfaceInfoQuery);
        }
        QueryWrapper<UserInterfaceInfo> queryWrapper = new QueryWrapper<>(userUserInterfaceInfoQuery);
        List<UserInterfaceInfo> userUserInterfaceInfoList = userUserInterfaceInfoService.list(queryWrapper);
        return ResultUtils.success(userUserInterfaceInfoList);
    }

    /**
     * 分页获取列表
     *
     * @param userUserInterfaceInfoQueryRequest
     * @param request
     * @return
     */
    @GetMapping("/list/page")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Page<UserInterfaceInfo>> listUserInterfaceInfoByPage(UserInterfaceInfoQueryRequest userUserInterfaceInfoQueryRequest, HttpServletRequest request) {
        if (userUserInterfaceInfoQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        UserInterfaceInfo userUserInterfaceInfoQuery = new UserInterfaceInfo();
        BeanUtils.copyProperties(userUserInterfaceInfoQueryRequest, userUserInterfaceInfoQuery);
        long current = userUserInterfaceInfoQueryRequest.getCurrent();
        long size = userUserInterfaceInfoQueryRequest.getPageSize();
        String sortField = userUserInterfaceInfoQueryRequest.getSortField();
        String sortOrder = userUserInterfaceInfoQueryRequest.getSortOrder();

        // description 需支持模糊搜索
        // 限制爬虫
        if (size > 50) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<UserInterfaceInfo> queryWrapper = new QueryWrapper<>(userUserInterfaceInfoQuery);
        queryWrapper.orderBy(StringUtils.isNotBlank(sortField),
                sortOrder.equals(CommonConstant.SORT_ORDER_ASC), sortField);
        Page<UserInterfaceInfo> userUserInterfaceInfoPage = userUserInterfaceInfoService.page(new Page<>(current, size), queryWrapper);
        return ResultUtils.success(userUserInterfaceInfoPage);
    }

    // endregion




}
