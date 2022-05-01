package com.jsut.classmanage.controller;

import com.alibaba.fastjson.JSONObject;
import com.jsut.classmanage.common.api.ApiErrorCode;
import com.jsut.classmanage.common.api.ApiResult;
import com.jsut.classmanage.model.vo.NoticeUserVo;
import com.jsut.classmanage.model.vo.NoticeVo;
import com.jsut.classmanage.model.vo.UserNoticeVo;
import com.jsut.classmanage.service.NoticeService;
import com.jsut.classmanage.service.StudentNoticeService;
import com.jsut.classmanage.util.PageUtils;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


import java.util.Objects;

import static com.jsut.classmanage.jwt.JwtUtil.USER_NAME;

/**
 * @className NoticeController
 **/
@Slf4j
@RestController
@RequestMapping("/api/notice")
@Api(tags = "通知接口")
public class NoticeController {

    @Resource
    private NoticeService noticeService;
    @Resource
    private StudentNoticeService studentNoticeService;


    /**
     * 分页查询当天通知信息，优先查询
     *
     * @param pageNo
     * @param size
     * @param userId
     * @return
     */
    @ApiOperation(value = "通知列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", required = false, example = "1"),
            @ApiImplicitParam(name = "size", required = false, example = "10")
    })
    @GetMapping("/queryPageByAccept")
    public ApiResult<PageUtils<UserNoticeVo>> queryTodayNoticeByPage(@RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                                                     @RequestParam(value = "size", defaultValue = "10") Integer size,
                                                                     @RequestHeader(value = USER_NAME) String userId) {

        log.info("查询当天通知信息入参,pageNo:{},size:{},accept:{},userId:{}", pageNo, size, userId);
        if (StringUtils.isEmpty(userId)) {
            return ApiResult.failed(ApiErrorCode.UNAUTHORIZED);
        }
        PageUtils<UserNoticeVo> pageUtils = noticeService.queryTodayNotice(pageNo, size, userId);

        return ApiResult.success(pageUtils);

    }


    @ApiOperation(value = "接受通知")
    @ApiParam(name = "noitceId", required = true, example = "10")
    @GetMapping("/acceptNotice")
    public ApiResult acceptNotice(@RequestParam Long noitceId,
                                  @RequestHeader(value = USER_NAME) String userId) {

        log.info("接受通知入参,noticeId:{},userId:{}", noitceId, userId);
        if (Objects.isNull(noitceId) || StringUtils.isEmpty(userId)) {
            return ApiResult.failed(ApiErrorCode.FAILED);
        }
        boolean flag = studentNoticeService.acceptNotice(noitceId, userId);

        return flag ? ApiResult.success() : ApiResult.failed(ApiErrorCode.FAILED);
    }


    @ApiOperation(value = "发布公告")
    @PostMapping("/publicNotice")
    public ApiResult publicNotice(@RequestBody NoticeVo noticeVo,
                                  @RequestHeader(value = USER_NAME) String userId) {

        log.info("发布公告入参：{}", JSONObject.toJSONString(noticeVo));
        if (Objects.isNull(noticeVo)) {
            return ApiResult.failed(ApiErrorCode.EMPTY_PARAM);
        }
        noticeVo.setUserId(userId);
        boolean flag = noticeService.addNotice(noticeVo);
        return flag ? ApiResult.success() : ApiResult.failed(ApiErrorCode.FAILED);
    }


    @ApiOperation(value = "通知列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", required = false, example = "1"),
            @ApiImplicitParam(name = "size", required = false, example = "10")
    })
    @GetMapping("/pageNotice")
    public ApiResult<PageUtils<NoticeVo>> pageNotice(@RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                                     @RequestParam(value = "size", defaultValue = "10") Integer size) {

        log.info("查询公告列表入参page:{},size:{}", pageNo, size);
        PageUtils<NoticeVo> pageUtils = noticeService.pageNotice(pageNo, size);

        return ApiResult.success(pageUtils);
    }

    @ApiOperation(value = "用户接受公告列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "noitceId", required = true, example = "1"),
            @ApiImplicitParam(name = "pageNo", required = false, example = "1"),
            @ApiImplicitParam(name = "size", required = false, example = "10")
    })
    @GetMapping("/acceptUser")
    public ApiResult<PageUtils<NoticeUserVo>> acceptUserList(@RequestParam Long noitceId,
                                                             @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                                             @RequestParam(value = "size", defaultValue = "10") Integer size) {

        log.info("接受公告用户列表param:{}", noitceId);
        if (Objects.isNull(noitceId)) {
            return ApiResult.failed(ApiErrorCode.EMPTY_PARAM);
        }
        PageUtils<NoticeUserVo> pageUtils = noticeService.pageUserNoticeInfo(pageNo, size, noitceId);
        return ApiResult.success(pageUtils);
    }


}
