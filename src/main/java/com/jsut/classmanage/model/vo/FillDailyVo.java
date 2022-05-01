package com.jsut.classmanage.model.vo;

import lombok.Data;

/**
 * @className FillDailyVo
 **/
@Data
public class FillDailyVo {
    /**
     * 学生学号
     */
    private String userId;

    private String userName;
    /**
     * 健康码状态, 1- 绿码，2- 黄码， 3- 红码
     */
    private Integer healthCode;

    /**
     * 是否发烧
     */
    private Integer isFever;

    /**
     * 是否咳嗽
     */
    private Integer isCough;

    /**
     * 其他不适
     */
    private Integer otherDiscomfort;

    /**
     * 是否核酸
     */
    private Integer isNucleicAcid;

    /**
     * 疫苗接种次数
     */
    private Integer vaccineNum;

    /**
     * 是否出校
     */
    private Integer isOutSchool;

    /**
     * 其他情况
     */
    private String otherThings;

    private String createTime;
}
