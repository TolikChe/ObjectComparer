import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Anatoly.Cherkasov on 21.04.14.
 * Описание свойств таблицы
 */
public class TableInfo {
    String name;
    String status;
    String partitioned;
    String temporary;
    String compression;
    String tablespace_name;
    String logging;
    String cache;
    String table_lock;
    String secondary;
    String nested;
    String row_movement;
    String monitoring;
    String read_only;

    // Информация о колонках таблицы
    ArrayList<ColumnInfo> columnInfoArrayList;

    /*
    * Конструктор - вариант 2
    * Получаем значения из ResultSet. Берем значения только из первой строки.
    */
    public TableInfo(ResultSet rs) throws SQLException {
        rs.next();
        this.name = rs.getString("TABLE_NAME");
        this.status = rs.getString("STATUS");
        this.partitioned = rs.getString("PARTITIONED");
        this.temporary = rs.getString("TEMPORARY");
        this.compression = rs.getString("COMPRESSION");
        this.tablespace_name = rs.getString("TABLESPACE_NAME");
        this.logging = rs.getString("LOGGING");
        this.cache = rs.getString("CACHE");
        this.table_lock = rs.getString("TABLE_LOCK");
        this.secondary = rs.getString("SECONDARY");
        this.nested = rs.getString("NESTED");
        this.row_movement = rs.getString("ROW_MOVEMENT");
        this.monitoring = rs.getString("MONITORING");
        this.read_only = rs.getString("READ_ONLY");
    }
}
