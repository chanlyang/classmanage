package com.jsut.classmanage.model.vo;

import lombok.Data;
import org.springframework.scheduling.support.SimpleTriggerContext;

/**
 * @className UserNoticeVo
 **/
@Data
public class UserNoticeVo {

    private Long noticeId;
    private String userId;
    private String userName;
    private String title;
    private String content;
    private String createTime;
    private Integer isRead;
}
