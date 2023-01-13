package cn.edu.xidian.bigdataplatform.mybatis.entity.dataencrypt;

public class DBColumn {
    private String name;
    private String type;

    public DBColumn() {
    }

    public DBColumn(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
