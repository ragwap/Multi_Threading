package Controllers;

import main_application.RobotInfo;
import sample.Main;

public interface RobotControl {
    RobotInfo getRobot();
    void setRobot(RobotInfo r);
    RobotInfo[] getAllRobots();
    boolean moveNorth();
    boolean moveEast();
    boolean moveSouth();
    boolean moveWest();
    boolean fire(int x, int y);
}
