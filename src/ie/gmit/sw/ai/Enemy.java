package ie.gmit.sw.ai;

import java.util.Random;

import ie.gmit.sw.traverser.RandomWalk;
import ie.gmit.sw.traverser.Traverser;

public class Enemy implements Runnable
{
	private Node node;
	private boolean isAlive;
	
	public Enemy(Node node) {
		this.node = node;
		node.setNodeType(NodeType.enemy);
		isAlive = true;
	}
	
	public void run() {
		Traverser traverser = new RandomWalk();
		Random r = new Random();
		
		do{
			try {
				Thread.sleep(r.nextInt((2000 - 1000) + 1) + 1000);
			} 			
			catch (InterruptedException e) {
				System.out.println("Sleep was interrupted");
			}
			
			node = traverser.traverse(node);
		}while(isAlive);
		System.out.println("Kill at " + node.getRow() + " " + node.getCol());
		node.setNodeType(NodeType.space);
		return;
	}
	
	public Node getNode()
	{
		return node;
	}
	
	public boolean isAlive() {
		return isAlive;
	}

	public void kill() {
		isAlive = false;		
	}
}
