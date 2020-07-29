package org.projectomandacaru.xlstoarff;

import org.apache.commons.io.FilenameUtils;
import org.projectomandacaru.xlstoarff.model.CsvToArff;
import org.projectomandacaru.xlstoarff.model.SpreadSheetToCsv;
import org.projectomandacaru.xlstoarff.model.Utils;
import org.projectomandacaru.xlstoarff.utils.SpreadSheetToCsvFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import weka.core.AbstractInstance;

import java.io.File;

@SpringBootApplication
public class Xls2ArffApplication implements CommandLineRunner {

    public static final String BUFFER = "5000";
    public static final int DEC = AbstractInstance.s_numericAfterDecimalPoint;

    private Logger logger = LoggerFactory.getLogger(Xls2ArffApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(Xls2ArffApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        if (args.length > 0) {
            StringBuilder nomeArquivo = new StringBuilder();
            for (int x = 0; x < args.length; x++) {
                if (x > 0) {
                    nomeArquivo.append(" ");
                }
                nomeArquivo.append(args[x]);
            }
            if (new File(nomeArquivo.toString()).exists()) {
                logger.info(String.format("Abrindo: '%s'", nomeArquivo.toString()));
                SpreadSheetToCsv spreadSheetToCsv = SpreadSheetToCsvFactory.factory(nomeArquivo.toString());
                String[] files = spreadSheetToCsv.readFileToCsv(nomeArquivo.toString(), Utils.getPath(nomeArquivo.toString()));
                for (String clusterName: files) {
                    File csvFile = new File(clusterName);
                    logger.info(String.format("Gerando arff a partir do '%s'", csvFile.getName()));
                    if (csvFile.exists()) {
                        CsvToArff.convert(clusterName, FilenameUtils.removeExtension(clusterName) + ".arff");
                        csvFile.delete();
                    } else {
                        logger.error(String.format("Arquivo '%s' não existe", csvFile.getName()));
                    }
                }
            } else {
                logger.error(String.format("Arquivo '%s' não existe", nomeArquivo.toString()));
            }
        }
    }
}