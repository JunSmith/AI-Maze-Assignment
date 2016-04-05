package ie.gmit.sw.traverser;

import java.util.ArrayList;
import java.util.PriorityQueue;

import ie.gmit.sw.ai.Node;
import ie.gmit.sw.ai.NodeType;

public class AStar implements Traverser{
	private Node goal;
	private int steps;
	
	public AStar(Node goal, int steps) {
		this.goal = goal;
		this.steps = steps;
	}

	public Node traverse(Node node) { 
    	
		PriorityQueue<Node> open = new PriorityQueue<Node>(steps, (Node current, Node next) -> (current.getPathCost() + current.getHeuristic(goal)) - (next.getPathCost() + next.getHeuristic(goal)));
		java.util.List<Node> closed = new ArrayList<Node>();
	
		open.offer(node);
		node.setPathCost(0);
		while(!open.isEmpty()) {
			node = open.poll();
			closed.add(node);
			node.SetVisited(true);
			
			if(node.is(goal)) {
				return goal;
			}
			
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
						n.SetVisited(false);
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
