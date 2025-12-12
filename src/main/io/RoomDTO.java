package main.io;

public class RoomDTO {
    public String id;
    public String name;
    public boolean hasTreasure;
    public boolean isEntrance;
    public int x;
    public int y;
    public Integer correctLeverId;

    public String getChallengeType() {
        if (correctLeverId != null) return "LEVER";
        if (isEntrance) return "ENTRANCE";
        if (hasTreasure) return "TREASURE";
        return "NORMAL";
    }
}