package com.example.acadzone;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import java.util.ArrayList;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;


    memoFragment memoFragment = new memoFragment();
    notesFragment notesFragment = new notesFragment();
    studyFragment studyFragment = new studyFragment();
    todolistFragment  todolistFragment = new todolistFragment();
    ScheduleFragment scheduleFragment = new ScheduleFragment();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);

    ActionBar actionBar = getSupportActionBar();
   actionBar.hide();



        bottomNavigationView  = findViewById(R.id.bottomNavigationView);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,memoFragment).commit();



        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.memo:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,memoFragment).commit();
                        return true;
                    case R.id.notes:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,notesFragment).commit();
                        return true;
                    case R.id.study:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,studyFragment).commit();
                        return true;
                    case R.id.todolist:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,todolistFragment).commit();
                        return true;
                    case R.id.schedule:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, scheduleFragment).commit();
                        return true;
                }

                return false;
            }
        });
    }

}