import java.util.*;
public class Main {
    public static void main(String[] args){
        Board board = new Board();
        board.VisualizeBoard();
        Player player = new Player(board, true);
        AIPlayer aip = new AIPlayer(board, false);
        board.setPlayers(player, aip);
        Boolean win = false;
        Boolean playerTur = true;
        Scanner s = new Scanner(System.in);
        while(!win){
            board.VisualizeBoard();
            if(playerTur){
                System.out.print("Select piece you want to move: ");
                int row = s.nextInt();
                int col = s.nextInt();
                System.out.println();
                System.out.print("Select where to move piece (press any letter to deselect piece): ");
                int newrow = s.nextInt();
                int newcol = s.nextInt();
            }
        }
    
        
    }
}
