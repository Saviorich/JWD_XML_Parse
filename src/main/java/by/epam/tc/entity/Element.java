package by.epam.tc.entity;

import java.util.ArrayList;
import java.util.List;

public class Element {
    private String name;
    private List<Attribute> attributes;
    private List<Element> childs;
    private String content;

    {
        attributes = new ArrayList<>();
        childs = new ArrayList<>();
    }

    public Element() {}

    public Element(String name, List<Attribute> attributes, List<Element> childs, String content) {
        this.name = name;
        this.attributes = attributes;
        this.childs = childs;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Attribute> getAttributes() {
        return new ArrayList<>(attributes);
    }

    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }

    public List<Element> getChilds() {
        return childs;
    }

    public void setChilds(List<Element> childs) {
        this.childs = childs;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void addAttribute(Attribute attribute) {
        attributes.add(attribute);
    }

    public void addAttribute(String name, String value) {
        attributes.add(new Attribute(name, value));
    }

    public void addElement(String name, List<Attribute> attributes, List<Element> elements, String content) {
        elements.add(new Element(name, attributes, elements, content));
    }

    public void addElement(Element element) {
        childs.add(element);
    }

    public void addChildElement(Element element) {
        childs.add(element);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Element element = (Element) o;

        if (!name.equals(element.name)) return false;
        if (attributes != null ? !attributes.equals(element.attributes) : element.attributes != null) return false;
        if (childs != null ? !childs.equals(element.childs) : element.childs != null)
            return false;
        return content != null ? content.equals(element.content) : element.content == null;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + (attributes != null ? attributes.hashCode() : 0);
        result = 31 * result + (childs != null ? childs.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Element{");
        sb.append("name='").append(name).append('\'');
        sb.append(", attributes=").append(attributes);
        sb.append(", child elements=").append(childs);
        sb.append(", content='").append(content).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
