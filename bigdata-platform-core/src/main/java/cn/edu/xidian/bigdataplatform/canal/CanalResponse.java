package cn.edu.xidian.bigdataplatform.canal;

/**
 * @author: Zhou Linxuan
 * @create: 2022-11-24
 * @description:
 */
public class CanalResponse<T> {
    public CanalResponse() {
    }

    int code;
    String message;
    T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"code\":")
                .append(code);
        sb.append(",\"message\":\"")
                .append(message).append('\"');
        sb.append(",\"data\":")
                .append(data);
        sb.append('}');
        return sb.toString();
    }
}
