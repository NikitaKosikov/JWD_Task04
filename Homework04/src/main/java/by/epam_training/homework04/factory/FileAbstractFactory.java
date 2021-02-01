package by.epam_training.homework04.factory;

import by.epam_training.homework04.server.service.parser.FileParser;
import by.epam_training.homework04.server.service.reader.FileDataReader;

public interface FileAbstractFactory {
    FileParser getFileParser();
    FileDataReader getFileDataReader(String path);
}
