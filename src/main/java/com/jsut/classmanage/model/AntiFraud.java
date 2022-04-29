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
 * @className AntiFraud
 * 反诈信息表
 **/
@Data
@Builder
@Accessors(chain = true)
@TableName("anti_fraud")
@NoArgsConstructor
@AllArgsConstructor
public class AntiFraud {

    @TableId(type = IdType.AUTO)
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

    private Integer isDel;
    private Timestamp createTime;
    private Timestamp updateTime;

}
