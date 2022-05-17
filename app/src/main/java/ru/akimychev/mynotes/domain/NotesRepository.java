package ru.akimychev.mynotes.domain;

import java.util.List;

public interface NotesRepository {
    void getAll(Callback <List<Note>> callback);

    void addNote(String title, String description, Callback <Note> callback);

    void removeNote (Note note, Callback <Void> callback);

    void updateNote(Note note, String title, String Description, Callback<Note> callback);
}
