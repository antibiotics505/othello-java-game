import java.util.ArrayList;

class Board {
    private static final int size = 8;
    private static final char empty = ' ';
    private char[][] board;

    public Board() {
        board = new char[size][size];
        initBoard();
    }

    private void initBoard() {
        //the default board
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = empty;
            }
        }
        board[size / 2 - 1][size / 2 - 1] = '○';
        board[size / 2][size / 2] = '○';
        board[size / 2 - 1][size / 2] = '●';
        board[size / 2][size / 2 - 1] = '●';
    }

    /**
     * Print the board.
     */
    public void printBoard() {
        String a = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        System.out.print("  ");
        for (int i = 0; i < size; i++) {
                System.out.print( "| " + a.charAt(i) + " ");
            }
        System.out.println("|");
        System.out.print("-   ");
        for (int i = 0; i < size; i++){
            System.out.print("-   ");
        }
        System.out.println();
        for (int i = 0; i < size ; i++) {
            System.out.print((i + 1) + " ");
            for (int j = 0; j < size; j++) {
                System.out.print("| " + board[i][j] + " ");
            }
            System.out.println("|");
            for (int k = 0; k < (size+1); k++){
                System.out.print("-   ");
            }
            System.out.println();
        }
    }
    
    /**
     * @param x
     * @param y
     * @param player
     * @return
     * Get a coordinate and player's color, return true if the corrdinate is valid to move.
     */
    public boolean isValidMove(int x, int y, Player player) {
        try {
            // The place where the player moves must be empty
            if (board[x][y] != empty) {
                //the place that player moves must be empty.
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        // Get the opponent's color
        char opponent = player.getOpponentColor();
        // After the player moves, some of the opponent's piece must change color,
        // so we need to determine if there is one or more opponent's piece in the way of the player's move.
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                // Skip the current position.
                if (dx == 0 && dy == 0) continue;
                int nx = x + dx, ny = y + dy;
                boolean foundOpponent = false;
                // Check all piece in the current direction
                while (nx >= 0 && nx < size && ny >= 0 && ny < size) {
                    // If an opponent's piece is found
                    if (board[nx][ny] == opponent) {
                        foundOpponent = true;
                        // If a player's piece is found and there was an opponent's piece before, it's a valid move
                    } else if (board[nx][ny] == player.getColor()) {
                        if (foundOpponent) return true;
                        break;
                        // If an empty space or non-opponent piece is found, stop checking in this direction
                    } else {
                        break;
                    }
                    // Move to the next position in the current direction
                    nx += dx;
                    ny += dy;
                }
            }
        }
        return false;
    }

    private String convertPosition(int i, int j){
        String position = "";
        char letter = (char) (j + 65);
        position += String.valueOf(letter);//j letter
        position += String.valueOf(i + 1);
        return position;
    }

    /**
     * @param board
     * @param player
     * @return
     * Add all valid position of the player to a arraylist, and return that list.
     */
    public ArrayList<String> VaildPosition(Board board, Player player){
        ArrayList<String> positions = new ArrayList<>();
        
        for(int i = 0; i < size; i ++){
            for(int j = 0; j < size; j ++){
                if (isValidMove(i, j, player)){                 
                    positions.add(convertPosition(i, j));//j:String i:int
                }
            }
        }
        return positions;
    }

    /**
     * @param x
     * @param y
     * @param player
     * Get a position and player's color. Place the piece on specific place and change the piecees' color if necessary.
     */
    public void makeMove(int x, int y, Player player) {
        // Place the player's piece.
        board[x][y] = player.getColor();
        // Get the opponent's color.
        char opponent = player.getOpponentColor();
        //Search over all possible directions
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                //Skip the current position.
                if (dx == 0 && dy == 0) continue;
                //Set the initial coordinates to the adjacent position of the new piece.
                int nx = x + dx, ny = y + dy;
                boolean foundOpponent = false;
                //Check all pieces in the current direction.
                while (nx >= 0 && nx < size && ny >= 0 && ny < size) {
                    // If an opponent's piece is found in the current direction.
                    if (board[nx][ny] == opponent) {
                        foundOpponent = true;
                    // If a player's piece is found after finding an opponent's piece, flip the pieces.
                    } else if (board[nx][ny] == player.getColor()) {
                        if (foundOpponent) {
                            // Set all pieces between the new piece and the found player's piece to the player's color.
                            int fx = x + dx, fy = y + dy;
                            while (fx != nx || fy != ny) {
                                board[fx][fy] = player.getColor();
                                fx += dx;
                                fy += dy;
                            }
                        }
                        break;
                    // If an empty space or non-opponent piece is found, stop checking.
                    } else {
                        break;
                    }
                    // Update the current checking position.
                    nx += dx;
                    ny += dy;
                }
            }
        }
    }

    /**
     * @param player
     * @return
     * Return true if a player has valid move.
     */
    public boolean hasValidMoves(Player player) {
        //determine if this player has valid move. 
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (isValidMove(i, j, player)) {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * @param color
     * @return
     * Count a player's total score.
     */
    public int countPieces(char color) {
        //Count to determine winner.
        int count = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] == color) {
                    count++;
                }
            }
        }
        return count;
    }
}
