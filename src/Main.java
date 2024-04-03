import java.io.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class DataFormatException extends Exception {
    public DataFormatException(String message) {
        super(message);
    }
}

class UserData {
    private String lastName;
    private String firstName;
    private String middleName;
    private String birthDate;
    private String phoneNumber;
    private char gender;

    public UserData(String lastName, String firstName, String middleName, String birthDate, String phoneNumber, char gender) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public char getGender() {
        return gender;
    }

    public String toString() {
        return String.format("<%s> <%s> <%s> <%s> <%s> <%c>", lastName, firstName, middleName, birthDate, phoneNumber, gender);
    }
}

class UserInput {
    public static UserData requestUserDataFromConsole() throws DataFormatException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите данные (Фамилия Имя Отчество дата_рождения номер_телефона пол):");
        String userDataString = scanner.nextLine();
        return parseUserData(userDataString);
    }

    private static UserData parseUserData(String userDataString) throws DataFormatException {
        String[] userDataParts = userDataString.split("\\s+");

        if (userDataParts.length != 6) {
            throw new DataFormatException("Неверное количество данных");
        }

        String lastName = validateName(userDataParts[0]);
        String firstName = validateName(userDataParts[1]);
        String middleName = validateName(userDataParts[2]);
        String birthDate = validateBirthDate(userDataParts[3]);
        String phoneNumber = validatePhoneNumber(userDataParts[4]);
        char gender = validateGender(userDataParts[5]);

        return new UserData(lastName, firstName, middleName, birthDate, phoneNumber, gender);
    }

    private static String validateName(String name) throws DataFormatException {
        if (!name.matches("[a-zA-Zа-яА-Я]+")) {
            throw new DataFormatException("Неверный формат имени или фамилии, должны присутствовать только английские и русские буквы");
        }
        return name;
    }

    private static String validateBirthDate(String birthDate) throws DataFormatException {
        if (!birthDate.matches("\\d{2}\\.\\d{2}\\.\\d{4}")) {
            throw new DataFormatException("Неверный формат даты рождения, пример верного ввода: 01.01.1990");
        }
        return birthDate;
    }

    private static String validatePhoneNumber(String phoneNumber) throws DataFormatException {
        if (!phoneNumber.matches("\\d{11}")) {
            throw new DataFormatException("Неверный формат номера телефона, требуется 11 цифр");
        }
        return phoneNumber;
    }

    private static char validateGender(String gender) throws DataFormatException {
        if (!gender.matches("[mfмжMFМЖ]")) {
            throw new DataFormatException("Неверный формат пола, есть только два пола f и m, еще варианты ввода F, M, м, М, ж, Ж");
        }
        return gender.charAt(0);
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Выберите действие:");
        System.out.println("1. Ввести данные пользователя");
        System.out.println("2. Получить информацию из файла");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                try {
                    UserData userData = UserInput.requestUserDataFromConsole();
                    saveUserDataToFile(userData);
                    System.out.println("Данные успешно сохранены в файле.");
                } catch (DataFormatException | IOException e) {
                    System.err.println("Ошибка: " + e.getMessage());
                    e.printStackTrace();
                }
                break;
            case 2:
                System.out.println("Функция чтения из файла пока не реализована.");
                break;
            default:
                System.out.println("Неверный выбор.");
        }
    }

    private static void saveUserDataToFile(UserData userData) throws IOException {
        String fileName = userData.getLastName() + ".txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(userData.toString() + "\n");
        }
    }
}
