package by.epam_training.homework04.configuration;

import by.epam_training.homework04.server.service.parser.FileParser;
import by.epam_training.homework04.server.service.reader.FileDataReader;
import by.epam_training.homework04.factory.FileAbstractFactory;
import by.epam_training.homework04.factory.factories.TextFactory;

import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileConfiguration {

    private FileDataReader fileDataReader;
    private FileParser fileParser;
    private  String filePath;

    public FileConfiguration(){}

    public FileConfiguration(FileAbstractFactory factory, String fileName) {
        filePath = getClass().getResource("/" + fileName).getPath();
        fileDataReader = factory.getFileDataReader(filePath);
        fileParser = factory.getFileParser();
    }

    public FileDataReader getFileDataReader() {
        return fileDataReader;
    }

    public void setFileDataReader(FileDataReader fileDataReader) {
        this.fileDataReader = fileDataReader;
    }

    public FileParser getFileParser() {
        return fileParser;
    }

    public void setFileParser(FileParser fileParser) {
        this.fileParser = fileParser;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public static FileConfiguration configurationFile(String fileName){
        FileAbstractFactory factory;
        FileConfiguration fileApplication = null;

        Pattern typeOfFilePattern = Pattern.compile(ResourceBundle.getBundle("regex").getString("type.of.file"));
        Matcher typeOfFileMatcher = typeOfFilePattern.matcher(fileName);
        if (typeOfFileMatcher.find()){
            if (".txt".equals(typeOfFileMatcher.group())){
                factory = new TextFactory();
                fileApplication = new FileConfiguration(factory, fileName);
            }
        }
        return fileApplication;
    }
}
