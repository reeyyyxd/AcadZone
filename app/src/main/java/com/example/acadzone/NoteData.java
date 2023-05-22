package com.example.acadzone;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;



@Dao
public interface NoteData {

    @Query("select * from note")
    List<Note> getNotes();

    @Insert
    void addNote(Note note);

    @Delete
    void deleteNote(Note note);
}