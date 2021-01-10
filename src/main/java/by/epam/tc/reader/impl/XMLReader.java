package by.epam.tc.reader.impl;

import by.epam.tc.reader.Reader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class XMLReader implements Reader {

    @Override
    public String readFile(String path) throws IOException {
        StringBuilder data = new StringBuilder();

        BufferedReader reader = new BufferedReader(new FileReader(path));

        while(reader.ready()) {
            String line = reader.readLine().trim();
            if (!line.isEmpty()) {
                data.append(line.startsWith("<") ? line : " " + line);
            }
        }

        reader.close();
        return data.toString();
    }
}
