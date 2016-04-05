package ie.gmit.sw.traverser;

import ie.gmit.sw.ai.Node;
import ie.gmit.sw.ai.NodeType;

public class RandomWalk implements Traverser {
	
	public Node traverse(Node curNode) { // Designed for enemies - Only returns one step at a time. Actively called until they die.
		Node[] adjacents;
		
		adjacents = curNode.getAdjacents(1);
		
		for(Node a : adjacents) {
			if(a.getNodeType() == NodeType.space) {
				NodeType type = curNode.getNodeType();
				a.setNodeType(type);
				curNode.setNodeType(NodeType.space);
				curNode = a;
			}
		}
		return curNode;
	}
}
