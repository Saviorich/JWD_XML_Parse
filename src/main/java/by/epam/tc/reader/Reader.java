package by.epam.tc.reader;

import java.io.IOException;

public interface Reader {
    String readFile(String path) throws IOException;
}
