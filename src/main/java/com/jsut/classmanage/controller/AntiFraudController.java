package com.jsut.classmanage.controller;

import com.alibaba.fastjson.JSONObject;
import com.jsut.classmanage.common.api.ApiErrorCode;
import com.jsut.classmanage.common.api.ApiResult;
import com.jsut.classmanage.model.AntiFraud;
import com.jsut.classmanage.model.vo.AntiFraudVo;
import com.jsut.classmanage.model.vo.NoticeUserVo;
import com.jsut.classmanage.service.AntiFraudService;
import com.jsut.classmanage.util.PageUtils;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.naming.NamingEnumeration;

import java.util.Objects;

import static com.jsut.classmanage.jwt.JwtUtil.USER_NAME;

/**
 * @className AntiFraudController
 **/
@Api(tags = "反诈信息接口")
@Slf4j
@RestController
@RequestMapping("/api/fraud")
public class AntiFraudController {

    @Resource
    private AntiFraudService antiFraudService;


    @ApiOperation(value = "每日学习的反诈信息")
    @GetMapping("/info")
    public ApiResult<AntiFraudVo> antiFraudInfoToday(@RequestHeader(value = USER_NAME) String userId) {
        AntiFraudVo result = antiFraudService.queryTodayInfo(userId);
        return ApiResult.success(result);
    }

    @ApiOperation(value = "反诈信息打卡")
    @ApiParam(name = "antiId", required = true, example = "10")
    @GetMapping("/punch")
    public ApiResult userPunch(@RequestHeader(value = USER_NAME) String userId,
                               @RequestParam Long anitId) {

        log.info("学习反诈打卡入参:{}", anitId);

        antiFraudService.userPunch(anitId, userId);
        return ApiResult.success();
    }

    @ApiOperation(value = "发布反诈信息")
    @PostMapping("/publish")
    public ApiResult publishAntiInfo(@RequestBody AntiFraud antiFraud,
                                     @RequestHeader(value = USER_NAME) String userId) {

        log.info("发布发展信息入参:{}", JSONObject.toJSONString(antiFraud));
        if (Objects.isNull(antiFraud)) {
            return ApiResult.failed(ApiErrorCode.EMPTY_PARAM);
        }
        antiFraud.setUserId(userId);
        antiFraudService.publishAntiInfo(antiFraud);
        return ApiResult.success();

    }

    @ApiOperation(value = "反诈信息列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", required = false, example = "1"),
            @ApiImplicitParam(name = "size", required = false, example = "10")
    })
    @GetMapping("/pageList")
    public ApiResult<PageUtils<AntiFraudVo>> pageList(@RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                                      @RequestParam(value = "size", defaultValue = "10") Integer size) {

        PageUtils<AntiFraudVo> pageUtils = antiFraudService.pageList(pageNo, size);

        return ApiResult.success(pageUtils);
    }

    @ApiOperation(value = "学生打卡列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "antiId", required = true, example = "10"),
            @ApiImplicitParam(name = "pageNo", required = false, example = "1"),
            @ApiImplicitParam(name = "size", required = false, example = "10")
    })
    @GetMapping("/studentPunch")
    public ApiResult<PageUtils<NoticeUserVo>> studnetPunch(@RequestParam Long antiId,
                                                           @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                                           @RequestParam(value = "size", defaultValue = "10") Integer size) {

        log.info("学习打卡用户列表param:{}", antiId);
        if (Objects.isNull(antiId)) {
            return ApiResult.failed(ApiErrorCode.EMPTY_PARAM);
        }
        PageUtils<NoticeUserVo> pageUtils = antiFraudService.pageUserAntiInfo(pageNo, size, antiId);
        return ApiResult.success(pageUtils);
    }
}
