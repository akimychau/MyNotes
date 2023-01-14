package ru.akimychev.mynotes.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.List;

import ru.akimychev.mynotes.R;
import ru.akimychev.mynotes.di.Dependencies;
import ru.akimychev.mynotes.domain.Callback;
import ru.akimychev.mynotes.domain.Note;

public class NotesListFragment extends Fragment {

    public static final String CLICKED_NOTE = "CLICKED_NOTE";
    public static final String SELECTED_NOTE = "SELECTED_NOTE";

    private Note selectedNote;
    private int selectedPosition;

    private NotesAdapter adapter;

    private ProgressBar progressBar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        adapter = new NotesAdapter(this);

        Dependencies.NOTES_REPOSITORY.getAll(new Callback<List<Note>>() {
            @Override
            public void onSuccess(List<Note> data) {
                adapter.setData(data);

                adapter.notifyDataSetChanged();

                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError(Throwable exception) {
                progressBar.setVisibility(View.GONE);

            }
        });


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notes_list, container, false);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Toolbar toolbar = view.findViewById(R.id.toolbarList);
        if (requireActivity() instanceof ToolbarHolder) {
            ((ToolbarHolder) requireActivity()).setToolbar(toolbar);
        }

        BottomNavigationView bottomNavigationView = view.findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
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

        adapter.setNoteClicked(new NotesAdapter.OnNoteClicked() {
            @Override
            public void onNoteClicked(Note notes) {
                getParentFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, NotesDetailsFragment.newInstance(notes))
                        .addToBackStack("NotesDetailsFragment")
                        .commit();
            }

            @Override
            public void onNoteLongClicked(Note note, int position) {

                selectedNote = note;
                selectedPosition = position;
            }
        });

        notesList.setAdapter(adapter);

        getParentFragmentManager()
                .setFragmentResultListener(AddNoteFragment.ADD_KEY_RESULT, getViewLifecycleOwner(), new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                        Note note = result.getParcelable(AddNoteFragment.ARG_NOTE);

                        int index = adapter.addNote(note);

                        adapter.notifyItemInserted(index);
                    }
                });

        getParentFragmentManager()
                .setFragmentResultListener(AddNoteFragment.UPDATE_KEY_RESULT, getViewLifecycleOwner(), new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                        Note note = result.getParcelable(AddNoteFragment.ARG_NOTE);

                        adapter.replaceNote(note, selectedPosition);

                        adapter.notifyItemChanged(selectedPosition);
                    }
                });

        progressBar = view.findViewById(R.id.progress);
        progressBar.setVisibility(View.GONE);

        view.findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getParentFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, AddNoteFragment.addInstance())
                        .addToBackStack("Add")
                        .commit();

            }
        });
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {

        MenuInflater menuInflater = requireActivity().getMenuInflater();
        menuInflater.inflate(R.menu.menu_notes_context, menu);

        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case(R.id.action_delete):

                progressBar.setVisibility(View.VISIBLE);

                Dependencies.NOTES_REPOSITORY.removeNote(selectedNote, new Callback<Void>() {
                    @Override
                    public void onSuccess(Void data) {
                        adapter.removeNote(selectedNote);

                        adapter.notifyItemRemoved(selectedPosition);
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Throwable exception) {

                    }
                });
                return true;

            case(R.id.action_edit):
                getParentFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, AddNoteFragment.editInstance(selectedNote))
                        .addToBackStack("EditNote")
                        .commit();
                return true;
        }

        return super.onContextItemSelected(item);
    }
}
