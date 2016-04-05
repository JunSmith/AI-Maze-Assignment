package ie.gmit.sw.ai;

public class Maze {
	private Node[][] maze;
	public Maze(int rows, int cols){
		maze = new Node[rows][cols];
		init();

		Node curNode = maze[(int) (Math.random() * maze.length)][(int) (Math.random() * maze.length)];
		curNode.SetVisited(true);
		curNode.setNodeType(NodeType.space);
		build(curNode);
		resetVisits();
		
		int featureNumber = (int)((rows * cols) * 0.01);
		addFeature(NodeType.weapon, NodeType.wall, featureNumber);
		addFeature(NodeType.help, NodeType.wall, featureNumber);
		addFeature(NodeType.bomb, NodeType.wall, featureNumber);
		addFeature(NodeType.hBomb, NodeType.wall, featureNumber);
		addFeature(NodeType.goal, NodeType.space, 1);
	}
	
	private void resetVisits() {
		for (int i = 0; i < maze.length; i++) {
			for (int j = 0; j < maze.length; j++) {
				maze[i][j].SetVisited(false);
			}
		}
	}

	private void init(){
		for (int row = 0; row < maze.length; row++){
			for (int col = 0; col < maze[row].length; col++){
				Node cur = new Node(row, col, maze);
				cur.setNodeType(NodeType.wall);
				maze[row][col] = cur;
			}
		}
	}
	
	private void addFeature(NodeType feature, NodeType replace, int number){
		int counter = 0;
		while (counter < number){
			int row = (int) (maze.length * Math.random());
			int col = (int) (maze[0].length * Math.random());
			
			if (maze[row][col].getNodeType() == replace){
				maze[row][col].setNodeType(feature);
				counter++;
			}
		}
	}
	
	public Node[] addEntity(NodeType feature, int number) {
		Node[] entArr = new Node[number];
		int counter = 0;
		int row;
		int col;
		
		while(counter < number) {
			row = (int) (maze.length * Math.random());
			col = (int) (maze[0].length * Math.random());
			
			if(maze[row][col].getNodeType() == NodeType.space) {
				maze[row][col].setNodeType(feature);
				entArr[counter] = maze[row][col];
				counter++;
			}
		}
		
		return entArr;
	}
	
	private void build(Node curNode) {
		Node[] nArr = curNode.getAdjacents(2);
		
		for(Node node : nArr) {
			if(node.getAdjacent())
				continue;
			
			else {
				node.SetVisited(true);
				node.setNodeType(NodeType.space);
				int halfRow = (curNode.getRow() + node.getRow()) / 2;
				int halfCol = (curNode.getCol() + node.getCol()) / 2;
				maze[halfRow][halfCol].SetVisited(true);
				maze[halfRow][halfCol].setNodeType(NodeType.space);
				node.setAdjacent(true);
				build(node);
			}
		}
	}
	
	public Node[][] getMaze(){
		return this.maze;
	}
	
	public String toString(){
		StringBuffer sb = new StringBuffer();
		for (int row = 0; row < maze.length; row++){
			for (int col = 0; col < maze[row].length; col++){
				sb.append(maze[row][col]);
				if (col < maze[row].length - 1) sb.append(",");
			}
			sb.append("\n");
		}
		return sb.toString();
	}
}