package work_with_DOM;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class Dom_read_xml {
    public static void main(String[] args) {
        try {
            // Чтобы загрузить структуру создаём объект DocumentBuilder
            DocumentBuilder doc_build = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            // Загружаем файл xml и получаем его в виде дерева
            Document doc = doc_build.parse("directory.xml");

            // Получаем корень дерева данных
            Node root = doc.getDocumentElement();

            System.out.println("List of persons:");
            System.out.println();

            // Просматриваем все подэлементы корня , т.е. person
            NodeList persons = root.getChildNodes();
            for (int i = 0; i < persons.getLength(); i++) {
                Node person = persons.item(i);
                // Если Node не текст, то это тэг person - заходим внутрь
                if (person.getNodeType() != Node.TEXT_NODE) {
                    NodeList person_dataset = person.getChildNodes();
                    for (int j = 0; j < person_dataset.getLength(); j++) {
                        Node person_data = person_dataset.item(j);
                        // Если Node не текст, то это один из параметров person . Выводим на экран.
                        if (person_data.getNodeType() != Node.TEXT_NODE) {
                            System.out.println(person_data.getNodeName() + ":" +
                                    person_data.getChildNodes().item(0).getTextContent());
                        }
                    }
                    System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
        }
    }
}
