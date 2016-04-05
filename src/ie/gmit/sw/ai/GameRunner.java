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
	
	public GameRunner() throws Exception{
		Maze m = new Maze(MAZE_DIMENSION, MAZE_DIMENSION);
		model = m.getMaze();
    	view = new GameView(model);
//    	player = new Player(model[(int) (MAZE_DIMENSION * Math.random())][(int) (MAZE_DIMENSION * Math.random())]);
    	Node[] nArr = m.addEntity(NodeType.player, 1);
    	player = new Player(nArr[0]);
    	eManager = new EnemyManager(model, ENEMY_COUNT, MAZE_DIMENSION);
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
        	itemOperation(player.getInventory().getItem());
        }
        else{
        	return;
        }
        
//        System.out.println(player.getRow() + " " + player.getCol());
    }
    public void keyReleased(KeyEvent e) {} //Ignore
	public void keyTyped(KeyEvent e) {} //Ignore
	
	private void itemOperation(NodeType item) {
		
		switch(item)
		{
			case bomb:
				System.out.println("Bomb used");
				Traverser aS = new AStar(eManager.getEnemy(1).getNode(), NodeType.enemy);
				Node found = aS.traverse(player.getNode());
				try {
				eManager.getEnemy(found).kill();
				}
				catch(Exception e) {
					System.out.println("Enemy not found");
				}
				eManager.getAliveEnemies();
				break;
				
			case hBomb: 
				System.out.println("hBomb used");
				for (int i = 0; i < model.length; i++) {
					
				}
				
			case help:
				System.out.println("help used");
				eManager.getAliveEnemies();
				player.setStamina(50);
				break;
				
			default:
				break;
		}
	}
	
	private boolean isValidMove(int r, int c) {
		if(player.getStamina() <= 0)
			return false;
		
		if(model[r][c].getNodeType() == NodeType.space) {
			model[player.getRow()][player.getCol()].setNodeType(NodeType.space);
			model[r][c].setNodeType(NodeType.player);
			player.setStamina(-1);
			return true;
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