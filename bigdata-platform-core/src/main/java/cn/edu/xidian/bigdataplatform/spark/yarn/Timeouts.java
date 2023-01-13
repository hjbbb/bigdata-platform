package cn.edu.xidian.bigdataplatform.spark.yarn;

/**
 * @author: Zhou Linxuan
 * @create: 2023-01-08
 * @description:
 */
import java.util.List;

public class Timeouts {

    private List<Timeout> timeout;
    public void setTimeout(List<Timeout> timeout) {
        this.timeout = timeout;
    }
    public List<Timeout> getTimeout() {
        return timeout;
    }

    public Timeouts() {
    }
}