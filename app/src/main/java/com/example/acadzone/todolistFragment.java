package com.example.acadzone;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class todolistFragment extends Fragment {
    public ArrayList<String> items;
    public ListView list;
    public Button button;
    public ArrayAdapter<String> itemsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_todolist, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        list = view.findViewById(R.id.list);
        button = view.findViewById(R.id.button);
        items = new ArrayList<>();
        itemsAdapter= new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1,items);
        list.setAdapter(itemsAdapter);

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                final int delete= i;

                new AlertDialog.Builder(requireContext())
                        .setIcon(R.drawable.deletememo)
                        .setTitle("Are you sure?")
                        .setMessage("Do you want to delete this task?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Context context = requireContext();
                                Toast.makeText(context, "Task Removed", Toast.LENGTH_SHORT).show();
                                items.remove(delete);
                                itemsAdapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("No",null)
                        .show();

                return true;
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItem();
            }
        });
    }

    private void addItem() {
        EditText input = getView().findViewById(R.id.edit_text);
        String itemText= input.getText().toString();
        if(!itemText.equals("")){
            itemsAdapter.add(itemText);
            input.setText("");
        }
        else {
            Toast.makeText(getContext(),"Enter a task", Toast.LENGTH_SHORT).show();
        }
    }


}
