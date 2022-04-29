package com.jsut.classmanage.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @className YesOrNoEnum
 **/
@Getter
@AllArgsConstructor
public enum YesOrNoEnum {

    YES(1, "是"),
    NO(0, "否");

    private int value;
    private String desc;


}
