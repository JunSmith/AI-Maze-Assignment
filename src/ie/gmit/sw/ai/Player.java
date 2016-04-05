package ie.gmit.sw.ai;

import net.sourceforge.jFuzzyLogic.FIS;

public class Player 
{
	private Node node;
	private int stamina; 
	private Inventory inventory;
	
	public Player(Node node)	
	{
		this.node = node;
		stamina = 100;
		inventory = new Inventory();
	}
	
	public void pickUp(NodeType item) {
		if(item == NodeType.weapon) 
			inventory.giveWeapon();		
		else 
			inventory.setItem(item);		
	}
	
	public Inventory getInventory() {
		return inventory;
	}
	
	public void fight(Enemy e)	{
		FIS fis = FIS.load("fcl/fight.fcl", true);
		
		fis.setVariable("stamina", stamina);
		fis.setVariable("weapon", inventory.useWeapon());
		fis.evaluate();
		int damage = (int) fis.getVariable("damage").getValue();
		e.kill();
		this.setStamina(-damage);
	}
	
	public int getStamina() {
		return stamina;
	}
	
	public void setStamina(int change) {
		this.stamina += change;
		if(stamina > 200) 
			stamina = 200;
	}
	
	public Node getNode() {
		return node;
	}
	
	public void setNode(int r, int c, Node[][] maze) {
		node = maze[r][c];
		node.setNodeType(NodeType.player);
	}

	public void setNode(Node node) {
		this.node = node;		
	}
	
	public int getRow()	{
		return node.getRow();
	}
	
	public int getCol() {
		return node.getCol();
	}
}
