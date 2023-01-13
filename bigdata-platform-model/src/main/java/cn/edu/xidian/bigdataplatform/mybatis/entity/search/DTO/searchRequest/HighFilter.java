package cn.edu.xidian.bigdataplatform.mybatis.entity.search.DTO.searchRequest;

import lombok.Data;

@Data
public class HighFilter {
    public String[] indices;
    public Cond[] conds;
}
