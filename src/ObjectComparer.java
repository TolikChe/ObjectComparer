/**
 * Created by Anatoly.Cherkasov on 18.04.14.
 * Класс запускалка
 */
public class ObjectComparer {

    public static void main(String[] args) {

        /**
         * Тут в зависимости от параметра мы либо делаем xml файл со структурой,
         * либо сравниваем существующий xml файл с тем что есть в базе
         */

        if ((args.length > 0) && (args[0].equalsIgnoreCase("-g") || args[0].equalsIgnoreCase("--generate"))) {
            /* Если мы тут то надо генерить файл */
            System.out.println("generate");

            /*Соединимся с базой*/
            OracleConnection conn = new OracleConnection ("CRM_DAILY","CRM_DAILY", "jdbc:oracle:thin:@//srv2-ora20.net.billing.ru:1521/ntdb10.net.billing.ru");
            conn.getDbBanner();

            // Получим информацию о таблицах и сохраним ее в файл
            conn.getTablesInfo();

            // Закроем соединение
            conn.closeConnection();
        }
        else if ((args.length > 0) && (args[0].equalsIgnoreCase("-c") || args[0].equalsIgnoreCase("--compare"))) {
            /* Если мы тут то надо сравнить существующий файл */
            System.out.println("compare");

            /*Соединимся с базой*/
            OracleConnection conn = new OracleConnection ("CRM_DAILY","CRM_DAILY", "jdbc:oracle:thin:@//srv2-ora20.net.billing.ru:1521/ntdb10.net.billing.ru");
            conn.getDbBanner();

            // Зачитываем файл с XML
            // Для каждого тега table проверяем что существует таблица с такими характеристикми
            conn.compareTablesInfo( "d:/file.xml" );

            // Закроем соединение
            conn.closeConnection();

        }
        else {
            /* Если мы тут то параметры мы не разобрали и надо выйти и написать какие параметры мы ждем */
            System.out.println("Параметры не разобраны");
            System.out.println("Возможные параметры: -g | --generate  - Сгенерировать новый файл");
            System.out.println("                     -c | --compare   - Сравнить с существующим файлом");
            return;
        }
    }
}
