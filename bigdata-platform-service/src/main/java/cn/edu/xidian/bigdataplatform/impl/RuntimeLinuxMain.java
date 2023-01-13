package cn.edu.xidian.bigdataplatform.impl;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
public class RuntimeLinuxMain {
    private static final String SPIDER_LOG_BASE_DIR = "/usr/local/spiders/scraping/logistics/logistics/logs/";
    private static final String SPIDER_LOG_FILE_SUFFIX = ".log";
    private static final String SPIDER_SCRIPT_BASE_LOCATION = "/usr/local/spiders/scraping/logistics/logistics/sh/";
    private static final String SPIDER_SCRIPT_START_SUFFIX = "_start.sh";
    private static final String NOHUP_WRAPPER = "/home/mainuser/nohup-wrapper.sh";

    public static void main(String[] args) throws IOException {
        BufferedReader br = null;
        File tmpFile = new File("/usr/local/spiders/scraping/logistics/logistics/temp.temp");
        if(!tmpFile.exists()) {
            tmpFile.createNewFile();
        }
        String cmd = SPIDER_SCRIPT_BASE_LOCATION + "logistics" + SPIDER_SCRIPT_START_SUFFIX;
        List<String> i = new ArrayList();
        i.add("sh");
        i.add(NOHUP_WRAPPER);
        i.add("sh");
        i.add(cmd);
        ProcessBuilder builder = new ProcessBuilder(i);
        builder.redirectOutput(tmpFile);
        builder.redirectErrorStream(false);
        try {
            int exitValue = builder.inheritIO().start().waitFor();
            InputStream in = new FileInputStream(tmpFile);
            br = new BufferedReader(new InputStreamReader(in));
            String line = null;
            while((line = br.readLine()) != null) {
                System.out.println(line);
            }
            br.close();
            br = null;
            tmpFile.delete();//卸磨杀驴。
            System.out.println("执行完成");
            System.out.println("process launching spider: " + exitValue);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}