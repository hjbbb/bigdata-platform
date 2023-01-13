package cn.edu.xidian.bigdataplatform.seatunnel;

import org.springframework.scheduling.annotation.Async;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SeatunnelStarter{
    public static String appId;
    public static final String APPLICATION_REGEX = "application_\\d+_\\d{4,}+";

    public static void main(String[] args) throws IOException, InterruptedException {
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    System.out.println("run seatunnel in another thread " + Thread.currentThread().getName());
                    SeatunnelStarter.run("/usr/local/seatunnel-xd/config/2023-01-05T15:56:13.245");
                    System.out.println("accept " + appId);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
        int i = 0;
        while (true) {
            System.out.println("sleep " + i);
            i++;
            Thread.sleep(10000);
        }
    }

    public static void run(String configFilePath) throws IOException {
        String cmd = "/usr/local/seatunnel-xd/bin/start-seatunnel-spark.sh " +
                "--master yarn " +
                "--deploy-mode client " +
                "--config " + configFilePath;
        System.out.println(cmd);
        ProcessBuilder builder = new ProcessBuilder(Arrays.asList("bash", "-c", cmd));
        builder.redirectErrorStream(true);
        System.out.println("process start");
        Process process = builder.start();
        System.out.println(process.isAlive());
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(process.getInputStream()));
        String oneLineOfLog;
        while ((oneLineOfLog = reader.readLine()) != null) {
            System.out.println(oneLineOfLog);
            Pattern pattern = Pattern.compile(APPLICATION_REGEX);
            Matcher matcher = pattern.matcher(oneLineOfLog);
            if (matcher.find()) {
                String sparkAppId = matcher.group();
                System.out.println("得到spark app id " + sparkAppId);
                appId = sparkAppId;
            }
        }
    }
}
