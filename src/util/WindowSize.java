package util;

import javafx.scene.shape.*;

public class WindowSize {
    public static final double WIDTH = 1400;
    public static final double HEIGHT = 900;

    public static void enhance(Path path) {

        for(PathElement x: path.getElements()) {
            if(x instanceof MoveTo) {
                ((MoveTo) x).setX(((MoveTo) x).getX()*HEIGHT/400);
                ((MoveTo) x).setY(((MoveTo) x).getY()*HEIGHT/400);
            }
            else if(x instanceof CubicCurveTo) {
                ((CubicCurveTo) x).setX(((CubicCurveTo) x).getX()*HEIGHT/400);
                ((CubicCurveTo) x).setY(((CubicCurveTo) x).getY()*HEIGHT/400);
                ((CubicCurveTo) x).setControlX1(((CubicCurveTo) x).getControlX1()*HEIGHT/400);
                ((CubicCurveTo) x).setControlY1(((CubicCurveTo) x).getControlY1()*HEIGHT/400);
                ((CubicCurveTo) x).setControlX2(((CubicCurveTo) x).getControlX2()*HEIGHT/400);
                ((CubicCurveTo) x).setControlY2(((CubicCurveTo) x).getControlY2()*HEIGHT/400);
            }
            else if(x instanceof QuadCurveTo) {
                ((QuadCurveTo) x).setX(((QuadCurveTo) x).getX()*HEIGHT/400);
                ((QuadCurveTo) x).setY(((QuadCurveTo) x).getY()*HEIGHT/400);
                ((QuadCurveTo) x).setControlX(((QuadCurveTo) x).getControlX()*HEIGHT/400);
                ((QuadCurveTo) x).setControlY(((QuadCurveTo) x).getControlY()*HEIGHT/400);
            }
            else if(x instanceof HLineTo) {
                ((HLineTo) x).setX(((HLineTo) x).getX()*HEIGHT/400);
            }
            else if(x instanceof VLineTo) {
                ((VLineTo) x).setY(((VLineTo) x).getY()*HEIGHT/400);
            }
        }
    }
}
