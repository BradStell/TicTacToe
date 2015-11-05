package brad;

import javafx.scene.image.ImageView;

/**
 * Created by Brad on 11/5/2015.
 *
 * Custom ImageView
 *  Needed to override minWidth and minHeight properties to be 0.0
 */
public class MyImageView extends ImageView {

    MyImageView() {
        super();
    }

    @Override
    public double minWidth(double height) {
        return 0.0;
    }

    @Override
    public double minHeight(double height) {
        return 0.0;
    }
}
