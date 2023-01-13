package cn.edu.xidian.bigdataplatform.mybatis.entity.search;
import lombok.Data;

@Data
public class WutongSource {
    private String indexName;
    public String depart;
    public String destination;
    public String distance;
    public String published;
    public String freightDescription;
    public String freightType;
    public String transportType;
    public String validity;
    public String remark;
    public String weight;
    public String volume;
    public String contact;
    public String contactNumber;
    public String crawlTime;
}
