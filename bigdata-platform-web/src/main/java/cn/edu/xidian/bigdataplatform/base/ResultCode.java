package cn.edu.xidian.bigdataplatform.base;

public enum ResultCode {
    /**
     * api状态码管理
     * @author cc
     * @date 2021-07-12 10:00
     */
    SUCCESS(0, "请求成功"),
    // 没有定义好错误类型的时候，用这个
    FAILED(10001, "操作失败"),
    DATASOURCE_ADD_FAILED(10002, "创建新数据源失败，无法插入数据库"),
    TOKEN_FAILED(10003, "token失效"),
    LOGIN_FAILED(10004, "登录失败"),
//    DOWNLOAD_FAILED(10005, "下载失败"),
//    UPLOAD_FAILED(10006, "上传失败"),

    PUBLIC_KEY_ERROR(10005,"公钥没有创建"),
    SQL_ERROR(10006,"数据库错误"),
    DATABASE_CONNOT_CONNECT(10007,"数据库无法链接"),
    FIELD_NOT_EXIST(10008,"字段不存在"),
    PARAM_NOT_COMPLETE(1009,"参数不全"),
    API_REPEAT(1010,"api重复注册"),
    // 杨澜原本写的10005，与jh冲突，改成10011
    INDEX_NAME_FAILED(10011,"索引名不存在"),
    SPIDER_TASK_NOT_EXIST(10012,"不存在该爬虫任务"),
    SPIDER_TASK_REPEATE(10013,"爬虫任务重复创建"),
    SPIDER_TASK_RUNNING(10014,"请先停止该爬虫任务，再修改"),
    START_CANAL_INSTANCE_FAILED(11101,"启动实例错误"),

    NONE(99999, "无");

    private int code;
    private String msg;

    private ResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
