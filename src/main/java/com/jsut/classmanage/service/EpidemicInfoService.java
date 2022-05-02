package com.jsut.classmanage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jsut.classmanage.common.api.ApiResult;
import com.jsut.classmanage.model.EpidemicInfo;
import com.jsut.classmanage.model.vo.EpidmicResultVo;
import com.jsut.classmanage.model.vo.FillDailyNewVo;
import com.jsut.classmanage.model.vo.FillDailyVo;
import com.jsut.classmanage.util.PageUtils;

import java.util.List;
import java.util.Map;

/**
 * @className EpidemicInfoService
 **/
public interface EpidemicInfoService extends IService<EpidemicInfo> {
    /**
     * 今日疫情
     * @return
     */
    List<EpidmicResultVo> todayEpidemic();

    /**
     * 发布疫情信息
     * @param fileds
     */
    void publicTodayEpidemic(List<String> fileds);

    /**
     * 红码人数
     * @return
     */
    Integer todayException();

    /**
     * 学生每日填报
     * @param epidemicInfo
     */
    void fillDaily(EpidemicInfo epidemicInfo);

    /**
     * 查询每日填报
     * @param healthCode
     * @param isOutSchool
     * @param noFill
     * @param pageNo
     * @param size
     * @return
     */
    PageUtils<FillDailyNewVo> queryFillDaily(Integer healthCode, Integer isOutSchool, Integer noFill, Integer pageNo, Integer size);
}
