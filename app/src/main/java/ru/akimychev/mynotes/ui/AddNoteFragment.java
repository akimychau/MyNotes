package ru.akimychev.mynotes.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;

import ru.akimychev.mynotes.R;
import ru.akimychev.mynotes.di.Dependencies;
import ru.akimychev.mynotes.domain.Callback;
import ru.akimychev.mynotes.domain.Note;

public class AddNoteFragment extends Fragment {

    public static final String ADD_KEY_RESULT = "AddNoteFragment_ADD_KEY_RESULT";
    public static final String UPDATE_KEY_RESULT = "AddNoteFragment_UPDATE_KEY_RESULT";
    public static final String ARG_NOTE = "ARG_NOTE";

    public static AddNoteFragment addInstance() {

        return new AddNoteFragment();
    }


    public static AddNoteFragment editInstance(Note note) {

        Bundle args = new Bundle();
        args.putParcelable(ARG_NOTE, note);

        AddNoteFragment fragment = new AddNoteFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notes_details, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Note noteToEdit = null;

        if (getArguments() != null && getArguments().containsKey(ARG_NOTE)) {

            noteToEdit = getArguments().getParcelable(ARG_NOTE);
        }

        EditText title = view.findViewById(R.id.name);
        EditText description = view.findViewById(R.id.description);

        if (noteToEdit != null) {
            title.setText(noteToEdit.getTitle());
            description.setText(noteToEdit.getDescription());
        }
        AppCompatImageButton btnDone = view.findViewById(R.id.done);

        Note finalNoteToEdit = noteToEdit;
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnDone.setEnabled(false);

                if (finalNoteToEdit != null) {
                    Dependencies.NOTES_REPOSITORY.updateNote(finalNoteToEdit, title.getText().toString(), description.getText().toString(), new Callback<Note>() {
                        @Override
                        public void onSuccess(Note data) {

                            getParentFragmentManager().popBackStack();

                            Bundle bundle = new Bundle();
                            bundle.putParcelable(ARG_NOTE, data);

                            getParentFragmentManager().setFragmentResult(UPDATE_KEY_RESULT, bundle);

                            btnDone.setEnabled(true);
                        }

                        @Override
                        public void onError(Throwable exception) {
                            btnDone.setEnabled(true);
                        }
                    });

                } else {

                    Dependencies.NOTES_REPOSITORY.addNote(title.getText().toString(), description.getText().toString(), new Callback<Note>() {
                        @Override
                        public void onSuccess(Note data) {

                            getParentFragmentManager().popBackStack();

                            Bundle bundle = new Bundle();
                            bundle.putParcelable(ARG_NOTE, data);

                            getParentFragmentManager().setFragmentResult(ADD_KEY_RESULT, bundle);

                            btnDone.setEnabled(true);

                        }

                        @Override
                        public void onError(Throwable exception) {
                            btnDone.setEnabled(true);
                        }
                    });
                }
            }
        });
    }
}
