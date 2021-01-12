package by.epam.tc.reader;

import java.io.IOException;

public interface Reader {
    Object readFile(String path) throws IOException;
}

class A {

    {
        System.out.println("First");
    }

    {
        System.out.println("Second");
    }

    void m() {
    }

    {
        System.out.println("Third");
    }

    public static void main(String[] args) {
        new A();
    }
}
