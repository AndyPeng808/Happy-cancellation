package domain;

public class SpecialType extends Board{
    private int aimnumber;
    private int number;
    private int formernumber;
    private boolean[][] isUsed = new boolean[5][5];

    public SpecialType(int level, int hummer, int number, char[][] board) {
        this.level = level;
        this.hummer = hummer;
        this.aimnumber = number;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (board[i][j] == 'x')
                    isUsed[i][j] = false;
                else isUsed[i][j] = true;
                this.state[i][j] = new Chess();
                this.state[i][j].setType(board[i][j]);
                copystate[i][j] = board[i][j];
            }
        }
    }

    protected boolean judge(int x, int y) {
        int left = 0;
        int right = 0;
        int up = 0;
        int down = 0;
        if (state[x][y].getType() >= 'a' && state[x][y].getType() <= 'e' || state[x][y].getType() == '|'
                || state[x][y].getType() == '+')
            return true;
        else {
            int finish = 1;
            for (int i = 1; i < 5 && finish == 1; i++) {
                if (y - i >= 0 && state[x][y - i].getType() == state[x][y].getType()&&isUsed[x][y - i])
                    up++;
                else
                    finish = 0;
            }
            finish = 1;
            for (int i = 1; i < 5 && finish == 1; i++) {
                if (y + i <= 4 && state[x][y + i].getType() == state[x][y].getType()&&isUsed[x][y + i])
                    down++;
                else
                    finish = 0;
            }
            finish = 1;
            for (int i = 1; i < 5 && finish == 1; i++) {
                if (x - i >= 0 && state[x - i][y].getType() == state[x][y].getType()&&isUsed[x-i][y])
                    left++;
                else
                    finish = 0;
            }
            finish = 1;
            for (int i = 1; i < 5 && finish == 1; i++) {
                if (x + i <= 4 && state[x + i][y].getType() == state[x][y].getType()&&isUsed[x+i][y])
                    right++;
                else
                    finish = 0;
            }
            if (up + down >= 2 || left + right >= 2)
                return true;
            else
                return false;

        }
    }

    protected void delete(int x, int y) {
        int left = 0;
        int right = 0;
        int up = 0;
        int down = 0;
        int former = countDeleteNumber();
        if (state[x][y].getType() >= 'a' && state[x][y].getType() <= 'e') {
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    if (state[i][j].getType() == state[x][y].getType() + 'A' - 'a') {
                        state[i][j].setisAlive(false);
                    }
                }
            }
            state[x][y].setisAlive(false);
        } else if (state[x][y].getType() == '|') {
            for (int i = 0; i < 5; i++)
                state[i][y].setisAlive(false);
        } else if (state[x][y].getType() == '+') {
            for (int i = -1; i < 2; i++) {
                for (int j = -1; j < 2; j++) {
                    if(x+i>=0&&x+i<5&&y+j>=0&&y+j<5)
                        state[x + i][y + j].setisAlive(false);
                }
            }
        } else {
            int finish = 1;
            for (int i = 1; i < 5 && finish == 1; i++) {
                if (y - i >= 0 && state[x][y - i].getType() == state[x][y].getType()&&isUsed[x][y-i])
                    up++;
                else
                    finish = 0;
            }
            finish = 1;
            for (int i = 1; i < 5 && finish == 1; i++) {
                if (y + i <= 4 && state[x][y + i].getType() == state[x][y].getType()&&isUsed[x][y+i])
                    down++;
                else
                    finish = 0;
            }
            finish = 1;
            for (int i = 1; i < 5 && finish == 1; i++) {
                if (x - i >= 0 && state[x - i][y].getType() == state[x][y].getType()&&isUsed[x-1][y])
                    left++;
                else
                    finish = 0;
            }
            finish = 1;
            for (int i = 1; i < 5 && finish == 1; i++) {
                if (x + i <= 4 && state[x + i][y].getType() == state[x][y].getType()&&isUsed[x+1][y])
                    right++;
                else
                    finish = 0;
            }
            if (up + down >= 2) {
                for (int i = 0; i <= up; i++)
                    state[x][y - i].setisAlive(false);
                for (int i = 0; i <= down; i++)
                    state[x][y + i].setisAlive(false);
            }
            if (left + right >= 2) {
                for (int i = 0; i <= left; i++)
                    state[x - i][y].setisAlive(false);
                for (int i = 0; i <= right; i++)
                    state[x + i][y].setisAlive(false);
            }
        }
        int latter = countDeleteNumber();
        if (latter - former >= 5)
            hummer++;
    }

    protected void fill() {
        for (int i = height - 1; i >= 0; i--) {
            for (int j = length - 1; j >= 0; j--) {
                if (!state[i][j].getisAlive() && i > 0 && isUsed[i][j]) {
                    int k = i - 1;
                    while (k >= 0 && !state[k][j].getisAlive() && isUsed[k][j]) {
                        k--;
                    }
                    for (int m = 0; m <= k && isUsed[i-m][j] && isUsed[k-m][j]; m++) {
                        state[i - m][j].setType(state[k - m][j].getType());
                        state[k - m][j].setisAlive(false);
                        state[i - m][j].setisAlive(true);
                    }
                }
            }
        }
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < length; j++) {
                if (!state[i][j].getisAlive() && isUsed[i][j]) {
                    state[i][j].random();
                    state[i][j].setisAlive(true);
                }
            }
        }
    }
    public void clear(int x, int y) {
        if (judge(x, y)) {
            delete(x, y);
            number++;
            fill();
        } else
            System.out.println("unable to clear");
    }

    public String displaystate() {
        return (number + "/" + aimnumber + " of times " + " has been eliminated.");
    }

    public void restart() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                state[i][j].setType(copystate[i][j]);
            }
        }
        number = 0;
    }

    public boolean success() {
        if (number >= aimnumber)
            return true;
        else
            return false;
    }
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

}
