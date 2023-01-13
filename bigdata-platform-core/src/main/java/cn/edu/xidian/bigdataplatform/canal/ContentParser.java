package cn.edu.xidian.bigdataplatform.canal;

import java.io.*;
import java.util.Properties;

/**
 * @author: Zhou Linxuan
 * @create: 2022-11-24
 * @description:
 */
public class ContentParser {
    public static void main(String[] args) throws IOException {
        //        String content = "# canal.instance.mysql.slaveId=0\\n\\n# enable gtid use true/false\\ncanal.instance.gtidon=false\\n\\n# position info\\ncanal.instance.master.address=127.0.0.1:3306\\ncanal.instance.master.journal.name=\\ncanal.instance.master.position=\\ncanal.instance.master.timestamp=\\ncanal.instance.master.gtid=\\n\\n# rds oss binlog\\ncanal.instance.rds.accesskey=\\ncanal.instance.rds.secretkey=\\ncanal.instance.rds.instanceId=\\n\\n# table meta tsdb info\\ncanal.instance.tsdb.enable=true\\n#canal.instance.tsdb.url=jdbc:mysql://127.0.0.1:3306/canal_tsdb\\n#canal.instance.tsdb.dbUsername=canal\\n#canal.instance.tsdb.dbPassword=canal\\n\\n#canal.instance.standby.address =\\n#canal.instance.standby.journal.name =\\n#canal.instance.standby.position =\\n#canal.instance.standby.timestamp =\\n#canal.instance.standby.gtid=\\n\\n# username/password\\ncanal.instance.dbUsername=root\\ncanal.instance.dbPassword=zlx1754wanc\\ncanal.instance.connectionCharset = UTF-8\\n# enable druid Decrypt database password\\ncanal.instance.enableDruid=false\\n#canal.instance.pwdPublicKey=MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBALK4BUxdDltRRE5/zXpVEVPUgunvscYFtEip3pmLlhrWpacX7y7GCMo2/JM6LeHmiiNdH1FWgGCpUfircSwlWKUCAwEAAQ==\\n\\n# table regex\\ncanal.instance.filter.regex=.*\\\\\\\\..*\\n# table black regex\\ncanal.instance.filter.black.regex=\\n# table field filter(format: schema1.tableName1:field1/field2,schema2.tableName2:field1/field2)\\n#canal.instance.filter.field=test1.t_product:id/subject/keywords,test2.t_company:id/name/contact/ch\\n# table field black filter(format: schema1.tableName1:field1/field2,schema2.tableName2:field1/field2)\\n#canal.instance.filter.black.field=test1.t_product:subject/product_image,test2.t_company:id/name/contact/ch\\n\\n# mq config\\ncanal.mq.topic=canal-example\\n# dynamic topic route by schema or table regex\\ncanal.mq.dynamicTopic=logistics\\\\\\\\.freight_source\\ncanal.mq.partition=0\\n# hash partition config\\n#canal.mq.partitionsNum=3\\n#canal.mq.partitionHash=test.table:id^name,.*\\\\\\\\..*\\n";
//        System.out.println(content);

        InputStream is = new FileInputStream("/usr/local/canal/admin/conf/instance-template.properties");
        Properties p = new Properties();
        p.load(is);
        p.setProperty("canal.mq.dynamicTopic", "logistics\\\\.freight_source");
        p.setProperty("canal.instance.dbUsername", "root123");
        p.setProperty("canal.instance.dbPassword", "zlx1754wanc");
        p.setProperty("canal.instance.master.address", "127.0.0.1:3306");

        StringWriter writer = new StringWriter();
        p.store(writer, "");
        String output = writer.getBuffer().toString();
        System.out.println(output);
    }
}
