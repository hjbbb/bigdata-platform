package cn.edu.xidian.bigdataplatform.bean;

import java.util.HashMap;

/**
 * @author: Zhou Linxuan
 * @create: 2023-01-09
 * @description:
 */
public class SpiderTableRelation {
    public static HashMap<String, String> spiderTableRelation = new HashMap<>();
    static {
        spiderTableRelation.put("broker", "broker");
        spiderTableRelation.put("logistics", "logistics");
        spiderTableRelation.put("driver", "car");
        spiderTableRelation.put("inter", "air_source");
        spiderTableRelation.put("Wutong", "Wutong");
    }

}
