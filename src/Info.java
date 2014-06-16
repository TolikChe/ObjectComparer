import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Anatoly.Cherkasov on 21.04.14.
 */
public class Info {
    ArrayList<TableInfo> tableInfoArrayList;

    /**
     * Конструктор. Наполним объект информацией из схемы
     * @throws SQLException
     */
    public Info(DBParams schema_params) throws SQLException, ClassNotFoundException {

        // Выделим параметры подключения к схеме
        String dbUser = schema_params.getSchemaName();
        String dbPass = schema_params.getSchemaPassword();
        String dbName = schema_params.getBaseName();
        String dbHost = schema_params.getBaseHost();
        Integer dbPort = schema_params.getBasePort();

        // Устанавливаем соединение с базой
        OracleConnection conn = new OracleConnection (dbUser, dbPass, "jdbc:oracle:thin:@"+dbHost+":"+dbPort+":"+dbName );

        // Получим информацию о таблицах из базы
        this.tableInfoArrayList = conn.getTablesInfo();

        // Закроем соединение
        conn.closeConnection();

    }

    /**
     * Просто инициализация полей объекта
     */
    public Info() {
        tableInfoArrayList = new ArrayList<TableInfo>();
    }


    /**
     * Проверка того что таблица существует в списке
     * @param tableName Имя таблицы
     * @return
     */
    public boolean isTableExists (String tableName) {

        for ( TableInfo tbl : this.tableInfoArrayList) {
            if (tbl.name.equalsIgnoreCase(tableName))
                return true;
        }

        return false;
    }

    /**
     * Получить объект-таблицу по имени таблицы
     * @param tableName Имя таблицы
     * @return
     */
    public TableInfo getTableInfoByName (String tableName) {
        for ( TableInfo tbl : this.tableInfoArrayList) {
            if (tbl.name.equalsIgnoreCase(tableName))
                return tbl;
        }
        return null;
    }


}
