package ie.gmit.sw.ai;

public class EnemyManager {
	private Thread[] threadArr;
	private Enemy[] enemyArr;
	
	public EnemyManager(Node[][] maze, int enemyCount, int dimension) {
		threadArr = new Thread[enemyCount];
		enemyArr = new Enemy[enemyCount];
		createEnemies(maze, enemyCount, dimension);
	}

	private void createEnemies(Node[][] maze, int enemies, int dimension) {
		for (int i = 0; i < enemies; i++) {
//			enemyArr[i] = new Enemy(maze, (int) (dimension * Math.random()), (int) (dimension * Math.random()));
			enemyArr[i] = new Enemy(maze[(int) (dimension * Math.random())][(int) (dimension * Math.random())]);
			threadArr[i] = new Thread(enemyArr[i]);
			threadArr[i].start();
		}
	}
	
//	public Enemy getEnemy(int row, int column) {
//		for(Enemy enemy : enemyArr)
//			if(enemy.getRow() == row && enemy.getCol() == column)
//				return enemy;
//		
//		return null;
//	}
	
	public Enemy getEnemy(Node n) {
		for (Enemy e : enemyArr) {
			Node eNode = e.getNode();
			if(n.is(eNode)) {
				return e;
			}				
		}
		
		return null;
	}
	
	public Enemy getEnemy(int i) {
		for (int j = i - 1; j < enemyArr.length - 1; j++) 
//			if (enemyArr[j].getHealth() > 0) 
			if(enemyArr[j].isAlive())
				return enemyArr[j];
		
		return null;
	}
	
	public int getAliveEnemies() {
		int count = 0;
		
		for (Enemy e : enemyArr) 
//			if(e.getHealth() > 0)
			if(e.isAlive())
				count++;		
		
		System.out.println(count + " Enemies alive");
		return count;
	}
	
	public void kill(Node n) {
		for (Enemy e : enemyArr) {
			Node eNode = e.getNode();
			if(n.is(eNode)) {
//				e.damage(50);
				e.kill();
			}
				
		}
	}
}
