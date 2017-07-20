package data;
import java.security.SecureRandom;

public class Room {
	public int occupied;
	public int[][] layout;
	public Boolean isBossRoom;
	public Room() {
		isBossRoom = false;
	}
	public static int[][] selectRoomType(){
		SecureRandom rand = new SecureRandom();
		int num = rand.nextInt(5);
		switch(num){
		case 0 : return RoomTypes.ROOM1.getRoomLayout();
		case 1: return RoomTypes.ROOM2.getRoomLayout();
		case 2: return RoomTypes.ROOM3.getRoomLayout();
		case 3: return RoomTypes.ROOM4.getRoomLayout();
		case 4: return RoomTypes.ROOM5.getRoomLayout();
		case 5: return RoomTypes.ROOM6.getRoomLayout();
		case 6: return RoomTypes.ROOM7.getRoomLayout();
		}
		return null;
	}
	public int getOccupied() {
		return occupied;
	}
	public int[][] getLayout() {
		return layout;
	}
	public void setOccupied(int occupied) {
		this.occupied = occupied;
	}
	public void setLayout(int[][] layout) {
		this.layout = layout;
	}
	public Boolean getIsBossRoom() {
		return isBossRoom;
	}
	public void setIsBossRoom(Boolean isBossRoom) {
		this.isBossRoom = isBossRoom;
	}

}
