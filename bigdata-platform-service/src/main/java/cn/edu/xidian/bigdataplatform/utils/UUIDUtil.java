package cn.edu.xidian.bigdataplatform.utils;

import org.springframework.lang.NonNull;

import java.util.UUID;

/**
 * @author: Zhou Linxuan
 * @create: 2022-10-03
 * @description: 生成UUID
 */
public class UUIDUtil {
    @NonNull
    public static String genUUID() {
        UUID uuId = UUID.randomUUID();
        return uuId.toString();
    }
}
