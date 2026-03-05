import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
//import java.util.HashMap;
import java.util.Scanner;

class Othello {
    private Board board;
    private Pvc pvc;
    private Player blackPlayer;
    private Player whitePlayer;
    private Player currentPlayer;

    public Othello() {
        board = new Board();
        pvc = new Pvc();
        blackPlayer = new Player('●');
        whitePlayer = new Player('○');
        currentPlayer = blackPlayer;
    }

    
    public void playGame() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose which type you want play(1: pvp or 2:pvc, enter the number)");
        String ans = scanner.nextLine();
        try{
            if(ans.equals("1") || ans.equals("2")){
                if (ans.equals("1")){
                    while (true) {
                        //print initial board.
                        board.printBoard();
                        //if no valid move, to the opponent player
                        if (!board.hasValidMoves(currentPlayer)) {
                            if (!board.hasValidMoves(getOpponent(currentPlayer))) {
                                break;
                            } else {
                                System.out.println("Player " + currentPlayer.getColor() + " has no valid moves, skipping turn.");
                                switchPlayer();
                                continue;
                            }
                        }
            
                        System.out.println("Player " + currentPlayer.getColor() + "'s turn.");
                        int blackCount = board.countPieces('●');
                        int whiteCount = board.countPieces('○');
                        System.out.println("Black: " + blackCount);
                        System.out.println("White: " + whiteCount);
                        System.out.print("Enter your move: ");
                        //System.out.println(board.VaildPosition(board, currentPlayer));
                        try {
                            String pos = scanner.next().toUpperCase();
                            
                            int y = convert(pos.charAt(0));
                            int x = Integer.valueOf(pos.substring(1)) - 1;
                            //System.out.printf("%d, %d",x,y);
                            //System.out.println(" ");
                            if (board.isValidMove(x, y, currentPlayer)) {
                                board.makeMove(x, y, currentPlayer);
                                writefile(pos);
                                switchPlayer();
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
                else{
                    pvc.PlayGameComputer();
                }
            }
            else{
                System.out.println("Invaild input");
            }
        }
        catch(Exception e){
            System.out.println("Invaild input");
        }
    }
    
    private void switchPlayer() {
        //switch player to another
        currentPlayer = (currentPlayer == blackPlayer) ? whitePlayer : blackPlayer;
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

    private void writefile(String pos){
        try{
            File file = new File("Test.txt");
            FileWriter writer = new FileWriter(file, true); 
            writer.write(pos + System.lineSeparator());
            //writer.write("f g h i");
            writer.close();
        }
        catch (IOException e) {
            System.out.println("File cannot be opened.");
        }
    }


    public static void main(String[] args) {
        Othello game = new Othello();
        game.playGame();
        //String a = "E";
        //char b = a.charAt(0);
        //System.out.println(convert(b));
    }
}