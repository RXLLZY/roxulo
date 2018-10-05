package com.swt.common.utils;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocatedFileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class HDFSUtils {
  private final FileSystem fileSystem;

  private HDFSUtils() throws IOException {
    System.setProperty("HADOOP_USER_NAME", "root");
    Configuration conf = new Configuration();
    conf.set("fs.defaultFS", "hdfs://192.168.0.26:9000/");
    conf.set("fs.hdfs.impl", "org.apache.hadoop.hdfs.DistributedFileSystem");
    fileSystem = FileSystem.get(conf);
  }

  /**
   * 列出指定路径下的所有文件
   *
   * @param dirPath 指定路径
   * @return 文件列表
   * @throws IOException -
   */
  public List<Path> getDirFiles(String dirPath) throws IOException {
    RemoteIterator<LocatedFileStatus> files = fileSystem.listFiles(new Path(dirPath), true);
    List<Path> pathList = new ArrayList<>();
    while (files.hasNext()) {
      LocatedFileStatus path = files.next();
      pathList.add(path.getPath());
    }
    return pathList;
  }

  /**
   * 上传本地文件到hdfs
   *
   * @param srcPath 本地路径，指定到文件名
   * @param tarPath hdfs路径，<b>指向到文件名，不是文件夹，可以跟本地文件名不同，用于更改文件名</b>
   * @throws IOException 上传过程IO异常
   */
  public void put2HDFS(String srcPath, String tarPath) throws IOException {
    // 要上传的源文件所在路径
    Path src = new Path(srcPath);
    // hadoop文件系统的跟目录
    Path dst = new Path(tarPath);
    if (!fileSystem.exists(dst.getParent())) {
      fileSystem.mkdirs(dst.getParent());
    }
    // 将源文件copy到hadoop文件系统
    fileSystem.copyFromLocalFile(src, dst);
  }

  /**
   * 检测hdfs文件是否存在
   *
   * @param filePath hdfs路径，指向到文件名
   * @return -
   * @throws IOException 检测过程IO异常
   */
  public boolean checkExist(String filePath) throws IOException {
    return fileSystem.exists(new Path(filePath));
  }

  @Override
  protected void finalize() throws Throwable {
    super.finalize();
    try {
      fileSystem.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
