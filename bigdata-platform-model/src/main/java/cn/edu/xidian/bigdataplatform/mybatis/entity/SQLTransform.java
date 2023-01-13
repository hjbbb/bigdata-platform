package cn.edu.xidian.bigdataplatform.mybatis.entity;

import com.typesafe.config.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class SQLTransform extends Transform {
    private String sourceTableName;
    private String sql;
    private String resultTableName;

    public SQLTransform(String source, String sql, String result) {
        this.transformConfigName = "Sql";
        this.sourceTableName = source;
        this.sql = sql;
        this.resultTableName = result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"sourceTableName\":\"")
                .append(sourceTableName).append('\"');
        sb.append(",\"sql\":\"")
                .append(sql).append('\"');
        sb.append(",\"resultTableName\":\"")
                .append(resultTableName).append('\"');
        sb.append('}');
        return sb.toString();
    }

    public String toConfig(int indent) {
        final StringBuilder sb = new StringBuilder();
        sb.append(StringUtils.repeat("\t", indent - 1));
        sb.append(this.transformConfigName);
        sb.append(" {\n");
        sb.append(StringUtils.repeat("\t", indent)).append("source_table_name").append(" = ").append(StringUtils.wrap(sourceTableName, "\"")).append("\n");
        sb.append(StringUtils.repeat("\t", indent)).append("sql").append(" = ").append(StringUtils.wrap(sql, "\"")).append("\n");
        sb.append(StringUtils.repeat("\t", indent)).append("result_table_name").append(" = ").append(StringUtils.wrap(resultTableName, "\"")).append("\n");
        sb.append(StringUtils.repeat("\t", indent - 1));
        sb.append("}\n");
        return sb.toString();
    }
}
