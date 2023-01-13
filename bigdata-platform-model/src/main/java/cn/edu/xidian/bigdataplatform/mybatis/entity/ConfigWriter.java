package cn.edu.xidian.bigdataplatform.mybatis.entity;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ConfigWriter {
    public static void main(String[] args) {
        SQLTransform transform = new SQLTransform("xxx", "select * from xxx", "ttt");
        SQLTransform transform1 = new SQLTransform("tt", "select * from tt", "qqq");
        JDBCSource jdbcSource = new JDBCSource("com.mysql.cj.jdbc.Driver", "zlx1754wanc",
                "freight_source", "jdbc:mysql://localhost:3306/wutong", "root", "freight");
        HBaseSink sink = new HBaseSink("xxx",
                "{\"table\":{\"namespace\":\"default\", \"name\":\"customer\"},\"rowkey\":\"c_custkey\",\"columns\":{\"c_custkey\":{\"cf\":\"rowkey\", \"col\":\"c_custkey\", \"type\":\"bigint\"},\"c_name\":{\"cf\":\"info\", \"col\":\"c_name\", \"type\":\"string\"},\"c_address\":{\"cf\":\"info\", \"col\":\"c_address\", \"type\":\"string\"},\"c_city\":{\"cf\":\"info\", \"col\":\"c_city\", \"type\":\"string\"},\"c_nation\":{\"cf\":\"info\", \"col\":\"c_nation\", \"type\":\"string\"},\"c_region\":{\"cf\":\"info\", \"col\":\"c_region\", \"type\":\"string\"},\"c_phone\":{\"cf\":\"info\", \"col\":\"c_phone\", \"type\":\"string\"},\"c_mktsegment\":{\"cf\":\"info\", \"col\":\"c_mktsegment\", \"type\":\"string\"}}}",
                "append", "/tmp/hbase_tmp");
        Source[] sources = new Source[]{jdbcSource};
        Transform[] transforms = new Transform[]{transform, transform1};
        Sink[] sinks = new Sink[]{sink};
        Env env = new Env();
        try {
            writeToExternalConfigFile(env, sources, transforms, sinks, "index");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean writeToExternalConfigFileFromTable(String configFromTable, String filePath) throws IOException {
        File configFile = new File(filePath);
        FileUtils.writeStringToFile(configFile, configFromTable, StandardCharsets.UTF_8);
        return configFile.length() == 0;
    }


    public static boolean writeToExternalConfigFileRaw(String source, String transform, String sink, String filePath) throws IOException {
        String env = "env {\n" +
                "\tspark.executor.memory = \"1g\"\n" +
                "\tspark.app.name = \"SeaTunnel\"\n" +
                "\tspark.executor.cores = \"1\"\n" +
                "\tspark.executor.instances = \"2\"\n" +
                "}";
        File configFile = new File(filePath);
        FileUtils.writeStringToFile(configFile, env+ "\n" + source + "\n" + transform + "\n" + sink + "\n", StandardCharsets.UTF_8);
        return configFile.length() == 0;
    }

    public static boolean writeToExternalConfigFile(Env env, Source[] sources, Transform[] transforms, Sink[] sinks, String filePath) throws IOException {
        StringBuilder allConfig = new StringBuilder();
        allConfig.append(env.toConfig(1));
        allConfig.append("source {\n");
        for (Source source : sources) {
            allConfig.append(source.toConfig(2));
        }
        allConfig.append("}\n");
        allConfig.append("transform {\n");
        for (Transform transform : transforms) {
            allConfig.append(transform.toConfig(2));
        }
        allConfig.append("}\n");
        allConfig.append("sink {\n");
        for (Sink sink : sinks) {
            allConfig.append(sink.toConfig(2));
        }
        allConfig.append("}\n");
        System.out.println(allConfig);
        File configFile = new File(filePath);
        FileUtils.writeStringToFile(configFile, allConfig.toString(), StandardCharsets.UTF_8);
        return configFile.length() == 0;
    }
}
