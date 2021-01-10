package by.epam.tc.view;

import by.epam.tc.entity.Element;

public class XMLPrinter {
    private static final String TAG_NAME = "TAG - ";
    private static final String CONTENT = "CONTENT - ";

    public static void consolePrint(Element e) {
        consolePrint(e, 1);
    }

    private static void consolePrint(Element e, int shift) {
        StringBuilder tabs = new StringBuilder();

        for (int i = 0; ++i<shift; tabs.append("\t"));

        StringBuilder info = new StringBuilder(tabs);

        info.append(TAG_NAME)
                .append(e.getName())
                .append((e.getAttributes().size() != 0) ? e.getAttributes() : "")
                .append("\n");

        if (e.getContent() != null) {
            info.append(tabs)
                    .append(CONTENT)
                    .append(e.getContent());
        }
        info.append("\n");

        System.out.println(info);

        shift++;
        if (e.getChildElements().size() != 0) {
            for (Element childElement : e.getChildElements()) {
                consolePrint(childElement, shift);
            }
        }
    }
}