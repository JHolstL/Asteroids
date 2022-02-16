package dk.sdu.mmi.cbse.asteroid;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import javafx.scene.shape.Polygon;

import java.util.ArrayList;
import java.util.Random;

public class AsteroidControlSystem implements IEntityProcessingService {

    private Random rnd = new Random();
    private ArrayList<Entity> asteroids = new ArrayList<>();

    public void fillAsteroids(){
        Entity asteroid = new Entity();
        asteroids.add(asteroid);
    }

    @Override
    public void process(GameData gameData, World world) {
        fillAsteroids();

        for (Entity asteroid : world.getEntities(Asteroid.class)) {
            PositionPart positionPart = asteroid.getPart(PositionPart.class);
            MovingPart movingPart = asteroid.getPart(MovingPart.class);

            movingPart.setLeft(rnd.nextBoolean());
            movingPart.setRight(rnd.nextBoolean());
            movingPart.setUp(rnd.nextBoolean());


            movingPart.process(gameData, asteroid);
            positionPart.process(gameData, asteroid);

            updateShape(asteroid, 20, 15.5, 65.5);
        }
    }

    private void updateShape(Entity entity, int numberOfNodes, double minRadius, double maxRadius) {
        float[] shapex = entity.getShapeX();
        float[] shapey = entity.getShapeY();
        shapex = new float[20];
        shapey = new float[20];
        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();
        float radians = positionPart.getRadians();


        double angleStep = Math.PI * 2 / numberOfNodes;
        Polygon shape = new Polygon();




//        entity.setRadius(20);

//        shapex[0] = (float) (x + Math.cos(radians) * 12);
//        shapey[0] = (float) (y + Math.sin(radians) * 8);
//
//        shapex[1] = (float) (x + Math.cos(radians - 4 * 3.1415f / 5) * 8);
//        shapey[1] = (float) (y + Math.sin(radians - 4 * 3.1145f / 5) * 8);
//
//        shapex[2] = (float) (x + Math.cos(radians + 3.1415f) * 5);
//        shapey[2] = (float) (y + Math.sin(radians + 3.1415f) * 5);
//
//        shapex[3] = (float) (x + Math.cos(radians + 4 * 3.1415f / 5) * 8);
//        shapey[3] = (float) (y + Math.sin(radians + 4 * 3.1415f / 5) * 8);

        for(int i = 0; i < numberOfNodes; ++i) {
            double targetAngle = angleStep * i; // This is the angle we want if all parts are equally spaced
            double angle = targetAngle + (rnd.nextDouble() - 0.5) * angleStep * 0.25; // add a random factor to the angle, which is +- 25% of the angle step
            double radius = minRadius + rnd.nextDouble() * (maxRadius - minRadius); // make the radius random but within minRadius to maxRadius
            // calculate x and y positions of the part point
            float x1 = (float) (Math.cos(angle) * radius);
            float y1 = (float) (Math.sin(angle) * radius);
//            shape.addPoint((int)x, (int)y);
            shapex[i] = x + x1;
            shapey[i] = y + y1;
        }

        entity.setShapeX(shapex);
        entity.setShapeY(shapey);
        asteroids.add(entity);
    }
}
