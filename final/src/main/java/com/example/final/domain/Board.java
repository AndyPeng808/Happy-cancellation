package domain;


public abstract class Board {
    public Chess[][] state = new Chess[5][5];
    protected char copystate[][] = new char[5][5];
    protected char formerstate[][] = new char[5][5];
    protected int height = 5;
    protected int length = 5;
    protected int level;
    protected int hummer;


    public abstract void setFormerstate();

    public abstract void getFormerstate();

    public int getLevel(){
        return level;
    }

    public int getHummer(){
        return hummer;
    }
    public  Chess getstate(int row, int col) {
        return state[row][col];
    }

    public abstract boolean success();

    public abstract void clear(int x, int y);

    public abstract String displaystate();

    public abstract void restart();

    public void hummer(int x, int y) {
        state[x][y].setisAlive(false);
        hummer--;
        fill();
    }

    public void swap(int x, int y,int i) {
            if (!(x == 0 && i == 1) && !(y == 0 && i == 3) && !(x == 4 && i == 2) && !(y == 4 && i == 4)
                    && (judgechanged(x, y, i).origin)) {
                change(x, y, i);
                clear(x, y);
            }
            else if (!(x == 0 && i == 1) && !(y == 0 && i == 3) && !(x == 4 && i == 2) && !(y == 4 && i == 4)
                    && (judgechanged(x, y, i).changed)) {
                change(x, y, i);
                switch (i) {
                    case 1:
                        clear(x - 1, y);
                        break;
                    case 2:
                        clear(x + 1, y);
                        break;
                    case 3:
                        clear(x, y - 1);
                        break;
                    case 4:
                        clear(x, y + 1);
                        break;
                }
            }
            else   System.out.println("unable to swap");;
    }

    public void printBoard() {
        System.out.println("当前棋盘布局：");
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < length; j++) {
                System.out.print(state[i][j].getType() + " ");
            }
            System.out.println();
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
                if (y - i >= 0 && state[x][y - i].getType() == state[x][y].getType())
                    up++;
                else
                    finish = 0;
            }
            finish = 1;
            for (int i = 1; i < 5 && finish == 1; i++) {
                if (y + i <= 4 && state[x][y + i].getType() == state[x][y].getType())
                    down++;
                else
                    finish = 0;
            }
            finish = 1;
            for (int i = 1; i < 5 && finish == 1; i++) {
                if (x - i >= 0 && state[x - i][y].getType() == state[x][y].getType())
                    left++;
                else
                    finish = 0;
            }
            finish = 1;
            for (int i = 1; i < 5 && finish == 1; i++) {
                if (x + i <= 4 && state[x + i][y].getType() == state[x][y].getType())
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

    protected int countDeleteNumber() {
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
                if (y - i >= 0 && state[x][y - i].getType() == state[x][y].getType())
                    up++;
                else
                    finish = 0;
            }
            finish = 1;
            for (int i = 1; i < 5 && finish == 1; i++) {
                if (y + i <= 4 && state[x][y + i].getType() == state[x][y].getType())
                    down++;
                else
                    finish = 0;
            }
            finish = 1;
            for (int i = 1; i < 5 && finish == 1; i++) {
                if (x - i >= 0 && state[x - i][y].getType() == state[x][y].getType())
                    left++;
                else
                    finish = 0;
            }
            finish = 1;
            for (int i = 1; i < 5 && finish == 1; i++) {
                if (x + i <= 4 && state[x + i][y].getType() == state[x][y].getType())
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

    protected void change(int x, int y, int change) {
        char i = state[x][y].getType();
        switch (change) {
            case 1:
                state[x][y].setType(state[x - 1][y].getType());
                state[x - 1][y].setType(i);
                break;
            case 2:
                state[x][y].setType(state[x + 1][y].getType());
                state[x + 1][y].setType(i);
                break;
            case 3:
                state[x][y].setType(state[x][y - 1].getType());
                state[x][y - 1].setType(i);
                break;
            case 4:
                state[x][y].setType(state[x][y + 1].getType());
                state[x][y + 1].setType(i);
                break;
        }
    }

    protected class changestate {
        boolean origin;
        boolean changed;
    }

    protected changestate judgechanged(int x, int y, int change) {
        // change为1时，和上方换；change为2时，和下方换；change为3时，和左方换；change为4时，和左方换；
        changestate c = new changestate();
        change(x, y, change);
        c.origin = judge(x, y);
        switch (change) {
            case 1:
                c.changed = judge(x - 1, y);
                change(x, y, change);
                break;
            case 2:
                c.changed = judge(x + 1, y);
                change(x, y, change);
                break;
            case 3:
                c.changed = judge(x, y - 1);
                change(x, y, change);
                break;
            case 4:
                c.changed = judge(x, y + 1);
                change(x, y, change);
                break;
        }
        return c;

    }

    protected void fill() {
        for (int i = height - 1; i >= 0; i--) {
            for (int j = length - 1; j >= 0; j--) {
                if (state[i][j].getisAlive() == false && i > 0) {
                    int k = i - 1;
                    while (k >= 0 && state[k][j].getisAlive() == false) {
                        k--;
                    }
                    for (int m = 0; m <= k; m++) {
                        state[i - m][j].setType(state[k - m][j].getType());
                        state[k - m][j].setisAlive(false);
                        state[i - m][j].setisAlive(true);
                    }
                }
            }
        }
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < length; j++) {
                if (state[i][j].getisAlive() == false) {
                    state[i][j].random();
                    state[i][j].setisAlive(true);
                }
            }
        }
    }

}
