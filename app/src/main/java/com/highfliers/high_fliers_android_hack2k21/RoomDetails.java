package com.highfliers.high_fliers_android_hack2k21;

public class RoomDetails {
    String roomName,category,section;

    public RoomDetails(String roomName, String category, String section) {
        this.roomName = roomName;
        this.category = category;
        this.section = section;
    }

    public String getSection() {
        return section;
    }

    public String getRoomName() {
        return roomName;
    }

    public String getCategory() {
        return category;
    }
}
