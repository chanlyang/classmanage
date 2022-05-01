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
    public Map<String, String> todayEpidemic() {

        LocalDateTime startTime = LocalDate.now().atStartOfDay();
        LocalDateTime endTime = LocalDate.now().plusDays(1).atStartOfDay();

        List<EpidemicInfo> epidemicInfos = epidemicInfoMapper.selectList(new QueryWrapper<EpidemicInfo>().lambda()
                .lt(EpidemicInfo::getCreateTime, endTime)
                .gt(EpidemicInfo::getCreateTime, startTime));

        Map<String, String> result = Maps.newHashMap();
        epidemicFileds.forEach(it -> {
            switch (it) {
                case "healthCode":
                    result.put("red", String.valueOf(epidemicInfos.stream().filter(x -> x.getHealthCode() == EpidemicInfo.HealthCodeEnum.RED.getValue()).count()));
                    result.put("yellow", String.valueOf(epidemicInfos.stream().filter(y -> y.getHealthCode() == EpidemicInfo.HealthCodeEnum.RED.getValue()).count()));
                    result.put("green", String.valueOf(epidemicInfos.stream().filter(z -> z.getHealthCode() == EpidemicInfo.HealthCodeEnum.RED.getValue()).count()));
                    break;
                case "isFever":
                    result.put(it, String.valueOf(epidemicInfos.stream().mapToInt(EpidemicInfo::getIsFever).sum()));
                    break;
                case "isCough":
                    result.put(it, String.valueOf(epidemicInfos.stream().mapToInt(EpidemicInfo::getIsCough).sum()));
                    break;
                case "otherDiscomfort":
                    result.put(it, String.valueOf(epidemicInfos.stream().mapToInt(EpidemicInfo::getOtherDiscomfort).sum()));
                    break;
                case "isNucleicAcid":
                    result.put(it, String.valueOf(epidemicInfos.stream().mapToInt(EpidemicInfo::getIsNucleicAcid).sum()));
                    break;
                case "vaccineNum":
                    result.put(it, String.valueOf(epidemicInfos.stream().mapToInt(EpidemicInfo::getVaccineNum).sum()));
                    break;
                case "isOutSchool":
                    result.put(it, String.valueOf(epidemicInfos.stream().mapToInt(EpidemicInfo::getIsOutSchool).sum()));
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
