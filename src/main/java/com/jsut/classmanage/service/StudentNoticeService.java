package com.jsut.classmanage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jsut.classmanage.model.StudentNotice;

/**
 * @className StudentNoticeService
 **/
public interface StudentNoticeService extends IService<StudentNotice> {
    /**
     * 学生
     * @param noitceId
     * @param userId
     * @return
     */
    boolean acceptNotice(Long noitceId, String userId);

    /**
     * 删除通知
     * @param noitceId
     * @return
     */
    boolean deleteNotice(Long noitceId);
}
