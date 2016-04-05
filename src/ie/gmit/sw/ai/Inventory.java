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
	
	public NodeType useItem() {
		NodeType i = item;
		item = NodeType.wall;
		return i;
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
}
