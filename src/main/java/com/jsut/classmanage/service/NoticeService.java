package com.jsut.classmanage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jsut.classmanage.model.Notice;
import com.jsut.classmanage.model.vo.NoticeUserVo;
import com.jsut.classmanage.model.vo.NoticeVo;
import com.jsut.classmanage.model.vo.UserNoticeVo;
import com.jsut.classmanage.util.PageUtils;

import java.util.List;

/**
 * @className NoticeService
 **/
public interface NoticeService extends IService<Notice> {

    /**
     * 分页查询通知信息
     * @param pageNo
     * @param size
     * @param userId
     * @return
     */
    PageUtils<UserNoticeVo> queryTodayNotice(Integer pageNo, Integer size, String userId);

    /**
     * 发布公告
     * @param noticeVo
     * @return
     */
    boolean addNotice(NoticeVo noticeVo);

    /**
     * 分页查询所有公告
     * @param pageNo
     * @param size
     * @return
     */
    PageUtils<NoticeVo> pageNotice(Integer pageNo, Integer size);

    /**
     * 查询学生接受公告名单
     * @param pageNo
     * @param size
     * @param noitceId
     * @return
     */
    PageUtils<NoticeUserVo> pageUserNoticeInfo(Integer pageNo, Integer size, Long noitceId);
}
