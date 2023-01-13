package cn.edu.xidian.bigdataplatform.mybatis.entity.dataencrypt;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class EncPublicKey {
    String key;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    public EncPublicKey(String key, LocalDateTime createTime) {
        this.key = key;
        this.createTime = createTime;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}
