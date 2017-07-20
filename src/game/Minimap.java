package game;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

import data.Room;

public class Minimap {
	int scale = 50;
	public Rectangle canvas = new Rectangle(12*scale, 12*scale+scale, scale - 5, scale -5);
	public Minimap(int opacity, Graphics g, Room[][] arr, Vector2f currentLoc, Vector2f bossRoom) {
		g.setColor(new Color(255,255,255,opacity));
		g.fill(canvas);
		for(int y = 0; y < 10; y++){
			for(int x = 0; x < 10; x++){
					if(arr[y][x].getOccupied()== 1){
						g.setColor(Color.white);
						g.fillRect(canvas.getMinX()+(x*4), canvas.getMinX()+(y*4)+scale, canvas.getWidth()/24, canvas.getWidth()/24);
					}
					if((int)currentLoc.getX()==x&&(int)currentLoc.getY()==y){
						g.setColor(Color.red);
						g.fillRect(canvas.getMinX()+(x*4), canvas.getMinX()+(y*4)+scale, canvas.getWidth()/24, canvas.getWidth()/24);
					}
					if((int)bossRoom.getX()==x&&(int)bossRoom.getY()==y){
						g.setColor(Color.green);
						g.fillRect(canvas.getMinX()+(x*4), canvas.getMinX()+(y*4)+scale, canvas.getWidth()/24, canvas.getWidth()/24);
					}
				}
			}
		}
	}
