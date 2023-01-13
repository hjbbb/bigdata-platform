package cn.edu.xidian.bigdataplatform.bean;

public class MetaDataItem {
    private String Field;
    private String Type;
    private String Null;
    private String Key;
    private String Default;
    private String Extra;

    public String getField() {
        return Field;
    }

    public void setField(String field) {
        this.Field = field;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        this.Type = type;
    }

    public String getAllowNull() {
        return Null;
    }

    public void setAllowNull(String allowNull) {
        this.Null = allowNull;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public String getDefault() {
        return Default;
    }

    public void setDefault(String aDefault) {
        Default = aDefault;
    }

    public String getExtra() {
        return Extra;
    }

    public void setExtra(String extra) {
        Extra = extra;
    }
}
