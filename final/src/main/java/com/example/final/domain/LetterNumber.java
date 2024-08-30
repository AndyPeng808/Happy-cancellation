package domain;

public class LetterNumber extends Board {
    private int aimnumber[];
    private int[] number = { 0, 0, 0, 0, 0 };
    private int[] formerNumber = { 0, 0, 0, 0, 0 };

    public void setFormerstate(){
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                formerstate[i][j] = state[i][j].getType();
            }
        }
        for(int i = 0; i < 5; i++){
            formerNumber[i]=number[i];
        }
    }
    public void getFormerstate(){
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                state[i][j].setType(formerstate[i][j]);
            }
        }
        for (int i = 0; i < 5; i++) {
            number[i]=formerNumber[i];
        }
    }
    public LetterNumber(int level, int hummer, int[] aimnumber, char[][] board) {
        this.level = level;
        this.aimnumber = aimnumber;
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
        number[state[x][y].getType() - 'A']++;
    }

    public void restart() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                state[i][j].setType(copystate[i][j]);
            }
        }
        for (int i = 0; i < 5; i++) {
            number[i] = 0;
        }
    }

    public char[][] copy(char[][] origin) {
        char[][] copy = new char[5][5];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < length; j++) {
                copy[i][j] = origin[i][j];
            }
        }
        return copy;
    }

    private int[] countaimletter() {
        int count[] = new int[5];
        for (int k = 0; k < 5; k++) {
            count[k] = 0;
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    if (state[i][j].getType() == 'A' + k && state[i][j].getisAlive())
                        count[k]++;
                }
            }
        }
        return count;
    }

    public void clear(int x, int y) {
        if (judge(x, y)) {
            int former[] = countaimletter();
            delete(x, y);
            int letter[] = countaimletter();
            for (int i = 0; i < 5; i++) {
                number[i] = number[i] + former[i] - letter[i];
            }
            fill();
        } else
            System.out.println("unable to clear");
    }

    public boolean success() {
        int i;
        for (i = 0; i < 5; i++) {
            if (number[i] < aimnumber[i])
                break;
        }
        if (i == 5)
            return true;
        else
            return false;
    }

    public String displaystate() {
        String s = "";
        for (int i = 0; i < 5; i++) {
            if (aimnumber[i] != 0) {
                s = s.concat(number[i] + "/" + aimnumber[i] + " of " + (char) ('A' + i) + " has been eliminated.\n");
            }
        }
        return s;
    }
}
