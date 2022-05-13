package ru.akimychev.mynotes.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import java.util.List;

import ru.akimychev.mynotes.R;
import ru.akimychev.mynotes.domain.InMemoryNotesRepository;
import ru.akimychev.mynotes.domain.Notes;

public class NotesListFragment extends Fragment {

    public static final String CLICKED_NOTE = "CLICKED_NOTE";
    public static final String SELECTED_NOTE = "SELECTED_NOTE";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notes_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Toolbar toolbar = view.findViewById(R.id.toolbarList);
        if (requireActivity() instanceof ToolbarHolder) {
            ((ToolbarHolder) requireActivity()).setToolbar(toolbar);
        }

        List<Notes> notes = InMemoryNotesRepository.getInstance(requireContext()).getAll();

        LinearLayout container = view.findViewById(R.id.container);

        for (Notes note : notes) {
            View itemView = getLayoutInflater().inflate(R.layout.item_note, container, false);

            itemView.findViewById(R.id.root).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getParentFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, NotesDetailsFragment.newInstance(note))
                            .addToBackStack("NotesDetailsFragment")
                            .commit();
                }
            });


            TextView name = itemView.findViewById(R.id.name);
            name.setText(note.getName());
            TextView description = itemView.findViewById(R.id.description);
            description.setText(note.getDescription());
            TextView date = itemView.findViewById(R.id.date);
            date.setText(note.getDate());

            container.addView(itemView);


        }
    }
}
