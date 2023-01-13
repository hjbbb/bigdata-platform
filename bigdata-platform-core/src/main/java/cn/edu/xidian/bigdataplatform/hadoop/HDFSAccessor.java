package cn.edu.xidian.bigdataplatform.hadoop;

import cn.edu.xidian.bigdataplatform.hadoop.bean.FileStatusBean;
import cn.edu.xidian.bigdataplatform.hadoop.bean.SuffixStats;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.fs.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;

/**
 * @author: Zhou Linxuan
 * @create: 2022-10-10
 * @description: 用来访问HDFS中的文件，返回文件的文件名等元数据
 */
@Component
public class HDFSAccessor {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private FileSystem hdfs;

    public void setHdfs(FileSystem fs) {
        hdfs = fs;
    }

    public FileStatusBean getAllDirectoriesRecursivelyFromRoot() throws IOException {
        FileStatusBean bean = new FileStatusBean();
        bean.path = "ROOT";
        bean.children = getDirectoriesRecursively(new Path("hdfs://localhost:9000/"));
        return bean;
    }

    public ArrayList<FileStatusBean> getAllDirectoriesFromRoot() throws IOException {
//        return getSubStatusBean(new Path("hdfs://localhost:9000/hbase"));
        return getDirectories(new Path("hdfs://localhost:9000/"));
    }

    public ArrayList<FileStatusBean> list(String path) throws IOException {
        Path p = new Path("hdfs://localhost:9000".concat(path));
        FileStatus[] statuses = hdfs.listStatus(p);
        ArrayList<FileStatusBean> res = new ArrayList<>();
        if (statuses != null) {
            for (FileStatus status : statuses) {
                FileStatusBean bean = fileStatusToBean(status);
                res.add(bean);
            }
        }
        return res;
    }

    public boolean mkdirs(String path) throws IOException {
        Path p = new Path("hdfs://localhost:9000".concat(path));
        if (hdfs.exists(p))
            return true;
        else return hdfs.mkdirs(p);
    }

    public boolean deletePath(String path) throws IOException {
        Path p = new Path("hdfs://localhost:9000/".concat(path));
        return hdfs.delete(p, true);
    }

    public void upload(String dir, String name, byte[] fileContent) throws IOException {
        Path p = new Path(appendPath(dir, name));
        FSDataOutputStream fout = hdfs.create(p, false);
        fout.write(fileContent);
        fout.close();
    }

    public void download(HttpServletResponse response, String path) throws IOException {
        Path p = new Path(path);
        FileStatus status = hdfs.getFileStatus(p);
        String filename = status.getPath().getName();
        FSDataInputStream fs = hdfs.open(p);
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", String.format("attachment; filename=\"" + filename + "\""));
        response.setContentLength((int) status.getLen());
        FileCopyUtils.copy(fs, response.getOutputStream());
    }

    public boolean rename(String src, String dest) throws IOException {
        Path s = new Path(src);
        Path d = new Path(dest);
        return hdfs.rename(s, d);
    }

    public ArrayList<FileStatusBean> getBlockLocations(String path) throws IOException {
        if (path.equals("")) {
            path = "/";
        }
        Path p = new Path("hdfs://localhost:9000".concat(path));
        return getSubStatusBean(p);
    }

    private String appendPath(String prefix, String s) {
        int l = prefix.length();
        String p = l > 0 && prefix.charAt(l - 1) == '/' ? prefix.substring(0, l - 1) : prefix;
        return p + "/" + s;
    }
    private FileStatusBean fileStatusToBean(FileStatus status) {
        FileStatusBean bean = new FileStatusBean();
        bean.isSymlink = status.isSymlink();
        bean.isDir = status.isDirectory();
        bean.isFile = status.isFile();
        bean.accessTime = status.getAccessTime();
        bean.modificationTime = status.getModificationTime();
        bean.blockSize = status.getBlockSize();
        bean.length = status.getLen();
        bean.group = status.getGroup();
        bean.owner = status.getOwner();
        bean.replication = status.getReplication();
        bean.permission = status.getPermission().toString();
        bean.path = status.getPath().toString();
        bean.name = status.getPath().getName();
        Path parent = status.getPath().getParent();
        bean.parent = parent == null ? null : parent.toString();
        bean.children = new ArrayList<>();
        return bean;
    }

    private ArrayList<FileStatusBean> getSubStatusBean(Path path) throws IOException{
        RemoteIterator<LocatedFileStatus> remoteIterator = hdfs.listLocatedStatus(path);
        ArrayList<FileStatusBean> res = new ArrayList<>();
        while (remoteIterator.hasNext()) {
            LocatedFileStatus fileStatus = remoteIterator.next();
            FileStatusBean child = fileStatusToBean(fileStatus);
            res.add(child);
        }
        return res;
    }



    private ArrayList<FileStatusBean> getDirectories(Path path) throws IOException {
        FileStatus rootStatus = hdfs.getFileStatus(path);
        Deque<FileStatusBean> queue = new ArrayDeque<>();
        ArrayList<FileStatusBean> res = new ArrayList<>();
        FileStatusBean rootBean = fileStatusToBean(rootStatus);
        queue.offer(rootBean);

        while (!queue.isEmpty()) {
            FileStatusBean cur = queue.pop();
            if (cur.isDir) {
                ArrayList<FileStatusBean> children = getSubStatusBean(new Path(cur.path));
                cur.children.addAll(children);
                for (FileStatusBean child : children) {
                    queue.offer(child);
                }
            }
            res.add(SerializationUtils.clone(cur));
        }

        return res;
    }

    private ArrayList<FileStatusBean> getDirectoriesRecursively(Path path) throws IOException {
        RemoteIterator<LocatedFileStatus> remoteIterator = hdfs.listLocatedStatus(path);
        ArrayList<FileStatusBean> res = new ArrayList<>();
        while (remoteIterator.hasNext()) {
            LocatedFileStatus fileStatus = remoteIterator.next();
            FileStatusBean file = fileStatusToBean(fileStatus);
            if (fileStatus.isDirectory()) {
                file.children = getDirectoriesRecursively(fileStatus.getPath());
                file.childrenNum = file.children.size();
            } else {
                file.children = new ArrayList<>();
            }
            res.add(file);
        }
        return res;
    }

}