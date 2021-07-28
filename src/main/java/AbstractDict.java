package main.java;

import java.io.IOException;
import java.util.ArrayList;

public abstract class AbstractDict {
    public abstract ArrayList<DictEntry> getAll() throws IOException;

    public abstract ArrayList<DictEntry> add(DictEntry entry, ArrayList<DictEntry> dictionary);

    public ArrayList<DictEntry> update(String newValue, ArrayList<DictEntry> candidatesForUpdate,  ArrayList<DictEntry> entries, int numberOfUpdated) {
        DictEntry updatedValue = candidatesForUpdate.get(numberOfUpdated-1);
        entries.remove(updatedValue);
        System.out.println("Старое значение удалено");
        updatedValue.setValue(newValue);
        entries.add(updatedValue);
        System.out.println("Нвове значение введено");
        return entries;
    }
    public ArrayList<DictEntry> getByKey(String key, ArrayList<DictEntry> entries) {
        ArrayList<DictEntry> foundEntries = new ArrayList<>();
        for (DictEntry entry : entries) {
            if (entry.getKey().equals(key)) foundEntries.add(entry);
        }
        if(foundEntries.isEmpty()){
            System.out.println("Значений не найдено");
            return null;
        }
        return foundEntries;
    }

    public ArrayList<DictEntry> delete(int numberOfDeleted, ArrayList<DictEntry> candidatesForDelete,  ArrayList<DictEntry> entries) {
        DictEntry updatedValue = candidatesForDelete.get(numberOfDeleted-1);
        entries.remove(updatedValue);
        System.out.println("Значение удалено");
        return entries;
    }
    public abstract void refresh(ArrayList<DictEntry> dictionary) throws IOException;
}
