package by.epam.tc.main;

import by.epam.tc.entity.Element;
import by.epam.tc.parser.impl.XMLParser;
import by.epam.tc.reader.impl.XMLReader;
import by.epam.tc.view.XMLPrinter;

import java.io.IOException;

public class Main {
    private static final String PATH = "src/main/resources/data.xml";

    public static void main(String[] args) throws IOException {
        Reader reader = new XMLReader();
        Parser parser = new XMLParser();

        String xmlData = reader.readFile(PATH);
        Element element = parser.parse(xmlData);

        XMLPrinter.consolePrint(element);
    }
}
