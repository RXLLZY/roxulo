package com.swt.common.datainput;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class ErrorCsvRecord {
    private String errorFilePath;
    private String header;

    private boolean first;
    private Writer writer;

    public ErrorCsvRecord(String errorFilePath) {
        this.errorFilePath = errorFilePath;
        first = true;
    }

    public String getErrorFilePath() {
        return errorFilePath;
    }

    public void setCsvHeader(String header) {
        this.header = header;
    }

    public void addRecord(String nextLine) throws IOException {
        if (first) {
            first = false;

            if (errorFilePath != null && !errorFilePath.trim().isEmpty()) {
                File file = new File(errorFilePath);
                File parentDirectory=file.getParentFile();
                if (!parentDirectory.exists()) {
                    parentDirectory.mkdirs();
                }

                if (file.exists()) {
                    file.delete();
                }
                writer = new FileWriter(file, true);
                if(file.length() > 0){
                    return ;
                }
                writer.write(header + "\n");
            }

        }

        if (writer != null) {
            writer.write(nextLine + "\n");
        }
    }

    public void closeWriter() {
        if (writer != null) {
            try {
                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
