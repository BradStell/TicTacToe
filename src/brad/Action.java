package brad;

/**
 * Created by Brad on 11/26/2015.
 */
public class Action {

    private int row;
    private int col;

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public static Action[] generateActions(State state) {

        Action[] actions = new Action[state.getNumChildren()];

        if (state.getWho() == MiniMax.MIN) {
            actions = generateMinActions();
        } else if (state.getWho() == MiniMax.MAX) {
            actions = generateMaxActions();
        }

        return actions;
    }

    private static Action[] generateMinActions() {

    }

    private static Action[] generateMaxActions() {

    }
}
