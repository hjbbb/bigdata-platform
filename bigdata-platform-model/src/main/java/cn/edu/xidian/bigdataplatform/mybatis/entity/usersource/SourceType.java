package cn.edu.xidian.bigdataplatform.mybatis.entity.usersource;

public enum SourceType implements BaseCodeEnum {

    MYSQL(1),
    KAFKA(2);

    private int code;

    SourceType(int code) {
        this.code = code;
    }

    @Override
    public int getCode() {
        return code;
    }
}
