import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
class Pvc {
    //private Othello othello;
    private Board board;
    private Player blackPlayer;
    private Player whitePlayer;
    private Player cPlayer;
    private Player pPlayer;

    public Pvc() {
        board = new Board();
        //othello = new Othello();
        blackPlayer = new Player('●');
        whitePlayer = new Player('○');
        cPlayer = whitePlayer;
        pPlayer = blackPlayer;
    }

    public void PlayGameComputer(){
        Scanner scanner = new Scanner(System.in);
        while (true) {
            //print initial board.
            board.printBoard();
            if (!board.hasValidMoves(pPlayer)) {
                if (!board.hasValidMoves(cPlayer)) {
                    break;
                } else {
                    System.out.println("Player " + pPlayer.getColor() + " has no valid moves, skipping turn.");
                    switchComputerPlayer();
                    board.printBoard();
                    int blackCount = board.countPieces('●');
                    int whiteCount = board.countPieces('○');
                    System.out.println("Black: " + blackCount);
                    System.out.println("White: " + whiteCount);
                    continue;
                }
            }
            System.out.println("Player " + pPlayer.getColor() + "'s turn.");
            int blackCount = board.countPieces('●');
            int whiteCount = board.countPieces('○');
            System.out.println("Black: " + blackCount);
            System.out.println("White: " + whiteCount);
            System.out.println("Enter your move");
            try {
                String pos = scanner.next().toUpperCase();
                int y = convert(pos.charAt(0));
                int x = Integer.valueOf(pos.substring(1)) - 1;
                //System.out.printf("%d, %d",x,y);
                //System.out.println(" ");
                if (board.isValidMove(x, y, pPlayer)) {
                    board.makeMove(x, y, pPlayer);
                    board.printBoard();
                    System.out.println("Player " + cPlayer.getColor() + "'s turn.");
                    blackCount = board.countPieces('●');
                    whiteCount = board.countPieces('○');
                    System.out.println("Black: " + blackCount);
                    System.out.println("White: " + whiteCount);

                    switchComputerPlayer();
                } else {
                    System.out.println("Invalid move, try again.");

                }
            }catch (Exception e) {
                System.out.println("Invalid input.");
                
            }
        }

        //player's turn all over, starting to determine who is winner.
        board.printBoard();
        int blackCount = board.countPieces('●');
        int whiteCount = board.countPieces('○');
        System.out.println("Game over.");
        System.out.println("Black: " + blackCount);
        System.out.println("White: " + whiteCount);
        if (blackCount > whiteCount) {
            System.out.println("Black wins.");
        } else if (whiteCount > blackCount) {
            System.out.println("White wins.");
        } else {
            System.out.println("It's a tie.");
        }
    }

    

    private String randomposition(ArrayList<String> positions){
        int length = positions.size();
        Random numList = new Random();
        int num = numList.nextInt(length);
        return positions.get(num);
    }

//A1

    private void switchComputerPlayer(){
        try {
            ArrayList<String> positions = board.VaildPosition(board, cPlayer);

            if (positions.size() == 0){
                System.out.println("Player " + cPlayer.getColor() + " has no valid moves, skipping turn.");
                return;
            }

            String pos = randomposition(positions);
            int y = convert(pos.charAt(0));
            int x = Integer.valueOf(pos.substring(1)) - 1;

            if (board.isValidMove(x, y, cPlayer)) {
                board.makeMove(x, y, cPlayer);
            } else {
                System.out.println("Invalid move, try again.");
            }
        } catch (Exception e) {
            System.out.println("Invalid input.");
        }
    }

    private int convert(char a){
        try {
            char b = Character.valueOf(a);
            return Integer.valueOf(b) - 65;
        } catch (Exception e) {
            return -100000;
        }
    }

    Player getOpponent(Player player) {
        //determine who is the opponent player
        return (player == blackPlayer) ? whitePlayer : blackPlayer;
    }

    
    public static void main(String[] args) {
        System.out.println();
    }
}
