package com.jsut.classmanage.model.vo;

import lombok.Data;

/**
 * @className FillDailyNewVo
 **/
@Data
public class FillDailyNewVo {

    /**
     * 学生学号
     */
    private String userId;

    private String userName;
    /**
     * 健康码状态, 1- 绿码，2- 黄码， 3- 红码
     */
    private String healthCode;

    /**
     * 是否发烧
     */
    private String isFever;

    /**
     * 是否咳嗽
     */
    private String isCough;

    /**
     * 其他不适
     */
    private String otherDiscomfort;

    /**
     * 是否核酸
     */
    private String isNucleicAcid;

    /**
     * 疫苗接种次数
     */
    private String vaccineNum;

    /**
     * 是否出校
     */
    private String isOutSchool;

    /**
     * 其他情况
     */
    private String otherThings;

    private String createTime;
}
