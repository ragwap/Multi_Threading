package ai_implementations;

import javafx.application.Platform;
import main_application.RobotAI;
import main_application.RobotControlImpl;
import main_application.RobotInfo;
import sample.Main;

import java.lang.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

public class AIImplementationRobA implements RobotAI {
    public float opponentHealth;
//    int noEntries = 10;
    LinkedBlockingQueue bq = new LinkedBlockingQueue();
    @Override
    public void runAI(RobotControlImpl rc) {
        String direction = "south";
        RobotInfo myRobotA;

        while (true)
        {
            try {

                myRobotA = rc.getRobot();

                for (RobotInfo robot :  rc.getAllRobots()) {
                    if ((!robot.getName().equals(myRobotA.getName())) && (Math.abs(myRobotA.getX() - robot.getX()) <= 2) && (Math.abs(myRobotA.getY() - robot.getY()) <= 2))
                    {
//                        if (bq.size() == noEntries) {
//                            Thread.sleep(2000);
//                        }
//                        else {
                            bq.put(robot.getX() + " : " + robot.getY());
                            if((rc.fire(robot.getX(), robot.getY())) == true)
                            {
                                Platform.runLater(() -> {
                                    Main.arena.Shoot();
                                    Main.logger.appendText("Robot : A shoots Robot B\n");
                                });
                                break;
                            }
                            bq.remove();
//                        }

                    }
                    Platform.runLater(() -> {
                        Main.arena.UpdateArena();
                        Main.logger.appendText("Robot : " + robot.getName() + "\n");
                        Main.logger.appendText("Moved to : " + robot.getX() + " , " + robot.getY() + "\n\n");
                    });
//                    bq.remove();
                }

                switch (direction) {
                    case "north":
                        if (!rc.moveNorth()) {
                            direction = "east";
                        }
                        break;
                    case "east" :
                        if (!rc.moveEast()) {
                            direction = "south";
                        }
                        break;
                    case "south" :
                        if (!rc.moveSouth()) {
                            direction = "west";
                        }
                        break;
                    case "west" :
                        if (!rc.moveWest()) {
                            direction = "north";
                        }
                        break;
                }

                Thread.sleep(1000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public float getOpponentHealth() {
        return opponentHealth;
    }

    public void setOpponentHealth(float opponentHealth) {
        this.opponentHealth = opponentHealth;
    }
}
