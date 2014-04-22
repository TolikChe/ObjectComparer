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
    * Задаем значения из ResultSet для значений колонки.
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

    /*
    * Задаем значения поштучно для значений колонки.
    */
    public ColumnInfo(String name, String data_type, String data_length, String data_precision, String data_scale, String nullable, String default_length ){
        this.name = name;
        this.data_type = data_type;
        this.data_length = data_length;
        this.data_precision = data_precision;
        this.data_scale = data_scale;
        this.nullable = nullable;
        this.default_length = default_length;
    }
}
