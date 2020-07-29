package org.projectomandacaru.xlstoarff.model;

import java.io.IOException;

public interface SpreadSheetToCsv {
    String SEPARATOR = ",";
    String BREAK_LINE = "\r\n";

    String[] readFileToCsv(String filename, String outputPath) throws IOException;
}