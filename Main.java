package kata.test.calc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Main {
    public static int value(char romanNumeral) { //Функция возвращает цифровое значение введен. римской цифры
        if (romanNumeral == 'I')
            return 1;
        if (romanNumeral == 'V')
            return 5;
        if (romanNumeral == 'X')
            return 10;
        if (romanNumeral == 'L')
            return 50;
        if (romanNumeral == 'C')
            return 100;
        return -1;
    }

    static int[] values = {100, 50, 10, 5, 1};
    static String[] romanNumerals = {"C", "L", "X", "V", "I"};

    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String userInput;
        while (true) {
            try {
                userInput = reader.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);

            }
            if (isSingleNumeral(userInput)) {
                try {
                    throw new IOException();
                } catch (IOException e) {
                    System.out.println("Строка не является математической операцией.");
                    break;
                }
            } else if (isWrongFormat(userInput)) {
                try {
                    throw new IOException();
                } catch (IOException e) {
                    System.out.println("Формат математической операции не удовлетворяет условию - два операнда и один оператор (+, -, /, *).");
                }
                break;
            } else if (isCorrectArabic(userInput)) {
                System.out.println(calculateArabicNumerals(userInput));
                break;
            } else if (isCorrectRoman(userInput)) {
                System.out.println(calculateRomanNumerals(userInput));
                break;
            } else if (isRomanArabic(userInput)) {
                System.out.println("Нельзя использовать одновременно разные системы счисления");
                break;
            } else {
                try {
                    throw new IOException();
                } catch (IOException e) {
                    System.out.println("Операнд должен быть числом от 1 до 10.");
                    break;
                }
            }
        }
    }

    static boolean isCorrectArabic(String input) { // Метод с шаблоном проверяет корректность введённых арабских цифр
        Pattern pattern = Pattern.compile("^([1-9]|10)? *[+/*-]? *([1-9]|10)?$");
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    static boolean isCorrectRoman(String input) { // Метод с шаблоном проверяет корректность введённых римских цифр
        Pattern pattern = Pattern.compile("^[IVXLC]+ *[+/*-]? *[IVXLC]+$");
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }
    static boolean isRomanArabic(String input) { // Метод с шаблоном проверяет не введённы ли римские и арабскик цифры одновремен.
        Pattern pattern = Pattern.compile("^([IVXLC]|[1-9])+ *[+/*-]? *([IVXLC]|[1-9])+$");
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    static boolean isSingleNumeral(String input) { // Метод с шаблоном проверяет, введен ли операнд
        Pattern pattern = Pattern.compile("^.|\\w.|\\w{1,9}.$");
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    static boolean isWrongFormat(String input) { //Метод с шаблоном проверяет, введены ли операнды и операторы
        Pattern pattern = Pattern.compile("^([1-9]|10)? *[+/*-]+ *([1-9]|10)? *[+/*-]+ *([1-9]|10)?.+");
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    public static String calculateArabicNumerals(String input) { // Метод возвращает результат матдействий над араб.цифрами
        int result = 0;
        String[] numbers;
        if (input.indexOf('+') > 0) {
            numbers = input.replaceAll("\\s", "").split("\\+");
            result = Integer.parseInt(numbers[0]) + Integer.parseInt(numbers[1]);
        } else if (input.indexOf('-') > 0) {
            numbers = input.replaceAll("\\s", "").split("-");
            result = Integer.parseInt(numbers[0]) - Integer.parseInt(numbers[1]);
        } else if (input.indexOf('*') > 0) {
            numbers = input.replaceAll("\\s", "").split("\\*");
            result = Integer.parseInt(numbers[0]) * Integer.parseInt(numbers[1]);
        } else if (input.indexOf('/') > 0) {
            numbers = input.replaceAll("\\s", "").split("/");
            //if (numbers[1].contains("0"))
                //System.out.println("На ноль делить нельзя");
            result = Integer.parseInt(numbers[0]) / Integer.parseInt(numbers[1]);

        }
        return String.valueOf(result);
    }

    public static String calculateRomanNumerals(String input) { //Метод возвращает результат матдействий над рим.цифрами
        StringBuilder roman = new StringBuilder();
        String result = null;
        int number1 = 0;
        int number2 = 0;
        String operand1;
        String operand2;
        String[] numbers = new String[2];
        if (input.contains("+")) {
            for (int i = 0; i < numbers.length; i++) {
                numbers[i] = input;
                numbers = (input.replaceAll("\\s", "").split("\\+"));
            }
            operand1 = numbers[0];
            operand2 = numbers[1];

            for (int i = 0; i < operand1.length(); i++) { //
                int indexChar = value(operand1.charAt(i));
                if (i + 1 < operand1.length()) {
                    int indexChar2 = value(operand1.charAt(i + 1));
                    if (indexChar >= indexChar2) {
                        number1 += indexChar;
                    } else {
                        number1 = indexChar2 - indexChar;
                        i++;
                    }
                } else {
                    number1 += indexChar;
                }
            }
            for (int i = 0; i < operand2.length(); i++) {
                int indexChar = value(operand2.charAt(i));
                if (i + 1 < operand2.length()) {
                    int indexChar2 = value(operand2.charAt(i + 1));
                    if (indexChar >= indexChar2) {
                        number2 += indexChar;
                    } else {
                        number2 = indexChar2 - indexChar;
                        i++;
                    }
                } else {
                    number2 += indexChar;
                }
            }
            int addition = number1 + number2;
            for (int i = 0; i < values.length; i++) {
                while (addition >= values[i]) {
                    addition = addition - values[i];
                    roman.append(romanNumerals[i]);
                }
            }
            result = String.valueOf(roman);

        } else if (input.contains("-")) {
            for (int i = 0; i < numbers.length; i++) {
                numbers[i] = input;
                numbers = (input.replaceAll("\\s", "").split("-"));
            }
            operand1 = numbers[0];
            operand2 = numbers[1];

            for (int i = 0; i < operand1.length(); i++) {
                int indexChar = value(operand1.charAt(i));
                if (i + 1 < operand1.length()) {
                    int indexChar2 = value(operand1.charAt(i + 1));
                    if (indexChar >= indexChar2) {
                        number1 += indexChar;
                    } else {
                        number1 = indexChar2 - indexChar;
                        i++;
                    }
                } else {
                    number1 += indexChar;
                }
            }
            for (int i = 0; i < operand2.length(); i++) {
                int indexChar = value(operand2.charAt(i));
                if (i + 1 < operand2.length()) {
                    int indexChar2 = value(operand2.charAt(i + 1));
                    if (indexChar >= indexChar2) {
                        number2 += indexChar;
                    } else {
                        number2 = indexChar2 - indexChar;
                        i++;
                    }
                } else {
                    number2 += indexChar;
                }
            }
            int subtraction = number1 - number2;
            if (subtraction < 0)
                System.out.println("В римской системе нет отрицательных чисел");

            for (int i = 0; i < values.length; i++) {
                while (subtraction >= values[i]) {
                    subtraction = subtraction - values[i];
                    roman.append(romanNumerals[i]);
                }
            }
            result = String.valueOf(roman);
        } else if (input.contains("*")) {
            for (int i = 0; i < numbers.length; i++) {
                numbers[i] = input;
                numbers = (input.replaceAll("\\s", "").split("\\*"));
            }
            operand1 = numbers[0];
            operand2 = numbers[1];

            for (int i = 0; i < operand1.length(); i++) {
                int indexChar = value(operand1.charAt(i));
                if (i + 1 < operand1.length()) {
                    int indexChar2 = value(operand1.charAt(i + 1));
                    if (indexChar >= indexChar2) {
                        number1 += indexChar;
                    } else {
                        number1 = indexChar2 - indexChar;
                        i++;
                    }
                } else {
                    number1 += indexChar;
                }
            }
            for (int i = 0; i < operand2.length(); i++) {
                int indexChar = value(operand2.charAt(i));
                if (i + 1 < operand2.length()) {
                    int indexChar2 = value(operand2.charAt(i + 1));
                    if (indexChar >= indexChar2) {
                        number2 += indexChar;
                    } else {
                        number2 = indexChar2 - indexChar;
                        i++;
                    }
                } else {
                    number2 += indexChar;
                }
            }
            int multiplication = number1 * number2;
            for (int i = 0; i < values.length; i++) {
                while (multiplication >= values[i]) {
                    multiplication = multiplication - values[i];
                    roman.append(romanNumerals[i]);
                }
            }
            result = String.valueOf(roman);
        } else if (input.contains("/")) {
            for (int i = 0; i < numbers.length; i++) {
                numbers[i] = input;
                numbers = (input.replaceAll("\\s", "").split("/"));
            }
            operand1 = numbers[0];
            operand2 = numbers[1];

            for (int i = 0; i < operand1.length(); i++) {
                int indexChar = value(operand1.charAt(i));
                if (i + 1 < operand1.length()) {
                    int indexChar2 = value(operand1.charAt(i + 1));
                    if (indexChar >= indexChar2) {
                        number1 += indexChar;
                    } else {
                        number1 = indexChar2 - indexChar;
                        i++;
                    }
                } else {
                    number1 += indexChar;
                }
            }
            for (int i = 0; i < operand2.length(); i++) {
                int indexChar = value(operand2.charAt(i));
                if (i + 1 < operand2.length()) {
                    int indexChar2 = value(operand2.charAt(i + 1));
                    if (indexChar >= indexChar2) {
                        number2 += indexChar;
                    } else {
                        number2 = indexChar2 - indexChar;
                        i++;
                    }
                } else {
                    number2 += indexChar;
                }
            }
            int division = number1 / number2;
            for (int i = 0; i < values.length; i++) {
                while (division >= values[i]) {
                    division = division - values[i];
                    roman.append(romanNumerals[i]);
                }
            }
            result = String.valueOf(roman);
        }
        return result;
    }
}

