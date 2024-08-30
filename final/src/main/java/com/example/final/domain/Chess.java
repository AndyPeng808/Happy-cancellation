package domain;

public class Chess {
    private char type;
    private int location[];
    private boolean isAlive;

    // int location[0] = x, location[1] = y;
    public Chess() {
        isAlive = true;
    }

    public Chess(char type) {
        this.type = type;
        isAlive = true;
    }

    public Chess(char type, int x, int y) {
        this.type = type;
        this.location = new int[] { x, y };
    }

    public char getType() {
        return type;
    }

    public void setType(char type) {
        this.type = type;
    }

    public int[] getLocation() {
        return location;
    }

    public boolean setisAlive(boolean isAlive) {
        this.isAlive = isAlive;
        return isAlive;
    }

    public boolean getisAlive() {
        return isAlive;
    }

    public void random() {
        int random = (int) (Math.random() * 10 + 1);
        if (random <= 9) {
            int random_2 = (int) (Math.random() * 5 + 1);
            switch (random_2) {
                case 1:
                    setType('A');
                    break;
                case 2:
                    setType('B');
                    break;
                case 3:
                    setType('C');
                    break;
                case 4:
                    setType('D');
                    break;
                case 5:
                    setType('E');
                    break;
            }
        } else {
            int random_2 = (int) (Math.random() * 7 + 1);
            switch (random_2) {
                case 1:
                    setType('a');
                    break;
                case 2:
                    setType('b');
                    break;
                case 3:
                    setType('c');
                    break;
                case 4:
                    setType('d');
                    break;
                case 5:
                    setType('e');
                    break;
                case 6:
                    setType('|');
                    break;
                case 7:
                    setType('+');
                    break;
            }
        }
    }
}
