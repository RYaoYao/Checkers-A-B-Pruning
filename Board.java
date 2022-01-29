import java.util.*;
//Create board
public class Board {

    private Player player, aip;
    private Checker[][] board;
    public int nWhPiece;
    public int nRPiece;
    public int nWKing;
    public int nRKing;

    public Board(){
        setBoard();
    }
    public void setPlayers(Player player,Player AIPlayer){
        this.player = player; this.aip = AIPlayer;
    }
    public Checker[][] getBoard(){
        return board;
    }
    //Starting board with 12 pawns and no kings
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
            player.removeChecker(newrow, newcol);
        }if(type == Checker.WKing){
            nWKing = nWKing-1;
            player.removeChecker(newrow, newcol);
        }if(type == Checker.R){
            nRPiece = nRPiece-1;
            aip.removeChecker(newrow, newcol);
        }if(type == Checker.RKing){
            nRKing = nRKing-1;
            aip.removeChecker(newrow, newcol);
        }
    }
    public void restoreChecker(int oldrow,int oldcol,int newrow,int newcol,Checker type){
        if(type == Checker.W){
            nWhPiece = nWhPiece+1;
            player.addChecker(oldrow, oldcol);
        }if(type == Checker.WKing){
            nWKing = nWKing+1;
            player.addChecker(oldrow, oldcol);
        }if(type == Checker.R){
            nRPiece = nRPiece+1;
            aip.addChecker(oldrow, oldcol);
        }if(type == Checker.RKing){
            nRKing = nRKing+1;
            aip.addChecker(oldrow, oldcol);
        }
        board[Math.floorDiv(oldrow+newrow, 2)][Math.floorDiv(oldcol+newcol,2)] = type;
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
}  