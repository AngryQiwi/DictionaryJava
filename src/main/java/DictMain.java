package main.java;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class DictMain {
    private static final Scanner scanner = new Scanner(System.in);
    private static ArrayList<DictEntry> entries = new ArrayList<>();
    private static AbstractDict dictionary;

    public static void main(String[] args) throws IOException {
        greetings();
    }

    static void greetings() throws IOException {
        while(true) {
            System.out.println("Добро пожаловать в интергалактический словарь.");
            System.out.println("В данный момент доступно 2 языка, для выбора пункта введите его номер:");
            System.out.println("1. Интергаланглийский");
            System.out.println("2. Числоматанский");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1": {
                    dictionary = new EnDict();
                    entries = dictionary.getAll();
                    chooseAction();
                    continue;
                }
                case "2": {
                    dictionary = new NumDict();
                    entries = dictionary.getAll();
                    chooseAction();
                    continue;
                }
                default: {
                    System.out.println("Такой словарь не существует");
                    continue;
                }
            }
        }
    }

    static void chooseAction() throws IOException {
        while(true) {
            System.out.println("Выберите действие:");
            System.out.println("1. Просмотр данных;");
            System.out.println("2. Найти значения по ключу;");
            System.out.println("3. Добавить значение;");
            System.out.println("4. Изменить значение;");
            System.out.println("5. Удалить значение.");
            System.out.println("6. Сменить словарь.");
            System.out.println("7. Выход.");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1": {
                    show();
                    continue;
                }
                case "2": {
                    search();
                    continue;
                }
                case "3": {
                    add();
                    continue;
                }
                case "4": {
                    update();
                    continue;
                }
                case "5": {
                    delete();
                    continue;
                }
                case "6": {
                    if(changeDict()) return;
                    continue;
                }
                case "7": {
                    exit();
                    continue;
                }
                default: {
                    System.out.println("Введено неверное значение, введите значение в соответствии с выбранным пунктом");
                }
            }
        }
    }

    static void show() throws IOException {
        System.out.println("===================================");
        System.out.println("===================================");
        for (DictEntry entry : entries) {
            System.out.println(entry.toString());
        }
        System.out.println("===================================");
        System.out.println("===================================");
    }

    static void search() {
        System.out.println("Введите слово:");
        String key = scanner.nextLine();
        ArrayList<DictEntry> foundEntries = dictionary.getByKey(key, entries);
        if(foundEntries==null) return;
        System.out.println("===================================");
        System.out.println("===================================");
        for (DictEntry entry : foundEntries) {
            System.out.println(entry.toString());
        }
        System.out.println("===================================");
        System.out.println("===================================");
    }

    static void add() {
        DictEntry entry = new DictEntry();
        System.out.println("Введите слово:");
        entry.setKey(scanner.nextLine());
        System.out.println("Введите значение:");
        entry.setValue(scanner.nextLine());
        entries = dictionary.add(entry, entries);
    }

    static void update() {
        System.out.println("Введите слово:");
        String key = scanner.nextLine();
        ArrayList<DictEntry> candidatesForUpdate = dictionary.getByKey(key, entries);
        if (candidatesForUpdate == null) return;
        System.out.println("Выберите значение, которое хотите изменить:");
        for (DictEntry candidate : candidatesForUpdate) {
            System.out.print((candidatesForUpdate.indexOf(candidate) + 1) + ". ");
            System.out.println(candidate.toString());
        }
        int numberOfUpdated;
        numberOfUpdated = scanner.nextInt();
        scanner.nextLine();
        while (numberOfUpdated < 1 || numberOfUpdated > candidatesForUpdate.size()) {
            System.out.println("Выбран неверный номер элемента, введите корректный номер:");
            numberOfUpdated = scanner.nextInt();
            scanner.nextLine();
        }
        System.out.println("Введите новое значение:");
        String newValue = scanner.nextLine();
        dictionary.update(newValue, candidatesForUpdate, entries, numberOfUpdated);
    }

    static void delete() {
        System.out.println("Введите слово:");
        String key = scanner.nextLine();
        ArrayList<DictEntry> candidatesForDelete = dictionary.getByKey(key, entries);
        if (candidatesForDelete == null) return;
        System.out.println("Выберите значение, которое хотите удалить:");
        for (DictEntry candidate : candidatesForDelete) {
            System.out.print((candidatesForDelete.indexOf(candidate) + 1) + ". ");
            System.out.println(candidate.toString());
        }
        int numberOfDeleted = scanner.nextInt();
        scanner.nextLine();
        while (numberOfDeleted < 1 || numberOfDeleted > candidatesForDelete.size()) {
            System.out.println("Выбран неверный номер элемента, введите корректный номер:");
            numberOfDeleted = scanner.nextInt();
            scanner.nextLine();
        }
        dictionary.delete(numberOfDeleted, candidatesForDelete, entries);
    }

    static void exit() throws IOException {
        System.out.println("Сохранить изменения?");
        System.out.println("1. Да");
        System.out.println("2. Нет");
        System.out.println("3. Отмена");
        String choice = scanner.nextLine();
        while(true){
            switch (choice) {
                case "1": {
                    dictionary.refresh(entries);
                    System.exit(0);
                }
                case "2": {
                    System.exit(0);
                }
                case "3": {
                    return;
                }
                default:{
                    System.out.println("Введено неверное значение, введите значение в соответствии с выбранным пунктом");
                }
            }
        }
    }
    static boolean changeDict() throws IOException {
        System.out.println("Сохранить изменения?");
        System.out.println("1. Да");
        System.out.println("2. Нет");
        System.out.println("3. Отмена");
        String choice = scanner.nextLine();
        while(true){
            switch (choice) {
                case "1": {
                    dictionary.refresh(entries);
                    return true;
                }
                case "2": {
                    return true;
                }
                case "3": {
                    return false;
                }
                default:{
                    System.out.println("Введено неверное значение, введите значение в соответствии с выбранным пунктом");
                }
            }
        }
    }
}
