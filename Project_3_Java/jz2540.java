
// ---------AI project 3
// Jinxi Zhao
// UNI: jz2540
//--------------------


import java.util.Random;
import java.util.Scanner;

public class jz2540 {
	static int N;		//board size
	static int M;		//winning chain
	static int S;		//decision time limits in seconds
	static int MODE;	//game mode: 1.Battle; 2.AI vs. Random AI; 3.AI vs. Itself
	
	static int leftEdge;	//these are edges, my agent will only generate moves that within the rectangle that formed by these four edges
	static int rightEdge;
	static int upEdge;
	static int downEdge;
	
	static int over = 0;	//0 is default, 1 is someone wins, 2 is draw
	static int SIDE = 0;	//0 is black, 1 is white
	static int cur_turn = 0;	//same as above
	static char[][] board;		//the board that we play
	
	static int best_x = -1 , best_y = -1;	//the best moves so far, calculated by Minimax
	static long startTime;	//record system time
	
	
	public static void main(String[] args) {
		
		// input parameters
		Scanner scan = new Scanner(System.in);
		System.out.print("Please input board size N: ");
		N = scan.nextInt();
		System.out.print("Please input winning chain length M: ");
		M = scan.nextInt();
		System.out.print("Please input decision time limits in seconds: ");
		S = scan.nextInt();
		System.out.print("Please choose a mode:\n"
						+"1.Battle\n"
						+"2.AI vs. Random AI\n"
						+"3.AI vs. Itself\n");
		MODE = scan.nextInt();
		newBoard();
		
		// battle mode
		if (MODE == 1){
			System.out.println("Agent plays: \n0.black(x)\n1.white(o)");
			SIDE = scan.nextInt();
			
			int x_coor = 0, y_coor = 0;
			
			//agent goes first (black)
			if (SIDE == 0){
				
				//just put one in the middle for the sake of goodness
				board[N/2][N/2] = 'x';			
				
				//initialize edges
				upEdge = N/2 - 1;
				downEdge = N/2 + 1;
				leftEdge = N/2 - 1;
				rightEdge = N/2 + 1;
				
				System.out.println("Agent's Move: [" + N/2 + " " + N/2 + "]");
				printBoard();
				cur_turn = 1;	//white's turn
			}
			
			//agent goes second (white)
			else if (SIDE == 1){
				System.out.print("Please input your move: ");			
				x_coor = scan.nextInt();
				y_coor = scan.nextInt();
				
				board[x_coor][y_coor] = 'x';
				
				//initialize edges
				upEdge = x_coor - 1 > 0 ? x_coor - 1 : 0;
				downEdge = x_coor + 1 < N ? x_coor + 1: N;
				leftEdge = y_coor - 1 > 0 ? x_coor - 1: 0;
				rightEdge = y_coor + 1 < N ? x_coor + 1: N;				
				
				printBoard();
				
				cur_turn = 1;
				play(cur_turn);
			}
			else{
				scan.close();
				return;
			}
			
			//take turns to play, over == 0 means the game is not over
			while( over == 0 ){
				System.out.println("Please input your move: ");
				x_coor = scan.nextInt();
				y_coor = scan.nextInt();
				
				if (board[x_coor][y_coor] != '.'){
					System.out.print("I got you! Spot is not open, you lost!!");
					scan.close();
					return;
				}
				
				if (cur_turn == 0)
					board[x_coor][y_coor] = 'x';
				else
					board[x_coor][y_coor] = 'o';
				
				updateEdge(x_coor, y_coor);
				printBoard();
				
				over = draw();		//if draw, set to 2, else 0
				over = win(x_coor, y_coor, cur_turn);	//if someone wins, set to 1, else 0

				if (over != 0)
					break;

								
				
				cur_turn=1-cur_turn;
				
				//play(cur_turn);				
				play(cur_turn);
			}
			
			
			if (over == 1)		//if someone wins
				if (cur_turn == 0)
					System.out.print("Black wins!");
				else
					System.out.print("White wins!");
			if (over == 2)		//if draw
				System.out.print("Board is full. Draw!");
			
		}
		
		
		
		// AI vs Random
		if (MODE == 2){
			System.out.println("Your smart Agent plays: \n0.black(x)\n1.white(o)");
			SIDE = scan.nextInt();
			
			int x_coor = 0, y_coor = 0;
			
			//smart agent goes first (black)
			if (SIDE == 0){
				
				board[N/2][N/2] = 'x';	
				
				//initialize edges
				upEdge = N/2 - 1;
				downEdge = N/2 + 1;
				leftEdge = N/2 - 1;
				rightEdge = N/2 + 1;
				
				System.out.println("Agent's Move: [" + N/2 + " " + N/2 + "]");
				printBoard();
				cur_turn = 1;

			}
			//smart agent goes second (white)
			if (SIDE == 1){
				// generate random coordinates until if it is empty on board
				Random rand = new Random();
				do{
					x_coor = rand.nextInt(N-1);
					y_coor = rand.nextInt(N-1);
				}while(board[x_coor][y_coor] != '.');
					
				board[x_coor][y_coor] = 'x';

				//initialize edges
				upEdge = x_coor - 1 > 0 ? x_coor : 0;
				downEdge = x_coor + 1 < N ? x_coor : N;
				leftEdge = y_coor - 1 > 0 ? x_coor : 0;
				rightEdge = y_coor + 1 < N ? x_coor : N;
				
				System.out.println("Random Move: [" + x_coor + " " + y_coor + "]");		
				printBoard();
				
				cur_turn = 1;
				play(cur_turn);
			}
			//take turns to play
			while( over == 0 ){
				// generate random coordinates until if it is empty on board
				Random rand = new Random();
				do{
					x_coor = rand.nextInt(N-1);
					y_coor = rand.nextInt(N-1);
				}while(board[x_coor][y_coor] != '.');
				
				System.out.println("Random Move: [" + x_coor + " " + y_coor + "]");
						
				if (cur_turn == 0)
					board[x_coor][y_coor] = 'x';
				else
					board[x_coor][y_coor] = 'o';
				
				updateEdge(x_coor, y_coor);
				printBoard();
				
				over = draw();		//if draw, set to 2, else 0
				over = win(x_coor, y_coor, cur_turn);	//if someone wins, set to 1, else 0
	
				//System.out.print("["+upEdge+" "+downEdge+" "+leftEdge+" "+rightEdge+"]");
				
				if (over != 0)
					break;
				
				//press enter to continue
				//System.out.print("Press enter to continue...");
				//scan.nextLine();				
				
				cur_turn=1-cur_turn;
				
				play(cur_turn);				
				
			}
			
			
			if (over == 1)
				if (cur_turn == 0)
					System.out.print("Black wins!");
				else
					System.out.print("White wins!");
			if (over == 2)
				System.out.print("Board is full. Draw!");
			scan.close();
		}
		
		
		// AI vs itself
		if (MODE == 3){
			//first move
			board[N/2][N/2] = 'x';
			
			//initialize edges			
			upEdge = (N/2 - 1);
			downEdge = N/2 + 1;
			leftEdge = N/2 - 1;
			rightEdge = N/2 + 1;
						
			System.out.println("Agent1's Move: [" + N/2 + " " + N/2 + "]");
			printBoard();
					
			cur_turn = 1;	//now is opponent's turn
			
			while (over == 0){
				//System.out.print("Press enter to continue...");
				//scan.nextLine();
				play(cur_turn);
			}
			
			if (over == 1)
				if (cur_turn == 0)
					System.out.print("Black wins!");
				else
					System.out.print("White wins!");
			if (over == 2)
				System.out.print("Board is full. Draw!");
			
		}
		
		scan.close();
	}
	
	
	public static void play(int side){
		//record the start time
		startTime = System.currentTimeMillis();
		
		char[][] BOARD = new char[N][N];	
		System.arraycopy(board, 0, BOARD, 0, board.length);		//keep a copy of the board		
		
				int[][] moves = getMoves();		//get moves for the first round
				int x, y;
				
				for (int depth = 3; depth < 10; depth++){
					
					x = -1;		
					y = -1;
					
					int max = -1000000000;
					//loop for every possible move
					for (int i = 0; i < moves.length; i++){
						int m = moves[i][0];
						int n = moves[i][1];
						
						// if this location can win, break
						if (getType(m, n, side) == 1){
							x = m;
							y = n;
							break;
						}
						
						//if the opponent can win by putting a stone at this spot, break
						if (getType(m, n, 1 - side) == 1){
							x = m;
							y = n;
							break;
						}
						
						// store current edges
						int up = upEdge;
						int down = downEdge;
						int left = leftEdge;
						int right = rightEdge;
						
						// put down a move
						if (side == 0)
							board[m][n] = 'x';
						else
							board[m][n] = 'o';
						
						// update edges
						updateEdge(m, n);
						
						//get the min value
						int v = getMin(-1000000000, 1000000000, depth);
						
						// restore board and edges
						board[m][n] = '.';
						upEdge = up;
						downEdge = down;
						leftEdge = left;
						rightEdge = right;
						
						// if v is larger than current max, update it, and store the coordinate
						if (v > max){
							max = v;
							x = m;
							y = n;
						}
					}
					
					// if timeout, break the loop, so that it drops anything it gets in this depth
					if (System.currentTimeMillis() - startTime >= S * 1000){
						break;
					}
					
					//	if time is not up, assign the best values it got to best_x and best_y
					best_x = x;
					best_y = y;
					
				}				
				
				
		System.arraycopy(BOARD, 0, board, 0, BOARD.length);		//restore the board

		
		if (best_x == -1 || best_y == -1){
			System.out.print("Cannot make a move![-1 -1]");
			cur_turn = 1- cur_turn;
			return;
		}
		
		if (side == 0)
			board[best_x][best_y] = 'x';
		else
			board[best_x][best_y] = 'o';
		
		// prints out moves for each agent with MODE 3
		if (MODE == 3)
			if (cur_turn == 0)
				System.out.print("Agent1's Move: [" + best_x + " " + best_y +"]\n");
			else
				System.out.print("Agent2's Move: [" + best_x + " " + best_y +"]\n");
		else
			System.out.print("Agent's Move: [" + best_x + " " + best_y + "]\n");
		
		
		updateEdge(best_x, best_y);
		printBoard();
		
		over = draw();
		over = win(best_x, best_y, side);
		if (over != 0)
			return;
		
		//change the turn
		cur_turn = 1 - cur_turn;
		}
	
	
	  //search a max value for the current state
	public static int getMax(int alpha, int beta, int depth){
			int max = alpha;
			// terminate condition, 1)bubble all the way up to root; 2)time is up
			if ( depth == 0)			
				return evaluate(1-cur_turn);
		
			
			int[][] moves = getMoves();	//get children
			
			for (int i = 0; i < moves.length; i++){
				int x = moves[i][0];
				int y = moves[i][1];
				
				if (getType(x, y, cur_turn) == 1)		//AI can win
					return getScore(1)*100 + depth*1000;	//give more weights on shallow depth
				// store current edges
				int up = upEdge;
				int down = downEdge;
				int left = leftEdge;
				int right = rightEdge;
				
				// change the board
				if (SIDE == 0)
					board[x][y] = 'x';
				else
					board[x][y] = 'o';
				// update edges assuming AI putting at this spot
				updateEdge(x, y);
				
				int v = getMin(max, beta, depth-1);
				//System.out.print("MinScore("+v+")");
				
				// restore board and edges
				board[x][y] = '.';
				upEdge = up;
				downEdge = down;
				leftEdge = left;
				rightEdge = right;
				
				if (v > max)
					max = v;
				
				// pruning
				if (max > beta)
					return max;
				
				//if timeout, return right away
				if (System.currentTimeMillis() - startTime >= S * 1000){
					break;
				}
			}
			return max;
		}


	
	  //search a min value for the current state
	public static int getMin(int alpha, int beta, int depth){
			int min = beta;
			
			// terminate condition, 1)bubble all the way up to root; 2)time is up
			if ( depth == 0)			
				return evaluate(cur_turn);
		
			int[][] moves = getMoves();	//get search range
			
			for (int i = 0; i < moves.length; i++){
				int x = moves[i][0];
				int y = moves[i][1];
				
				if (getType(x, y, 1 - cur_turn) == 1)		//opponent can win
					return -getScore(1)*100 + depth*1000;
				
				// store current edges
				int up = upEdge;
				int down = downEdge;
				int left = leftEdge;
				int right = rightEdge;
				
				// change the board
				if (SIDE == 0)
					board[x][y] = 'x';
				else
					board[x][y] = 'o';
				// update edges assuming AI putting at this spot
				updateEdge(x, y);
				
				int v = getMax(alpha, min, depth-1);
				//System.out.print("MaxScore("+v+")");
				
				// restore board and edges
				board[x][y] = '.';
				upEdge = up;
				downEdge = down;
				leftEdge = left;
				rightEdge = right;
				
				if (v < min)
					min = v;
				
				// pruning
				if (min <= alpha)
					return min;
				
				// if timeout, return right away
				if (System.currentTimeMillis() - startTime >= S * 1000){
					break;
				}
			}
			return min;
		}


	// evaluation function
	// basically, it searches all empty spots on the board, and try it with black or white stones to get a score
	// and add them all separately, so that we can get a general view of who is in advantageous side
	public static int evaluate(int side){
		
		int score_0 = 0;	//AI score
		int score_1 = 0;	//opponent score
		
		for (int i = upEdge; i < downEdge; i++)
			for (int j = leftEdge; j < rightEdge; j++)
				if (board[i][j] == '.'){			//search every open spot
					int type0 = getType(i, j, side);		//plug in AI's stone
					int type1 = getType(i, j, 1 - side);	//plug in opponent's stone
					
					//the first three types are important, which can lead to a winning, so I give more weights
					if (type0 == 1)			
						return getScore(type0)*300;
					if (type0 == 2)
						return getScore(type0)*40;
					if (type0 == 3)
						return getScore(type0)*10;
					
					if (type1 == 1)
						return -getScore(type1)*300;
					if (type0 == 2)
						return -getScore(type1)*40;
					if (type0 == 3)
						return -getScore(type1)*10;
					
					// add up scores
					score_0 += getScore(type0);			
					score_1 -= getScore(type1);
				}
		
		// if current turn is AI's turn, return a positive value, otherwise negative
		if (cur_turn == side)
			return 2*score_0 + score_1;
		else
			return score_0 + 2*score_1;
		
	}


	//Analyze lines in different directions
	public static int[] analysis (int x, int y, int dx, int dy, int side){
		
		char side_sym_0, side_sym_1;
		if (side == 0){
			side_sym_0 = 'x';
			side_sym_1 = 'o';
		}
		else{
			side_sym_0 = 'o';
			side_sym_1 = 'x';
		}

		
		int p_stone = 1;	//number of stones in positive direction
		int n_stone = 1;	//negative direction
		int stones = 0;		//all stones on both direction
		int link = 0;		// continuous stones
		int p_mid = 0;		//location of blank spots in positive direction
		int n_mid = 0;		//negative direction
		int p_free = 0;		//the consecutive stones is free on one side?
		int n_free = 0;
		
		
		int i;			//need to use this after loop
		// analyse lines in different direction, dx, dy are direction vectors such as (0,1) and (0,-1)
		for (i = 1; x + i*dx < N && x + i*dx >= 0 && y + i*dy < N && y + i*dy >= 0; i++){
			if (board[x + i*dx][y + i*dy] == side_sym_0)
				p_stone++;		//if its "my" stone, count add one
			else if (board[x + i*dx][y + i*dy] == '.'){	//if it is empty
				// if there is already a middle empty spot in the chain, and now encounter another one, stop search this direction
				if (p_mid != 0)
					break;
				if ( x + (i+1)*dx >= 0 && x + (i+1)*dx < N && y + (i+1)*dy >= 0 && y + (i+1)*dy < N ){		//check if it is within boundary
					//two continuous empty spots, or empty spot followed by opponent's stone, then stop counting and break
					if (board[x + (i+1)*dx][y + (i+1)*dy] == '.' || board[x + (i+1)*dx][y + (i+1)*dy] == side_sym_1)
						break;
					else{			//else it must be "my" stone followed by an empty spot, then I count it as a chain has middle empty spot, record the location
						p_mid = i;
						continue;
					}
				}
				else		//if out of boundary, break
					break;
			}
			else 	// else it must be opponent's stone, stop searching 
				break;
		}
		
		
		// check it a chain is free on the side, free means an empty spot follow by the last "my" stone
		if (x + i*dx < N && x + i*dx >= 0 && y + i*dy < N && y + i*dy >= 0){
			//if the last spot is empty and there is no middle empty spot, it is free on this direction
			if (board[x + i*dx][y + i*dy] == '.' && p_mid == 0)
				p_free++;
		}
		else		// i points to the last position searched, if the loop is finished, the last position is should be i - 1 instead of i
			if (board[x + (i-1)*dx][y + (i-1)*dy] == '.' && p_mid == 0)
				p_free++;
	
		
		//the other direction
		for (i = 1; x - i*dx < N && x - i*dx >= 0 && y - i*dy < N && y - i*dy >= 0; i++){
			if (board[x - i*dx][y - i*dy] == side_sym_0)
				n_stone++;		//if its "my" stone, count add one
			else if (board[x - i*dx][y - i*dy] == '.'){	//if it is empty
				// if there is already a middle empty spot in the chain, and now encounter another one, stop search this direction
				if (n_mid != 0)
					break;
				if (x - (i+1)*dx >= 0 && x - (i+1)*dx < N && y - (i+1)*dy >= 0 && y - (i+1)*dy < N){		//check if it is within boundary
					//two continuous empty spots, or empty spot followed by opponent's stone, then stop counting and break
					if (board[x - (i+1)*dx][y - (i+1)*dy] == '.' || board[x - (i+1)*dx][y - (i+1)*dy] == side_sym_1)
						break;
					else{			//else it must be "my" stone followed by an empty spot, then I count it as a chain has middle empty spot, record the location
						n_mid = i;
						continue;
					}
				}
				else		//if out of boundary, break
					break;
			}
			else 	// else it must be opponent's stone, stop searching 
				break;
		}
			
		// check it a chain is free on the side, free means an empty spot follow by the last "my" stone
		if (x - i*dx < N && x - i*dx >= 0 && y - i*dy < N && y - i*dy >= 0){
			//if the last spot is empty and there is no middle empty spot, it is free on this direction
			if (board[x - i*dx][y - i*dy] == '.' && n_mid == 0)
				n_free++;
		}
		else		// i points to the last position searched, if the loop is finished, i will plus one, the last position is should be i - 1 instead of i
			if (board[x - (i-1)*dx][y - (i-1)*dy] == '.' && n_mid == 0)
				n_free++;
		

		
		
		//-------Analyze types and return
		stones = p_stone + n_stone - 1;
		link = p_mid + n_mid - 1;
		
		//test
		//System.out.print("("+stones+" "+n_stone+" "+n_stone+" "+link+")");
		

		//if there is no middle empty spot on both direction, return
	    if( p_mid == 0 && n_mid == 0 ) 
	        return new int[] {stones, p_free + n_free};
	    
	    //if both directions have middle empty spot
	    else if (p_mid != 0 && n_mid != 0){
	    		return new int[]{link, 2};

	    }
	    //only one direction has middle empty spot
	    else{	
	    	int left_link = n_stone + p_mid - 1;	//continuous stone from the location of right middle empty spot
	    	int right_link = p_stone + n_mid - 1;	// from left
	    	
	    	if (stones < M)								//ooxx.Xx..		..xX.xx..
	    		if(p_free != 0 || n_free != 0)
	    				return new int[]{stones, 1};
	    	if (p_mid != 0)
	    		if (left_link <= M && n_free != 0) 
	    			return new int[]{left_link, 2};
	    	if (n_mid != 0)
	    		if (right_link <= M && p_free != 0)
	    			return new int[]{right_link, 2};
	    	return new int[]{1, 1};
	    }
	    	

	    }

	

	public static int getType (int x, int y, int side){
	    	
	    int[][] type = new int[4][2];
	    type[0]	= analysis(x, y, 1, 0, side);	//horizontal
	    type[1]	= analysis(x, y, 0, 1, side);	//vertical
	    type[2]	= analysis(x, y, 1, 1, side);	//left diagonal
	    type[3]	= analysis(x, y, -1, 1, side);	//right diagonal

	    
	    //initialize it to 0, to hold different types from analysis
	    int[][] chain = new int[M][2];
	    for (int j = 0; j < chain.length; j++){
			chain[j][0] = 0;
	    	chain[j][1] = 0;
	    }
	    
	    //"sum up" all types, and put them in chain[][]
	    for (int j = 0; j < 4; j++)
	    		for (int k = M; k > 0; k--)
	    			if (type[j][0] == k){
	    				if (type[j][1] == 2)
	    					chain[k-1][1]++;
	    				//if (type[j][1] == 1)
	    				else
	    					chain[k-1][0]++;
	    				break;
	    				}
	    
	    //if win, return right away
	    if(chain[M-1][0] != 0 || chain[M-1][1] != 0)
	    	return 1;
	    if(chain[M-2][1] > 0 || chain[M-2][0] > 1 || (chain[M-2][0] > 0 && chain[M-3][1] > 0))
	    	return 2;
	    
	    
	    //dynamically generates a type, and return
	    //since type one and type two are defined above, generate types from 3 to 4 * (M-3) + 3
	    // here, take M=5 as an example
	    int n = 3;
	    for (int i = chain.length - 3; i > 0; i--){
	    	if (chain[i][1] > 1)		// at least free three-chain
	    		return n;
	    	if (chain[i][0] > 0 && chain[i][1] > 0)		//one dead and one free three-chain
	    		return n + 1;
	    	if (chain[i + 1][0] > 0)	// one dead four-chain
	    		return n + 2;
	    	if (chain[i][1] > 0)		// one free three-chain
	    		return n + 3;
	    	n+=4;
	    }
	    return 4 * (M - 3) + 3;			//	default type, the least important
	}


	//get a score for a specific type
	//also generate score dynamically according to types
	//here I use factorial to calculate a score
	//take M=5 as an example, there are total types of 11,
	//score for type one is 11!, for type 2 is 10!
	//in this way, the more important a type is, the more score it gets
	public static int getScore(int n){
		int numType = 4 * (M - 3) + 3;
		
		for (int i = 1; i <= numType; i++)
			if (n == i)
				return cal(numType - (i-1));
		return 0;
	}


	//use to calculate factorial
	public static int cal(int n){
		int sum=1;
		if (n == 0)
			return 1;
		for (int i=1; i<=n; i++)
			sum = sum * i;
		return sum;
	}


	//-------

	public static int[][] getMoves(){	
		int count = 0;
		for (int i = upEdge; i <= downEdge; i++)
			for (int j = leftEdge; j <= rightEdge; j++)
				if (board[i][j] == '.')
					count++;

		int[][] moves = new int[count][3];
		int n=0;
		
		for (int i = upEdge; i <= downEdge; i++)
			for (int j = leftEdge; j <= rightEdge; j++)
				if (board[i][j] == '.'){	
					int type1 = getType(i, j, cur_turn);
					int type2 = getType(i, j, 1-cur_turn);
					
					moves[n][0] = i;
					moves[n][1] = j;
					moves[n][2] = getScore(type1) + getScore(type2);	//choose moves that generally has significant impact on both side
					n++;
				}
		
		// sort the array
		for (int i = 0; i < moves.length-1; i++) 
			for (int j = i+1; j < moves.length; j++) 
				if (moves[i][2] < moves[j][2]) {
				
					int[] temp=new int[3];
					temp = moves[i];
					moves[i] = moves[j];
					moves[j] = temp;
				}
		
		//select only the first 8 best moves
		int size = 8 > n ? n : 8;
	    int[][] bestMoves = new int[size][3];
	    System.arraycopy(moves, 0, bestMoves, 0, size);
		
		return bestMoves;
	}

	
	public static void newBoard(){
		board = new char[N][N];
		for (int i = 0; i < N; i++){
			for (int j = 0; j < N; j++)
				board[i][j] = '.';
		}
	}
	
	public static void updateEdge(int x, int y){
		
		//update edges for every move
		leftEdge = y - 2 < leftEdge ? y - 2 : leftEdge;
		rightEdge = y + 2 > rightEdge ? y + 2 : rightEdge;
		upEdge = x - 2 < upEdge ? x - 2 : upEdge;
		downEdge = x + 2 >= downEdge ? x + 2 : downEdge;
		
		//in case that updated edges are beyond board size N
		leftEdge = 0 > leftEdge ? 0 : leftEdge;
		rightEdge = N - 1 < rightEdge ? N - 1 : rightEdge;
		upEdge = 0 > upEdge ? 0 : upEdge;
		downEdge = N - 1 < downEdge ? N - 1 : downEdge;
	}


	public static void printBoard(){
		//for (int i=0; i<N;i++)
		//	System.out.print(i);
		for (int i = 0; i < N; i++){
			//System.out.print(i);
			for (int j = 0; j < N; j++)
				System.out.print(board[i][j]);
			System.out.println();
		}
	}
	
	//win the game?
	public static int win(int x, int y, int side){
		//if detects a M-stone chain in any direction, wins
		if (analysis(x, y, 1, 0, side)[0] == M)
			return 1;
		if (analysis(x, y, 0, 1, side)[0] == M)
			return 1;
		if (analysis(x, y, 1, 1, side)[0] == M)
			return 1;
		if (analysis(x, y, 1, -1, side)[0] == M)
			return 1;
		return 0;
	}
	// draw?
	public static int draw(){
		for (int i = 0; i < N; i++){
			for (int j = 0; j < N; j++)
				if (board[i][j] == '.')
					return 0;
		}
		return 2;
	}
	
}
