package ru.akimychev.mynotes.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import ru.akimychev.mynotes.R;
import ru.akimychev.mynotes.domain.Note;

public class NotesDetailsFragment extends Fragment {

    public static final String ARG_NOTES = "ARG_NOTES";
    private TextView name;
    private TextView description;


    public NotesDetailsFragment() {
        super(R.layout.fragment_notes_details);
    }

    public static NotesDetailsFragment newInstance(Note notes) {

        Bundle args = new Bundle();
        args.putParcelable(ARG_NOTES, notes);

        NotesDetailsFragment fragment = new NotesDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        name = view.findViewById(R.id.name);
        description = view.findViewById(R.id.description);

        Toolbar toolbar = view.findViewById(R.id.toolbarDetails);
        if (requireActivity() instanceof ToolbarHolder) {
            ((ToolbarHolder) requireActivity()).setToolbar(toolbar);
        }

        getParentFragmentManager()
                .setFragmentResultListener(ru.akimychev.mynotes.ui.NotesListFragment.CLICKED_NOTE, getViewLifecycleOwner(), new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                        Note note = result.getParcelable(ru.akimychev.mynotes.ui.NotesListFragment.SELECTED_NOTE);
                        showNote(note);
                    }
                });
        if (getArguments() != null && getArguments().containsKey(ARG_NOTES)) {

            Note notes = getArguments().getParcelable(ARG_NOTES);
            showNote(notes);
        }
    }

    private void showNote(Note notes) {
        name.setText(notes.getTitle());
        description.setText(notes.getDescription());
    }
}