import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//На базе подходящего контейнерного класса разработать класс «Телефонный справочник», который бы содержал
// произвольное количество абонентов. Для каждого абонента известна фамилия и один номер телефона. Фамилии
// разных абонентов могут быть одинаковыми, номера телефонов - нет. Справочник должен иметь методы поиска
// номеров телефонов по фамилии и поиска фамилии по номеру телефона. Базовый контейнерный класс выбрать самостоятельно.

//     1. Добавить в класс «Телефонный справочник» методы сохранения справочника в файле и загрузки справочника из файла.
//     2. Добавить возможность пополнения справочника новыми абонентами с клавиатуры.


//Класс Main реализует «Телефонный справочник» на основе контейнера HashMap<String, String>
//в качестве ключа используется номер телефона в виде строки, а в качестве значения - фамилия
//класс включает в себя метод main который запускает программу, а также включены методы:
//add - добавляет запись по заданным номеру телефона и фамилии
//del - удаляет запись по номеру телефона
//save - сохраняет в текстовом файле phone.txt
//load - загружает из текстового файла phone.txt
//PrintPhonebook - выводит на екран все записи
//FindSurname - производит поиск фамилии по номеру телефона
//FindNumberPhone - производит поиск списка номеров по фамилии

public class Main  {
    private static Map<String, String> string = new HashMap<String, String>();

    //addPB - добавляет запись по заданным номеру телефона и фамилии
    private static void add(String phone, String name) {
        string.put(phone, name);
    }

    //delPB - удаляет запись по номеру телефона
    private static void del(String phone) {
        string.remove(phone);
    }

    //savePB - сохраняет БД в текстовом файле phone.txt
    private static void save() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(new File("phone.txt")));
        for(Map.Entry<String,String> k: string.entrySet()){
            writer.write(k.getKey() + " " + k.getValue()+System.lineSeparator());
        }
        writer.close();
    }

    //loadPB - загружает БД из текстового файла phone.txt
    //производит проверку на наличие файла
    public static void load() throws IOException {
        File file = new File("phone.txt");
        if (file.exists()){
            BufferedReader reader = new BufferedReader(new FileReader(new File("phone.txt")));
            String act;
            while ((act=reader.readLine())!=null) {
                String[] dat = act.split(" ");
                string.put(dat[0], dat[1]);
            }
            reader.close();
        }
    }

    //PrintPhonebook - выводит на екран все записи БД
    public static void PrintPhonebook(){
        System.out.println("Телефонный справочник: ");
        for(Map.Entry<String,String> k: string.entrySet()){
            System.out.println(k.getValue()+": "+ k.getKey());
        }
    }

    //FindSurname - производит поиск фамилии по номеру телефона заданому в качестве аргумента
    //возвращает строку
    public static String FindSurname(String number){
        String result = string.get(number);
        if (result == null) return "абонент с таким номером не найдей";
        return result;
    }

    //FindNumberPhone - производит поиск списка номеров по фамилии заданой в качестве аргумента
    //возвращает массив строк
    public static String[] FindNumberPhone(String surname){
        List <String> result = new ArrayList<String>();
        for (Map.Entry entry : string.entrySet()) {
            if (surname.equalsIgnoreCase((String)entry.getValue())){
                result.add((String)entry.getKey());
            }
        }
        if (result.size() == 0) result.add("абонент с такой фамилией не найден");
        return result.toArray(new String[0]);
    }

    public static void main(String[] args) throws IOException {
        //переменная описывает вызываемое действие
        String act;

        //загрузка БД
        load();
        //вывод записей на екран
        PrintPhonebook();

        //вывод на екран описания возможных действий с указанием команд
        System.out.println("выбор действия: (add)добавить данные, (del)удалить данные, (num) найти номера по фамилии, (sur)найти фамилию, " +
                "(save)сохранить, (exit)выход");

        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        act = bf.readLine();
        while (!act.equals("exit")) {
            String str="del";
            switch (str) {
                //добавление записи
                case "add":
                {
                    System.out.println("Введите фамилию:");
                    String name = bf.readLine();
                    System.out.println("Введите телефон:");
                    String phone = bf.readLine();
                    add(phone, name);
                }break;

                    //удаление записи
                case "del":
                {
                    System.out.println("Введите телефон:");
                        String phone = bf.readLine();
                        del(phone);
                    break;
                }
                case "num":
                    //поиск номеров по фамилии
                {
                    System.out.println("Введите фамилию:");
                            String surname = bf.readLine();
                            String[] numbers = FindNumberPhone(surname);
                            for (String number : numbers) {
                                System.out.println(number);
                            }
                            break;

                }
                    case "sur":
                            //поиск фамилии по номеру
                            {
                                System.out.println("Введите номер:");
                                String number = bf.readLine();
                                System.out.println(FindSurname(number));
                                break;
                            }
                case "save": {
                                //сохранение БД в файл
                                {
                                    save();
                                    break;
                                }
                            }

                default:
                {
                //запрос на следующее действие
                System.out.println("выбор действия: (add)добавить данные, (del)удалить данные, (num) найти номера по фамилии, (sur)найти фамилию, " +
                        "(save)сохранить, (exit)выход");
                act = bf.readLine();
                    }
                }



            }
        }

    }

