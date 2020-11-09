package ai_implementations;

import javafx.application.Platform;
import main_application.RobotAI;
import main_application.RobotControlImpl;
import main_application.RobotInfo;
import sample.Main;

import javax.swing.*;
import java.util.concurrent.LinkedBlockingQueue;

public class AIImplementationRobB implements RobotAI {
    LinkedBlockingQueue bq = new LinkedBlockingQueue(); /** Defines the blocking queue */
    int noRobots = 2; /** Number of robots is 2 since we have only 2 AI implementations */
    @Override
    public void runAI(RobotControlImpl rc) {
        String direction = "west";
        RobotInfo myRobotB;

        while (true)
        {
            if (noRobots == 2) {
                try {
                    myRobotB = rc.getRobot();
                    for (RobotInfo robot :  rc.getAllRobots()) {
                        if ((!robot.getName().equals(myRobotB.getName())) && (Math.abs(myRobotB.getX() - robot.getX()) <= 2) && (Math.abs(myRobotB.getY() - robot.getY()) <= 2))
                        {
                            bq.put(robot.getX() + " : " + robot.getY());
                            if((rc.fire(robot.getX(), robot.getY())) == true)
                            {
                                Platform.runLater(() -> {
                                    if ((robot.getHealth() <= 100) && (robot.getHealth() > 30)) {
                                        Main.arena.Shoot();
                                        robot.setHealth(robot.getHealth() - 35);
                                        Main.logger.appendText("Robot : B shoots Robot A\n");
                                    }
                                    else if (robot.getHealth() == 30) {
                                        Main.arena.Shoot();
                                        robot.setHealth(robot.getHealth() - 30);
                                        --noRobots;
                                        Main.logger.appendText("Robot : B shoots Robot A\n");
                                    }
                                    else if (robot.getHealth() == 0) {
                                        Main.logger.appendText("Robot : " + robot.getName() + " died\n");
                                        System.out.println("zero B : " + robot.getHealth());
                                    }
                                });
                                break;
                            }
                            bq.remove();

                        }
                        Platform.runLater(() -> {
                            Main.arena.UpdateArena();
                            Main.logger.appendText("Robot : " + robot.getName() + "\n");
                            Main.logger.appendText("Moved to : " + robot.getX() + " , " + robot.getY() + "\n\n");
                        });

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
            else {
                if (Main.rbArray[0].getHealth() == 0) {
                    JOptionPane.showMessageDialog(null, "Robot " + Main.rbArray[1].getName() + " is victorious");
                    Platform.runLater(() -> {
                        System.exit(0);
                    });
                }
                else if(Main.rbArray[1].getHealth() == 0) {
                    JOptionPane.showMessageDialog(null, "Robot " + Main.rbArray[0].getName() + " is victorious");
                    Platform.runLater(() -> {
                        System.exit(0);
                    });
                }
            }
        }
    }
}
