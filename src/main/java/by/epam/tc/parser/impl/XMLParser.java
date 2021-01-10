package by.epam.tc.parser.impl;

import by.epam.tc.entity.Attribute;
import by.epam.tc.entity.Element;
import by.epam.tc.parser.Parser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XMLParser implements Parser {

    private final Pattern openingTagPattern = Pattern.compile("<[^/!]+>");
    private final Pattern closingTagPattern = Pattern.compile("</.+>");
    private final Pattern openingWithClosingPattern = Pattern.compile("<.+/>");
    private final Pattern tagsWithContentPattern = Pattern.compile("<.+>.+</.+>");
    private final Pattern tagNamePattern = Pattern.compile("<(.+?)(\\s(.+?\\s+?=\\s+?\".+?\")\\s*)?/?>");
    private final Pattern attributePattern = Pattern.compile("<.+?\\s(.+?\\s+?=\\s+?\".+?\")\\s*/?>");

    public List<String> parseIntoTags(String data) {
        List<String> xmlData = new ArrayList<>();

        StringBuilder xmlLine = new StringBuilder();
        for (int i = 0; i < data.length(); i++) {
            xmlLine.append(data, i, data.indexOf('>', i) + 1);
            i += xmlLine.length() - 1;
            if (i != data.length() - 1 && data.charAt(i + 1) != '<' && data.charAt(i + 1) != '>') {
                int length = xmlLine.length();
                xmlLine.append(data, ++i, data.indexOf('>', i) + 1);
                i += xmlLine.length() - length - 1;
            }
            xmlData.add(xmlLine.toString());
            xmlLine.setLength(0);
        }
        return xmlData;
    }

    private boolean isOpeningWithClosingPattern(String s) {
        Matcher matcher = openingWithClosingPattern.matcher(s);
        return matcher.matches();
    }

    public List<Attribute> parseAttributes(String s) {
        List<Attribute> attributes = new ArrayList<>();
        Matcher matcher = attributePattern.matcher(s);
        if (matcher.find()) {
            String attributesString = matcher.group(1);
            String[] splittedAttributes = attributesString.split("\"");
            for (int i = 0; i < splittedAttributes.length; i+=2){
                String name = splittedAttributes[i];
                String value = splittedAttributes[i+1];

                name = name.replace("=", "").trim();
                attributes.add(new Attribute(name, value));
            }
        }
        return attributes;
    }

    private boolean isTagsWithContent(String s) {
        Matcher matcher = tagsWithContentPattern.matcher(s);
        return matcher.matches();
    }

    public String parseTagName(String s) {
        Matcher matcher = tagNamePattern.matcher(s);
        return matcher.find() ? matcher.group(1) : null;
    }

    public String parseContent(String s) {
        // Element by index 0 is an empty string ""
        return s.split("<.+?>")[1];
    }

    private boolean isOpeningTag(String s) {
        Matcher matcher = openingTagPattern.matcher(s);
        return matcher.matches();
    }

    private boolean isClosingTag(String s) {
        Matcher matcher = closingTagPattern.matcher(s);
        return matcher.matches();
    }

    private boolean hasAttributes(String s) {
        return s.contains("=");
    }

    @Override
    public Element parse(String xml) {
        List<String> xmlData = parseIntoTags(xml);
        List<Element> elementStack = new ArrayList<>();

        Element rootElement = new Element();
        rootElement.setName(parseTagName(xmlData.get(0)));
        if (hasAttributes(xmlData.get(0))) {
            rootElement.setAttributes(parseAttributes(xmlData.get(0)));
        }
        elementStack.add(rootElement);

        for (int i = 1; i < xmlData.size() - 1; i++) {
            String data = xmlData.get(i);
            if (isOpeningTag(data)) {
                Element element = new Element();
                element.setName(parseTagName(data));
                if (hasAttributes(data)) {
                    element.setAttributes(parseAttributes(data));
                }
                elementStack.add(element);
            } else if (isOpeningWithClosingPattern(data)) {
                Element element = new Element();
                element.setName(parseTagName(data));
                if (hasAttributes(data)){
                    element.setAttributes(parseAttributes(data));
                }
                elementStack.get(elementStack.size() - 1).addChildElement(element);
            } else if (isTagsWithContent(data)) {
                Element element = new Element();
                element.setName(parseTagName(data));
                if (hasAttributes(data)) {
                    element.setAttributes(parseAttributes(data));
                }
                element.setContent(parseContent(data).trim());
                elementStack.get(elementStack.size() - 1).addChildElement(element);
            } else if (isClosingTag(data)){
                Element poppedElement = elementStack.remove(elementStack.size() - 1);
                elementStack.get(elementStack.size() - 1).addChildElement(poppedElement);
            }
        }
        return rootElement;
    }
}