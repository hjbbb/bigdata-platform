package cn.edu.xidian.bigdataplatform;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author: Zhou Linxuan
 * @create: 2022-11-18
 * @description:
 */
@SpringBootTest
public class CommandLineTests {
    private static final Logger logger = LoggerFactory.getLogger(CommandLineTests.class);

    @Test
    void test() {
//        String cmd = "ps -ef | grep python3 | awk '{print $2}'";
//        ProcessBuilder builder = new ProcessBuilder(ImmutableList.of("bash", "-c", cmd));
//        builder.redirectOutput();
//        builder.redirectErrorStream(true);
//        Process process = null;
//        try {
//            process = builder.start();
//            process.waitFor();
//        } catch (InterruptedException | IOException e) {
//            e.printStackTrace();
//        }
//        InputStream inputStream = process.getInputStream();
//        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//        String line;
//        try {
//            while ((line = bufferedReader.readLine()) != null) {
//                logger.debug(line);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    @Test
    void runCmd() {
        CompletableFuture.supplyAsync(new Supplier<InputStream>() {
            @Override
            public InputStream get() {
                String cmd = "/home/zlx/workspace3/dev/bigdata-platform/example/testStartProcess.sh";
                ProcessBuilder builder = new ProcessBuilder(Arrays.asList("/bin/bash", "-c", cmd));
                builder.redirectOutput();
                builder.redirectErrorStream(true);
                Process process = null;
                try {
                    process = builder.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return process.getInputStream();
            }
        }).thenAccept(new Consumer<InputStream>() {
            @Override
            public void accept(InputStream is) {
                if (is == null) {
                    logger.warn("cannot get inputstream");
                    return;
                }
                BufferedReader reader =
                        new BufferedReader(new InputStreamReader(is));
                String oneLineOfLog = null;
                try {
                    while ((oneLineOfLog = reader.readLine()) != null) {
                        System.out.println(oneLineOfLog);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        logger.info("runCmd end");
    }
}
