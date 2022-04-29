package com.jsut.classmanage.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.sql.Timestamp;

/**
 * @className Punch
 * 学生打卡表
 **/
@Data
@Builder
@Accessors(chain = true)
@TableName("punch")
@NoArgsConstructor
@AllArgsConstructor
public class Punch {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 学生学号
     */
    private String userId;
    /**
     * 反诈信息id
     */
    private Long antiFraudId;

    private Integer isDel;
    private Timestamp createTime;
    private Timestamp updateTime;


}
