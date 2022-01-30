import java.util.*;
//Create board
public class Board {

    private Player player ;
    private AIPlayer aip;
    private Checker[][] board;
    public int nWhPiece;
    public int nRPiece;
    public int nWKing;
    public int nRKing;

    public Board(){
        setBoard();
        player = new Player(this, true);
         aip = new AIPlayer(this, false);
    }
    private void checkPromote(int row, int col){
        Checker ch = board[row][col];{
            if(ch == Checker.W){
                if(row == 7){
                    nWKing +=1;
                    board[row][col] = Checker.WKing;
                }
            }if(ch == Checker.R){
                if(row == 0){
                    nRKing +=1;
                    board[row][col] = Checker.RKing;
                }
            }
        }
    }
    private void setBoard(){
        nWhPiece = 12; // Three rows of 4 pieces
        nRPiece = 12;
        nWKing = 0;
        nRKing = 0;
        board = new Checker[8][8]; //8x8 board
        for (int i=0; i<board.length; i++){
            int start =0;
            if (i%2 == 0)
                start =1;
            
        Checker pieceType = Checker.Empty;
        if(i<=2)
            pieceType =Checker.W;
        if(i>=5)
            pieceType = Checker.R;
        
            for (int j=start; j<board[i].length; j+=2){
                board[i][j] = pieceType;
            }
        }
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == null)
                    board[i][j] = Checker.Empty;
            }
        }
    }
    public Checker[][] getBoard(){
        return board;
    }
    public void Play(boolean pturn){
        Scanner s = new Scanner(System.in);
        boolean val = false;
        while(!val){
            System.out.print("Select piece you want to move: ");
            int row = s.nextInt();
            int col = s.nextInt();
            System.out.println();
            System.out.print("Select where to move piece (press any letter to deselect piece): ");
            int newrow = s.nextInt();
            int newcol = s.nextInt();
            if(player.isValidMove(this, row, col, newrow, newcol, true)){
                ArrayList<Integer> temp = new ArrayList<Integer>();
                val = true;
                temp.add(row); temp.add(col); temp.add(newrow); temp.add(newcol);
                 player.doAction(this, temp);
                 if(newrow - row == 2){
                    boolean es = true;
                 while (es){
                    ArrayList<ArrayList<Integer>> eat = player.checkEat(this, newrow, newcol, true);
                    if(!eat.isEmpty()){
                        player.doAction(this, eat.get(0));
                        newrow = eat.get(0).get(2);
                        newcol = eat.get(0).get(3);
                    }else{
                        es = false;
                    }
                 }
                 }
                 checkPromote(newrow, newcol);
            }
        }
             ArrayList<Integer> move= aip.getNextMove(this, player.getCheckers().size());
                aip.doAction(this, move);

                int newrow = move.get(2); int newcol = move.get(3);
                if(newrow-move.get(0) == -2){
                boolean es = true;
                 while (es){
                    ArrayList<ArrayList<Integer>> eat = aip.checkEat(this, newrow, newcol, false);
                    if(!eat.isEmpty()){
                        aip.doAction(this, eat.get(0));
                        newrow = eat.get(0).get(2);
                        newcol = eat.get(0).get(3);
                    }else{
                        es = false;
                    }
                    
                 }
                 checkPromote(newrow, newcol);
                }
                 
                player.showCheckers();
                System.out.println();
                aip.showCheckers();
            }

    //Starting board with 12 pawns and no kings
    
    // Simmple GUI
    public void VisualizeBoard(){
        System.out.println(" |-------------------------------|");
        for (int i=7;i>=0;i--){
                System.out.print(i + "|");
            for (int j=0;j<8;j++){
                if(board[i][j] == Checker.Empty)
                    System.out.print("   |");
                if(board[i][j] == Checker.R)
                    System.out.print(" R |");
                if(board[i][j] == Checker.W)
                    System.out.print(" W |");
                    if(board[i][j] == Checker.WKing)
                    System.out.print(" WK|");
                    if(board[i][j] == Checker.RKing)
                    System.out.print(" WK|");
            }
            System.out.println();
             System.out.println(" |-------------------------------|");
           
        }
    }
    public void UpdateBoard(Checker[][] board){
        this.board = board;
    }
    public void removeChecker(int oldrow,int oldcol,int newrow,int newcol,Checker type){
        board[Math.floorDiv(oldrow+newrow, 2)][Math.floorDiv(oldcol+newcol,2)] = Checker.Empty;
        if(type == Checker.W){
            nWhPiece = nWhPiece-1;
            player.removeChecker(Math.floorDiv(oldrow+newrow, 2), Math.floorDiv(oldcol+newcol,2));
        }if(type == Checker.WKing){
            nWhPiece = nWhPiece-1;
            nWKing = nWKing-1;
            player.removeChecker(Math.floorDiv(oldrow+newrow, 2), Math.floorDiv(oldcol+newcol,2));
        }if(type == Checker.R){
            nRPiece = nRPiece-1;
            aip.removeChecker(Math.floorDiv(oldrow+newrow, 2), Math.floorDiv(oldcol+newcol,2));
        }if(type == Checker.RKing){
            nRPiece = nRPiece-1;
            nRKing = nRKing-1;
            aip.removeChecker(Math.floorDiv(oldrow+newrow, 2), Math.floorDiv(oldcol+newcol,2));
        }
    }
    public void restoreChecker(int oldrow,int oldcol,int newrow,int newcol,Checker type){
        board[Math.floorDiv(oldrow+newrow, 2)][Math.floorDiv(oldcol+newcol,2)] = type;
        if(type == Checker.W){
            nWhPiece = nWhPiece+1;
            player.addChecker(Math.floorDiv(oldrow+newrow, 2), Math.floorDiv(oldcol+newcol,2));
        }if(type == Checker.WKing){
            nWKing = nWKing+1;
            player.addChecker(Math.floorDiv(oldrow+newrow, 2), Math.floorDiv(oldcol+newcol,2));
        }if(type == Checker.R){
            nRPiece = nRPiece+1;
            aip.addChecker(Math.floorDiv(oldrow+newrow, 2), Math.floorDiv(oldcol+newcol,2));
        }if(type == Checker.RKing){
            nRKing = nRKing+1;
            aip.addChecker(Math.floorDiv(oldrow+newrow, 2), Math.floorDiv(oldcol+newcol,2));
        }
    }
    public boolean Wcontinue(){
        int[][] dirs ;
        dirs = new int[][] {{1,-1},{1,1},{2,-2},{2,2}};
        ArrayList<ArrayList<Integer>>checkers = player.getCheckers();
        for (int i =0; i<checkers.size();i++){
            int row = checkers.get(i).get(0);
            int col = checkers.get(i).get(1);
            for(int j=0;j<dirs.length;j++){
                player.isValidMove(this, row, col, row+dirs[j][0], col+dirs[j][1], true);
                return true;
            }
        }
        return false;
    }
    public boolean Rcontinue(){
        int[][] dirs = new int[2][2];
        dirs = new int[][] {{-1,-1},{-1,1},{-2,-2},{-2,2}};
        ArrayList<ArrayList<Integer>>checkers = aip.getCheckers();
        for (int i =0; i<checkers.size();i++){
            int row = checkers.get(i).get(0);
            int col = checkers.get(i).get(1);
            for(int j=0;j<dirs.length;j++){
                aip.isValidMove(this, row, col, row+dirs[j][0], col+dirs[j][1], false);
                return true;
            }
        }
        return false;
    }
    public Player getPlayer(){
        return player;
    }
}  