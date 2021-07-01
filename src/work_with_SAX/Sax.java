package work_with_SAX;

import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.Attributes;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class Sax {
    public static void main(String[] args) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            DefaultHandler handler = new DefaultHandler() {
                boolean name = false;
                @Override
                public void startElement(String uri, String localName, String qName, Attributes attributes){
                    // Если тэг имеет имя name, то мы этот момент отмечаем - начался тэг name
                    if (qName.equalsIgnoreCase("name")) {
                        name = true;
                    }
                }
                // Метод вызывается когда SAXParser считывает текст между тэгами
                @Override
                public void characters(char[] ch, int start, int length){
                    // Если перед этим мы отметили, что имя тэга name - значит нам надо текст использовать.
                    if (name) {
                        System.out.println("Name: " + new String(ch, start, length));
                        name = false;
                    }
                }
            };
            // Стартуем разбор методом parse, которому передаем наследника от DefaultHandler,
            // который будет вызываться в нужные моменты
            parser.parse("directory.xml", handler);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
