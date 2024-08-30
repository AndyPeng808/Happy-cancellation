package domain;

public class DeleteNumber extends Board {
    private int deleteNumber;
    private int aimnumber;
    private int formerDeletenumber;

    public void setFormerstate(){
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                formerstate[i][j] = state[i][j].getType();
            }
        }
        formerDeletenumber=deleteNumber;
    }
    public void getFormerstate(){
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                state[i][j].setType(formerstate[i][j]);
            }
        }
        deleteNumber=formerDeletenumber;
    }
    public DeleteNumber(int level, int hummer, int number, char[][] board) {
        this.level = level;
        this.aimnumber = number;
        this.hummer = hummer;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                this.state[i][j] = new Chess();
                this.state[i][j].setType(board[i][j]);
                copystate[i][j] = board[i][j];
            }
        }
    }

    @Override
    public void hummer(int x, int y) {
        super.hummer(x, y);
        deleteNumber++;
    }

    public void restart() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                state[i][j].setType(copystate[i][j]);
            }
        }
        deleteNumber = 0;
    }

    private int countDeleteNumber1() {
        int number = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (state[i][j].getisAlive() == false) {
                    number++;
                }
            }
        }
        return number;
    }

    public void clear(int x, int y) {
        if (judge(x, y)) {
            delete(x, y);
            deleteNumber += countDeleteNumber1();
            fill();
        } else
            System.out.println("unable to clear");
    }

    public boolean success() {
        if (deleteNumber >= aimnumber)
            return true;
        else
            return false;
    }

    public String displaystate() {
        return (deleteNumber + "/" + aimnumber + " of number " + " has been eliminated.");
    }
}
