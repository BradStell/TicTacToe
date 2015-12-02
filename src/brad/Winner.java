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


    public Winner() {
        direction = null;
        whoWon = 0;
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
