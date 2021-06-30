package work_with_DOM;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class Dom_add_del_elem {
    public static void main(String[] args) {
        try {
            // Чтобы загрузить структуру создаём объект DocumentBuilder
            DocumentBuilder doc_build = DocumentBuilderFactory.newInstance().newDocumentBuilder();

            // Загружаем файл xml и получаем его в виде дерева
            Document doc = doc_build.parse("directory.xml");

            addPerson(doc);
            deletePerson(doc);

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    // Метод добавления элементов в файл xml
    public static void addPerson(Document doc){
        String data;
        // Получаем корень дерева данных
        Node root = doc.getDocumentElement();
        // Создаём новую персону по элементам
        Element person = doc.createElement("person");
        // Создаём элемент name
        Element name =  doc.createElement("name");
        // Заполняем элемент данными
        data = inputData("name");
        name.setTextContent(data);

        Element phone = doc.createElement("phone");
        data = inputData("phone");
        phone.setTextContent(data);

        Element email = doc.createElement("email");
        data = inputData("email");
        email.setTextContent(data);

        // Добавляем элементы в новую персону
        person.appendChild(name);
        person.appendChild(phone);
        person.appendChild(email);

        // Добавляем персону в список
        root.appendChild(person);

        // Записываем данные в файл
        writeData(doc);
    }
    // Метод запроса данных от пользователя
    public static String inputData(String element){
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter "+element);
        return scan.next();
    }
    // Метод записи данных в файл
    public static void writeData(Document doc){
        try {
            Transformer trf = TransformerFactory.newInstance().newTransformer();
            DOMSource source = new DOMSource(doc);
            FileOutputStream fos = new FileOutputStream("new_directory.xml");
            StreamResult result = new StreamResult(fos);
            trf.transform(source,result);

        }catch (IOException | TransformerException ex){
            ex.printStackTrace();
        }
    }
    //метод удаления элементов из файла xml
    public static void deletePerson(Document doc){
        String name_for_del;
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter name for delete ");
        name_for_del = scan.next();

        NodeList persons = doc.getElementsByTagName("person");
        for (int i = 0; i < persons.getLength(); i++) {
            Element person  =(Element)persons.item(i);
            Element name = (Element) person.getElementsByTagName("name").item(0);
            String name_data = name.getTextContent();
            name_data =  name_data.replaceAll("\\s+","");
            if (name_data.equals(name_for_del)){
                person.getParentNode().removeChild(person);
            }
        }
        writeData(doc);
    }
}
