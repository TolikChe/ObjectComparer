/**
 * Created by Anatoly.Cherkasov on 18.04.14.
 * Класс запускалка
 */
public class ObjectComparer {

    public static void main(String[] args) {

        ParamsParse params = null;
        /**
         * Тут в зависимости от параметров мы либо делаем xml файл со структурой базы,
         * либо сравниваем существующий xml файл с тем что есть в базе
         */

        // Проверим число параметров. В качестве параметра должен быть задан файл.
        if (args.length != 1) {
            System.out.println("Неверный формат вызова.");
            System.out.println("В качетве параметра необходимо указать файл с параметрами.");
            return;
        }

        // Прочитаем параметры из файла
        try {
            params = new ParamsParse(args[0]);
        } catch (Exception e) {
            System.out.println("ObjectComparer: В процессе разбора параметров возникла ошибка");
            e.printStackTrace();
        }


        if (params.getMode().equalsIgnoreCase("generate")) {
            // Если мы тут то надо генерить файл //
            System.out.println("generate");

            try {
                // Сгенерим текст и сложим его в файл
                PosibleActions action = new PosibleActions();
                action.generate((String)params.getTarget(), (DBParams)params.getSource());
            } catch (Exception e) {
                System.out.println("ObjectComparer.generate: Ошибка при вызове генератора нового файла с информацией о схеме");
                e.printStackTrace();
            }
        }
        else if (params.getMode().equalsIgnoreCase("compare")) {
            // Если мы тут то надо сравнить существующий файл //
            System.out.println("compare");

            try {
                // Сгенерим текст и сложим его в файл
                PosibleActions action = new PosibleActions();
                action.compare((DBParams)params.getTarget(), (String)params.getSource());
            } catch (Exception e) {
                System.out.println("ObjectComparer.compare: Ошибка при вызове режима сравнениея файла со схемой");
                e.printStackTrace();
            }
        }
    }
}
