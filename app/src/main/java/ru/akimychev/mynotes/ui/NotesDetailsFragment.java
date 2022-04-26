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
import ru.akimychev.mynotes.domain.Notes;

public class NotesDetailsFragment extends Fragment {

    public static final String ARG_NOTES = "ARG_NOTES";
    private TextView name;
    private TextView description;

    public NotesDetailsFragment() {
        super(R.layout.fragment_notes_details);
    }

    public static NotesDetailsFragment newInstance(Notes notes) {

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

        Toolbar toolbarBack = view.findViewById(R.id.toolbar);
        toolbarBack.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager()
                        .popBackStack();
            }
        });

        Toolbar toolbarMenu = view.findViewById(R.id.menu);
        toolbarMenu.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager()
                        .beginTransaction()
                        .add(R.id.fragment_container, new MenuFragment())
                        .addToBackStack("NotesDetailsFragment")
                        .commit();
            }
        });

        getParentFragmentManager()
                .setFragmentResultListener(ru.akimychev.mynotes.ui.NotesListFragment.CLICKED_NOTE, getViewLifecycleOwner(), new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                        Notes note = result.getParcelable(ru.akimychev.mynotes.ui.NotesListFragment.SELECTED_NOTE);
                        showNote(note);
                    }
                });
        if (getArguments() != null && getArguments().containsKey(ARG_NOTES)) {

            Notes notes = getArguments().getParcelable(ARG_NOTES);
            showNote(notes);
        }


    }

    private void showNote(Notes notes) {
        name.setText(notes.getName());
        description.setText(notes.getDescription());
    }
}