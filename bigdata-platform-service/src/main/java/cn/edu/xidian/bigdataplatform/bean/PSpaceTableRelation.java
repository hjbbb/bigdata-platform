package cn.edu.xidian.bigdataplatform.bean;

import java.util.HashMap;

/**
 * @author: Zhou Linxuan
 * @create: 2023-01-12
 * @description:
 */
public class PSpaceTableRelation {
    public static HashMap<String, String> pSpaceTypeTableMap = new HashMap<>();
    public static HashMap<String, String> pSpaceTableTypeMap = new HashMap<>();

    static {
        pSpaceTypeTableMap.put("point", "point");
        pSpaceTypeTableMap.put("realDynamic", "realDynamicData");
        pSpaceTypeTableMap.put("history", "HisDynamicData");
        pSpaceTypeTableMap.put("realAlarm", "realAlarmData");
        pSpaceTypeTableMap.put("hisAlarm", "HisAlarmData");


        pSpaceTableTypeMap.put("point", "point");
        pSpaceTableTypeMap.put("realDynamicData", "realDynamic");
        pSpaceTableTypeMap.put("HisDynamicData", "history");
        pSpaceTableTypeMap.put("realAlarmData", "realAlarm");
        pSpaceTableTypeMap.put("HisAlarmData", "hisAlarm");

    }
}
