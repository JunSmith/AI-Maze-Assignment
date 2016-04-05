package ie.gmit.sw.ai;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Node {
	private int row;
	private int col;
	private NodeType nodeType;
	private boolean visited;
	private boolean adjacent;
	private boolean wall;
	private Node[][] maze;
	private int distance;
	private Node parent;
	
	public Node(int row, int col, Node[][] maze) {
		this.row = row;
		this.col = col;
		this.visited = false;
		this.maze = maze;
		this.adjacent = false;
		this.wall = false;
	}

	public void setNodeType(NodeType nodeType) {
		this.nodeType = nodeType;
	}
	
	public NodeType getNodeType() {
		return nodeType;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}
	
	public void SetVisited(boolean v) {
		this.visited = v;
	}
	
	public boolean isVisited() {
		return visited;
	}
	
	public Node[] getAdjacents(int steps) {
		List<Node> nl = new LinkedList<Node>();
		int count = 0;
		
		if(row > steps) {
			nl.add(maze[row - steps][col]);
			count++;
		}
		if(row < maze.length - steps ){
			nl.add(maze[row + steps][col]);
			count++;
		}
		if(col > steps) {
			nl.add(maze[row][col - steps]);
			count++;
		}
		if(col < maze.length - steps ) {
			nl.add(maze[row][col + steps]);
			count++;
		}
		
		Collections.shuffle(nl);
		return (Node[]) nl.toArray(new Node[count]);
	}
	
	public boolean isItem() {
//		if (nodeType != NodeType.space && nodeType != NodeType.wall && nodeType != NodeType.enemy && nodeType != NodeType.goal) {
//			return true;
//		}
		if(nodeType == NodeType.weapon || nodeType == NodeType.bomb || nodeType == NodeType.hBomb)
			return true;
		
		else 
			return false;
	}
	
	public boolean getAdjacent() {
		return adjacent;
	}
	
	public void setAdjacent(boolean bool) {
		this.adjacent = bool;
	}
	
	public boolean getWall() {
		return wall;
	}
	
	public void setWall() {
		this.wall = true;
	}
	
	public int getHeuristic(Node goal){
		double x1 = this.col;
		double y1 = this.row;
		double x2 = goal.getCol();
		double y2 = goal.getRow();
		return (int) Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2));
	}
	
	public int getPathCost() {
		return distance;
	}

	public void setPathCost(int distance) {
		this.distance = distance;		
	}
	
	public boolean is(Node node) {
		if(this.getRow() == node.getRow() && this.getCol() == node.getCol()) 
			return true;		
		
		else 
			return false;
	}
	
	public void setParent(Node parent) {
		this.parent = parent;
	}
	
	public Node getParent(){
		return parent;
	}
	
	public String toString() {
		return (row + " | " + col);
	}
}
