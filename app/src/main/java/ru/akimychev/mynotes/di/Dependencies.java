package ru.akimychev.mynotes.di;

import ru.akimychev.mynotes.domain.FireStoreNotesRepository;
import ru.akimychev.mynotes.domain.NotesRepository;

public class Dependencies {

    public static NotesRepository getNotesRepository() {

        return NOTES_REPOSITORY;
    }

    public static NotesRepository NOTES_REPOSITORY = new FireStoreNotesRepository();
}
