package cn.edu.xidian.bigdataplatform.mybatis.utils;

import cn.edu.xidian.bigdataplatform.mybatis.entity.usersource.BaseCodeEnum;
import org.springframework.lang.Nullable;

/**
 * 把code转化为对应的枚举
 */
public class CodeEnumUtil {
    @Nullable
    public static <E extends Enum<?> & BaseCodeEnum> E codeOf(Class<E> enumClass, int code) {
        E[] enumConstants = enumClass.getEnumConstants();
        for (E e : enumConstants) {
            if (e.getCode() == code) {
                return e;
            }
        }
        return null;
    }
}
