import java.util.Scanner;

public class PlayGame {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("You are the Hero, O");
		System.out.println("Evade the enemies, X, as long as possible!");
		System.out.println("Movement is controlled by wasd.");
		System.out.println("Type w to move left, a to move left, s to move down, d to move right, or nothing to stay still, then press enter.");
		
		
		
		Map game = new Map();
		game.print();
		
		boolean notLost = true;
		
		while(notLost) {
			Scanner stdin = new Scanner(System.in);
			String input = stdin.next();
			game.movePlayer(input.charAt(0));
			game.moveEnemies();
			game.print();
			System.out.println();
			if(game.eaten()) {
				System.out.println("Game Over. You have been eaten!");
				notLost = false;
			}
		}
		

	}
}
