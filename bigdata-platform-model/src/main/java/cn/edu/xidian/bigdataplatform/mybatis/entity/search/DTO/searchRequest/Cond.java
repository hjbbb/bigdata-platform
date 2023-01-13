package cn.edu.xidian.bigdataplatform.mybatis.entity.search.DTO.searchRequest;

import lombok.Data;

@Data
public class Cond {
    public String fields;
    public String option;
    public String content;//???
    public String conj;
}
