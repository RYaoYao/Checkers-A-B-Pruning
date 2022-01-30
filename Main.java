import java.lang.reflect.Array;
import java.util.*;
public class Main {
    public static void main(String[] args){
        Board board = new Board();
        board.VisualizeBoard();
        Boolean win = false;
        Boolean playerTur = true;
        while(!win){
            board.VisualizeBoard();
            board.Play(true);
            }
        }
}
