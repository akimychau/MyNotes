package ru.akimychev.mynotes.domain;

import android.content.Context;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ru.akimychev.mynotes.R;

public class InMemoryNotesRepository implements NotesRepository {

    private static NotesRepository INSTANCE;

    private final Context context;

    public InMemoryNotesRepository(Context context) {
        this.context = context;
    }

    public static NotesRepository getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new InMemoryNotesRepository(context);
        }
        return INSTANCE;
    }

    @Override
    public List<Notes> getAll() {
        ArrayList<Notes> result = new ArrayList<>();
        result.add(new Notes(context.getString(R.string.firstName),
                context.getString(R.string.firstDescription), new Date()));
        result.add(new Notes(context.getString(R.string.secondName),
                context.getString(R.string.secondDescription), new Date()));
        result.add(new Notes(context.getString(R.string.thirdtName),
                context.getString(R.string.thirdDescription), new Date()));
        result.add(new Notes(context.getString(R.string.firstName),
                context.getString(R.string.firstDescription), new Date()));
        result.add(new Notes(context.getString(R.string.secondName),
                context.getString(R.string.secondDescription), new Date()));
        result.add(new Notes(context.getString(R.string.thirdtName),
                context.getString(R.string.thirdDescription), new Date()));
        result.add(new Notes(context.getString(R.string.firstName),
                context.getString(R.string.firstDescription), new Date()));
        result.add(new Notes(context.getString(R.string.secondName),
                context.getString(R.string.secondDescription), new Date()));
        result.add(new Notes(context.getString(R.string.thirdtName),
                context.getString(R.string.thirdDescription), new Date()));
        result.add(new Notes(context.getString(R.string.firstName),
                context.getString(R.string.firstDescription), new Date()));
        result.add(new Notes(context.getString(R.string.secondName),
                context.getString(R.string.secondDescription), new Date()));
        result.add(new Notes(context.getString(R.string.thirdtName),
                context.getString(R.string.thirdDescription), new Date()));
        return result;
    }

    @Override
    public void add(Notes notes) {

    }
}
