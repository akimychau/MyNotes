package ru.akimychev.mynotes.domain;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SharedPrefNotesRepository implements NotesRepository {
    private List<Note> savedNotes;

    private final Executor executor = Executors.newSingleThreadExecutor();

    private final Handler handler = new Handler(Looper.getMainLooper());

    private static final String KEY_SAVED_NOTES = "KEY_SAVED_NOTES";

    private final SharedPreferences sharedPreferences;

    public SharedPrefNotesRepository(Context context) {
        sharedPreferences = context.getSharedPreferences("NOTES", Context.MODE_PRIVATE);
    }

    @Override
    public void getAll(Callback<List<Note>> callback) {

        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

        Gson gson = new Gson();

        String savedData = sharedPreferences.getString(KEY_SAVED_NOTES, "[]");

        Type type = new TypeToken<ArrayList<Note>>() {}.getType();

        savedNotes = gson.fromJson(savedData, type);

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onSuccess(savedNotes);
                    }
                });
            }
        });
    }

    @Override
    public void addNote(String title, String description, Callback<Note> callback) {

        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

        Note note = new Note(UUID.randomUUID().toString(), title, description, new Date());

        Gson gson = new Gson();

        String savedData = sharedPreferences.getString(KEY_SAVED_NOTES, "[]");

        Type type = new TypeToken<ArrayList<Note>>() {}.getType();

        savedNotes = gson.fromJson(savedData, type);

        savedNotes.add(note);

        String toWrite = gson.toJson(savedNotes, type);

        sharedPreferences.edit()
                .putString(KEY_SAVED_NOTES, toWrite)
                .apply();

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onSuccess(note);
                    }
                });
            }
        });

    }

    @Override
    public void removeNote(Note note, Callback<Void> callback) {

        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Gson gson = new Gson();

                String savedData = sharedPreferences.getString(KEY_SAVED_NOTES, "[]");

                Type type = new TypeToken<ArrayList<Note>>() {}.getType();

                savedNotes = gson.fromJson(savedData, type);

                savedNotes.remove(note);

                String toWrite = gson.toJson(savedNotes, type);

                sharedPreferences.edit()
                        .putString(KEY_SAVED_NOTES, toWrite)
                        .apply();

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onSuccess(null);
                    }
                });
            }
        });
    }

    @Override
    public void updateNote(Note note, String title, String Description, Callback<Note> callback) {

        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Note newNote = new Note(UUID.randomUUID().toString(), title, Description, note.getDate());

                int index = savedNotes.indexOf(note);

                savedNotes.set(index, newNote);

                Gson gson = new Gson();

                String savedData = sharedPreferences.getString(KEY_SAVED_NOTES, "[]");

                Type type = new TypeToken<ArrayList<Note>>() {}.getType();

                savedNotes = gson.fromJson(savedData, type);

                String toWrite = gson.toJson(savedNotes, type);

                sharedPreferences.edit()
                        .putString(KEY_SAVED_NOTES, toWrite)
                        .apply();

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onSuccess(null);
                    }
                });
            }
        });
    }
}
