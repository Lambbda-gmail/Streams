import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Main {
    /* static test values because need only one instance of each /
    / arraylist for exercises 1-3 */
    private static final ArrayList<Integer> intList =
            Stream.of(5, 2, 10, 9, 4, 3, 10, 1, 13)
                    .collect(Collectors.toCollection(ArrayList::new));

    /* Exercise 1 /
    / Реализуйте удаление из листа всех дубликатов */
    @Test
    void Exercise_1() {
        var result = intList.stream()
                .distinct()
                .toList()
                .toString();
        assertEquals("[5, 2, 10, 9, 4, 3, 1, 13]", result);
    }

    /* Exercise 2 /
    / Найдите в списке целых чисел 3-е наибольшее число (пример: 5 2 10 9 4 3 10 1 13 => 10) */
    @Test
    void Exercise_2() {
        var result = intList.stream()
                .sorted((x, y) -> Integer.compare(y, x))
                .skip(2)
                .findFirst()
                .orElse(-1)
                .toString();
        assertEquals("10", result);
    }

    /* Exercise 3 /
    / Найдите в списке целых чисел 3-е наибольшее «уникальное» число (пример: 5 2 10 9 4 3 10 1 13 => 9,
    в отличие от прошлой задачи здесь разные 10 считает за одно число) */
    @Test
    void Exercise_3() {
        var result = intList.stream()
                .distinct()
                .sorted((x, y) -> Integer.compare(y, x))
                .skip(2)
                .findFirst()
                .orElse(-1)
                .toString();
        assertEquals("9", result);
    }

    private record Employee(String name, int age, String role) {
    }

    /* arraylist of Employee for exercises 4-5 */
    private static final ArrayList<Employee> employeeList = Stream.of(
            new Employee("Людмила", 32, "Бухгалтер"),
            new Employee("Пахом", 72, "Инженер"),
            new Employee("Афанасий", 54, "Инженер"),
            new Employee("Геннадий", 46, "Уборщик"),
            new Employee("Коловрат", 56, "Инженер"),
            new Employee("Иннокентий", 60, "Сварщик"),
            new Employee("Евлампий", 64, "Инженер")
    ).collect(Collectors.toCollection(ArrayList::new));

    /* Exercise 4 /
    / Имеется список объектов типа Сотрудник (имя, возраст, должность),
    необходимо получить список имен 3 самых старших сотрудников с должностью «Инженер», в порядке убывания возраста */
    @Test
    void Exercise_4() {
        var result = employeeList.stream()
                .filter(x -> x.role.equals("Инженер"))
                .sorted((x, y) -> Integer.compare(y.age, x.age))
                .limit(3)
                .map(Employee::name)
                .toList()
                .toString();
        assertEquals("[Пахом, Евлампий, Коловрат]", result);
    }

    /* Exercise 5 /
    / Имеется список объектов типа Сотрудник (имя, возраст, должность),
    посчитайте средний возраст сотрудников с должностью «Инженер» */
    @Test
    void Exercise_5() {
        var result = employeeList.stream()
                .filter(x -> x.role.equals("Инженер"))
                .collect(Collectors.averagingInt(x -> x.age))
                .toString();
        assertEquals("61.5", result);
    }

    /* arraylist for exercise 6 and 8, also for generation stringLine */
    private static final ArrayList<String> stringList =
            Stream.of("shortstring1", "shortstr2", "someverylongstring",
                            "notlongstring", "hhortstring1", "shortstring1", "shortstring1")
                    .collect(Collectors.toCollection(ArrayList::new));

    /* Exercise 6 /
    / Найдите в списке слов самое длинное */
    @Test
    void Exercise_6() {
        var result = stringList.stream()
                .max(Comparator.comparingInt(String::length))
                .orElse("");
        assertEquals("someverylongstring", result);
    }

    /* string for exercise 7 generated from stringList */
    private static final String stringLine = String.join(" ", stringList);

    /* Exercise 7 /
    / Имеется строка с набором слов в нижнем регистре, разделенных пробелом.
    Постройте хеш-мапы, в которой будут хранится пары: слово - сколько раз оно встречается во входной строке */
    @Test
    void Exercise_7() {
        var anotherList = stringLine.split(" ");   // зачем даётся строка если её нельзя стримить? Есть какой-то лайфхак по подсчёту substring в строке?
        var result = Arrays.stream(anotherList)
                .distinct()
                .collect(Collectors.toMap(x -> x, y -> Arrays.stream(anotherList)
                        .filter(z -> z.equals(y))
                        .count())) // "сколько раз слово встречается во входной строке" имеет двоякое значение, мы либо считаем совпадения целого слова с разделением на пробелы, либо общее количество совпадений по строке, даже как часть другого слова
                .toString();
        assertEquals("{shortstr2=1, shortstring1=3, notlongstring=1, someverylongstring=1, hhortstring1=1}", result);
    }

    /* Exercise 8 /
    / Отпечатайте в консоль строки из списка в порядке увеличения длины слова,
    если слова имеют одинаковую длины, то должен быть сохранен алфавитный порядок */
    @Test
    void Exercise_8() {
        var result = stringList.stream()
                .sorted(Comparator.naturalOrder()) // что значит "сохранён" алфавитный порядок если его нет? Если сохранить порядок без алфавитного, то этот sorted не нужен
                .sorted(Comparator.comparing(String::length))
                .toList()
                .toString();
        assertEquals("[shortstr2, hhortstring1, shortstring1, shortstring1, shortstring1, notlongstring, someverylongstring]", result);
    }

    /* array of string for exercise 9 */
    private static final String[] stringArray = new String[]{
            "Lorem ipsum dolor sit amet",
            "consectetur adipiscing elit Duis elementum",
            "nunc nisl vitae pulvinar sapien",
            "posuere nec Nam nibh lacus",
            "aliquam non tempor in commodo",
            "in lorem Curabitur ac tempor"
    };

    /* Exercise 9 /
    / Имеется массив строк, в каждой из которых лежит набор из 5 строк, разделенных пробелом,
    найдите среди всех слов самое длинное, если таких слов несколько, получите любое из них */
    @Test
    void Exercise_9() {
        var result = Arrays.stream(stringArray)
                .mapMulti((x, consumer) -> Arrays.stream(x.split(" "))
                        .forEach(consumer))
                .max(Comparator.comparing(x -> x.toString().length()))
                .orElse("");
        assertEquals("consectetur", result);
    }
}
