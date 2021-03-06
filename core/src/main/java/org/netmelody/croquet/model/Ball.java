package org.netmelody.croquet.model;


public final class Ball {

    public static final Ball BLACK  = Ball.coloured("#191B16");
    public static final Ball YELLOW = Ball.coloured("#FDC62B");
    public static final Ball RED    = Ball.coloured("#FF3D46");
    public static final Ball BLUE   = Ball.coloured("#41B8FA");
    public static final Ball GREEN  = Ball.coloured("#72D471");
    public static final Ball BROWN  = Ball.coloured("#D47F3E");
    public static final Ball WHITE  = Ball.coloured("#FEFFFB");
    public static final Ball PINK   = Ball.coloured("#FFDCD7");

    public final String hexColor;
    public final float radius = 0.460375f;

    private Ball(String hexColor) {
        this.hexColor = hexColor;
    }

    public static Ball coloured(String hexColor) {
        return new Ball(hexColor);
    }
    
    @Override
    public String toString() {
        return hexColor;
    }
}
