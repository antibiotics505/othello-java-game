class Player {
    private char color;

    public Player(char color) {
        this.color = color;
    }

    public char getColor() {
        return color;
    }

    public char getOpponentColor() {
        return (color == '●') ? '○' : '●';
    }
}

