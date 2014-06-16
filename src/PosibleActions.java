import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;


/**
 * Created by Anatoly.Cherkasov on 23.04.14.
 *
 * Класс содержит вего два метода: generate и compare
 *
 * Метод generate создает заданный файл и наполняет его данными на основе указанной схемы.
 * Метод compare на основе существующего файла выполняет сравнение с указанной схемой и создает файл - результат сравненеия. поле этого к файле применятеся xslt преобразование для получения html отчета.
 */

public class PosibleActions {

    /**
     *  Эта функция генерит XML на основе информации из схемы. Результат помещается в файл.
     * @param fileName Файл в который необходимо пометить рузельтат
     * @param schema_params Параметры схемы к которой необходимо подключиться
     */
    public void generate( String fileName, DBParams schema_params ) throws SQLException, IOException, ParserConfigurationException, ClassNotFoundException {

        // Получим информацию о таблицах. Эта информация будет хранится в структуре типа Info
        // Будет выполнено соединение со схемой и от туда считана информация
        Info info = null;
        try {
            info = new Info(schema_params);
        } catch (SQLException e) {
            System.out.println("PosibleActions.generate: ошибка при получении информации из схемы");
            e.printStackTrace();
            throw e;
        }

        // Если инофрмация получена успешно то надо собрать документ
        String strXML = null;
        try {
            // Создадим построитель xml.
            XMLBuilder xmlBuilder = new XMLBuilder();
            // Попросим один из вариантов xml. (Возможно позже появятся другие)
            strXML = xmlBuilder.genStandartXML(info);
        } catch (ParserConfigurationException e) {
            System.out.println("PosibleActions.generate: ошибка при построении xml");
            e.printStackTrace();
            throw e;
        }

        // Запишем полученный XML в файл
        File file = new File(fileName);
        // Если файла не существует то создадим его
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("PosibleActions.generate: ошибка при создании файла результата");
                e.printStackTrace();
                throw e;
            }
        }

        // Выполним запись в файл
        try {
            FileOutputStream fop = new FileOutputStream(file);
            fop.write(strXML.getBytes());
            fop.close();
        } catch (IOException e) {
            System.out.println("PosibleActions.generate: ошибка при записи в файл");
            e.printStackTrace();
            throw e;
        }

        System.out.println("Generating done");

    }

    /* Эта функция, открывает файл и на основе него сравнивает есть ли объекты в базе с теме же параметрами */
    /* Делаем следующее. Считываем XML из файла в структуру. */
    /* Строим вторую структуру по текущему состоянию. */
    /* Начинаем сравнивать поэлементно. На выходе будет XML новой структуры. */

    /**
     * Эта функция, открывает файл и на основе него сравнивает есть ли объекты в базе с теме же параметрами
     * Делаем следующее. Считываем XML из файла в структуру.
     * Строим вторую структуру по текущему состоянию.
     * Начинаем сравнивать поэлементно. На выходе будет XML новой структуры.
     * @param schema_params Параметры схемы к которой необходимо подключиться
     * @param fileName Файл из которого необходимо считать информацию о схеме
     * @throws SQLException
     */
    public void compare ( DBParams schema_params, String fileName ) throws Exception {

        String strXML = "";
        // Прочитаем текст из файла
        try {
         byte[] encoded = Files.readAllBytes(Paths.get(fileName));
         strXML =  new String(encoded);

        } catch (IOException e) {
            System.out.println("PosibleActions.compare: ошибка при чтении из файла");
            e.printStackTrace();
            throw e;
        }

        // Создадим построитель - преобразователь xml.
        XMLBuilder xmlBuilder = new XMLBuilder();


        // Преобразуем строку в XML и заполним объект info
        Info fileInfo = null;
        try {
            // Нам нужно разобрать один из вариантов xml. (Возможно позже появятся другие)
            fileInfo = xmlBuilder.parseStandartXml(strXML);
        } catch (Exception e) {
            System.out.println("PosibleActions.compare: ошибка при разборе xml");
            e.printStackTrace();
            throw e;
        }

        // Получим информацию о таблицах. Эта информация будет хранится в структуре типа Info
        // Будет выполнено соединение со схемой и от туда считана информация
        Info baseInfo = null;
        try {
            baseInfo = new Info(schema_params);
        } catch (SQLException e) {
            System.out.println("PosibleActions.compare: ошибка при получении информации из схемы");
            e.printStackTrace();
            throw e;
        }

        // Запустим сравнение. На выходе получим XML
        String strDiffXML = null;
        try {
            strDiffXML = xmlBuilder.compareStandartXML(baseInfo, fileInfo);
        } catch (Exception e) {
            System.out.println("PosibleActions.compare: ошибка при сравнении информации из схемы и из файла");
            e.printStackTrace();
            throw e;
        }

        // Запишем XML в файл, этол сделано дял удобства разработки и в дальнейшем быстрой смены xslt без изменения приложения
        // Запись можно не делдать так как ниже все равно оперируем переменной с xml а не файлом
        File file_diff = new File("dif_file.xml");
        // Если файла не существует то создадим его
        if (!file_diff.exists()) {
            try {
                file_diff.createNewFile();
            } catch (IOException e) {
                System.out.println("PosibleActions.compare: ошибка при создании файла - результата сравнения");
                e.printStackTrace();
                throw e;
            }
        }

        // Произведем запись в файл
        try {
            FileOutputStream fop = new FileOutputStream(file_diff);

            fop.write(strDiffXML.getBytes());
            fop.close();

        } catch (IOException e) {
            System.out.println("PosibleActions.compare: ошибка при записи файла - результата сравнения");
            e.printStackTrace();
            throw e;
        }

        // Выполним преобразование xslt
        try {
            TransformerFactory factory = TransformerFactory.newInstance();
            Source xslt = new StreamSource(new File("dif_file.xsl"));
            Transformer transformer = factory.newTransformer(xslt);

            // Source text = new StreamSource(new File("dif_file.xml"));
            Source text = new StreamSource( new ByteArrayInputStream( strDiffXML.getBytes()));
            transformer.transform(text, new StreamResult("output.html"));
        } catch (TransformerException e) {
            System.out.println("PosibleActions.compare: ошибка при преобразования файла - результата сравнения в html");
            e.printStackTrace();
            throw e;
        }

        System.out.println("Comparing done");
    }
}
