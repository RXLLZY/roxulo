package com.swt.common.datainput;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 必须提供文件头和输出路径方可使用write方法
 *
 * @author BuGu
 */
public class CsvWriter implements AutoCloseable {

    private static final String DEFAULT_DELIMITER = ",";
    private static final String DEFAULT_QUOTE = "\"";
    private static final String DEFAULT_RECORD_SEPARATOR = "\r\n";
    private static final String DEFAULT_CHARACTER_SET = "UTF-8";

    private String[] header; //Csv字段头
    private byte[] delimiter; //Csv分隔符
    private byte[] quote; //Csv封闭符
    private byte[] recordSeparator; //行分隔符
    private String outputPath; //csv文件输出路径
    private boolean append;
    private boolean trim;

    private OutputStream writer;

    /**
     * 设置是否追加到已有文件中
     *
     * @param append
     * @return
     */
    public CsvWriter withAppend(boolean append) {
        this.append = append;
        return this;
    }
    /**
     * 设置Csv文件头
     *
     * @param header
     * @return
     */
    public CsvWriter withHeader(String[] header) {
        this.header = header;
        return this;
    }

    /**
     * 设置Csv文件头
     *
     * @param header
     * @return
     */
    public CsvWriter withHeader(List<String> header) {
        this.header = new String[header.size()];
        for (int i = 0; i < header.size(); i++) {
            this.header[i] = header.get(i);
        }
        return this;
    }

    /**
     * 设置Csv封闭符
     *
     * @return
     */
    public CsvWriter withQuote(String quote) {
        try {
            this.quote = quote.getBytes(DEFAULT_CHARACTER_SET);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * 设置Csv分隔符
     *
     * @param delimiter
     * @return
     */
    public CsvWriter withDelimiter(String delimiter) {
        try {
            this.delimiter = delimiter.getBytes(DEFAULT_CHARACTER_SET);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * 设置Csv行分隔符
     *
     * @param recordSeparator
     * @return
     */
    public CsvWriter withRecordSeparator(String recordSeparator) {
        try {
            this.recordSeparator = recordSeparator.getBytes(DEFAULT_CHARACTER_SET);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return this;
    }
    public CsvWriter withTrim(boolean trim) {
        this.trim = trim;
        return this;
    }
    /**
     * 设置输出文件路径
     *
     * @param filePath
     * @return
     */
    public CsvWriter withOutputPath(String filePath) {
        this.outputPath = filePath;
        return this;
    }

    /**
     * 初始化Writer
     *
     * @throws Exception
     */
    @SuppressWarnings("deprecation")
    public void initWriter() throws Exception {
        if (header == null) {
            throw new Exception("没有设置文件头");
        }
        if (delimiter == null) {
            delimiter = DEFAULT_DELIMITER.getBytes(DEFAULT_CHARACTER_SET);
        }
        if (recordSeparator == null) {
            recordSeparator = DEFAULT_RECORD_SEPARATOR.getBytes(DEFAULT_CHARACTER_SET);
        }
        if (quote == null) {
            quote = DEFAULT_QUOTE.getBytes(DEFAULT_CHARACTER_SET);
        }

        if (!outputPath.trim().isEmpty()) {
            if(!append) {
                if (new File(outputPath).exists()) {
                    FileUtils.write(new File(outputPath), "");
                }
            }

            writer = new BufferedOutputStream(new FileOutputStream(outputPath, true), 1024 * 24);

            if(!append) {
                for (int i = 0; i < header.length; i++) {
                    if (i > 0) {
                        writer.write(delimiter);
                    }
                    writeField(header[i]);
                }
                writer.write(recordSeparator);
            }
        }
    }

    public void write(List<String> data) throws Exception {
        if (writer == null) {
            initWriter();
        }

        for (int i = 0; i < data.size(); i++) {
            if (i > 0) {
                writer.write(delimiter);
            }
            if(trim) {
                writeField(data.get(i).trim());
            }else {
                writeField(data.get(i));
            }
        }
        writer.write(recordSeparator);
    }

    public void write(Map<String, String> data) throws Exception {
        if (writer == null) {
            initWriter();
        }

        for (int i = 0; i < header.length; i++) {
            if (i > 0) {
                writer.write(delimiter);
            }
            if(trim) {
                writeField(data.get(header[i]).trim());
            }else {
                writeField(data.get(header[i]));
            }

        }
        writer.write(recordSeparator);

    }

    private void writeField(String value) throws IOException {
        byte[] record = value.getBytes(DEFAULT_CHARACTER_SET);
        writer.write(quote);

        List<Integer> enclosures = getEnclosurePositions(record, quote);
        if (enclosures == null) {
            writer.write(record);
        } else {
            int from = 0;
            for (int i = 0; i < enclosures.size(); i++) {
                int position = enclosures.get(i);
                writer.write(record, from, position + quote.length - from);
                writer.write(quote); // write enclosure a second time
                from = position + quote.length;
            }
            if (from < record.length) {
                writer.write(record, from, record.length - from);
            }
        }
        writer.write(quote);
    }

    private List<Integer> getEnclosurePositions(byte[] record, byte[] binaryEnclosure) {
        List<Integer> positions = null;
        if (binaryEnclosure != null && binaryEnclosure.length > 0) {
            for (int i = 0; i < record.length - binaryEnclosure.length + 1; i++) //+1 because otherwise we will not find it at the end
            {
                // verify if on position i there is an enclosure
                //
                boolean found = true;
                for (int x = 0; found && x < binaryEnclosure.length; x++) {
                    if (record[i + x] != binaryEnclosure[x])
                        found = false;
                }
                if (found) {
                    if (positions == null)
                        positions = new ArrayList<Integer>();
                    positions.add(i);
                }
            }
        }
        return positions;
    }

    public String getOutputPath() {
        return outputPath;
    }

    @Override
    public void close() throws IOException {
        if (writer != null) {
            writer.close();
        }
        writer = null;
    }
}
