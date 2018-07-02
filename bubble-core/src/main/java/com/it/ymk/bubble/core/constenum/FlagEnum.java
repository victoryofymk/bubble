package com.it.ymk.bubble.core.constenum;

import org.springframework.context.annotation.Description;

/**
 * 枚举
 *
 * @author yanmingkun
 * @date 2018-01-03 16:02
 */
@Description("FLAG枚举值")
public enum FlagEnum {
                      Y("1", "是"), N("0", "否"),
                      ;

    private String code;

    private String desc;

    FlagEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static FlagEnum getByCode(String code) {
        for (FlagEnum flagEnum : FlagEnum.values()) {
            if (flagEnum.getCode().equals(code)) {
                return flagEnum;
            }

        }
        return null;
    }

    public static FlagEnum getByDesc(String desc) {
        for (FlagEnum flagEnum : FlagEnum.values()) {
            if (flagEnum.getDesc().equals(desc)) {
                return flagEnum;
            }
        }
        return null;
    }

}
