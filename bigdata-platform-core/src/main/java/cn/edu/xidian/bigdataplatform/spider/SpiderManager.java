package cn.edu.xidian.bigdataplatform.spider;

import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author: Zhou Linxuan
 * @create: 2022-11-22
 * @description:
 */
@Component
public class SpiderManager {
    private static final String SPIDER_LOG_BASE_DIR = "/usr/local/spiders/scraping/logistics/logistics/logs/";
    private static final String SPIDER_LOG_FILE_SUFFIX = ".log";
    private static final String SPIDER_SCRIPT_BASE_LOCATION = "/usr/local/spiders/scraping/logistics/logistics/sh/";
    private static final String SPIDER_SCRIPT_START_FILE = "spider_start.sh";
    private static final String SPIDER_SCRIPT_PAUSE_FILE = "spider_pause.sh";
    private static final String SPIDER_SCRIPT_STOP_FILE = "spider_stop.sh";
    private static final String SPIDER_SCRIPT_TIMING_START_FILE = "timing_start.sh";
    private static final String SPIDER_SCRIPT_TIMING_STOP_FILE = "timing_stop.sh";
    private static final String NOHUP_WRAPPER = "/home/mainuser/nohup-wrapper.sh";

    private static Logger logger = LoggerFactory.getLogger(SpiderManager.class);


    public void launchSpiderTimingUsingShell(String spiderName, String minute, String hour, String frequency,String value){
        String cmd = SPIDER_SCRIPT_BASE_LOCATION + SPIDER_SCRIPT_TIMING_START_FILE;
        ProcessBuilder builder = new ProcessBuilder(ImmutableList.of("sh", cmd, spiderName, minute, hour, frequency, value));
        logger.debug(StringUtils.join(builder.command()));
        builder.redirectOutput();
        builder.redirectErrorStream(false);
        Process process = null;
        try {
            int exitValue = builder.inheritIO().start().waitFor();
            logger.debug("process launching timing spider: " + exitValue);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void launchSpiderUsingShell(String spiderName){
        String cmd = SPIDER_SCRIPT_BASE_LOCATION + SPIDER_SCRIPT_START_FILE;
        logger.debug(cmd);
        ProcessBuilder builder = new ProcessBuilder(ImmutableList.of("sh", NOHUP_WRAPPER, "sh", cmd ,spiderName));
        builder.redirectOutput();
        builder.redirectErrorStream(false);
        Process process = null;
        try {
            int exitValue = builder.inheritIO().start().waitFor();
            logger.debug("process launching spider: " + exitValue);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean probeSpiderByName(String spiderName) {
        String cmd = "ps -ef | grep " + spiderName + " | grep -v \"grep\"";
        ProcessBuilder builder = new ProcessBuilder(ImmutableList.of("bash", "-c", cmd));
        builder.redirectOutput();
        builder.redirectErrorStream(false);
        Process process = null;
        try {
            process = builder.start();
            process.waitFor();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
        InputStream inputStream = process.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                if (line.contains("scrapy") || line.contains("python3")) {
                    if(line.contains(spiderName)){
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    public void stopSpiderUsingShell(String spiderName) {
        String cmd = SPIDER_SCRIPT_BASE_LOCATION  + SPIDER_SCRIPT_STOP_FILE;
        ProcessBuilder builder = new ProcessBuilder(ImmutableList.of("sh", cmd , spiderName));
        builder.redirectOutput();
        builder.redirectErrorStream(false);
        Process process = null;
        try {
            int exitValue = builder.inheritIO().start().waitFor();
            logger.debug("process Stop spider: " + exitValue);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void stopSpiderTimingUsingShell(String spiderName){
        String cmd = SPIDER_SCRIPT_BASE_LOCATION  + SPIDER_SCRIPT_TIMING_STOP_FILE;
        ProcessBuilder builder = new ProcessBuilder(ImmutableList.of("sh", cmd , spiderName));
        builder.redirectOutput();
        builder.redirectErrorStream(false);
        Process process = null;
        try {
            int exitValue = builder.inheritIO().start().waitFor();
            logger.debug("process Stop spider: " + exitValue);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void pauseSpiderUsingShell(String spiderName){
        String cmd = SPIDER_SCRIPT_BASE_LOCATION + SPIDER_SCRIPT_PAUSE_FILE;
        ProcessBuilder builder = new ProcessBuilder(ImmutableList.of("sh", cmd , spiderName));
        builder.redirectOutput();
        builder.redirectErrorStream(false);
        Process process = null;
        try {
            int exitValue = builder.inheritIO().start().waitFor();
            logger.debug("process Stop Timing spider: " + exitValue);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
