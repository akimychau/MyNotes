package ru.akimychev.mynotes.di;

import ru.akimychev.mynotes.domain.InMemoryNotesRepository;
import ru.akimychev.mynotes.domain.NotesRepository;

public class Dependencies {

    public static final NotesRepository NOTES_REPOSITORY = new InMemoryNotesRepository();
}
