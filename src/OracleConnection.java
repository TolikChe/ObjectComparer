/**
 * Created by Anatoly.Cherkasov on 21.04.14.
 */

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;

public class OracleConnection {


    String jdbcUrl = null;
    String user = null;
    String password = null;
    Connection conn = null;

    /**
     * Констурктор. Устанавливает соединеие с базой
     *
     * @param user     Имя схемы
     * @param password Пароль для схемы
     * @param dbUrl    URL строчка с подключением вида jdbc:oracle:thin:@machine_name:1521:database_name
     */
    public OracleConnection(String user, String password, String dbUrl) {
        this.jdbcUrl = dbUrl;
        this.user = user;
        this.password = password;


        /*Попытаемся установить соединение*/
        try {
            conn = DriverManager.getConnection(this.jdbcUrl, this.user, this.password);
        } catch (SQLException e) {
            System.out.println("Connection Failed!");
            e.printStackTrace();
            return;
        }

        /*Проверим что соединение установилось*/
        if (conn != null) {
            System.out.println("Connection OK!");
        } else {
            System.out.println("Failed to make connection!");
            return;
        }

        /*Получим информацию  о соединении*/
        try {
            DatabaseMetaData dma = conn.getMetaData();
            System.out.println("Connected to _ " + dma.getURL());
            System.out.println("Driver        _ " + dma.getDriverName());
            System.out.println("Version       _ " + dma.getDriverVersion());
            System.out.println("__");
        } catch (SQLException e) {
            System.out.println("Error in getting metadata!");
            e.printStackTrace();
            return;
        }

    }

    /**
     * Генерим информацю о таблицах и колонках схемы, к которой подключились
     */
    public void getTablesInfo() {

        Info info = new Info();

        // Запрос для информации о таблице
        String sqlTable = "SELECT table_name, " +
                          "       status, " +
                          "       partitioned, " +
                          "       temporary, " +
                          "       compression, " +
                          "       logging, " +
                          "       cache, " +
                          "       table_lock, " +
                          "  FROM dba_tables" +
                          " WHERE table_name like 'CMS%' AND owner = '"+ this.user +"'";

        // Запрос для информации о колонках таблицы
        String sqlColumn = "SELECT column_name, " +
                            "      data_type, " +
                            "      data_length, " +
                            "      data_precision, " +
                            "      data_scale, " +
                            "      nullable, " +
                            "      default_length " +
                            "  FROM dba_tab_columns" +
                            " WHERE owner = '"+ this.user +"' and table_name = ";

        ResultSet tableResultSet;
        ResultSet columnResultSet;

        /* Получим информацию о таблице */
        try {
            // Получим информацию о таблицах
            Statement  tblStatement = conn.createStatement();
            tableResultSet = tblStatement.executeQuery (sqlTable);
            while (tableResultSet.next()) {
                //
                Statement colStatement = conn.createStatement();
                // получим инфорацию о колонках таблицы
                columnResultSet = colStatement.executeQuery (sqlColumn + "'" + tableResultSet.getString("TABLE_NAME") + "'");
                // Создадим объект с информацией
                TableInfo ti = new TableInfo();
                ti.setTableInfo(tableResultSet,columnResultSet);
                // Добавим объект в коллекцию
                info.tableInfoArrayList.add( ti );
                // Закроем набор
                columnResultSet.close();
                colStatement.close();
            }
            // Закрыть результирующий набор
            tableResultSet.close();
            tblStatement.close();
        } catch (SQLException e) {
            System.out.println("Ошибка при получени информации о таблицах");
            e.printStackTrace();
        }

        /* Теперь передадим собранную информацию построителю */
        XMLBuilder xml = new XMLBuilder(info);

        /* Попросим один из вариантов xml*/
        xml.genStandartXML();
    }

    /**
     * Сравниваем информацю о таблицах и колонках схемы, к которой подключились, с тем что есть в файле.
     */
    public void compareTablesInfo( String fileName ) {
        /* Теперь создадим объект построитель */
        XMLBuilder xml = new XMLBuilder();

        /* Передадим ему строку с XML что бы он распихал ее по info*/
        xml.parseStandartXml(fileName);

        /* Теперь для каждой таблицы выстраиваю запрос и проверяю что такая таблица есть */
        for (TableInfo ti : xml.info.tableInfoArrayList) {
            // Запрос для информации о таблице
            String sqlTable = "SELECT count(*) " +
                                "  FROM dba_tables" +
                                " WHERE table_name = '" + ti.name + "' AND owner = '"+ this.user +"'";

            System.out.println( sqlTable );
            /* Для каждой колонки таблицы выстраиваю запрос и проверяю что такая колонка есть */
            for (ColumnInfo ci : ti.columnInfoArrayList) {
                //
                // Запрос для информации о колонке
                String sqlColumn = "SELECT count(*)" +
                                    "  FROM dba_tab_columns" +
                                    " WHERE owner = '"+ this.user +"' and table_name = '"+ ti.name +"' and column_name = '" + ci.name +"'" ;
                System.out.println( sqlColumn );
            }
        }
    }


    /**
     * Получить баннер от базы. Для проверки содеинения
     */
    public void getDbBanner() {

        System.out.println("Banner:");

        String sql = "select banner from v$version";
        try {
            // Создадим Оператор-объект для посылки SQL операторов в драйвер
            Statement stmt = conn.createStatement();
            // Создадим запрос, путем создания ResultSet объекта
            ResultSet rs = stmt.executeQuery (sql);
            // Показать все колонки и ряды из набора результатов
            while (rs.next()) {
                System.out.println(rs.getString("BANNER"));
            }
            // Закрыть результирующий набор
            rs.close();
            // Закрыть оператор
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Закрываем соединение с базой
     */
    public void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println("Failed to close connection!");
                e.printStackTrace();
            }
        }
    }

}
