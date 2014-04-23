import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Anatoly.Cherkasov on 21.04.14.
 * Описание свойств колонки
 */
public class ColumnInfo {

    String owner;
    String table_name;
    String name;
    String data_type;
    String data_length;
    String data_precision;
    String data_scale;
    String nullable;
    String default_length;

    // Запросы для таблицы
    ArrayList<String> sqlSelectList;

    private void generateSqlList (){

        sqlSelectList.add("SELECT count(*) " +
                          "  FROM dba_tab_columns" +
                          " WHERE owner = '"+ owner +"' and table_name = '"+ table_name +"' and column_name = '" + name +"'");

        sqlSelectList.add("SELECT count(*) " +
                "  FROM dba_tab_columns" +
                " WHERE owner = '"+ owner +"' and table_name = '"+ table_name +"' and column_name = '" + name +"' and trim(data_type) = '"+data_type+"'");

        sqlSelectList.add("SELECT count(*) " +
                "  FROM dba_tab_columns" +
                " WHERE owner = '"+ owner +"' and table_name = '"+ table_name +"' and column_name = '" + name +"' and data_length = "+data_length);

        sqlSelectList.add("SELECT count(*) " +
                "  FROM dba_tab_columns" +
                " WHERE owner = '"+ owner +"' and table_name = '"+ table_name +"' and column_name = '" + name +"' and data_precision = "+data_precision);

        sqlSelectList.add("SELECT count(*) " +
                "  FROM dba_tab_columns" +
                " WHERE owner = '"+ owner +"' and table_name = '"+ table_name +"' and column_name = '" + name +"' and data_scale = "+data_scale);

        sqlSelectList.add("SELECT count(*) " +
                "  FROM dba_tab_columns" +
                " WHERE owner = '"+ owner +"' and table_name = '"+ table_name +"' and column_name = '" + name +"' and trim(nullable) = '"+nullable+"'");

        sqlSelectList.add("SELECT count(*) " +
                "  FROM dba_tab_columns" +
                " WHERE owner = '"+ owner +"' and table_name = '"+ table_name +"' and column_name = '" + name +"' and default_length = "+default_length);
    }

    /*
    * Задаем значения из ResultSet для значений колонки.
    */
    public ColumnInfo(ResultSet rs) throws SQLException {
        this.owner = rs.getString("OWNER");
        this.table_name = rs.getString("TABLE_NAME");
        this.name = rs.getString("COLUMN_NAME");
        this.data_type = rs.getString("DATA_TYPE");
        this.data_length = rs.getString("DATA_LENGTH");
        this.data_precision = rs.getString("DATA_PRECISION");
        this.data_scale = rs.getString("DATA_SCALE");
        this.nullable = rs.getString("NULLABLE");
        this.default_length = rs.getString("DEFAULT_LENGTH");

        sqlSelectList = new ArrayList<String>();
        generateSqlList();
    }

    /*
    * Задаем значения поштучно для значений колонки.
    */
    public ColumnInfo(String owner,
                      String table_name,
                      String name,
                      String data_type,
                      String data_length,
                      String data_precision,
                      String data_scale,
                      String nullable,
                      String default_length ){
        this.owner = owner;
        this.table_name = table_name;
        this.name = name;
        this.data_type = data_type;
        this.data_length = data_length;
        this.data_precision = data_precision;
        this.data_scale = data_scale;
        this.nullable = nullable;
        this.default_length = default_length;

        sqlSelectList = new ArrayList<String>();
        generateSqlList();
    }
}
