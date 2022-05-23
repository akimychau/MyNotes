package ru.akimychev.mynotes.domain;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class FireStoreNotesRepository implements NotesRepository{

    public static final String KEY_TITLE = "title";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_DATE = "date";

    public static final String NOTES = "notes";

    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    @Override
    public void getAll(Callback<List<Note>> callback) {

        firestore.collection(NOTES)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        ArrayList<Note> result = new ArrayList<>();

                        for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()) {
                            String id = documentSnapshot.getId();

                            String title = documentSnapshot.getString(KEY_TITLE);
                            String description = documentSnapshot.getString(KEY_DESCRIPTION);
                            Date date = documentSnapshot.getDate(KEY_DATE);

                            result.add(new Note(id, title, description, date));
                        }

                        callback.onSuccess(result);
                    }
                });
    }

    @Override
    public void addNote(String title, String description, Callback<Note> callback) {

        HashMap<String, Object> data = new HashMap<>();

        Date date = new Date();

        data.put(KEY_TITLE, title);
        data.put(KEY_DESCRIPTION, description);
        data.put(KEY_DATE, date);

        firestore.collection(NOTES)
                .add(data)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        callback.onSuccess(new Note(documentReference.getId(), title, description, date));
                    }
                });
    }

    @Override
    public void removeNote(Note note, Callback<Void> callback) {

        firestore.collection(NOTES)
                .document(note.getId())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        callback.onSuccess(unused);
                    }
                });
    }

    @Override
    public void updateNote(Note note, String title, String description, Callback<Note> callback) {

        HashMap<String, Object> data = new HashMap<>();

        data.put(KEY_TITLE, title);
        data.put(KEY_DESCRIPTION, description);

        firestore.collection(NOTES)
                .document(note.getId())
                .update(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        callback.onSuccess(new Note(note.getId(), title, description, note.getDate()));
                    }
                });

    }
}
