package com.jsut.classmanage.controller;

import com.alibaba.fastjson.JSONObject;
import com.jsut.classmanage.common.api.ApiErrorCode;
import com.jsut.classmanage.common.api.ApiResult;
import com.jsut.classmanage.model.EpidemicInfo;
import com.jsut.classmanage.model.vo.EpidmicResultVo;
import com.jsut.classmanage.model.vo.FillDailyVo;
import com.jsut.classmanage.service.EpidemicInfoService;
import com.jsut.classmanage.util.PageUtils;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.jsut.classmanage.jwt.JwtUtil.USER_NAME;

/**
 * @className EpidemicController
 **/
@Api(tags = "疫情信息接口")
@Slf4j
@RestController
@RequestMapping("/api/epidemic")
public class EpidemicController {


    @Resource
    private EpidemicInfoService epidemicInfoService;


    @ApiOperation(value = "今日疫情")
    @GetMapping("/todayInfo")
    public ApiResult<List<EpidmicResultVo>> todayEpidemic() {
        List<EpidmicResultVo> result = epidemicInfoService.todayEpidemic();
        return ApiResult.success(result);
    }

    @ApiOperation(value = "发布疫情字段")
    @ApiParam(name = "files", required = true, example = "['healthCode','isFreve']")
    @PostMapping("/publishTodayEpidemic")
    public ApiResult publishTodayEpidemic(@RequestBody List<String> fileds) {
        log.info("发布疫情信息入参:{}", JSONObject.toJSONString(fileds));
        epidemicInfoService.publicTodayEpidemic(fileds);
        return ApiResult.success();
    }

    @ApiOperation(value = "今日异常疫情")
    @GetMapping("/todayException")
    public ApiResult<Integer> todayException() {
        Integer num = epidemicInfoService.todayException();
        return ApiResult.success(num);
    }

    @ApiOperation(value = "每日填报")
    @PostMapping("/fillDaily")
    public ApiResult fillDaily(@RequestBody EpidemicInfo epidemicInfo,
                               @RequestHeader(value = USER_NAME) String userId) {
        log.info("每日填报入参：{},userId:{}", JSONObject.toJSONString(epidemicInfo), userId);
        if (Objects.isNull(epidemicInfo)) {
            return ApiResult.failed(ApiErrorCode.EMPTY_PARAM);
        }
        epidemicInfo.setUserId(userId);
        epidemicInfoService.fillDaily(epidemicInfo);
        return ApiResult.success();
    }

    @ApiOperation(value = "查询每日填报，可以条件查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "healthCode", required = false, example = "2"),
            @ApiImplicitParam(name = "isOutSchool", required = false, example = "1"),
            @ApiImplicitParam(name = "noFill", required = false, example = "1"),
            @ApiImplicitParam(name = "pageNo", required = false, example = "1"),
            @ApiImplicitParam(name = "size", required = false, example = "10")
    })
    @GetMapping("/queryFillDaily")
    public ApiResult<PageUtils<FillDailyVo>> queryFillDaily(@RequestParam(value = "healthCode", required = false) Integer healthCode,
                                                            @RequestParam(value = "isOutSchool", required = false) Integer isOutSchool,
                                                            @RequestParam(value = "noFill", required = false) Integer noFill,
                                                            @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                                            @RequestParam(value = "size", defaultValue = "10") Integer size) {

        log.info("查询每日填报入参，healthCode:{},isOutSchool:{},nofill:{}", healthCode, isOutSchool, noFill);
        PageUtils<FillDailyVo> pageUtils = epidemicInfoService.queryFillDaily(healthCode, isOutSchool, noFill, pageNo, size);
        return ApiResult.success(pageUtils);
    }


}
