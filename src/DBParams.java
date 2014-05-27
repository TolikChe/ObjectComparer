/**
 * Created by Anatoly.Cherkasov on 22.05.14.
 *
 * Вспомогательный класс для хранения параметров базы
 */
public class DBParams {

    private String schemaName;
    private String schemaPassword;
    private String baseName;
    private String baseHost;
    private Integer basePort;


    public String getBaseName() {
        return baseName;
    }

    public void setBaseName(String baseName) {
        this.baseName = baseName;
    }

    public String getSchemaPassword() {
        return schemaPassword;
    }

    public void setSchemaPassword(String schemaPassword) {
        this.schemaPassword = schemaPassword;
    }

    public String getSchemaName() {
        return schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    public String getBaseHost() {
        return baseHost;
    }

    public void setBaseHost(String baseHost) {
        this.baseHost = baseHost;
    }

    public Integer getBasePort() {
        return basePort;
    }

    public void setBasePort(Integer basePort) {
        this.basePort = basePort;
    }


    /**
     * Проверка того что все поля не пустые
     * @return
     */
    public boolean checkNotEmpty() {
        if (this.schemaName.isEmpty() || this.schemaPassword.isEmpty() || this.baseName.isEmpty() || this.basePort.toString().isEmpty() || this.baseHost.isEmpty() ) {
            return false;
        }
        return true;
    }


}
