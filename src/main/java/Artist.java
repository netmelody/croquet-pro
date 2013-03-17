import java.awt.Color;
import java.awt.Graphics2D;

import org.jbox2d.callbacks.DebugDraw;
import org.jbox2d.common.Color3f;
import org.jbox2d.common.MathUtils;
import org.jbox2d.common.OBBViewportTransform;
import org.jbox2d.common.Transform;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;
import org.jbox2d.pooling.arrays.IntArray;
import org.jbox2d.pooling.arrays.Vec2Array;

public final class Artist extends DebugDraw {
    public static int POINTS_IN_CIRCLE = 20;

    private final ArtistCanvas canvas;

    public Artist(ArtistCanvas canvas) {
        super(new OBBViewportTransform());
        viewportTransform.setYFlip(true);
        this.canvas = canvas;
        setFlags(DebugDraw.e_shapeBit);
        setCamera(0, 10, 10);
        viewportTransform.setExtents(canvas.getCentre());
    }

    public void draw(Model model) {
        if(canvas.prepare()){
            World pose = model.pose();
            pose.setDebugDraw(this);
            pose.drawDebugData();
            canvas.unveil();
        }
    }

    private final Vec2Array vec2Array = new Vec2Array();
    @Override
    public void drawCircle(Vec2 center, float radius, Color3f color) {
        Vec2[] vecs = vec2Array.get(POINTS_IN_CIRCLE);
        generateCirle(center, radius, vecs, POINTS_IN_CIRCLE);
        drawPolygon(vecs, POINTS_IN_CIRCLE, color);
    }

    private final Vec2 saxis = new Vec2();
    @Override
    public void drawSolidCircle(Vec2 centre, float radius, Vec2 axis, Color3f color) {
        Vec2[] vecs = vec2Array.get(POINTS_IN_CIRCLE);
        generateCirle(centre, radius, vecs, POINTS_IN_CIRCLE);
        drawSolidPolygon(vecs, POINTS_IN_CIRCLE, color);
        if (axis != null) {
            saxis.set(axis).mulLocal(radius).addLocal(centre);
            drawSegment(centre, saxis, color);
        }
    }

    private final Vec2 sp1 = new Vec2();
    private final Vec2 sp2 = new Vec2();
    @Override
    public void drawSegment(Vec2 p1, Vec2 p2, Color3f color) {
        getWorldToScreenToOut(p1, sp1);
        getWorldToScreenToOut(p2, sp2);

        Graphics2D g = getGraphics();
        Color c = Color.GREEN;
        g.setColor(c);

        g.drawLine((int) sp1.x, (int) sp1.y, (int) sp2.x, (int) sp2.y);
    }

    private final Vec2 temp = new Vec2();
    private final static IntArray xIntsPool = new IntArray();
    private final static IntArray yIntsPool = new IntArray();
    @Override
    public void drawSolidPolygon(Vec2[] vertices, int vertexCount, Color3f color) {
        Graphics2D g = getGraphics();
        int[] xInts = xIntsPool.get(vertexCount);
        int[] yInts = yIntsPool.get(vertexCount);

        for (int i = 0; i < vertexCount; i++) {
            getWorldToScreenToOut(vertices[i], temp);
            xInts[i] = (int) temp.x;
            yInts[i] = (int) temp.y;
        }

        Color c = Color.BLUE;
        g.setColor(c);
        g.fillPolygon(xInts, yInts, vertexCount);
        drawPolygon(vertices, vertexCount, color);
    }

    @Override public void drawString(float x, float y, String s, Color3f color) { }
    @Override public void drawPoint(Vec2 argPoint, float argRadiusOnScreen, Color3f argColor) { }
    @Override public void drawTransform(Transform xf) { }

    private Graphics2D getGraphics() {
        return canvas.getGraphics2d();
    }

    private static void generateCirle(Vec2 argCenter, float radius, Vec2[] argPoints, int numberOfPoints) {
        float inc = MathUtils.TWOPI / numberOfPoints;
        for (int i = 0; i < numberOfPoints; i++) {
            argPoints[i].x = (argCenter.x + MathUtils.cos(i * inc) * radius);
            argPoints[i].y = (argCenter.y + MathUtils.sin(i * inc) * radius);
        }
    }
}
