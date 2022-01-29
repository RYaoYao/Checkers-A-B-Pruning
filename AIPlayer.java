import java.util.ArrayList;


public class AIPlayer extends Player{
    int curdep = 0;
    private int maxdepth;
    private int numnodes;
    private int maxprune;
    private int minprune;
    private int depthlimit;
    private ArrayList<Integer> bestmove = new ArrayList<Integer>();
    public AIPlayer(Board board, boolean isHuman){
        super(board, isHuman);
    }

   
    public int[][] getNextMove(Board board,int oppcheckers){
         depthlimit = 26 -(checkers.size() + oppcheckers);
        int[][] move = alphaBetaSearch(board);
        return move;
    }

    public boolean terminalTest(Board board){
        if (board.nWhPiece == 0 || board.nRPiece == 0)
        return true;
        else return false;
    }
    public int[][] alphaBetaSearch(Board board){
        int[][] move = new int[2][2];
         curdep = 0;
        maxdepth = 0;
        numnodes = 0;
        maxprune = 0;
        minprune = 0;

        ArrayList<Integer> bestmove = new ArrayList<Integer>();
        int deplimit = this.depthlimit;

        double v = max_value(board,-1000,1000,deplimit);
        return move;
    }
    public double max_value(Board board,double alpha, double beta, int deplimit){
        
        double v=Double.NEGATIVE_INFINITY;
        if (terminalTest(board))
        return utility( board);
        if (deplimit ==0)
        return heuristic();

        curdep = curdep+1;
        maxdepth = Math.max(maxdepth, curdep);
        numnodes +=1;
        ArrayList<ArrayList<Integer>> moves = GenMoves(false, board);
        for(int i =0;i<moves.size();i++){
            double next = 0;
            Checker captured = doAction(board, moves.get(i));
            if (board.Wcontinue()){
                next = min_value(board,alpha,beta,deplimit-1);
            }else{
                 next = max_value(board, alpha, beta, deplimit-1);
            }
            if (next> v){
                v = next;
                if (deplimit == this.depthlimit){
                    bestmove = moves.get(i);
                }

            }
            resetAction(board,moves.get(i), captured);
            if (v >= beta){
                maxprune += 1;
                curdep -= 1;
                return v;
            }
            alpha = Math.max(alpha, v);
        }
        curdep -=1;
        return v;
    }
    public double min_value(Board board,double alpha, double beta, int deplimit){
        double v=Double.POSITIVE_INFINITY;
        if (terminalTest(board))
        return utility( board);
        if (deplimit ==0)
        return heuristic();

        curdep = curdep+1;
        maxdepth = Math.max(maxdepth, curdep);
        numnodes +=1;
        ArrayList<ArrayList<Integer>> moves = GenMoves(true, board);
        for(int i =0;i<moves.size();i++){
            double next = 0;
            Checker captured = doAction(board, moves.get(i));
            if (board.Rcontinue()){
                next = max_value(board,alpha,beta,deplimit-1);
            }else{
                 next = min_value(board, alpha, beta, deplimit-1);
            }
            if (next< v){
                v = next;
            }
            resetAction(board,moves.get(i), captured);
            if (v <= beta){
                maxprune += 1;
                curdep -= 1;
                return v;
            }
            alpha = Math.min(beta, v);
        }
        curdep -=1;
        return v;
    }

    
    public int utility(Board board){
        int util=0;
        int aipiece = 0;
        int aiking = 0;
        int ppiece = 0;
        int pking = 0;
        int alpha=0;
        int sum1=0;
        int sum2=0;
        return util;
    }
    public int heuristic(){
        int heu = 0;
        return heu;
    }
}
