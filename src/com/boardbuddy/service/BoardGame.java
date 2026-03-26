package com.boardbuddy.service;

public class BoardGame {

    public BoardGame() {}

    public BoardGame(String id, String name, int minPlayers, int maxPlayers, int playTime, int year, String image, String thumbnail) {
        this.id = id;
        this.name = name;
        this.minPlayers = minPlayers;
        this.maxPlayers = maxPlayers;
        this.playTime = playTime;
        this.year = year;
        this.image = image;
        this.thumbnail = thumbnail;
    }

    public void setId(String id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setMin(int minPlayers) {
        this.minPlayers = minPlayers;
    }
    public void setMax(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }
    public void setPlay(int playTime) {
        this.playTime = playTime;
    }
    public void setYear(int year) {
        this.year = year;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public int getMinPlayers() {
        return minPlayers;
    }
    public int getMaxPlayers() {
        return maxPlayers;
    }
    public int getPlayTime() {
        return playTime;
    }
    public int getYear() {
        return year;
    }
    public String getImage() {
        return image;
    }
    public String getThumbnail() {
        return thumbnail;
    }

    private String id;
    private String name;
    private int minPlayers;
    private int maxPlayers;
    private int playTime;
    private int year;
    private String image;
    private String thumbnail;
}
