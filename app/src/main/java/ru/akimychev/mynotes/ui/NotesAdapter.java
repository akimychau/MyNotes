package ru.akimychev.mynotes.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import ru.akimychev.mynotes.R;
import ru.akimychev.mynotes.domain.Notes;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {

    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy, HH:mm", Locale.getDefault());
    private final List<Notes> data = new ArrayList<>();
    private OnNoteClicked noteClicked;

    public OnNoteClicked getNoteClicked() {
        return noteClicked;
    }

    public void setNoteClicked(OnNoteClicked noteClicked) {
        this.noteClicked = noteClicked;
    }

    public void setData(Collection<Notes> notes) {
        data.addAll(notes);
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);

        NotesViewHolder holder = new NotesViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        Notes note = data.get(position);

        holder.name.setText(note.getName());
        holder.description.setText(note.getDescription());
        holder.date.setText(simpleDateFormat.format(note.getDate()));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    interface OnNoteClicked {
        void onNoteClicked(Notes notes);
    }

    class NotesViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView description;
        TextView date;


        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            description = itemView.findViewById(R.id.description);
            date = itemView.findViewById(R.id.date);

            itemView.findViewById(R.id.root).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (noteClicked != null) {
                        int clickedPosition = getAdapterPosition();
                        noteClicked.onNoteClicked(data.get(clickedPosition));
                    }
                }
            });
        }
    }
}
