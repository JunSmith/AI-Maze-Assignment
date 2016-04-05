package ie.gmit.sw.traverser;

import java.util.ArrayList;
import java.util.PriorityQueue;

import ie.gmit.sw.ai.Node;
import ie.gmit.sw.ai.NodeType;

public class AStar implements Traverser{
	private Node goal;
	private NodeType nType;
	private int steps;
	
	public AStar(Node goal, NodeType nType, int steps) {
		this.goal = goal;
		this.nType = nType;
		this.steps = steps;
	}

	public Node traverse(Node node) { // H-Bomb search, guaranteed to find and kill one enemy
		
//		int visitCount = 0;
    	
		PriorityQueue<Node> open = new PriorityQueue<Node>(steps, (Node current, Node next) -> (current.getPathCost() + current.getHeuristic(goal)) - (next.getPathCost() + next.getHeuristic(goal)));
		java.util.List<Node> closed = new ArrayList<Node>();
	
		open.offer(node);
		node.setPathCost(0);
		while(!open.isEmpty()) {
			node = open.poll();
			closed.add(node);
			node.SetVisited(true);
//			visitCount++;
			
//			if(node.is(goal)) {
			if(node.getNodeType() == nType) {
				System.out.println("Goal found");
				return goal;
			}
//			node.setNodeType(NodeType.goal);
			
			Node[] adjacents = node.getAdjacents(1);
			for(Node n : adjacents) {
				if(n.getNodeType() == NodeType.space || n.getNodeType() == NodeType.enemy || n.getNodeType() == NodeType.goal) {
					int score = node.getPathCost() + 1 + n.getHeuristic(goal);
					int existing = n.getPathCost() + n.getHeuristic(goal);
					
					if ((open.contains(n) || closed.contains(n)) && existing < score){
						continue;
					}else{
						open.remove(n);
						closed.remove(n);
						n.setParent(node);
						n.setPathCost(node.getPathCost() + 1);
						open.add(n);
					}	
				}
			}
		}
		
		for(Node n : closed) {
			n.SetVisited(false);
		}
		return null;
	}

}
