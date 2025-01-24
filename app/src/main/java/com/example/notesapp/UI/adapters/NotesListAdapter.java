package com.example.notesapp.UI.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notesapp.R;
import com.example.notesapp.UI.UIStates.NoteUiState;
import com.example.notesapp.databinding.NoteListItemBinding;

import org.w3c.dom.Text;

import java.util.List;

public class NotesListAdapter extends RecyclerView.Adapter<NotesListAdapter.ViewHolder> {

    private MutableLiveData<List<NoteUiState>> notes ;
    private NoteItemListener noteItemListener;
    public NotesListAdapter(MutableLiveData<List<NoteUiState>> notes,NoteItemListener longClickedListener) {
        this.noteItemListener=longClickedListener;
        this.notes = notes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_list_item,parent,false);
        return new ViewHolder(view,noteItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getTitle().setText(notes.getValue().get(position).getTitle());
        String fullbody =notes.getValue().get(position).getBody();
        holder.getBody().setText(fullbody.length()>50 ? fullbody.substring(0,50): fullbody);
    }

    @Override
    public int getItemCount() {
        return notes.getValue().size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView title;
        private final TextView body;
        public ViewHolder(View view,NoteItemListener listener){
            super(view);
            title = (TextView) view.findViewById(R.id.item_title);
            body = (TextView) view.findViewById(R.id.item_body);
            view.setOnLongClickListener(e->{
                int position = getAdapterPosition();
                if(position != RecyclerView.NO_POSITION){
                    listener.onItemLongClick(notes.getValue().get(position).getId());
                    notifyDataSetChanged();
                }
                return  true;
            });
            view.setOnClickListener(e->{
                int postion = getAdapterPosition();
                if(postion != RecyclerView.NO_POSITION){
                    NoteUiState note = notes.getValue().get(postion);
                    listener.onItemClick(note.getId(), note.getTitle(),note.getBody());
                }
            });

        }

        public TextView getTitle() {
            return title;
        }

        public TextView getBody() {
            return body;
        }

    }

    public interface NoteItemListener {
        void onItemLongClick(int id);
        void onItemClick(int id,String tittle,String body);
    }

}
