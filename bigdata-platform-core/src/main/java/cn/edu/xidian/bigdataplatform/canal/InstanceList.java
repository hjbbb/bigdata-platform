package cn.edu.xidian.bigdataplatform.canal;

import java.util.List;

/**
 * @author: Zhou Linxuan
 * @create: 2023-01-03
 * @description:
 */
public class InstanceList {
    private int count;
    private List<InstanceConfig> items;
    private int offset;
    private int page;
    private int size;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"count\":")
                .append(count);
        sb.append(",\"items\":")
                .append(items);
        sb.append(",\"offset\":")
                .append(offset);
        sb.append(",\"page\":")
                .append(page);
        sb.append(",\"size\":")
                .append(size);
        sb.append('}');
        return sb.toString();
    }

    public InstanceList() {
    }

    public InstanceList(int count, List<InstanceConfig> items, int offset, int page, int size) {
        this.count = count;
        this.items = items;
        this.offset = offset;
        this.page = page;
        this.size = size;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<InstanceConfig> getItems() {
        return items;
    }

    public void setItems(List<InstanceConfig> items) {
        this.items = items;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
