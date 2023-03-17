import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        //Ввод данных из консоли.
        String string2 = scanner.nextLine();
        ArrayList<String> myList = new ArrayList<String>(Arrays.asList(string2.split(" ")));
        //Подсчёт всех слов в строке текста и вывод их на экран.
        System.out.println(myList.stream()
                // разбиваем строки на слова
                .flatMap(line -> Stream.of(line.split("\\s+")))
                // выкидываем небуквенные символы
                // и приводим к нижнему регистру
                .map(word -> word.replaceAll("[^A-Za-zА-Яа-яЁё]+", "").toLowerCase())
                // собираем в Map<String, Integer>
                // и считаем количество вхождений
                .collect(Collectors.toMap(key -> key, val -> 1, Integer::sum))
                .entrySet().stream()
                .count());

        //Топ-10 самых часто упоминаемых слов, упорядоченных по количеству упоминаний в
        //обратном порядке. В случае одинакового количества упоминаний слова должны быть отсортированы по алфавиту.
        myList.stream()
                // разбиваем строки на слова
                .flatMap(line -> Stream.of(line.split("\\s+")))
                // выкидываем небуквенные символы
                // и приводим к нижнему регистру
                .map(word -> word.replaceAll("[^A-Za-zА-Яа-яЁё]+", "").toLowerCase())
                // собираем в Map<String, Integer>
                // и считаем количество вхождений
                .collect(Collectors.toMap(key -> key, val -> 1, Integer::sum))
                .entrySet().stream()
                // отсортированы
                .sorted((e1, e2) -> {
                    // в порядке убывания их кол-ва упоминаний
                    int val = e1.getValue().compareTo(e2.getValue()) * -1;
                    if (val == 0) {
                        // потом уже в алфавитном порядке
                        val = e1.getKey().compareTo(e2.getKey());
                        // слова на английском идут после русских
                        // тоже в алфавитном порядке
                        if (e1.getKey().charAt(0) <= 'z'
                                && e2.getKey().charAt(0) > 'z'
                                || e1.getKey().charAt(0) > 'z'
                                && e2.getKey().charAt(0) <= 'z') {
                            val *= -1;
                        }
                    }
                    return val;
                })
                // вывод
                .forEach(e -> System.out.println(e.getValue() + " - " + e.getKey()));
    }
}