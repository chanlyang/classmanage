package com.jsut.classmanage.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jsut.classmanage.common.YesOrNoEnum;
import com.jsut.classmanage.mapper.EpidemicInfoMapper;
import com.jsut.classmanage.mapper.StudentMapper;
import com.jsut.classmanage.model.EpidemicInfo;
import com.jsut.classmanage.model.Student;
import com.jsut.classmanage.model.vo.EpidmicResultVo;
import com.jsut.classmanage.model.vo.FillDailyVo;
import com.jsut.classmanage.service.EpidemicInfoService;
import com.jsut.classmanage.util.PageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @className EpidemicInfoServiceImpl
 **/
@Slf4j
@Service("epidemicInfoService")
public class EpidemicInfoServiceImpl extends ServiceImpl<EpidemicInfoMapper, EpidemicInfo> implements EpidemicInfoService {


    @Resource
    private EpidemicInfoMapper epidemicInfoMapper;
    @Resource
    private StudentMapper studentMapper;

    private volatile List<String> epidemicFileds = Lists.newCopyOnWriteArrayList();

    private static final DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    /**
     * 初始化疫情信息字段，默认全部有，可以修改
     */
    @PostConstruct
    public void init() {
        epidemicFileds.add("healthCode");
        epidemicFileds.add("isFever");
        epidemicFileds.add("isCough");
        epidemicFileds.add("otherDiscomfort");
        epidemicFileds.add("isNucleicAcid");
        epidemicFileds.add("vaccineNum");
        epidemicFileds.add("isOutSchool");
    }


    @Override
    public List<EpidmicResultVo> todayEpidemic() {

        LocalDateTime startTime = LocalDate.now().atStartOfDay();
        LocalDateTime endTime = LocalDate.now().plusDays(1).atStartOfDay();

        List<EpidemicInfo> epidemicInfos = epidemicInfoMapper.selectList(new QueryWrapper<EpidemicInfo>().lambda()
                .lt(EpidemicInfo::getCreateTime, endTime)
                .gt(EpidemicInfo::getCreateTime, startTime));

        List<EpidmicResultVo> result = Lists.newArrayList();
        epidemicFileds.forEach(it -> {
            switch (it) {
                case "healthCode":
                    EpidmicResultVo epidmicResultVo1 = new EpidmicResultVo();
                    epidmicResultVo1.setName("红码");
                    epidmicResultVo1.setTotal( String.valueOf(epidemicInfos.stream().filter(x -> x.getHealthCode() == EpidemicInfo.HealthCodeEnum.RED.getValue()).count()));
                    EpidmicResultVo epidmicResultVo2 = new EpidmicResultVo();
                    epidmicResultVo2.setName("黄码");
                    epidmicResultVo2.setTotal(String.valueOf(epidemicInfos.stream().filter(y -> y.getHealthCode() == EpidemicInfo.HealthCodeEnum.YELLOW.getValue()).count()));
                    EpidmicResultVo epidmicResultVo3 = new EpidmicResultVo();
                    epidmicResultVo3.setName("绿码");
                    epidmicResultVo3.setTotal(String.valueOf(epidemicInfos.stream().filter(z -> z.getHealthCode() == EpidemicInfo.HealthCodeEnum.GREEN.getValue()).count()));
                    result.add(epidmicResultVo1);
                    result.add(epidmicResultVo2);
                    result.add(epidmicResultVo3);
                    break;
                case "isFever":
                    EpidmicResultVo epidmicResultVo4 = new EpidmicResultVo();
                    epidmicResultVo4.setName("发烧人数");
                    epidmicResultVo4.setTotal( String.valueOf(epidemicInfos.stream().mapToInt(EpidemicInfo::getIsFever).sum()));
                    result.add(epidmicResultVo4);
                    break;
                case "isCough":
                    EpidmicResultVo epidmicResultVo5 = new EpidmicResultVo();
                    epidmicResultVo5.setName("咳嗽人数");
                    epidmicResultVo5.setTotal(String.valueOf(epidemicInfos.stream().mapToInt(EpidemicInfo::getIsCough).sum()));
                    result.add(epidmicResultVo5);
                    break;
                case "otherDiscomfort":
                    EpidmicResultVo epidmicResultVo6 = new EpidmicResultVo();
                    epidmicResultVo6.setName("其他不适人数");
                    epidmicResultVo6.setTotal(String.valueOf(epidemicInfos.stream().mapToInt(EpidemicInfo::getOtherDiscomfort).sum()));
                    result.add(epidmicResultVo6);
                    break;
                case "isNucleicAcid":
                    EpidmicResultVo epidmicResultVo7 = new EpidmicResultVo();
                    epidmicResultVo7.setName("今日做核酸人数");
                    epidmicResultVo7.setTotal(String.valueOf(epidemicInfos.stream().mapToInt(EpidemicInfo::getIsNucleicAcid).sum()));
                    result.add(epidmicResultVo7);
                    break;
                case "vaccineNum":
                    EpidmicResultVo epidmicResultVo8 = new EpidmicResultVo();
                    epidmicResultVo8.setName("本班接种疫苗数");
                    epidmicResultVo8.setTotal( String.valueOf(epidemicInfos.stream().mapToInt(EpidemicInfo::getVaccineNum).sum()));
                    result.add(epidmicResultVo8);
                    break;
                case "isOutSchool":
                    EpidmicResultVo epidmicResultVo9 = new EpidmicResultVo();
                    epidmicResultVo9.setName("未在校人数");
                    epidmicResultVo9.setTotal(String.valueOf(epidemicInfos.stream().mapToInt(EpidemicInfo::getIsOutSchool).sum()));
                    result.add(epidmicResultVo9);
                    break;
                default:
                    log.error("没找到疫情统计类型");
            }
        });
        return result;
    }

    @Override
    public void publicTodayEpidemic(List<String> fileds) {
        epidemicFileds.forEach(it -> {
            epidemicFileds.remove(it);
        });
        epidemicFileds.addAll(fileds);
    }

    @Override
    public Integer todayException() {

        LocalDateTime startTime = LocalDate.now().atStartOfDay();
        LocalDateTime endTime = LocalDate.now().plusDays(1).atStartOfDay();

        List<EpidemicInfo> epidemicInfos = epidemicInfoMapper.selectList(new QueryWrapper<EpidemicInfo>().lambda()
                .lt(EpidemicInfo::getCreateTime, endTime)
                .gt(EpidemicInfo::getCreateTime, startTime));

        Long num = epidemicInfos.stream().filter(x -> x.getHealthCode() == EpidemicInfo.HealthCodeEnum.RED.getValue()).count();
        return num.intValue();
    }

    @Override
    public void fillDaily(EpidemicInfo epidemicInfo) {
        log.info("每日填报:{}", JSONObject.toJSONString(epidemicInfo));
        epidemicInfoMapper.insert(epidemicInfo);
    }

    @Override
    public PageUtils<FillDailyVo> queryFillDaily(Integer healthCode, Integer isOutSchool, Integer noFill, Integer pageNo, Integer size) {

        LocalDateTime startTime = LocalDate.now().atStartOfDay();
        LocalDateTime endTime = LocalDate.now().plusDays(1).atStartOfDay();

        List<EpidemicInfo> epidemicInfos = epidemicInfoMapper.selectList(new QueryWrapper<EpidemicInfo>().lambda()
                .lt(EpidemicInfo::getCreateTime, endTime)
                .gt(EpidemicInfo::getCreateTime, startTime));

        List<String> userIds = Lists.transform(epidemicInfos, it -> it.getUserId());

        Page<Student> page = new Page<>(pageNo, size);
        IPage<Student> studentIPage = studentMapper.selectPage(page, new QueryWrapper<Student>().lambda().eq(Student::getIsDel, YesOrNoEnum.NO.getValue()));


        List<FillDailyVo> result = Lists.transform(studentIPage.getRecords(), it -> {
            FillDailyVo fillDailyVo = new FillDailyVo();
            fillDailyVo.setUserId(it.getUserId());
            fillDailyVo.setUserName(it.getUserName());
            EpidemicInfo epidemicInfo = epidemicInfos.stream().filter(v -> v.getUserId().equals(it.getUserId())).findAny().orElse(null);
            if (Objects.nonNull(epidemicInfo)) {
                fillDailyVo.setHealthCode(epidemicInfo.getHealthCode());
                fillDailyVo.setIsFever(epidemicInfo.getIsFever());
                fillDailyVo.setIsCough(epidemicInfo.getIsCough());
                fillDailyVo.setOtherDiscomfort(epidemicInfo.getOtherDiscomfort());
                fillDailyVo.setIsNucleicAcid(epidemicInfo.getIsNucleicAcid());
                fillDailyVo.setVaccineNum(epidemicInfo.getVaccineNum());
                fillDailyVo.setIsOutSchool(epidemicInfo.getIsOutSchool());
                fillDailyVo.setOtherThings(epidemicInfo.getOtherThings());
                fillDailyVo.setCreateTime(df.format(epidemicInfo.getCreateTime().toLocalDateTime()));

            }
            return fillDailyVo;
        });
        if (Objects.nonNull(healthCode)) {
            result = result.stream().filter(it -> it.getHealthCode().equals(healthCode)).collect(Collectors.toList());
        }
        if (Objects.nonNull(isOutSchool)) {
            result = result.stream().filter(it -> it.getIsOutSchool().equals(isOutSchool)).collect(Collectors.toList());
        }
        if (Objects.nonNull(noFill)) {
            result = result.stream().filter(it -> !userIds.contains(it.getUserId())).collect(Collectors.toList());
        }

        return new PageUtils<FillDailyVo>(result, Long.valueOf(studentIPage.getTotal()).intValue(), Long.valueOf(studentIPage.getSize()).intValue(), Long.valueOf(studentIPage.getCurrent()).intValue());
    }
}
