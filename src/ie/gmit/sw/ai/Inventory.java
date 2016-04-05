package ie.gmit.sw.ai;

public class Inventory {
	private NodeType item;
	private int weaponState;
	
	public Inventory() {
		item = NodeType.wall;
		weaponState = 0;
	}
	
	public void setItem(NodeType item) {
		this.item = item;
//		if(item == NodeType.weapon)
//			weaponState = 3;
	}
	
	public void giveWeapon(){
		weaponState = 3;
	}
	
	public int getWeaponState() {
		return weaponState;
	}
	
	public NodeType getItem() {
		return item;
	}
	
	public int useWeapon() {
		int state = weaponState;
		if(weaponState > 0)
			weaponState--;
		else
			item = NodeType.wall;
		return state;
	}
	
	public NodeType use() {
		NodeType curItem = item;
		item = NodeType.wall;
		//Uses item held.
		// Bomb - DFS deal damage of 20(?) steps
		// H-Bomb - deal heavier damage with A* search?
		
		// Return the character and do something in GameRunner?
		return curItem;
	}
}
