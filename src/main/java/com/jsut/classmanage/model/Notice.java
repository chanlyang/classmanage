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
 * @className Notice
 * 通知表发布表
 **/
@Data
@Builder
@Accessors(chain = true)
@TableName("notice")
@NoArgsConstructor
@AllArgsConstructor
public class Notice {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 发布人ID，老师编号
     */
    private String userId;

    /**
     * 通知标题
     */
    private String title;

    /**
     * 通知内容
     */
    private String content;

    private Integer isDel;
    private Timestamp createTime;
    private Timestamp updateTime;

}
