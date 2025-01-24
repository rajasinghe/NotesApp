package com.example.notesapp.UI.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notesapp.R;
import com.example.notesapp.UI.UIStates.NoteUiState;
import com.example.notesapp.databinding.NoteListItemBinding;

import org.w3c.dom.Text;

import java.util.List;

public class NotesListAdapter extends RecyclerView.Adapter<NotesListAdapter.ViewHolder> {

    private List<NoteUiState> notes ;

    public NotesListAdapter(List<NoteUiState> notes) {
        this.notes = notes;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_list_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getTitle().setText(notes.get(position).getTitle());
        String fullbody =notes.get(position).getBody();
        holder.getBody().setText(fullbody.length()>20 ? fullbody.substring(0,20): fullbody);
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView title;
        private final TextView body;
        public ViewHolder(View view){
            super(view);
            title = (TextView) view.findViewById(R.id.item_title);
            body = (TextView) view.findViewById(R.id.item_body);
        }

        public TextView getTitle() {
            return title;
        }

        public TextView getBody() {
            return body;
        }

    }

}
