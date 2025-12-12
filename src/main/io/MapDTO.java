package main.io;

import main.data.impl.list.LinkedUnorderedList;

public class MapDTO {
    public LinkedUnorderedList<RoomDTO> rooms = new LinkedUnorderedList<>();
    public LinkedUnorderedList<HallDTO> halls = new LinkedUnorderedList<>();
}
