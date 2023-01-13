package cn.edu.xidian.bigdataplatform.hadoop;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author: Zhou Linxuan
 * @create: 2022-10-10
 * @description: 初始化HDFS client
 */
@Component
public class HadoopConnector {
    private static final Logger logger = LoggerFactory.getLogger(HadoopConnector.class);
    private Path rootPath;
    private Configuration conf;

    @Autowired
    private HDFSAccessor hdfsAccessor;

    @Value("${hdfs.rootPath}")
    private String hdfsRootPath;

    public HadoopConnector() {

    }

    public void init() throws IOException {
        System.setProperty("hadoop.home.dir", "/usr/local/hadoop");
        conf = new Configuration();
        conf.addResource(new Path("file:///usr/local/hadoop/etc/hadoop/conf/core-site.xml"));
        conf.addResource(new Path("file:///usr/local/hadoop/etc/hadoop/conf/hdfs-site.xml"));
        rootPath = new Path(hdfsRootPath);
        this.hdfsAccessor.setHdfs(rootPath.getFileSystem(conf));
        logger.info("init done");
    }
}
