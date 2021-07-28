package main.java;

import java.io.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EnDict extends AbstractDict {
    final String FILE_NAME = "src/main/resources/dictEn.txt";

    @Override
    public ArrayList<DictEntry> getAll() throws IOException {
        ArrayList<DictEntry> entries = new ArrayList<>();
        FileReader reader = new FileReader(FILE_NAME);
        StringBuilder entryStr = new StringBuilder();
        int c;
        while ((c = reader.read()) != -1) {
            if (c == ';') {
                String[] keyValue = entryStr.toString().split("-");
                entries.add(new DictEntry(keyValue[0], keyValue[1]));
                entryStr.delete(0, entryStr.length());
            } else entryStr.append((char) c);
        }
        reader.close();
        entries.sort(new DictComparator());
        return entries;
    }

    @Override
    public ArrayList<DictEntry> add(DictEntry entry, ArrayList<DictEntry> dictionary) {
        Pattern pattern = Pattern.compile("[a-zA-Z]{4}");
        Matcher matcher = pattern.matcher(entry.getKey());
        if (matcher.matches()) {
            for(DictEntry entry1: dictionary){
                if (entry.getKey().equals(entry1.getKey())&&entry.getValue().equals(entry1.getValue())) {
                    System.out.println("Ввод не выполнен, значение существует");
                    return dictionary;
                }
            }
            dictionary.add(entry);
            System.out.println("Ввод выполнен.");
            dictionary.sort(new DictComparator());
            return dictionary;
        }
        System.out.println("Ввод не выполнен, некорректное значение");
        return dictionary;
    }

    @Override
    public void refresh(ArrayList<DictEntry> dictionary) throws IOException {
        new FileWriter(FILE_NAME).close();
        FileWriter writer = new FileWriter(FILE_NAME, true);
        for (DictEntry entry : dictionary) {
            writer.append(entry.toString());
        }
        writer.close();
    }
}
