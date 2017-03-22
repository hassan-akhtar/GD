package com.ets.gd.Models;

/**
 * Created by hakhtar on 3/22/2017.
 * General Data
 */

public class Note {
    private String noteTitle;
    private String noteDescription;


    public Note() {
    }

    public Note(String noteTitle, String noteDescription) {
        this.noteTitle = noteTitle;
        this.noteDescription = noteDescription;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteDescription() {
        return noteDescription;
    }

    public void setNoteDescription(String noteDescription) {
        this.noteDescription = noteDescription;
    }
}


