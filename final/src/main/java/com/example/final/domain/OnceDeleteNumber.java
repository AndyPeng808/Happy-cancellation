package domain;

public class OnceDeleteNumber extends Board {
    private int onceDeleteNumber;
    private int aimOnceDeleteNumber;
    private int number;
    private int formernumber;

    public void setFormerstate(){
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                formerstate[i][j] = state[i][j].getType();
            }
        }
        formernumber=number;
    }
    public void getFormerstate(){
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                state[i][j].setType(formerstate[i][j]);
            }
        }
        number=formernumber;
    }
    public OnceDeleteNumber(int level, int hummer,int number, int aimOnceDeleteNumber, char[][] board) {
        this.level = level;
        this.number = number;
        this.hummer = hummer;
        this.aimOnceDeleteNumber = aimOnceDeleteNumber;
        onceDeleteNumber = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                this.state[i][j] = new Chess();
                this.state[i][j].setType(board[i][j]);
                copystate[i][j] = (board[i][j]);
            }
        }
    }

    public void restart() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                state[i][j].setType(copystate[i][j]);
            }
        }
        onceDeleteNumber = 0;
    }

    public void clear(int x, int y) {
        if (judge(x, y)) {
            delete(x, y);
            if (countDeleteNumber() > number)
                onceDeleteNumber++;
            fill();
        } else
            System.out.println("unable to clear");
    }

    public boolean success() {
        if (onceDeleteNumber >= aimOnceDeleteNumber)
            return true;
        else
            return false;
    }

    public String displaystate() {
        return (onceDeleteNumber + "/" + aimOnceDeleteNumber + " of required clear operation.");
    }
}
