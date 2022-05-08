package ru.akimychev.mynotes.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

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

        BottomNavigationView bottomNavigationView = view.findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                RecyclerView notesList = view.findViewById(R.id.notes_list);
                switch (item.getItemId()) {
                    case R.id.string_view:
                        notesList.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));

                        return true;

                    case R.id.grid_view:
                        notesList.setLayoutManager(new GridLayoutManager(requireContext(), 2));

                        return true;
                }
                return false;
            }
        });


        RecyclerView notesList = view.findViewById(R.id.notes_list);
        notesList.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));

        NotesAdapter adapter = new NotesAdapter();

        adapter.setNoteClicked(new NotesAdapter.OnNoteClicked() {
            @Override
            public void onNoteClicked(Notes notes) {
                getParentFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, NotesDetailsFragment.newInstance(notes))
                        .addToBackStack("NotesDetailsFragment")
                        .commit();
            }
        });

        notesList.setAdapter(adapter);

        List<Notes> notes = InMemoryNotesRepository.getInstance(requireContext()).getAll();

        adapter.setData(notes);
        adapter.notifyDataSetChanged();


    }
}
