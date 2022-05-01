package com.jsut.classmanage.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @className NoticeVo
 **/
@Data
@ApiModel(description = "发布公告参数")
public class NoticeVo {

    private Long id;
    private String userId;
    @ApiModelProperty(name = "标题",example = "dsds")
    private String title;
    @ApiModelProperty(name = "内容",example = "dsdeewe")
    private String content;
    private String createTime;
}
