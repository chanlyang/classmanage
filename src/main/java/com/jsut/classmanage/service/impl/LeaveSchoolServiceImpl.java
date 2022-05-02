package com.jsut.classmanage.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.update.UpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.jsut.classmanage.common.YesOrNoEnum;
import com.jsut.classmanage.common.exception.ApiAsserts;
import com.jsut.classmanage.mapper.LeaveSchoolMapper;
import com.jsut.classmanage.mapper.StudentMapper;
import com.jsut.classmanage.mapper.TecherMapper;
import com.jsut.classmanage.model.LeaveSchool;
import com.jsut.classmanage.model.Student;
import com.jsut.classmanage.model.Techer;
import com.jsut.classmanage.model.vo.ApplyLeaveSchool;
import com.jsut.classmanage.model.vo.MyApplyVo;
import com.jsut.classmanage.service.LeaveSchoolService;
import com.jsut.classmanage.util.PageUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

/**
 * @className LeaveSchoolServiceImpl
 **/
@Slf4j
@Service("leaveSchoolService")
public class LeaveSchoolServiceImpl extends ServiceImpl<LeaveSchoolMapper, LeaveSchool> implements LeaveSchoolService {


    @Resource
    private LeaveSchoolMapper leaveSchoolMapper;
    @Resource
    private StudentMapper studentMapper;
    @Resource
    private TecherMapper techerMapper;


    private static final DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    @Override
    public void applyLeaveSchool(ApplyLeaveSchool applyLeaveSchool) {
        log.info("请假入参:{}", JSONObject.toJSONString(applyLeaveSchool));
        LeaveSchool leaveSchool = LeaveSchool.builder()
                .isLeaveSchool(YesOrNoEnum.YES.getValue())
                .currStatus(LeaveSchool.LeaveStatus.DEFAULT.getValue())
                .userId(applyLeaveSchool.getUserId())
                .leaveReason(applyLeaveSchool.getReason())
                .leaveTime(Integer.parseInt(applyLeaveSchool.getApplyLongTime()))
                .startTime(Timestamp.valueOf(applyLeaveSchool.getStartTime()))
                .endTime(Timestamp.valueOf(applyLeaveSchool.getEndTime()))
                .build();

        leaveSchoolMapper.insert(leaveSchool);
    }


    @Override
    public PageUtils<MyApplyVo> queryMyApply(String userId, Integer pageNo, Integer size) {

        Page<LeaveSchool> page = new Page<>(pageNo, size);
        IPage<LeaveSchool> leaveSchoolIPage = leaveSchoolMapper.selectPage(page, new QueryWrapper<LeaveSchool>().lambda()
                .eq(LeaveSchool::getUserId, userId)
                .orderByDesc(LeaveSchool::getCreateTime));

        Student student = studentMapper.selectOne(new QueryWrapper<Student>().lambda()
                .eq(Student::getUserId, userId));
        if (Objects.isNull(student)) {
            ApiAsserts.fail("无用户信息");
        }

        List<MyApplyVo> result = Lists.transform(page.getRecords(), it -> {
            MyApplyVo myApplyVo = new MyApplyVo();
            myApplyVo.setUserId(it.getUserId());
            myApplyVo.setName(student.getUserName());
            myApplyVo.setStartTime(df.format(it.getStartTime().toLocalDateTime()));
            myApplyVo.setEndTime(df.format(it.getEndTime().toLocalDateTime()));
            LeaveSchool.LeaveStatus leaveStatus = LeaveSchool.LeaveStatus.getByValue(it.getCurrStatus());
            myApplyVo.setStatus(leaveStatus.getDesc());
            if (StringUtils.isNoneBlank(it.getTecherUserId())) {
                Techer techer = techerMapper.selectOne(new QueryWrapper<Techer>().lambda()
                        .eq(Techer::getUserId, it.getTecherUserId()));
                if (Objects.nonNull(techer)) {
                    myApplyVo.setUserName(techer.getUserName());
                }
            }
            return myApplyVo;
        });
        return new PageUtils<MyApplyVo>(result, Long.valueOf(leaveSchoolIPage.getTotal()).intValue(), Long.valueOf(leaveSchoolIPage.getSize()).intValue(), Long.valueOf(leaveSchoolIPage.getCurrent()).intValue());
    }

    @Override
    public PageUtils<MyApplyVo> pageList(String userId, Integer pageNo, Integer size) {

        Page<LeaveSchool> page = new Page<>(pageNo, size);
        IPage<LeaveSchool> leaveSchoolIPage = leaveSchoolMapper.selectPage(page, new QueryWrapper<LeaveSchool>().lambda()
                .orderByDesc(LeaveSchool::getCreateTime));


        List<MyApplyVo> result = Lists.transform(leaveSchoolIPage.getRecords(), it -> {
            MyApplyVo myApplyVo = new MyApplyVo();
            myApplyVo.setUserId(it.getUserId());
            Student student = studentMapper.selectOne(new QueryWrapper<Student>().lambda()
                    .eq(Student::getUserId, it.getUserId()));
            myApplyVo.setName(Objects.nonNull(student) ? student.getUserName() : "");
            myApplyVo.setStartTime(df.format(it.getStartTime().toLocalDateTime()));
            myApplyVo.setEndTime(df.format(it.getEndTime().toLocalDateTime()));
            myApplyVo.setReason(it.getLeaveReason());
            myApplyVo.setStatus(String.valueOf(it.getCurrStatus()));
            return myApplyVo;
        });

        return new PageUtils<MyApplyVo>(result, Long.valueOf(leaveSchoolIPage.getTotal()).intValue(), Long.valueOf(leaveSchoolIPage.getSize()).intValue(), Long.valueOf(leaveSchoolIPage.getCurrent()).intValue());

    }

    @Override
    public void approval(String userId, Integer status, Long leaveId) {
        log.info("审批请假,userId:{},status:{},leaveId:{}", userId, status, leaveId);

        LeaveSchool leaveSchool = leaveSchoolMapper.selectById(leaveId);
        if (Objects.isNull(leaveSchool)) {
            ApiAsserts.fail("无请假信息");
        }
        if (status > 2 || status < 0) {
            ApiAsserts.fail("无此操作");
        }

        LambdaUpdateWrapper<LeaveSchool> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(LeaveSchool::getId, leaveSchool.getId());
        wrapper.set(LeaveSchool::getCurrStatus, status);

        this.update(wrapper);
    }
}
