package com.jsut.classmanage.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.Accessors;

import java.sql.Timestamp;

/**
 * @className EpidemicInfo
 * 学生疫情信息填报表
 **/
@Data
@Builder
@Accessors(chain = true)
@TableName("epidemic_info")
@NoArgsConstructor
@AllArgsConstructor
public class EpidemicInfo {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 学生学号
     */
    private String userId;
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


    private Integer isDel;
    private Timestamp createTime;
    private Timestamp updateTime;




    @AllArgsConstructor
    @Getter
    public enum HealthCodeEnum {
        GREEN(1, "绿码"),
        YELLOW(2, "黄码"),
        RED(3, "红码");

        private int value;
        private String desc;

    }

}
