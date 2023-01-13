package cn.edu.xidian.bigdataplatform.ranger;

/**
 * @author suman.bn
 */
public interface Client {

    /**
     * Starts the client
     *
     * @throws Exception
     */
    void start() throws Exception;

    /**
     * Stops the client
     *
     * @throws Exception
     */
    void stop() throws Exception;
}
