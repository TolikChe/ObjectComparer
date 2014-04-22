import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Anatoly.Cherkasov on 21.04.14.
 * Описание свойств колонки
 */
public class ColumnInfo {

    String name;
    String data_type;
    String data_length;
    String data_precision;
    String data_scale;
    String nullable;
    String default_length;

    /*
    * Конструктор - вариант 1
    * Получаем значения из ResultSet. Берем значения только из первой строки.
    */
    public ColumnInfo(ResultSet rs) throws SQLException {
        this.name = rs.getString("COLUMN_NAME");
        this.data_type = rs.getString("DATA_TYPE");
        this.data_length = rs.getString("DATA_LENGTH");
        this.data_precision = rs.getString("DATA_PRECISION");
        this.data_scale = rs.getString("DATA_SCALE");
        this.nullable = rs.getString("NULLABLE");
        this.default_length = rs.getString("DEFAULT_LENGTH");
    }
}
