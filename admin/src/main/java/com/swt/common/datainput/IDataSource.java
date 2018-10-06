package com.swt.common.datainput;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 输入源接口
 * 目前实现了两种输入源：csv和Excel
 */
public interface IDataSource {

	/**
	 * 获取文件头（所有字段）
	 * @return
	 * @throws IOException
	 */
	List<String> getHeader() throws IOException;

	/**
	 * 数据是否有下一行
	 * @return
	 * @throws IOException
	 */
	boolean hasNext() throws IOException;

	/**
	 * 下一行
	 * @return
	 * @throws IOException
	 */
	Map<String, String> next() throws IOException;

	/**
	 * 关闭文件读入流
	 */
	void close();

	/**
	 * 获取输入文件的绝对路径
	 * @return
	 */
	String getFilePath();

	/**
	 * 获取文件有多少行数据，不包括文件头
	 * @return
	 * @throws IOException
	 */
	long getSize() throws IOException;
}
