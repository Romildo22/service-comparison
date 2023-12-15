package org.soap;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class SoapData {
    private List<User> users;
    private List<Music> musics;
    private List<Playlist> playlists;

    // Getters
    public List<User> getUsers() {
        return users;
    }

    public List<Music> getMusics() {
        return musics;
    }

    public List<Playlist> getPlaylists() {
        return playlists;
    }

    // Setters
    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void setMusics(List<Music> musics) {
        this.musics = musics;
    }

    public void setPlaylists(List<Playlist> playlists) {
        this.playlists = playlists;
    }
}

class User {
    private String id;
    private String name;
    private int age;

    // Getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    // Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }
}


class Music {
    private String id;
    private String name;
    private String author;
    private String playlistId;

    // Getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public String getPlaylistId() {
        return playlistId;
    }

    // Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPlaylistId(String playlistId) {
        this.playlistId = playlistId;
    }
}


class Playlist {
    private String id;
    private String name;
    private String userId;

    // Getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUserId() {
        return userId;
    }

    // Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}