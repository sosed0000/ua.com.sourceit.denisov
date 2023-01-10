package task4.variant1.subtask1;

import java.text.SimpleDateFormat;
import java.util.*;

public class Notepad {
    public static void main(String[] args) {
        Notepad notepad = new Notepad();
        notepad.addNote("First note");
        notepad.addNote("Second note");
        System.out.println(notepad.getNotesByDate(new Date()));
    }

    private final Map<String, List<Note>> storage = new HashMap<>();
    private final String notepadName;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public Notepad() {
        this.notepadName = "Notepad " + dateFormat.format(new Date());
    }

    public Notepad(String notepadName) {
        this.notepadName = notepadName;
    }

    class Note {
        String note;

        Note(String note) {
            this.note = note;
        }

        @Override
        public String toString() {
            return note;
        }
    }

    public void addNote(String note) {
        String key = dateFormat.format(new Date());
        if (!storage.containsKey(key)) {
            List<Note> dayList = new ArrayList<>();
            dayList.add(new Note(note));
            storage.put(key, dayList);
        } else
            storage.get(key).add(new Note(note));
    }

    public Map<String, List<Note>> getAllNotes() {
        return storage;
    }

    public List<Note> getNotesByDate(Date date) {
        if (date == null)
            return null;
        return getNotesByDate(dateFormat.format(date));
    }

    public List<Note> getNotesByDate(String date) {  //yyyy-MM-dd
        return storage.get(date);
    }

    public String getNotepadName() {
        return notepadName;
    }
}
