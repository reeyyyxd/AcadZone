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
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class notesFragment extends Fragment {

    Button btnCreateNote;
    FloatingActionButton fabAdd;
    RecyclerView recyclerNotes;
    View view;

    EditText editTitle, editContent;
    Button btnAdd;

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


        fabAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                Dialog dialog = new Dialog(getContext());
               dialog.setContentView(R.layout.add_note);



                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String title= editTitle.getText().toString();
                        String content = editContent.getText().toString();

                        if(!content.equals("")){


                        }
                        else{
                            Toast.makeText(getContext(),"Please enter",Toast.LENGTH_SHORT).show();
                        }


                    }
                });




            }
        });

        btnCreateNote.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                fabAdd.performClick();
            }
        });

    }

    private void initVar(){
        btnCreateNote = view.findViewById(R.id.btnCreateNote);
        fabAdd = view.findViewById(R.id.fabAdd);
        recyclerNotes = view.findViewById(R.id.recyclerNotes);

        recyclerNotes.setLayoutManager(new GridLayoutManager(getContext(), 2));
    }
}