package ru.akimychev.mynotes.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import ru.akimychev.mynotes.R;
import ru.akimychev.mynotes.domain.Notes;

public class NotesDetailsActivity extends AppCompatActivity {

    public static final String EXTRA_NOTES = "EXTRA_NOTES";

    public static void show(Context context, Notes notes) {
        Intent intent = new Intent(context, NotesDetailsActivity.class);
        intent.putExtra(EXTRA_NOTES, notes);

        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_details);

        if (savedInstanceState == null) {

            Notes notes = getIntent().getParcelableExtra(EXTRA_NOTES);

            NotesDetailsFragment notesDetailsFragment = NotesDetailsFragment.newInstance(notes);

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, notesDetailsFragment)
                    .commit();
        }
    }
}