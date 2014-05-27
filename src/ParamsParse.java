import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

/**
 * Created by Anatoly.Cherkasov on 22.05.14.
 * Класс считывает параметры из файла переданного в качестве параметра.
 *
 * Считывается xml. Разбирается его содержимое и значения раскладываются по переменным.
 * Возможны два варианта содержимого в файле параметров.
 * 1. Параметры для того что бы сгенерировать файл с информацией о схеме
 * Его струкутра имеет следующий вид
 * <params>
 *  <mode>generate</mode>
 *  <source>
 *      <db>
 *          <schema_name>CRM_DAILY</schema_name>
 *          <schema_password>CRM_DAILY</schema_password>
 *          <base_name>ntdb10</base_name>
 *      </db>
 *  </source>
 *  <target>
 *      <file_name>file.xml</file_name>
 *  </target>
 * </params>
 *
 * 2. Параметры для того что бы сравнить существующий файл с указанной схемой
 * Его структура имеет следующий вид:
 * <params>
 *  <mode>compare</mode>
 *  <source>
 *      <file_name>file.xml</file_name>
 *  </source>
 *  <target>
 *      <db>
 *          <schema_name>CRM_DAILY</schema_name>
 *          <schema_password>CRM_DAILY</schema_password>
 *          <base_name>ntdb10</base_name>
 *      </db>
 *  </target>
 * </params>
 *
 */



public class ParamsParse {

    // Режим работы. Может принимать значение generate или compare
    private String mode;

    /**
     * В зависимости от режима работы у нас в части TARGET и SOURCE могут прятаться разные объекты
     * В случае режима generate в части SOURCE у нас будут параметры базы из которой надо достать информацию о схеме, а в части TARGET будет имя файла в который надо сложить информацию о схеме
     * В случае режима compare в части SOURCE у нас будет имя файла из которого надо достать информацию о структуре схемы а в части TARGET будет имя базы с который надо сравнивать содержимое файла
     *
     * Спрячем эту логику за методами
     */

    private DBParams db;
    private String fileName;

    /**
     * Констурктор. Разбираем файл с параметрами и распихиваем параметры по переменным класса
     * Заодно выполняется проверка того что стркутра файла параметров соответствует режиму работы
     * @param filePath
     */
    public ParamsParse (String filePath) throws Exception {


        // Инициализация объекта
        db = new DBParams();

        try {
            // Откроем файл
            File fXmlFile = new File(filePath);

            // Подготовим парсер
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            // Распарсим файл
            Document doc = dBuilder.parse(fXmlFile);

            // Без этого узел может расползтись на несколько строк и будет неправильно разобран
            doc.getDocumentElement().normalize();

            // Получим режим работы
            this.mode = doc.getDocumentElement().getElementsByTagName("mode").item(0).getTextContent();

            // В зависимости от режима работы будем выбирать определенные структуры
            // Если режим неизвестный то выходим с ошибкой
            if ( !(this.mode.equalsIgnoreCase("compare") || this.mode.equalsIgnoreCase("generate")) ) {
                throw new Exception("Неизвестный режим в файле параметров");
            }

            // Получим остальные параметры
            // Имена конечных тегов одинаковые так что искать можно без условностей.
            this.fileName = doc.getDocumentElement().getElementsByTagName("file_name").item(0).getTextContent();

            db.setSchemaName(doc.getDocumentElement().getElementsByTagName("schema_name").item(0).getTextContent());
            db.setSchemaPassword(doc.getDocumentElement().getElementsByTagName("schema_password").item(0).getTextContent());
            db.setBaseName(doc.getDocumentElement().getElementsByTagName("base_name").item(0).getTextContent());
            db.setBaseHost(doc.getDocumentElement().getElementsByTagName("base_host").item(0).getTextContent());
            db.setBasePort(Integer.parseInt(doc.getDocumentElement().getElementsByTagName("base_port").item(0).getTextContent()));
        }
        catch (Exception e) {
            System.out.println("ParamsParse: Ошибка при разборе параметров из файла");
            System.out.println(e.toString());
            throw e;
        }

        // После того как файл с параметрами разобрали надо проверить что все поля заполнены
        if ( ! db.checkNotEmpty())
            throw new Exception("ParamsParse: Ошибка в параметрах. Параметры схемы заполнены не верно.");

        if (this.fileName.isEmpty())
            throw new Exception("ParamsParse: Ошибка в параметрах. Параметр file_name задан неверно.");
    }

    // Возмождность получить инфрмацию о том в каком режиме работаем
    public String getMode() {
        return mode;
    }

    /**
     * Получаем объект, который выступает в роли цели
     * В случае если режим работы generate то на выходе получим имя файла в который надо будет закинуть информацию о структуре схемы
     * В случае если режим работы compare то на выходе получим объект типа DBParams в котором будет находится параметры схемы, с которой надо выполнять сравнение
     * @return Объект - "выходная цель"
     * @throws Exception Если режим работы не generate и не compare
     */
    public Object getTarget() throws Exception {
        if (this.mode.equalsIgnoreCase("generate")){
            return this.fileName;
        }
        else if (this.mode.equalsIgnoreCase("compare")){
            return this.db;
        }
        else {
            throw new Exception("Error work-mode");
        }
    }

    /**
     * Получаем объект, который выступает в роли источника информации
     * В случае если режим работы generate то на выходе получим объект типа DBParams в котором будет находится параметры схемы, с которой надо взять информацию об объектах схемы
     * В случае если режим работы compare то на выходе получим имя файла в котором уже лежит информацию о структуре схемы. С этой структурой будем выполнять сравнение.
     * @return Объект - "источник информации"
     * @throws Exception Если режим работы не generate и не compare
     */
    public Object getSource() throws Exception {
        if (this.mode.equalsIgnoreCase("generate")){
            return this.db;
        }
        else if (this.mode.equalsIgnoreCase("compare")){
            return this.fileName;
        }
        else {
            throw new Exception("Error work-mode");
        }
    }
}
