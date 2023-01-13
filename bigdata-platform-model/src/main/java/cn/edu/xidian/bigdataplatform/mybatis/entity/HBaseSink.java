package cn.edu.xidian.bigdataplatform.mybatis.entity;

import org.apache.commons.lang3.StringUtils;

/**
 * hbase {
 *     source_table_name = "hive_dataset"
 *     hbase.zookeeper.quorum = "host01:2181,host02:2181,host03:2181"
 *     catalog = "{\"table\":{\"namespace\":\"default\", \"name\":\"customer\"},\"rowkey\":\"c_custkey\",\"columns\":{\"c_custkey\":{\"cf\":\"rowkey\", \"col\":\"c_custkey\", \"type\":\"bigint\"},\"c_name\":{\"cf\":\"info\", \"col\":\"c_name\", \"type\":\"string\"},\"c_address\":{\"cf\":\"info\", \"col\":\"c_address\", \"type\":\"string\"},\"c_city\":{\"cf\":\"info\", \"col\":\"c_city\", \"type\":\"string\"},\"c_nation\":{\"cf\":\"info\", \"col\":\"c_nation\", \"type\":\"string\"},\"c_region\":{\"cf\":\"info\", \"col\":\"c_region\", \"type\":\"string\"},\"c_phone\":{\"cf\":\"info\", \"col\":\"c_phone\", \"type\":\"string\"},\"c_mktsegment\":{\"cf\":\"info\", \"col\":\"c_mktsegment\", \"type\":\"string\"}}}"
 *     staging_dir = "/tmp/hbase-staging/"
 *     save_mode = "overwrite"
 * }
 */
public class HBaseSink extends Sink{
    private String sourceTableName;
    private String quorum;
    private String catalog;
    private String saveMode;
    private String stagingDir;

    public HBaseSink(String sourceTableName, String catalog, String saveMode, String stagingDir) {
        this.sourceTableName = sourceTableName;
        this.catalog = catalog;
        this.saveMode = saveMode;
        this.stagingDir = stagingDir;
        this.quorum = "hadoop000:2181,hadoop001:2182,hadoop002:2181";
        this.sinkConfigName = "hbase";
    }

    public void setSourceTableName(String sourceTableName) {
        this.sourceTableName = sourceTableName;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public void setSaveMode(String saveMode) {
        this.saveMode = saveMode;
    }

    public void setStagingDir(String stagingDir) {
        this.stagingDir = stagingDir;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"sourceTableName\":\"")
                .append(sourceTableName).append('\"');
        sb.append(",\"quorum\":\"")
                .append(quorum).append('\"');
        sb.append(",\"catalog\":\"")
                .append(catalog).append('\"');
        sb.append(",\"saveMode\":\"")
                .append(saveMode).append('\"');
        sb.append(",\"stagingDir\":\"")
                .append(stagingDir).append('\"');
        sb.append('}');
        return sb.toString();
    }

    public String toConfig(int indent) {
        final StringBuilder sb = new StringBuilder();
        sb.append(StringUtils.repeat("\t", indent - 1));
        sb.append(this.sinkConfigName);
        sb.append(" {\n");
        sb.append(StringUtils.repeat("\t", indent))
                .append("source_table_name")
                .append(" = ")
                .append(StringUtils.wrap(sourceTableName, "\""))
                .append("\n");
        sb.append(StringUtils.repeat("\t", indent))
                .append("quorum")
                .append(" = ")
                .append(StringUtils.wrap(quorum, "\""))
                .append("\n");
        sb.append(StringUtils.repeat("\t", indent))
                .append("catalog")
                .append(" = ")
                .append(StringUtils.wrap(catalog.replace("\"", "\\\""), "\""))
                .append("\n");
        sb.append(StringUtils.repeat("\t", indent))
                .append("save_mode")
                .append(" = ")
                .append(StringUtils.wrap(saveMode, "\""))
                .append("\n");
        sb.append(StringUtils.repeat("\t", indent))
                .append("staging_dir")
                .append(" = ")
                .append(StringUtils.wrap(stagingDir, "\"")).append("\n");
        sb.append(StringUtils.repeat("\t", indent - 1));
        sb.append("}\n");
        return sb.toString();
    }
}
