package com.jsut.classmanage.controller;

import com.alibaba.fastjson.JSONObject;
import com.jsut.classmanage.common.api.ApiErrorCode;
import com.jsut.classmanage.common.api.ApiResult;

import com.jsut.classmanage.model.vo.ApplyLeaveSchool;
import com.jsut.classmanage.model.vo.MyApplyVo;
import com.jsut.classmanage.service.LeaveSchoolService;
import com.jsut.classmanage.util.PageUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import java.util.Objects;

import static com.jsut.classmanage.jwt.JwtUtil.USER_NAME;

/**
 * @className LeaveSchoolController
 **/
@Api(tags = "请假接口")
@Slf4j
@RestController
@RequestMapping("/api/leave")
public class LeaveSchoolController {


    @Resource
    private LeaveSchoolService leaveSchoolService;

    @ApiOperation(value = "申请请假")
    @PostMapping("/apply")
    public ApiResult applcy(@RequestBody ApplyLeaveSchool applyLeaveSchool,
                            @RequestHeader(value = USER_NAME) String userId) {
        log.info("请假申请入参:{}", JSONObject.toJSONString(applyLeaveSchool));
        if (Objects.isNull(applyLeaveSchool)) {
            return ApiResult.failed(ApiErrorCode.EMPTY_PARAM);
        }
        applyLeaveSchool.setUserId(userId);
        leaveSchoolService.applyLeaveSchool(applyLeaveSchool);
        return ApiResult.success();
    }

    @ApiOperation(value = "我的请假信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", required = false, example = "1"),
            @ApiImplicitParam(name = "size", required = false, example = "10")
    })
    @GetMapping("/myApply")
    public ApiResult<PageUtils<MyApplyVo>> myApply(@RequestHeader(value = USER_NAME) String userId,
                                                   @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                                   @RequestParam(value = "size", defaultValue = "10") Integer size) {

        PageUtils<MyApplyVo> pageUtils = leaveSchoolService.queryMyApply(userId, pageNo, size);
        return ApiResult.success(pageUtils);
    }

    @ApiOperation(value = "请假列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", required = false, example = "1"),
            @ApiImplicitParam(name = "size", required = false, example = "10")
    })
    @GetMapping("/pageList")
    public ApiResult<PageUtils<MyApplyVo>> pageList(@RequestHeader(value = USER_NAME) String userId,
                                                    @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                                    @RequestParam(value = "size", defaultValue = "10") Integer size) {

        PageUtils<MyApplyVo> pageUtils = leaveSchoolService.pageList(userId, pageNo, size);
        return ApiResult.success(pageUtils);
    }

    @ApiOperation(value = "请假审批")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "status", required = true, example = "1"),
            @ApiImplicitParam(name = "leaveId", required = true, example = "10")
    })
    @GetMapping("/approval")
    public ApiResult approval(@RequestHeader(value = USER_NAME) String userId,
                              @RequestParam Integer status,
                              @RequestParam Long leaveId) {

        log.info("审批入参:{},status:{}", leaveId, status);
        leaveSchoolService.approval(userId, status, leaveId);
        return ApiResult.success();

    }

}
