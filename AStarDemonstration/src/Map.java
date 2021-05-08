import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Map {
	char[][] floor;
	int[] playerPosition = {8, 19};
	int[][] enemiesPosition = {{1, 19},{18, 2},{18, 36}};
	int[] enemyNext = {0,0};
	Set<Integer> usedSquares = new HashSet<>();
	
	
	public Map(){

		String row0s = "_______________________________________";
		String row1s = "|                                     |";
		String row2s = "|   _____________    _____________    |";
		String row3s = "|   |            |   |            |   |";
		String row4s = "|   |            |   |            |   |";
		String row5s = "|   |            |   |            |   |";
		String row6s = "|   |            |   |            |   |";
		String row7s = "|   |____________|   |____________|   |";
		String row8s = "|                                     |";
		String row9s = "|   _____________    _____________    |";
		String rowAs = "|   |            |   |            |   |";
		String rowBs = "|   |            |   |            |   |";
		String rowCs = "|   |            |   |            |   |";
		String rowDs = "|   |            |   |            |   |";
		String rowEs = "|   |            |   |            |   |";
		String rowFs = "|   |            |   |            |   |";
		String rowGs = "|   |            |   |            |   |";
		String rowHs = "|   |____________|   |____________|   |";
		String rowIs = "|                                     |";
		String rowJs = "|_____________________________________|";
		floor = new char[20][39];
		floor[0] = row0s.toCharArray();
		floor[1] = row1s.toCharArray();
		floor[2] = row2s.toCharArray();
		floor[3] = row3s.toCharArray();
		floor[4] = row4s.toCharArray();
		floor[5] = row5s.toCharArray();
		floor[6] = row6s.toCharArray();
		floor[7] = row7s.toCharArray();
		floor[8] = row8s.toCharArray();
		floor[9] = row9s.toCharArray();
		floor[10] = rowAs.toCharArray();
		floor[11] = rowBs.toCharArray();
		floor[12] = rowCs.toCharArray();
		floor[13] = rowDs.toCharArray();
		floor[14] = rowEs.toCharArray();
		floor[15] = rowFs.toCharArray();
		floor[16] = rowGs.toCharArray();
		floor[17] = rowHs.toCharArray();
		floor[18] = rowIs.toCharArray();
		floor[19] = rowJs.toCharArray();
		



		
		
		
	}
	void print(){
		for(int i = 0; i< floor.length;i++) {
			System.out.println();
			for(int j = 0; j< floor[0].length;j++) {
				if(enemiesPosition[0][0] == i && enemiesPosition[0][1] == j) { //hardcoded based on 3 enemies (bad)
					System.out.print("X");
				}else if(enemiesPosition[1][0] == i && enemiesPosition[1][1] == j) {
					System.out.print("X");
				}else if(enemiesPosition[2][0] == i && enemiesPosition[2][1] == j) {
					System.out.print("X");
				}else if(playerPosition[0] == i && playerPosition[1] == j) {
					System.out.print("O");
				}else {
					System.out.print(floor[i][j]);
				}
				
			}
		}
	}
	boolean useableLocation(int row, int col) {
		
		if(row == 1 || row == 8 || row == 18) {
			return true;
		}
		if(col == 2 || col == 19 || col == 36) {
			return true;
		}
		return false;
	}
	int[] enemyNextSquare(int cRow, int cCol, int tRow, int tCol){ //current row and column, target 
		Random rng = new Random(); //random chance not to move
		if(rng.nextDouble() > 0.80) {
			int[] stayPut = {cRow, cCol};
			return stayPut;
		}
		
		
		int[][] nextMoves = new int[4][3]; //4 options to move, col, row, score
		
		//0 is up
		nextMoves[0][0] = cRow - 1;
		nextMoves[0][1] = cCol;
		nextMoves[0][2] = Math.abs(tRow - cRow + 1) + Math.abs(tCol - cCol); //manhattan distance from target is the scoring function
																			//("Best" option has the smallest manhattan distance)
		//1 is down
		nextMoves[1][0] = cRow + 1;
		nextMoves[1][1] = cCol;
		nextMoves[1][2] = Math.abs(tRow - cRow - 1) + Math.abs(tCol - cCol);
		
		//2 is left
		nextMoves[2][0] = cRow;
		nextMoves[2][1] = cCol - 1;
		nextMoves[2][2] = Math.abs(tRow - cRow) + Math.abs(tCol - cCol + 1);
		
		//3 is left
		nextMoves[3][0] = cRow;
		nextMoves[3][1] = cCol + 1;
		nextMoves[3][2] = Math.abs(tRow - cRow) + Math.abs(tCol - cCol - 1);
		
		//sort the moves to find the best choice.
		//bubble sort fine for 4 moves
		for(int i = 3; i > 0; i--) {
			for(int j = 0; j<i;j++) {
				if(nextMoves[j][2] > nextMoves[j+1][2]) {
					int temp1 = nextMoves[j][0];
					int temp2 = nextMoves[j][1];
					int temp3 = nextMoves[j][2];
					nextMoves[j][0] = nextMoves[j+1][0];
					nextMoves[j][1] = nextMoves[j+1][1];
					nextMoves[j][2] = nextMoves[j+1][2];
					nextMoves[j+1][0] = temp1;
					nextMoves[j+1][1] = temp2;
					nextMoves[j+1][2] = temp3;
				}
			}
		}
		
		//begin backtracking using the sorted array
		for(int i = 0; i< 4; i++) {
			//use *100 + to convert 2d into integer hash for checking reused squares to avoid infinite loop
			if(useableLocation(nextMoves[i][0], nextMoves[i][1]) && !usedSquares.contains(nextMoves[i][0]*100+nextMoves[i][1])) {
				//only set enemynext on the first step
				enemyNext[0] = nextMoves[i][0];
				enemyNext[1] = nextMoves[i][1];
				//remove above for further steps
				usedSquares.add(nextMoves[i][0]*100+nextMoves[i][1]);
				if(enemyNextSquareBool(nextMoves[i][0], nextMoves[i][1], tRow, tCol)) {
					usedSquares.clear();
					return enemyNext;
				}
			}
			
			
		}
		
		return enemyNext;
		
	}
	boolean enemyNextSquareBool(int cRow, int cCol, int tRow, int tCol) {
		if(cRow == tRow && cCol == tCol) { //hit the target
			return true;
		}
		int[][] nextMoves = new int[4][3]; //4 options to move, col, row, score
		
		//0 is up
		nextMoves[0][0] = cRow - 1;
		nextMoves[0][1] = cCol;
		nextMoves[0][2] = Math.abs(tRow - cRow + 1) + Math.abs(tCol - cCol); //manhattan distance from target is the scoring function
																			//("Best" option has the smallest manhattan distance)
		//1 is down
		nextMoves[1][0] = cRow + 1;
		nextMoves[1][1] = cCol;
		nextMoves[1][2] = Math.abs(tRow - cRow - 1) + Math.abs(tCol - cCol);
		
		//2 is left
		nextMoves[2][0] = cRow;
		nextMoves[2][1] = cCol - 1;
		nextMoves[2][2] = Math.abs(tRow - cRow) + Math.abs(tCol - cCol + 1);
		
		//3 is left
		nextMoves[3][0] = cRow;
		nextMoves[3][1] = cCol + 1;
		nextMoves[3][2] = Math.abs(tRow - cRow) + Math.abs(tCol - cCol - 1);
		
		//sort the moves to find the best choice.
		//bubble sort fine for 4 moves
		for(int i = 3; i > 0; i--) {
			for(int j = 0; j<i;j++) {
				if(nextMoves[j][2] > nextMoves[j+1][2]) {
					int temp1 = nextMoves[j][0];
					int temp2 = nextMoves[j][1];
					int temp3 = nextMoves[j][2];
					nextMoves[j][0] = nextMoves[j+1][0];
					nextMoves[j][1] = nextMoves[j+1][1];
					nextMoves[j][2] = nextMoves[j+1][2];
					nextMoves[j+1][0] = temp1;
					nextMoves[j+1][1] = temp2;
					nextMoves[j+1][2] = temp3;
				}
			}
		}
		
		//begin backtracking using the sorted array
		for(int i = 0; i< 4; i++) {
			//use *100 + to convert 2d into integer hash for checking reused squares to avoid infinite loop
			if(useableLocation(nextMoves[i][0], nextMoves[i][1]) && !usedSquares.contains(nextMoves[i][0]*100+nextMoves[i][1])) {
				usedSquares.add(nextMoves[i][0]*100+nextMoves[i][1]);
				if(enemyNextSquareBool(nextMoves[i][0], nextMoves[i][1], tRow, tCol)) {
					return true;
				}
			}
			
			
		}
		
		return false; //if no pathway found, return false.
		
	}
	void movePlayer(char c) {
		if(c == 'w') {
			playerPosition[0]--;
			if(!useableLocation(playerPosition[0], playerPosition[1])) { //if not useable, undo
				playerPosition[0]++;
			}
		}else if(c == 's') {
			playerPosition[0]++;
			if(!useableLocation(playerPosition[0], playerPosition[1])) {
				playerPosition[0]--;
			}
		}else if(c == 'a') {
			playerPosition[1]--;
			if(!useableLocation(playerPosition[0], playerPosition[1])) {
				playerPosition[1]++;
			}
		}else if(c == 'd') {
			playerPosition[1]++;
			if(!useableLocation(playerPosition[0], playerPosition[1])) {
				playerPosition[1]--;
			}
		}
	}
	void moveEnemies() {
		for(int[] enemy : enemiesPosition) {
			int[] enemysNext = enemyNextSquare(enemy[0], enemy[1], playerPosition[0], playerPosition[1]);
			enemy[0] = enemysNext[0];
			enemy[1] = enemysNext[1];
		}
	}
	boolean eaten() {
		for(int[] enemy : enemiesPosition) {
			if(enemy[0] == playerPosition[0] && enemy[1] == playerPosition[1]) {
				return true;
			}
		}
		return false;
	}
}
