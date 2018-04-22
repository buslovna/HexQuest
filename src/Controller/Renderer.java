package Controller;

import Model.AreaEffects.AreaEffect;
import Model.Items.ObstacleItem;
import Model.Zone.Terrain;
import Model.Zone.World;
import Model.Zone.Zone;
import View.Menu.GameplayView;
import View.SpriteBase;
import View.Status.StatusView;
import View.Zone.AreaEffectView;
import View.Zone.Items.ItemView;
import View.Zone.Items.ObstacleView;
import View.Zone.MapView;
import javafx.beans.value.ObservableStringValue;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.awt.Point;
import java.awt.geom.*;

import java.util.Collection;

public class Renderer {

    private World world;
    private GameplayView gameplayView;
    private StatusView statusView;
    private Canvas canvas;
    private GraphicsContext graphicsContext;
    private SpriteBase sprites;
    private ObstacleView obstacleView;
    private MapView mapView;
    private AreaEffectView areaEffectView;

    // sets the radius/size of tiles and stuff
    private final int radius = 32;

    // TODO: CHANGE TO ADD PROPER IMPLEMENTATION
    public Renderer(World world, GameplayView gameplayView) {
        this.gameplayView = gameplayView;
        this.canvas = gameplayView.getCanvas();
        this.graphicsContext = canvas.getGraphicsContext2D();
        this.world = world;
        sprites = new SpriteBase();
        statusView = new StatusView(canvas);
        mapView = new MapView(graphicsContext, sprites);
        obstacleView = new ObstacleView(graphicsContext, sprites);
        areaEffectView = new AreaEffectView(graphicsContext, sprites);
    }

    public void render() {
        // clear canvas for each render
//        graphicsContext.clearRect(0,0,1000,800);
        graphicsContext.setFill(Color.BLACK);
        graphicsContext.fillRect(0, 0, canvas.getWidth(),canvas.getHeight());
        renderTiles();
        renderPlayer();
        renderObstacles();
        statusView.render(world.getPlayer());

    }
    public Canvas getCanvas() {
        return canvas;
    }

    public void renderTiles() {
        Zone zone = world.getCurrentZone();
        Collection<Point> zoneCollection = zone.getAllTerrainPoints();
        Point[] zoneArr = zoneCollection.toArray(new Point[zoneCollection.size()]);
        for (int i = 0; i < zoneArr.length; i++) {
            Terrain zoneTerrain = zone.getTerrain(zoneArr[i]);
            Point2D imageCoordinates = calculateImageCoordinates((int) zoneArr[i].getX(), (int) zoneArr[i].getY(), radius);
            mapView.render(zoneTerrain, imageCoordinates, radius);
            if (zone.getAreaEffect(zoneArr[i]) != null) {
                AreaEffect currAE = zone.getAreaEffect(zoneArr[i]);
                areaEffectView.render(currAE, imageCoordinates, radius);
            }
        }

    }

    public void renderObstacles() {
        Zone zone = world.getCurrentZone();
        Collection<Point> obstacleCollection = zone.getAllObstacleItemPoints();
        Point[] obstacleArr = obstacleCollection.toArray(new Point[obstacleCollection.size()]);
        for (int i = 0; i < obstacleArr.length; i++) {
            ObstacleItem obstacle = zone.getObstacleItem(obstacleArr[i]);
            Point2D imageCoordinates = calculateImageCoordinates((int) obstacleArr[i].getX(), (int) obstacleArr[i].getY(), radius);
            obstacleView.render(imageCoordinates, radius);
        }
    }


    private void renderPlayer() {
        Point playerLocation = world.getPlayer().getLocation();
        Point2D imageCoordinates = calculateImageCoordinates((int) playerLocation.getX(),(int) playerLocation.getY(), radius);
        graphicsContext.drawImage(sprites.getCharacterSprite(0), imageCoordinates.getX(), imageCoordinates.getY(), 2*radius, 2*radius);
    }

    private void renderItems() {
        Collection<Point> collectionPoints = world.getCurrentZone().getAllObstacleItemPoints();
//        Point2D imageCoordinates = calculateImageCoordinates((int) playerLocation.getX(),(int) playerLocation.getY());
//        graphicsContext.drawImage(sprites.getItemSprite(0), imageCoordinates.getX(), imageCoordinates.getY(), 2*radius, 2*radius);
    }

    private Point2D calculateImageCoordinates(int x, int y, int radius) {
        double a = 0, b = 0;

        if (x % 2 == 1) {
            a = radius * 1.5 * x;
            b = (2 * radius * y) + radius;
        }
        if (x % 2 == 0) {
            a = radius * 1.5 * x;
            b = radius * 2 * y;
        }


        return new Point2D.Double(a, b);
    }
}
