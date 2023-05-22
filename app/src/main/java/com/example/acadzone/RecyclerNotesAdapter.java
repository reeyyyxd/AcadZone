    package com.example.acadzone;

    import android.app.AlertDialog;
    import android.content.Context;
    import android.content.DialogInterface;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.LinearLayout;
    import android.widget.TextView;

    import androidx.annotation.NonNull;
    import androidx.recyclerview.widget.RecyclerView;

    import java.util.ArrayList;




    public class RecyclerNotesAdapter extends RecyclerView.Adapter <RecyclerNotesAdapter.ViewHolder> {
        Context context;
        ArrayList<Note> arrNotes =  new ArrayList<>();
        DatabaseHelper databaseHelper;
        private NotesInterface notesInterface;


        public interface NotesInterface {
            void onNoteDelete();
        }

        RecyclerNotesAdapter(Context context, ArrayList<Note> arrNotes, DatabaseHelper databaseHelper, NotesInterface notesInterface){
            this.context= context;
            this.arrNotes= arrNotes;
            this.databaseHelper= databaseHelper;
            this.notesInterface = notesInterface;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
            return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.note_row,parent,false));
        }


        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.txtTitle.setText(arrNotes.get(holder.getAdapterPosition()).getTitle());
            holder.txtContent.setText(arrNotes.get(holder.getAdapterPosition()).getContent());

            holder.llrow.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    deleteItem(holder.getAdapterPosition());
                    return true;
                }
            });
        }

        @Override
        public int getItemCount(){
            return arrNotes.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{

            TextView txtTitle, txtContent;
            LinearLayout llrow;
            public ViewHolder(@NonNull View itemView){
                super(itemView);
                txtTitle= itemView.findViewById(R.id.txtTitle);
                txtContent= itemView.findViewById(R.id.txtContent);
                llrow=itemView.findViewById(R.id.llrow);
            }
        }
        public void deleteItem(int pos){

            AlertDialog dialog = new AlertDialog.Builder(context)
                    .setTitle("Delete")
                    .setMessage("Do you want to delete this note?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            databaseHelper.noteData().deleteNote(new Note(arrNotes.get(pos).getId(),
                                    arrNotes.get(pos).getTitle(), arrNotes.get(pos).getContent()));
                            notesInterface.onNoteDelete(); // <-- add this line
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    }).show();
        }
        }