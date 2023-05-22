package com.example.acadzone;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class notesFragment extends Fragment implements RecyclerNotesAdapter.NotesInterface {

    Button btnCreateNote;
    FloatingActionButton fabAdd;
    RecyclerView recyclerNotes;
    View view;
    DatabaseHelper databaseHelper;
    LinearLayout noNotes;


    @Override
    public void onNoteDelete() {
        showNotes();
    }

    public notesFragment(){
        //required empty.
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_notes, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initVar();
        showNotes();


        fabAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                Dialog dialog = new Dialog(getContext());
               dialog.setContentView(R.layout.add_note);

                EditText editTitle, editContent;
                Button btnAdd;

                editTitle= dialog.findViewById(R.id.editTitle);
                editContent= dialog.findViewById(R.id.editContent);
                btnAdd= dialog.findViewById(R.id.btnAdd);


                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String title= editTitle.getText().toString();
                        String content = editContent.getText().toString();

                        if(!content.equals("")){

                            databaseHelper.noteData().addNote(new Note(title,content));
                            showNotes();

                            dialog.dismiss();
                        }
                        else{
                            Toast.makeText(getContext(),"Please enter",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                    dialog.show();



            }


        });

        btnCreateNote.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                fabAdd.performClick();
            }
        });

    }

    public void showNotes() {
        ArrayList<Note> arrNotes= (ArrayList<Note>) databaseHelper.noteData().getNotes();

        if(arrNotes.size()>0){
            recyclerNotes.setVisibility(View.VISIBLE);
            noNotes.setVisibility(View.GONE);

            RecyclerNotesAdapter adapter = new RecyclerNotesAdapter(getContext(), arrNotes, databaseHelper, this); // <-- pass the instance of notesFragment
            recyclerNotes.setAdapter(adapter);
        }
        else {
            noNotes.setVisibility(View.VISIBLE);
            recyclerNotes.setVisibility(View.GONE);
        }
    }

    private void initVar(){
        btnCreateNote = view.findViewById(R.id.btnCreateNote);
        fabAdd = view.findViewById(R.id.fabAdd);
        recyclerNotes = view.findViewById(R.id.recyclerNotes);
        noNotes= view.findViewById(R.id.noNotes);

        recyclerNotes.setLayoutManager(new GridLayoutManager(getContext(), 2));

        databaseHelper = DatabaseHelper.getInstance(getContext().getApplicationContext());

    }
}