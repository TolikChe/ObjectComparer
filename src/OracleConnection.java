/**
 * Created by Anatoly.Cherkasov on 21.04.14.
 */

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;

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
    public Info getTablesInfo() {

        Info info = new Info();

        // Запрос для информации о таблице
        String sqlTable = "SELECT trim(owner) as owner, "  +
                          "       trim(table_name) as table_name, " +
                          "       trim(status) as status, " +
                          "       trim(partitioned) as partitioned, " +
                          "       trim(temporary) as temporary, " +
                          "       trim(compression) as compression, " +
                          "       trim(logging) as logging, " +
                          "       trim(cache) as cache, " +
                          "       trim(table_lock) as table_lock " +
                          "  FROM dba_tables" +
                          " WHERE table_name like 'CMS%' AND owner = '"+ this.user +"'";

        // Запрос для информации о колонках таблицы
        String sqlColumn = "SELECT trim(owner) as owner, " +
                            "      trim(table_name) as table_name, " +
                            "      trim(column_name) as column_name, " +
                            "      trim(data_type) as data_type, " +
                            "      data_length as data_length, " +
                            "      data_precision as data_precision, " +
                            "      data_scale as data_scale, " +
                            "      trim(nullable) as nullable, " +
                            "      default_length as default_length " +
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
            return null;
        }

        // Вернем информацию об объектах
        return info;
    }

    /**
     * Сравниваем информацю о таблицах и колонках схемы, к которой подключились, с тем что есть в файле.
     */
    /*
    public void compareTablesInfo( Info info ) {

        // Для каждой таблицы выстраиваю запрос и проверяю что такая таблица есть
        for (TableInfo ti : info.tableInfoArrayList) {
            // Запрос для информации о таблице
            for (String sql : ti.sqlSelectList) {
                System.out.println(sql);
            }
            System.out.println("---");
            // Для каждой колонки таблицы выстраиваю запрос и проверяю что такая колонка есть
            for (ColumnInfo ci : ti.columnInfoArrayList) {
                //
                // Запрос для информации о колонке
                for (String sql : ci.sqlSelectList) {
                    System.out.println(sql);
                }
            }
            System.out.println("----------------------------------");
        }
    }
    */

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
