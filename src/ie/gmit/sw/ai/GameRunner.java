package ie.gmit.sw.ai;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import ie.gmit.sw.traverser.AStar;
import ie.gmit.sw.traverser.Traverser;

public class GameRunner implements KeyListener{
	private static final int MAZE_DIMENSION = 60;
	private static final int ENEMY_COUNT = 50;
	private Node[][] model;
	private GameView view;
	private EnemyManager eManager;
	private Player player;
	private Maze m;
	
	public GameRunner() throws Exception{
		m = new Maze(MAZE_DIMENSION, MAZE_DIMENSION);
		model = m.getMaze();
    	view = new GameView(model);
    	Node[] pArr = m.addEntity(NodeType.player, 1);
    	player = new Player(pArr[0]);
//    	Node p = m.getGoal();
//    	p.setCol(m.getGoal().getCol() + 3);
//    	player = new Player(p);
    	Node[] eArr = m.addEntity(NodeType.enemy, ENEMY_COUNT);
    	eManager = new EnemyManager(eArr, ENEMY_COUNT, MAZE_DIMENSION);
    	Thread vut = new Thread(new ViewUpdater(view, 30, player));
    	vut.start();
    	
    	Dimension d = new Dimension(GameView.DEFAULT_VIEW_SIZE, GameView.DEFAULT_VIEW_SIZE);
    	view.setPreferredSize(d);
    	view.setMinimumSize(d);
    	view.setMaximumSize(d);
    	
    	JFrame f = new JFrame("G00302135 - B.Sc. in Computing (Software Development)");
    	
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        f.addKeyListener(this);
        f.getContentPane().setLayout(new FlowLayout());
        f.add(view);
        f.setSize(1000,1000);
        f.setLocation(100,100);
        f.pack();
        f.setVisible(true);
	}	

    public void keyPressed(KeyEvent e) {
    	int currentRow = player.getNode().getRow();
    	int currentCol = player.getNode().getCol();
    	
        if (e.getKeyCode() == KeyEvent.VK_RIGHT && currentCol < MAZE_DIMENSION - 1) {
        	if (isValidMove(currentRow, currentCol + 1)) 
        		player.setNode(currentRow, currentCol + 1, model);
        }
        else if (e.getKeyCode() == KeyEvent.VK_LEFT && currentCol > 0) {
        	if (isValidMove(currentRow, currentCol - 1)) 
        		player.setNode(currentRow, currentCol - 1, model);
        }
        else if (e.getKeyCode() == KeyEvent.VK_UP && currentRow > 0) {
        	if (isValidMove(currentRow - 1, currentCol)) 
        		player.setNode(currentRow - 1, currentCol, model);	  	
        }
        else if (e.getKeyCode() == KeyEvent.VK_DOWN && currentRow < MAZE_DIMENSION - 1) {
        	if (isValidMove(currentRow + 1, currentCol)) 
        		player.setNode(currentRow + 1, currentCol, model);	  	
        }
        else if (e.getKeyCode() == KeyEvent.VK_Z){
        	view.toggleZoom();
        }
        else if (e.getKeyCode() == KeyEvent.VK_X){
        	itemOperation(player.getInventory().useItem());
        }
        else{
        	return;
        }
    }
    public void keyReleased(KeyEvent e) {} //Ignore
	public void keyTyped(KeyEvent e) {} //Ignore
	
	private void itemOperation(NodeType item) {
		
		switch(item)
		{
			case bomb:
				System.out.println("Bomb used");
				Traverser asb = new AStar(eManager.getEnemy(1).getNode(), NodeType.enemy, 20);
				Node bFound = asb.traverse(player.getNode());
				try {
					eManager.getEnemy(bFound).kill();
				}
				catch(Exception e) {
					System.out.println("Enemy not found");
				}
				eManager.getAliveEnemies();
				break;
				
			case hBomb: 
				System.out.println("hBomb used");
				Traverser ash = new AStar(eManager.getEnemy(1).getNode(), NodeType.enemy, 40);
				Node hFound = ash.traverse(player.getNode());
				try {
					eManager.getEnemy(hFound).kill();
				}
				catch(Exception e) {
					System.out.println("Enemy not found");
				}
				eManager.getAliveEnemies();
				break;
				
			case help:
				System.out.println("help used");
				eManager.getAliveEnemies();
				player.setStamina(50);
				Traverser asHelp = new AStar(m.getGoal(), NodeType.goal, 100);
				Node helpFound = asHelp.traverse(player.getNode());
				if(helpFound.getNodeType() == NodeType.goal)
					System.out.println("Found");
				break;
				
			default:
				break;
		}
	}
	
	private boolean isValidMove(int r, int c) {
		
		if(model[r][c].getNodeType() == NodeType.space) {
			model[player.getRow()][player.getCol()].setNodeType(NodeType.space);
			model[r][c].setNodeType(NodeType.player);
			player.setStamina(-1);
			return true;
		}
		
		else if(model[r][c].getNodeType() == NodeType.goal) {
			view.win();
			return false;
		}
		
		else {
			Interact(model[r][c]);
			return false;
		}
	}
	
	private void Interact(Node node) {
		if(node.isItem()) {
			NodeType item = node.getNodeType();
			if(item == NodeType.weapon)
				node.setNodeType(NodeType.wall);
			else
				node.setNodeType(player.getInventory().getItem());
			player.pickUp(item);
		}
		
		else if(node.getNodeType() == NodeType.help) {
			player.setStamina(50);
			node.setNodeType(NodeType.wall);
		}
		
		else if(node.getNodeType() == NodeType.enemy) {
			Enemy enemy = eManager.getEnemy(node);
			if(enemy.isAlive())
				player.fight(enemy);
		}
	}
	
	public static void main(String[] args) throws Exception{
		new GameRunner();
	}
}