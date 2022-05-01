package com.jsut.classmanage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jsut.classmanage.model.AntiFraud;
import com.jsut.classmanage.model.vo.AntiFraudVo;
import com.jsut.classmanage.model.vo.NoticeUserVo;
import com.jsut.classmanage.util.PageUtils;

/**
 * @className AntiFraudService
 **/
public interface AntiFraudService extends IService<AntiFraud> {

    /**
     * 学习反诈
     * @return
     */
    AntiFraudVo queryTodayInfo(String userId);

    /**
     * 反诈学习打卡
     *
     * @param anitId
     * @param userId
     */
    void userPunch(Long anitId, String userId);

    /**
     * 发布反诈信息
     * @param antiFraud
     */
    void publishAntiInfo(AntiFraud antiFraud);

    /**
     * 反诈信息列表
      * @param pageNo
     * @param size
     * @return
     */
    PageUtils<AntiFraudVo> pageList(Integer pageNo, Integer size);

    /**
     * 学生学习打卡表
     * @param pageNo
     * @param size
     * @param antiId
     * @return
     */
    PageUtils<NoticeUserVo> pageUserAntiInfo(Integer pageNo, Integer size, Long antiId);
}
