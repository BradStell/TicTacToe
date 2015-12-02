package brad;

/**
 * Created by Brad on 12/2/2015.
 */
public class Winner {

    public static String HORIZONTAL = "Horiz";
    public static String VERTICAL = "Vert";
    public static String DIAGONAL = "Diag";
    private String direction;
    private int whoWon;
    private int startLine;
    private boolean isOver;


    public Winner() {
        direction = null;
        whoWon = 0;
        startLine = -1;
        isOver = false;
    }

    public void setIsOver(boolean b) {
        isOver = b;
    }

    public boolean getIsOver() {
        return isOver;
    }

    public void setStartLine(int num) {
        startLine = num;
    }

    public int getStartLine() {
        return startLine;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getDirection() {
        return direction;
    }

    public void setWhoWon(int whoWon) {
        this.whoWon = whoWon;
    }

    public int getWhoWon() {
        return whoWon;
    }
}
