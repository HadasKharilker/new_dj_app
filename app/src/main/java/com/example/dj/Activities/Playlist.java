package com.example.dj.Activities;

import java.util.Set;

public class Playlist {
    private String djUid;
    private Set<String> requestedSongs;

    public Playlist(String djUid,Set<String> requestedSongs){
        this.djUid=djUid;
        this.requestedSongs=requestedSongs;

    }
    public Playlist(String djUid){
        this.djUid=djUid;


    }
    public Playlist(){}

    public void addSongs(String song) throws Exception {
        if(this.requestedSongs==null){
            this.requestedSongs.add(song);
        }

        else if (this.requestedSongs.contains(song)) {
            throw new Exception("Already exists!");
        }
        else
            this.requestedSongs.add(song);

    }

    public Set<String> getAllSongs() {

        return this.requestedSongs;

    }







}
