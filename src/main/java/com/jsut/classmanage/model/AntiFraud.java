package com.jsut.classmanage.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(description = "发布反诈信息")
public class AntiFraud {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 标题
     */
    @ApiModelProperty(value = "标题",example = "反诈1")
    private String title;
    /**
     * 反诈内容
     */
    @ApiModelProperty(value = "反诈内容，富文本")
    private String content;

    /**
     * 发布人ID
     */
    private String userId;

    private Integer isDel;
    private Timestamp createTime;
    private Timestamp updateTime;

}
