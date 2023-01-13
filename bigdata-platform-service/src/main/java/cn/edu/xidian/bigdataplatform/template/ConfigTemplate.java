package cn.edu.xidian.bigdataplatform.template;

/**
 * @author: Zhou Linxuan
 * @create: 2022-11-13
 * @description: SQL模板，把用户选择的清洗选项填进来
 */
public class ConfigTemplate {
    public static final String envConfigTemplate = "env {\n" +
            "  spark.app.name = \"SeaTunnel\"\n" +
            "  spark.executor.instances = 2\n" +
            "  spark.executor.cores = 1\n" +
            "  spark.executor.memory = \"1g\"\n" +
            "  spark.streaming.batchDuration = 5\n" +
            "}\n";
    public static final String sourceConfigTemplate = "source {\n" +
            "  ${source}" + "\n" +
            "}\n";

    public static final String kafkaSourceConfigTemplate = "  kafkaStream {\n" +
            "    topics = \"${topicName}\"\n" +
            "    consumer.bootstrap.servers = \"${brokers}\"\n" +   // 服务器逗号分割
            "    consumer.group.id = \"seatunnel_group\"\n" +       // 默认为seatunnel_group
            "    result_table_name = \"${resultTableName}\"\n" +        // 默认为对应的mysql表名
            "  }\n";

    public static final String transformConfigTemplate = "transform {\n" +
            "  ${transform}\n" +
            "}\n";

    // 从kafka消息中抽取数据
    public static final String extractBinlogTransformConfigTemplate = "  Sql {\n" +
            "    sql = \"select get_json_object(raw_message,'$.data[*]') data from ${resultTableName}\"\n" +  // source的resultConfigName
            "    result_table_name = \"extracted_message\"\n" +
            "  }\n";

    // struct<id:string,depart:string,destination:string, distance:string, published:string, freight_description:string,
    // freight_type:string, transport_type:string, validity:string, remark:string, weight:string, volume:string,
    // contact:string, contact_number:string,freight_id:string,distance_int:string>
    public static final String structedBinlogTransformConfigTemplate = "  Sql {\n" +
            "    source_table_name = \"extracted_message\"\n" +
            "    sql = \"select from_json(data, '${schema}') struct_col from extracted_message\"\n" + // spark ddl格式的schema，见上面注释
            "    result_table_name = \"struct_df\"\n" +
            "  }\n";

    public static final String formattedBinlogTransformConfigTemplate = "  Sql {\n" +
            "    source_table_name = \"struct_df\"\n" +
            "    sql = \"select struct_col.* from struct_df\"\n" +
            "    result_table_name = \"formatted_df\"\n" +
            "  }\n";

    public static final String cleanedBinlogTransformConfigTemplate = "  Sql {\n" +
            "    source_table_name = \"formatted_df\"\n" +
            "    sql = \"select ${selectClause} from formatted_df\"\n" +
            "    result_table_name = \"cleaned_df\"\n" +
            "  }\n";

    public static final String sinkConfigTemplate = "sink {\n" +
            "  ${sink}\n" +
            "}";

    public static final String esSinkConfigTemplate = "  elasticsearch {\n" +
            "    hosts = [\"${hostNames}\"]\n" +
            "    index = \"${indexName}\"\n" +
            "  }\n";
}
