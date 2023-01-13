package cn.edu.xidian.bigdataplatform.hadoop.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author: Zhou Linxuan
 * @create: 2022-10-11
 * @description:
 */
public class FileStatusBean implements Serializable {
    public String path;
    public long accessTime;
    public long blockSize;
    public int childrenNum;
    public String group;
    public long modificationTime;
    public long length;
    public String owner;
    public String permission;
    public String name;
    public int replication;
    public boolean isFile;
    public boolean isDir;
    public boolean isSymlink;
    public String parent;

    public ArrayList<FileStatusBean> children;

    public FileStatusBean() {
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"path\":\"")
                .append(path).append('\"');
        sb.append(",\"accessTime\":")
                .append(accessTime);
        sb.append(",\"blockSize\":")
                .append(blockSize);
        sb.append(",\"childrenNum\":")
                .append(childrenNum);
        sb.append(",\"group\":\"")
                .append(group).append('\"');
        sb.append(",\"modificationTime\":")
                .append(modificationTime);
        sb.append(",\"length\":")
                .append(length);
        sb.append(",\"owner\":\"")
                .append(owner).append('\"');
        sb.append(",\"permission\":\"")
                .append(permission).append('\"');
        sb.append(",\"replication\":")
                .append(replication);
        sb.append(",\"isFile\":")
                .append(isFile);
        sb.append(",\"isDir\":")
                .append(isDir);
        sb.append(",\"isSymlink\":")
                .append(isSymlink);
        sb.append(",\"parent\":\"")
                .append(parent).append('\"');
        sb.append(",\"subDirectories\":")
                .append(children);
        sb.append('}');
        return sb.toString();
    }
}
