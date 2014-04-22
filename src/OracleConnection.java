/**
 * Created by Anatoly.Cherkasov on 21.04.14.
 */

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

    public String getTablesInfo(String tableName) {

        Info info = new Info();

        // Запрос для информации о таблице
        String sqlTable = "SELECT table_name, " +
                          "       status, " +
                          "       partitioned, " +
                          "       temporary, " +
                          "       compression, " +
                          "       tablespace_name, " +
                          "       logging, " +
                          "       cache, " +
                          "       table_lock, " +
                          "       secondary, " +
                          "       nested, " +
                          "       row_movement, " +
                          "       monitoring, " +
                          "       read_only " +
                          "  FROM dba_tables" +
                          " WHERE table_name = '"+tableName+"' AND owner = '"+ this.user +"'";

        // Запрос для информации о колонках таблицы
        String sqlColumn = "SELECT column_name, " +
                            "      data_type, " +
                            "      data_length, " +
                            "      data_precision, " +
                            "      data_scale, " +
                            "      nullable, " +
                            "      default_length " +
                            "  FROM dba_tab_columns" +
                            " WHERE table_name = '"+tableName+"' AND owner = '"+ this.user +"'";

        // Создадим Оператор-объект для посылки SQL операторов в драйвер
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        /* Получим информацию о таблице */
        try {

            // Создадим запрос, путем создания ResultSet объекта
            ResultSet rs = stmt.executeQuery (sqlTable);

            // Сохраним инфрмацию о таблице
            info.tableInfo = new TableInfo(rs);

            // Закрыть результирующий набор
            rs.close();
        } catch (SQLException e) {
            System.out.println("Ошибка при получени информации о таблице " + tableName);
            e.printStackTrace();
            return "";
        }

        /* Получим информацию о столбцах таблицы */
        try {
            // Создадим Оператор-объект для посылки SQL операторов в драйвер
            // Statement stmt = conn.createStatement();
            // Создадим запрос, путем создания ResultSet объекта
            ResultSet rs = stmt.executeQuery (sqlColumn);

            // Сохраним инфрмацию о колонках
            while (rs.next()){
                info.columnInfoArrayList.add(new ColumnInfo(rs));
            }

            // Закрыть результирующий набор
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return "";
        }

        // Закрыть оператор
        try {
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        /* Теперь передадим собранную информацию построителю */
        XMLBuilder xml = new XMLBuilder(info);

        /* Попросим один из вариантов xml*/
        xml.genStandartXML();

        return "";
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
