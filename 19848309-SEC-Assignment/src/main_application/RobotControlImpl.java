package main_application;

import Controllers.RobotControl;
import sample.Main;

public class RobotControlImpl implements RobotControl {

    private RobotInfo ri;
    public boolean north;
    public boolean south;
    public boolean west;
    public boolean east;
    public boolean isFire;

    public RobotControlImpl() {
    }

    public RobotInfo getRobot(){
        return this.ri;
    }

    public void setRobot(RobotInfo r)
    {
        this.ri = r;
    }

    public RobotInfo[] getAllRobots(){

        return Main.rbArray;

    }

    public boolean moveNorth(){
        this.north = true;
        if (ri.getY() > 0) {
            ri.setY(ri.getY() - 1);
            this.north = true;
        }
        else {
            this.north = false;
        }
        return this.north;
    }

    public boolean moveEast(){
        this.east = true;
        if (ri.getX() < 11) {
            ri.setX(ri.getX() + 1);
            this.east = true;
        }
        else {
            this.east = false;
        }
        return this.east;
    }

    public  boolean moveSouth(){
        this.south = true;
        if (ri.getY() < 7) {
            ri.setY(ri.getY() + 1);
            this.south = true;
        }
        else {
            this.south = false;
        }
        return this.south;
    }

    public boolean moveWest(){
        this.west = true;
        if (ri.getX() > 0) {
            ri.setX(ri.getX() - 1);
            this.west = true;
        }
        else {
            this.west = false;
        }
        return this.west;
    }

    public boolean fire(int x, int y) {
        if ((Math.abs(ri.getX() - x) <= 1) && (Math.abs(ri.getY() - y) <= 1)) {
            this.isFire = true;
            System.out.println(this.isFire);
        }
        else {
            this.isFire = false;
        }
        return isFire;
    }
}
