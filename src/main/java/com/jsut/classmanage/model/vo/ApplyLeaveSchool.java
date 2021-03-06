package com.jsut.classmanage.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * @className ApplyLeaveSchool
 **/
@Data
@ApiModel(description = "申请请假参数")
public class ApplyLeaveSchool {
    private String userId;
    @ApiModelProperty(name = "原因", example = "休假")
    private String reason;
    @ApiModelProperty(name = "时长", example = "2")
    private String applyLongTime;
    @ApiModelProperty(name = "开始时间", example = "2022-05-03 00:00:00")
    @JsonFormat(shape = JsonFormat.Shape.STRING,timezone = "GMT+8")
    private LocalDateTime  startTime;
    @ApiModelProperty(name = "结束时间", example = "2022-05-05 00:00:00")
    @JsonFormat(shape = JsonFormat.Shape.STRING,timezone = "GMT+8")
    private LocalDateTime endTime;
}
