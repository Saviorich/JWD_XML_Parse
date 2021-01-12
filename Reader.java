package by.epam.tc.reader;

import java.io.IOException;

public interface Reader {
    Object readFile(String path) throws IOException;
}