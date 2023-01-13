package cn.edu.xidian.bigdataplatform.hadoop.bean;

/**
 * @author: Zhou Linxuan
 * @create: 2022-11-14
 * @description:
 */
public class SuffixStats {
    public String suffix;
    public int count;
    public double avgSize;

    public SuffixStats() {
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"suffix\":\"")
                .append(suffix).append('\"');
        sb.append(",\"count\":")
                .append(count);
        sb.append(",\"avgSize\":")
                .append(avgSize);
        sb.append('}');
        return sb.toString();
    }
}
