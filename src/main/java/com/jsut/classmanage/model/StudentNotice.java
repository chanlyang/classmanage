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
 * @className StudentNotice
 *
 * 学生接受公告表
 **/
@Data
@Builder
@Accessors(chain = true)
@TableName("student_notice")
@NoArgsConstructor
@AllArgsConstructor
public class StudentNotice {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 学生学号
     */
    private String userId;

    /**
     * 公告id
     */
    private String noticeId;

    /**
     * 通知标题
     */
    private String title;

    private Integer isDel;
    private Timestamp createTime;
    private Timestamp updateTime;

}
