import java.util.ArrayList;


public class AIPlayer extends Player{
    int curdep = 0;
    private int maxdepth;
    private int numnodes;
    private double beta;
    private double alpha;
    private int depthlimit;
    private ArrayList<ArrayList<Integer>> bestmove;
    public AIPlayer(Board board, boolean isHuman){
        super(board, isHuman);
    }

   
    public ArrayList<Integer> getNextMove(Board board,int oppcheckers){
         this.depthlimit = 26 -(checkers.size() + oppcheckers);
         if(depthlimit<4)
         depthlimit = 4;
        ArrayList<ArrayList<Integer>> move = alphaBetaSearch(board);

        return move.get(0);
    
    }

    public boolean terminalTest(Board board){
        if (board.nWhPiece == 0 || board.nRPiece == 0)
        return true;
        else return false;
    }
    //From the book need all possible moves
    public ArrayList<ArrayList<Integer>> alphaBetaSearch(Board board){
         curdep = 0;
        maxdepth = 0;
        this.numnodes = 0;
        
         bestmove = new ArrayList<ArrayList<Integer>>();
        int deplimit = this.depthlimit;
        alpha =Double.NEGATIVE_INFINITY;
        beta =Double.POSITIVE_INFINITY;
        double v = max_value(board,alpha,beta,deplimit);
        
        return bestmove;
    }
    public double max_value(Board board,double alpha, double beta, int deplimit){
        
        if (terminalTest(board) || deplimit == 0){
        return utility( board);
        }
    double v=Double.NEGATIVE_INFINITY;
        curdep = curdep+1;
        maxdepth = Math.max(maxdepth, curdep);
        this.numnodes +=1;
        
        ArrayList<ArrayList<Integer>> moves = GenMoves(false, board);
        /*for (int k=0;k<moves.size();k++){
            System.out.println(moves.get(k).get(0) + " " + moves.get(k).get(1) + " " + moves.get(k).get(2)  + " " + moves.get(k).get(3));
        }*/
        for(int i =0;i<moves.size();i++){
            double next = 0;
            Checker captured = doAction(board, moves.get(i));
                next = min_value(board,alpha,beta,deplimit-1);
            if (next> v){
                v = next;
                if (deplimit == this.depthlimit){
                    System.out.println("Here");
                    bestmove.clear();
                    bestmove.add(moves.get(i));
                }
            }
            resetAction(board,moves.get(i), captured);
            if (v >= beta){
                curdep -= 1;
                return v;
            }
            alpha = Math.max(alpha, v);
        }
        curdep -=1;
        return v;
    }
    public double min_value(Board board,double alpha, double beta, int deplimit){
      
        if (terminalTest(board) || deplimit == 0)
        return utility( board);
        double v=Double.POSITIVE_INFINITY;
        curdep = curdep+1;
        maxdepth = Math.max(maxdepth, curdep);
        
       
        this.numnodes +=1;
        ArrayList<ArrayList<Integer>> moves = GenMoves(true, board);
        /*for (int k=0;k<moves.size();k++){
            System.out.println(moves.get(k).get(0) + " " + moves.get(k).get(1) + " " + moves.get(k).get(2)  + " " + moves.get(k).get(3));
        }*/
        for(int k =0;k<moves.size();k++){
            double next = 0;
            Checker captured = doAction(board, moves.get(k));
                next = max_value(board,alpha,beta,deplimit-1);
            if (next< v){
                v = next;
            }
            resetAction(board,moves.get(k), captured);
            if (v <= alpha){
                curdep -= 1;
                return v;
            }
            beta = Math.min(beta, v);
        }
        curdep -=1;
        return v;
    }
 //Utility & Evaluation function from: https://github.com/billjeffries/jsCheckersAI/blob/ad96fb0bdb410a300335b5803f82633a6ad08db3/scripts/checkers-engine.js#L842
   private int evaluate_postion(int x,int y){
    if (x == 0 || x == 7 || y == 0 || y == 7){
        return 5;
    }
    else {
        return 3;
    }
   }
    public double utility(Board board){
        double cpie = 0, ck = 0, hpie=0, hk = 0, cpossum=0, hpossum=0;
        Checker[][] cboard = board.getBoard();
        for(int i =0; i<8;i++){
            for(int j=0;j<8;j++){
                Checker piece = cboard[i][j];
                if(piece == Checker.W || piece == Checker.WKing){
                    hpie +=1;
                    if(piece == Checker.WKing)
                        hk +=1;
                double pos = evaluate_postion(i, j);
                hpossum +=pos;
                }else if(piece == Checker.R || piece == Checker.RKing){
                    cpie +=1;
                    if(piece == Checker.RKing)
                        ck +=1;
                double pos = evaluate_postion(i, j);
                cpossum +=pos;
                }
            }
        }
        double pdiff = cpie - hpie;
        double kdiff = ck - hk;
        if (hpie == 0)
            hpie = 0.00001;
        double avghpos = hpossum / hpie;
        if (cpie == 0)
            cpie = 0.00001;
        double avgcpos = cpossum / cpie;

        double avgposdiff = avghpos - avgcpos;

        double[] features = {pdiff, kdiff, avgposdiff};
        double[] weights = {100, 10 ,1};
        double util = 0;
        for(int k = 0;k<features.length;k++){
            double fw = features[k] * weights[k];
            util += fw;
        }
        return util;
    }

}
