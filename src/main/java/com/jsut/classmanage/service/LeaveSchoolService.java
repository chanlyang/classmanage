package com.jsut.classmanage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jsut.classmanage.model.LeaveSchool;
import com.jsut.classmanage.model.vo.ApplyLeaveSchool;
import com.jsut.classmanage.model.vo.MyApplyVo;
import com.jsut.classmanage.util.PageUtils;

/**
 * @className LeaveSchoolService
 **/
public interface LeaveSchoolService extends IService<LeaveSchool> {
    /**
     * 发起请假申请
     * @param applyLeaveSchool
     */
    void applyLeaveSchool(ApplyLeaveSchool applyLeaveSchool);

    /**
     * 查询我的请假信息
     * @param userId
     * @param pageNo
     * @param size
     * @return
     */
    PageUtils<MyApplyVo> queryMyApply(String userId, Integer pageNo, Integer size);

    /**
     * 学生请假列表
     * @param userId
     * @param pageNo
     * @param size
     * @return
     */
    PageUtils<MyApplyVo> pageList(String userId, Integer pageNo, Integer size);

    /**
     * 审批
     * @param userId
     * @param status
     * @param leaveId
     */
    void approval(String userId, Integer status, Long leaveId);
}
