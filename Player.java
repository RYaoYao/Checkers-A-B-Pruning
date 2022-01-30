import java.lang.reflect.Array;
import java.util.*;

public class Player {
    public ArrayList<ArrayList<Integer>> checkers = new ArrayList<ArrayList<Integer>>();
    public Player(Board board, boolean isHuman){
            Checker[][] cboard = board.getBoard();

            for(int i=0;i< cboard.length;i++){
                for(int j=0;j< cboard[i].length;j++){
                    if(isHuman){
                        if(cboard[i][j] == Checker.W || cboard[i][j] == Checker.WKing){
                            ArrayList<Integer> temp = new ArrayList<Integer>();
                            temp.add(i); temp.add(j);
                            checkers.add(temp);
                        }
                    }else{
                        if(cboard[i][j] == Checker.R || cboard[i][j] == Checker.RKing){
                            ArrayList<Integer> temp = new ArrayList<Integer>();
                            temp.add(i); temp.add(j);
                            checkers.add(temp);
                        }
                    }
                }
            }
        }
    public int terminal_test(){
        return 0;
    }
    public ArrayList<ArrayList<Integer>> getCheckers(){
        return checkers; 
    }
    public  ArrayList<ArrayList<Integer>> GenMoves(boolean isHuman,Board board){
        int[][] regmovdir;
        int[][] eatmovdir;
        if (isHuman){
        regmovdir = new int[][]{{1,-1}, {1,1}};
        eatmovdir = new int[][]{{2,-2}, {2,2}};
        }
        else{
            regmovdir = new int[][]{{-1,-1}, {-1,1}};
            eatmovdir = new int[][]{{-2,-2}, {-2,2}};
    }
   ArrayList<ArrayList<Integer>> regMoves = new ArrayList<ArrayList<Integer>>();
   ArrayList<ArrayList<Integer>> eatMoves = new ArrayList<ArrayList<Integer>>();
   ArrayList<ArrayList<Integer>> c ;
   
   if(isHuman)
    c = board.getPlayer().getCheckers();
    else
    c = checkers;
   for (int i=0;i<c.size();i++){
       int oldrow = c.get(i).get(0);
        int oldcol = c.get(i).get(1);
        
            if(board.getBoard()[oldrow][oldcol] == Checker.WKing || board.getBoard()[oldrow][oldcol] == Checker.RKing) {
                    regmovdir = new int[][] {{1,-1}, {1,1},{-1,-1},{-1,1}};
                    eatmovdir =  new int[][] {{2,-1}, {2,1},{-2,-1},{-2,1}};
            }
                for(int j=0;j<regmovdir.length;j++){
                if (isValidMove(board, oldrow, oldcol, oldrow+regmovdir[j][0], oldcol+regmovdir[j][1], isHuman)){
                    ArrayList<Integer> temp = new ArrayList<>();
                    temp.add(oldrow);
                    temp.add(oldcol);
                    temp.add(oldrow+regmovdir[j][0]);
                    temp.add(oldcol+regmovdir[j][1]);
                    regMoves.add(temp);
                }
            }
            for(int j=0;j<eatmovdir.length;j++){
                if (isValidMove(board, oldrow, oldcol, oldrow+eatmovdir[j][0], oldcol+eatmovdir[j][1], isHuman)){
                    ArrayList<Integer> temp = new ArrayList<>();
                    temp.add(oldrow);
                    temp.add(oldcol);
                    temp.add(oldrow+eatmovdir[j][0]);
                    temp.add(oldcol+eatmovdir[j][1]);
                    eatMoves.add(temp);
                }
            }
            }
    
    if(!eatMoves.isEmpty()){
        return eatMoves;
    }
    return regMoves;
   }
   public boolean isValidMove(Board board,int row,int col, int newrow,int newcol, boolean isHuman){
       if (row <0 || row > 7 || col <0 || row >7 || newrow <0 || newrow >7 || newcol< 0|| newcol>7){
        return false;
       }if(board.getBoard()[row][col] == Checker.Empty){
           return false;
       }
        if( board.getBoard()[newrow][newcol] != Checker.Empty ){
            return false;
        }
        if(isHuman){
            if(board.getBoard()[row][col] == Checker.WKing){
                if(Math.abs(newrow -row) == 1){
                    return Math.abs(newcol-col) == 1;
                }else if(Math.abs(newrow-row) == 2){
                    return ((Math.abs(newcol - col) == 2 && (board.getBoard()[newrow-1][newcol+1] == Checker.R)) || (Math.abs(newcol - col) == 2 && board.getBoard()[newrow-1][newcol-1] == Checker.R)
                    || (Math.abs(newcol - col) == 2 && (board.getBoard()[newrow+1][newcol+1] == Checker.R)) || (Math.abs(newcol - col) == 2 && board.getBoard()[newrow+1][newcol-1] == Checker.R)
                    || (Math.abs(newcol - col) == 2 && (board.getBoard()[newrow-1][newcol+1] == Checker.RKing))|| (Math.abs(newcol - col) == 2 && (board.getBoard()[newrow-1][newcol+1] == Checker.RKing)) ||
                    (Math.abs(newcol - col) == 2 && (board.getBoard()[newrow+1][newcol+1] == Checker.RKing))|| (Math.abs(newcol - col) == 2 && (board.getBoard()[newrow+1][newcol+1] == Checker.RKing)) );
                }
                else
                return false;
            }
         else if(board.getBoard()[row][col] == Checker.W){
            if(newrow - row == 1){
                return Math.abs(newcol - col ) == 1;
            }else if(newrow-row == 2){
                return ((newcol - col == -2 && (board.getBoard()[newrow-1][newcol+1] == Checker.R)) || (newcol - col == 2 && board.getBoard()[newrow-1][newcol-1] == Checker.R));
            }
            else
            return false;
         } 
     
        }else{
            if(board.getBoard()[row][col] == Checker.RKing){
                if(Math.abs(newrow -row) == 1){
                    return Math.abs(newcol-col) == 1;
                }else if(Math.abs(newrow-row) == 2){
                    return ((Math.abs(newcol - col) == 2 && (board.getBoard()[newrow-1][newcol+1] == Checker.W)) || (Math.abs(newcol - col) == 2 && board.getBoard()[newrow-1][newcol-1] == Checker.W)
                    || (Math.abs(newcol - col) == 2 && (board.getBoard()[newrow+1][newcol+1] == Checker.W)) || (Math.abs(newcol - col) == 2 && board.getBoard()[newrow+1][newcol-1] == Checker.W)
                    || (Math.abs(newcol - col) == 2 && (board.getBoard()[newrow-1][newcol+1] == Checker.WKing))|| (Math.abs(newcol - col) == 2 && (board.getBoard()[newrow-1][newcol+1] == Checker.WKing)) ||
                    (Math.abs(newcol - col) == 2 && (board.getBoard()[newrow+1][newcol+1] == Checker.WKing))|| (Math.abs(newcol - col) == 2 && (board.getBoard()[newrow+1][newcol+1] == Checker.WKing)) );
                }
                else
                return false;
            }else if(board.getBoard()[row][col] == Checker.R){
            if(newrow - row == -1){
                return Math.abs(newcol - col ) == 1;
            }else if(newrow-row == -2){
                return (newcol - col == -2 && board.getBoard()[newrow+1][newcol+1] == Checker.W) || (newcol - col == 2 && board.getBoard()[newrow+1][newcol-1] == Checker.W);
            }
            else
            return false;
        }
        }
        return true;
}
public Checker doAction(Board board, ArrayList<Integer> move){
    int oldrow = move.get(0);
    int oldcol = move.get(1);
    int newrow = move.get(2);
    int newcol = move.get(3);
    Checker[][] cboard = board.getBoard();
    Checker captured = Checker.Empty;
    Checker toMove = cboard[oldrow][oldcol];
    cboard[newrow][newcol] = toMove;
    cboard[oldrow][oldcol] = Checker.Empty;
    board.UpdateBoard(cboard);
    for (int i=0;i<checkers.size();i++){
        if(checkers.get(i).get(0) == oldrow && checkers.get(i).get(1) == oldcol){
            checkers.get(i).set(0, newrow); checkers.get(i).set(1, newcol);
        }
    }
    if (Math.abs(oldrow-newrow) == 2){
        captured = cboard[Math.floorDiv(oldrow+newrow, 2)][Math.floorDiv(oldcol+newcol,2)];
            board.removeChecker(oldrow, oldcol, newrow, newcol, captured);
    }
    
    return captured;
}
public void showCheckers(){
    for (int i =0;i<checkers.size();i++){
        System.out.println("" + checkers.get(i).get(0) + " " + checkers.get(i).get(1));
    }
}
public Checker resetAction(Board board, ArrayList<Integer> move,Checker captured){
    int oldrow = move.get(0);
    int oldcol = move.get(1);
    int newrow = move.get(2);
    int newcol = move.get(3);
    Checker[][] cboard = board.getBoard();
    Checker toMove = cboard[newrow][newcol];
    cboard[newrow][newcol] = Checker.Empty;
    cboard[oldrow][oldcol] = toMove;
    board.UpdateBoard(cboard);
    for (int i=0;i<checkers.size();i++){
        if(checkers.get(i).get(0) == newrow && checkers.get(i).get(1) == newcol){
            checkers.get(i).set(0, oldrow); checkers.get(i).set(1, oldcol);
        }
    }
    board.UpdateBoard(cboard);
    if (Math.abs(oldrow-newrow) == 2){
            board.restoreChecker(oldrow, oldcol, newrow, newcol, captured);
    
    }
    board.UpdateBoard(cboard);
    return captured;
}
public void removeChecker(int row,int col){
    for (int i=0;i<checkers.size();i++){
        if(checkers.get(i).get(0) == row && checkers.get(i).get(1) == col){
            checkers.remove(i);
        }
    }
}
public ArrayList<ArrayList<Integer>> checkEat(Board board,int oldrow, int oldcol, boolean isHuman){
    int[][] eatmovdir=new int[2][2];
    if (isHuman){
        eatmovdir = new int[][]{{2,-2}, {2,2}};
    }
    else{eatmovdir = new int[][]{{-2,-2}, {-2,2}};}
    ArrayList<ArrayList<Integer>> eatMoves = new ArrayList<ArrayList<Integer>>();
    for(int j=0;j<eatmovdir.length;j++){
    if (isValidMove(board, oldrow, oldcol, oldrow+eatmovdir[j][0], oldcol+eatmovdir[j][1], isHuman)){
        ArrayList<Integer> temp = new ArrayList<>();
        temp.add(oldrow);
        temp.add(oldcol);
        temp.add(oldrow+eatmovdir[j][0]);
        temp.add(oldcol+eatmovdir[j][1]);
        eatMoves.add(temp);
    }
}
    return eatMoves;
}

public void addChecker(int row,int col){
    ArrayList<Integer> temp = new ArrayList<Integer>();
    temp.add(row);
    temp.add(col);
    checkers.add(temp);
}
}