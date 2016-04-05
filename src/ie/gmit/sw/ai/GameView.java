package ie.gmit.sw.ai;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.imageio.*;
import javax.swing.*;

public class GameView extends JPanel implements ActionListener{
	private static final long serialVersionUID = 1L;
	public static final int DEFAULT_VIEW_SIZE = 800;	
	private static final int IMAGE_COUNT = 10;
	private int cellspan = 5;	
	private int cellpadding = 2;
	private Node[][] maze;
	private BufferedImage[] images;
	private int enemy_state = 5;
	private Timer timer;
	private int currentRow;
	private int currentCol;
	private boolean zoomOut = false;
	private int imageIndex = -1;
	private int stamina;
	private int weaponState;
	
	public GameView(Node[][] maze) throws Exception{
		init();
		this.maze = maze;
		setBackground(Color.GRAY);
		setDoubleBuffered(true);
		timer = new Timer(300, this);
		timer.start();
	}
	
	public void setCurrentRow(int row) {
		if (row < cellpadding){
			currentRow = cellpadding;
		}else if (row > (maze.length - 1) - cellpadding){
			currentRow = (maze.length - 1) - cellpadding;
		}else{
			currentRow = row;
		}
	}

	public void setCurrentCol(int col) {
		if (col < cellpadding){
			currentCol = cellpadding;
		}else if (col > (maze[currentRow].length - 1) - cellpadding){
			currentCol = (maze[currentRow].length - 1) - cellpadding;
		}else{
			currentCol = col;
		}
	}

	public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
              
        cellspan = zoomOut ? maze.length : 5;         
        final int size = DEFAULT_VIEW_SIZE/cellspan;
        g2.drawRect(0, 0, GameView.DEFAULT_VIEW_SIZE, GameView.DEFAULT_VIEW_SIZE);
        
        for(int row = 0; row < cellspan; row++) {
        	for (int col = 0; col < cellspan; col++){  
        		int x1 = col * size;
        		int y1 = row * size;
        		
        		NodeType ch = NodeType.wall;
       		
        		if (zoomOut){
        			ch = maze[row][col].getNodeType();
        			if (row == currentRow && col == currentCol){
        				g2.setColor(Color.YELLOW);
        				g2.fillRect(x1, y1, size, size);
        				continue;
        			}
        		}else{
        			ch = maze[currentRow - cellpadding + row][currentCol - cellpadding + col].getNodeType();
        	        g2.setColor(Color.DARK_GRAY);
        	        g2.fillRect(10, 10, 300, 70);
        	        g2.setColor(Color.WHITE);
        	        g2.drawString("Stamina", 20, 30);
        	        g2.drawString("Weapon", 20, 65);
        	        g2.setColor(Color.BLACK);
        	        g2.fillRect(75, 15, 210, 20);
        	        g2.fillRect(75, 50, 100, 20);
        	        g2.setColor(Color.GREEN);
        	        g2.fillRect(80, 20, stamina, 10);        	        
        	        g2.setColor(Color.LIGHT_GRAY);
        	        g2.fillRect(80, 55, weaponState, 10);
        	        
        		}
        		
        		
        		if (ch == NodeType.wall){        			
        			imageIndex = 0;
        		}else if (ch == NodeType.weapon){
        			imageIndex = 1;
        		}else if (ch == NodeType.help){
        			imageIndex = 2;
        		}else if (ch == NodeType.bomb){
        			imageIndex = 3;
        		}else if (ch == NodeType.hBomb){
        			imageIndex = 4;
        		}else if (ch == NodeType.enemy){
        			imageIndex = enemy_state;       			
        		}else if (ch == NodeType.player) {
        			imageIndex = 7;
        		}else if (ch == NodeType.goal) {
        			imageIndex = 8;
        		}else if (ch == NodeType.hint) {
        			imageIndex = 8;
        		}else{        		
        			imageIndex = -1;
        		}
        		
        		if (imageIndex >= 0){
        			g2.drawImage(images[imageIndex], x1, y1, null);
        		}else{
        			g2.setColor(Color.GRAY);
        			g2.fillRect(x1, y1, size, size);
        		}      		
        	}
        }
	}
	
	public void toggleZoom(){
		zoomOut = !zoomOut;		
	}

	public void actionPerformed(ActionEvent e) {	
		if (enemy_state < 0 || enemy_state == 5){
			enemy_state = 6;
		}else{
			enemy_state = 5;
		}
		this.repaint();
	}
	
	public void setStats(int stamina, int weaponState) {
		this.stamina = stamina;
//		int ws = (int) weaponState * 30;
		this.weaponState = weaponState * 30;
	}
	
	private void init() throws Exception{
		images = new BufferedImage[IMAGE_COUNT];
		images[0] = ImageIO.read(new java.io.File("src/resources/hedge.png"));
		images[1] = ImageIO.read(new java.io.File("src/resources/sword.png"));		
		images[2] = ImageIO.read(new java.io.File("src/resources/help.png"));
		images[3] = ImageIO.read(new java.io.File("src/resources/bomb.png"));
		images[4] = ImageIO.read(new java.io.File("src/resources/h_bomb.png"));
		images[5] = ImageIO.read(new java.io.File("src/resources/spider_down.png"));
		images[6] = ImageIO.read(new java.io.File("src/resources/spider_up.png"));
		images[7] = ImageIO.read(new java.io.File("src/resources/player.png"));
		images[8] = ImageIO.read(new java.io.File("src/resources/trophy.png"));
		images[9] = ImageIO.read(new java.io.File("src/resources/hint.png"));
	}
}