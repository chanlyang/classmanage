package com.jsut.classmanage.model.vo;

import lombok.Data;


/**
 * @className AntiFraudVo
 **/
@Data
public class AntiFraudVo {

    private Long id;

    /**
     * 标题
     */
    private String title;
    /**
     * 反诈内容
     */
    private String content;

    /**
     * 发布人ID
     */
    private String userId;

    private String createTime;

    private Integer isPunch;
}
