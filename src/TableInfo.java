import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Anatoly.Cherkasov on 21.04.14.
 * Описание свойств таблицы
 */
public class TableInfo {
    String owner;
    String name;
    String status;
    String partitioned;
    String temporary;
    String compression;
    String logging;
    String cache;
    String table_lock;

    // Запросы для таблицы
    //private ArrayList<String> sqlSelectList;

    // Информация о колонках таблицы
    ArrayList<ColumnInfo> columnInfoArrayList;

    /*
    * Получаем значения из ResultSet. Берем значения только из первой строки.
    */
    public void setTableInfo(ResultSet tableResultSet, ResultSet columnResultSet ) throws SQLException {
        // Заполняем Информацию о таблице из текущего сета
        this.owner = tableResultSet.getString("OWNER");
        this.name = tableResultSet.getString("TABLE_NAME");
        this.status = tableResultSet.getString("STATUS");
        this.partitioned = tableResultSet.getString("PARTITIONED");
        this.temporary = tableResultSet.getString("TEMPORARY");
        this.compression = tableResultSet.getString("COMPRESSION");
        this.logging = tableResultSet.getString("LOGGING");
        this.cache = tableResultSet.getString("CACHE");
        this.table_lock = tableResultSet.getString("TABLE_LOCK");

        // Сгенерим запросы
        // generateSqlList();
        //
        // Добавляем информацию о колонках
        while(columnResultSet.next()) {
            columnInfoArrayList.add(new ColumnInfo(columnResultSet));
        }
    }

    /*
     * Получаем значения напрямую. Берем значения только из первой строки.
     */
    public void setTableInfo( String owner,
                              String name,
                              String status,
                              String partitioned,
                              String temporary,
                              String compression,
                              String logging,
                              String cache,
                              String table_lock,
                              ArrayList<ColumnInfo> columnInfoArrayList) {
        // Заполняем Информацию о таблице из текущего сета
        this.owner = owner;
        this.name = name;
        this.status = status;
        this.partitioned = partitioned;
        this.temporary = temporary;
        this.compression = compression;
        this.logging = logging;
        this.cache = cache;
        this.table_lock = table_lock;

        // Сгенерим запросы
        // generateSqlList();

        //
        // Добавляем информацию о колонках
        this.columnInfoArrayList = columnInfoArrayList;
    }


    /*
    * Конструктор - вариант 2
    * Получаем значения из ResultSet. Берем значения только из первой строки.
    */
    public TableInfo() {
        // Инициализация массива
        columnInfoArrayList = new ArrayList<ColumnInfo>();
        // sqlSelectList = new ArrayList<String>();
    }


    /**
     * Получить объект-колонку по имени таблицы
     * @param columnName Имя колонки
     * @return
     */
    public ColumnInfo getColumnInfoByName (String columnName) {
        for ( ColumnInfo col : this.columnInfoArrayList) {
            if (col.name.equalsIgnoreCase(columnName))
                return col;
        }
        return null;
    }


}
