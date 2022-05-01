package com.jsut.classmanage.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.Accessors;

import java.sql.Timestamp;
import java.util.Objects;

/**
 * @className LeaveSchool
 * 请假表
 **/
@Data
@Builder
@Accessors(chain = true)
@TableName("leave_school")
@NoArgsConstructor
@AllArgsConstructor
public class LeaveSchool {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 学生学号
     */
    private String userId;

    /**
     * 请假时长，单位天
     */
    private Integer leaveTime;

    /**
     * 请假理由
     */
    private String leaveReason;

    /**
     * 是否离校
     */
    private Integer isLeaveSchool;

    /**
     * 开始时间
     */
    private Timestamp startTime;

    /**
     * 结束时间
     */
    private Timestamp endTime;

    /**
     * 请假状态
     */
    private Integer currStatus;

    /**
     * 审核人ID，教师ID
     */
    private String techerUserId;


    private Integer isDel;
    private Timestamp createTime;
    private Timestamp updateTime;


    @AllArgsConstructor
    @Getter
    public enum LeaveStatus {

        DEFAULT(0, "待审批"),
        ACCESS(1, "通过"),
        REFUSE(2, "拒绝");

        private int value;
        private String desc;


        public static LeaveStatus getByValue(Integer value) {
            if (Objects.isNull(value)) {
                return DEFAULT;
            }
            for (LeaveStatus it : LeaveStatus.values()) {
                if (it.getValue() == value) {
                    return it;
                }
            }
            return DEFAULT;
        }
    }

}
