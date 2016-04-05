package ie.gmit.sw.ai;

public class EnemyManager {
	private Thread[] threadArr;
	private Enemy[] enemyArr;
	
	public EnemyManager(Node[] nodes, int enemyCount, int dimension) {
		threadArr = new Thread[enemyCount];
		enemyArr = new Enemy[enemyCount];
		createEnemies(nodes);
	}
	
	private void createEnemies(Node[] nodes) {
		for (int i = 0; i < enemyArr.length; i++) {
			enemyArr[i] = new Enemy(nodes[i]);
			threadArr[i] = new Thread(enemyArr[i]);
			threadArr[i].start();
		}
	}
	
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
