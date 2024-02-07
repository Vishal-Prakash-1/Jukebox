package org.example;

public class Song {
    String songid;
    String songname;
    String genre;
    String artist;
    String duration;
    String songpath;

    public Song(String songid, String songname, String genre, String artist, String duration, String songpath) {
        this.songid = songid;
        this.songname = songname;
        this.genre = genre;
        this.artist = artist;
        this.duration = duration;
        this.songpath = songpath;
    }

    @Override
    public String toString() {
        return "Song{" +
                "songid='" + songid + '\'' +
                ", songname='" + songname + '\'' +
                ", genre='" + genre + '\'' +
                ", artist='" + artist + '\'' +
                ", duration='" + duration + '\'' +
                ", songpath='" + songpath + '\'' +
                '}';
    }
}
