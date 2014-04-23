import java.io.*;


/**
 * Created by Anatoly.Cherkasov on 23.04.14.
 */
public class PosibleActions {

    /* Эта функция генерит XML и складывает в файл */
    public void generate( String fileName, String dbUser, String dbPass, String dbUrl ) {
        // Соединимся с базой
        OracleConnection conn = new OracleConnection (dbUser, dbPass, dbUrl);

        // Получим информацию о таблицах
        Info info = conn.getTablesInfo();

        // Закроем соединение
        conn.closeConnection();

        if (info != null) {
            // Построим на основе информации XML документ
            /* Теперь передадим собранную информацию построителю */
            XMLBuilder xml = new XMLBuilder(info);

            /* Попросим один из вариантов xml*/
            String strXML = xml.genStandartXML();

            // Запишем XML в файл
            File file = new File(fileName);

            FileOutputStream fop = null;
            try {
                fop = new FileOutputStream(file);

                // if file doesn't exists, then create it
                if (!file.exists()) {
                    file.createNewFile();
                }

                fop.write(strXML.getBytes());
                fop.close();

                System.out.println("Done");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error in writing to file");
            }
        }
    }

    /* Эта функция, открывает файл и на основе него сравнивает есть ли объекты в базе с теме же параметрами */
    public void compare ( String fileName, String dbUser, String dbPass, String dbUrl ) {

        String strXML = "";

        // Прочитаем текст из файла
        File file = new File(fileName);
        try{
            if (!file.exists() || !file.isFile()) {
                System.out.println("Файл не существует");
                return;
            }
            // Будем читать в буффер
            BufferedReader in = new BufferedReader( new InputStreamReader(new FileInputStream(file)));

            // Что бы этономить на памяти будем конструировать строку построителем
            StringBuffer stringBuffer = new StringBuffer();
            String line;

            while ((line = in.readLine()) != null) {
                stringBuffer.append(line);
            }

            // Вот получившаяся строка
            strXML = stringBuffer.toString();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Ошибка при четнии из файла");
            return;
        }

        // Преобразуем строку в XML и заполним объект info
        XMLBuilder xml = new XMLBuilder();
        xml.parseStandartXml(strXML);

        // Теперь на основе этого объекта info построим запросы, которые отправим к базе и узнаем какие объекты совпадают а какие нет
        // Соединимся с базой
        OracleConnection conn = new OracleConnection (dbUser, dbPass, dbUrl);

        // Получим информацию о таблицах
        conn.compareTablesInfo( xml.info );

        // Закроем соединение
        conn.closeConnection();

        System.out.println("Done");
    }

}
