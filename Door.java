class Door extends Feature
{
    Room nextRoom;
    boolean isLocked;
    
    Door(String filePath, double x, double y, Room nextRoom)
    {
        super("Door to " + nextRoom.name, filePath, x, y);
        
        this.nextRoom = nextRoom;
    }
}