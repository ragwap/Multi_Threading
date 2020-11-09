package sample;

import javafx.scene.canvas.*;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import main_application.RobotControlImpl;


/**
 * A JavaFX GUI element that displays a grid on which you can draw images, text and lines.
 */
public class JFXArena extends Pane
{
    // Represents the image to draw. You can modify this to introduce multiple images.
    private static final String IMAGE_FILE = "1554047213.png";
    private Image robot1;

    private static final String IMAGE_FILE2 = "droid2.png";
    private Image robot2;

    private RobotControlImpl rc = new RobotControlImpl();
    // The following values are arbitrary, and you may need to modify them according to the
    // requirements of your application.
    private int gridWidth = 12;
    private int gridHeight = 8;

    private int robot1X = Main.robotInfoA.getX();
    private int robot1Y = Main.robotInfoA.getY();

    private int robot2X = Main.robotInfoB.getX();
    private int robot2Y = Main.robotInfoB.getY();

    private double gridSquareSize; // Auto-calculated
    private Canvas canvas; // Used to provide a 'drawing surface'.

    int finalRoboX, finalRoboY, finalRobo2X, finalRobo2Y;

    /**
     * Creates a new arena object, loading the robot image and initialising a drawing surface.
     */
    public JFXArena()
    {
        // Here's how you get an Image object from an image file (which you provide in the
        // 'resources/' directory.
        robot1 = new Image(getClass().getClassLoader().getResourceAsStream(IMAGE_FILE));
        robot2 = new Image(getClass().getClassLoader().getResourceAsStream(IMAGE_FILE2));

        // You will get an exception here if the specified image file cannot be found.

        canvas = new Canvas();
        canvas.widthProperty().bind(widthProperty());
        canvas.heightProperty().bind(heightProperty());
        getChildren().add(canvas);
    }


    /**
     * Moves a robot image to a new grid position.
     *
     * You will probably need to significantly modify this method. Currently it just serves as a
     * demonstration.
     */
    public void setRobotPosition(int x, int y)
    {

    }



    private GraphicsContext g1;
    /**
     * This method is called in order to redraw the screen, either because the user is manipulating
     * the window, OR because you've called 'requestLayout()'.
     *
     * You will need to modify the last part of this method; specifically the sequence of calls to
     * the other 'draw...()' methods. You shouldn't need to modify anything else about it.
     */
    @Override
    public void layoutChildren()
    {
        super.layoutChildren();
        GraphicsContext gfx = canvas.getGraphicsContext2D();
        gfx.clearRect(0.0, 0.0, canvas.getWidth(), canvas.getHeight());

        g1 = gfx;
        // First, calculate how big each grid cell should be, in pixels. (We do need to do this
        // every time we repaint the arena, because the size can change.)
        gridSquareSize = Math.min(
                getWidth() / (double) gridWidth,
                getHeight() / (double) gridHeight);

        double arenaPixelWidth = gridWidth * gridSquareSize;
        double arenaPixelHeight = gridHeight * gridSquareSize;


        // Draw the arena grid lines. This may help for debugging purposes, and just generally
        // to see what's going on.
        gfx.setStroke(Color.DARKGREY);
        gfx.strokeRect(0.0, 0.0, arenaPixelWidth - 1.0, arenaPixelHeight - 1.0); // Outer edge

        for(int gridX = 1; gridX < gridWidth; gridX++) // Internal vertical grid lines
        {
            double x = (double) gridX * gridSquareSize;
            gfx.strokeLine(x, 0.0, x, arenaPixelHeight);
        }

        for(int gridY = 1; gridY < gridHeight; gridY++) // Internal horizontal grid lines
        {
            double y = (double) gridY * gridSquareSize;
            gfx.strokeLine(0.0, y, arenaPixelWidth, y);
        }

        // Invoke helper methods to draw things at the current location.
        // ** You will need to adapt this to the requirements of your application. **
        drawImage(gfx, robot1, Main.robotInfoA.getX(), Main.robotInfoA.getY());
        drawLabel(gfx, Main.robotInfoA.getName() + " " + Main.robotInfoA.getHealth() + "%", Main.robotInfoA.getX(), Main.robotInfoA.getY());
//        drawLine(gfx, robot1X, robot1Y, robot1X + 1.0, robot1Y - 2.0);

        drawImage(gfx, robot2, Main.robotInfoB.getX(), Main.robotInfoB.getY());
        drawLabel(gfx, Main.robotInfoB.getName() + " " + Main.robotInfoB.getHealth() + "%", Main.robotInfoB.getX(), Main.robotInfoB.getY());

//        drawLine(gfx,Main.robotInfoB.getX(), Main.robotInfoB.getY(), Main.robotInfoB.getX() - 1, Main.robotInfoB.getY() - 0);
    }

    /** Each AI calls this method to Update the arena when the robots move */
    public void UpdateArena()
    {
        GraphicsContext gfx = canvas.getGraphicsContext2D();
        gfx.clearRect(0.0, 0.0, canvas.getWidth(), canvas.getHeight());

        drawImage(gfx, robot1, Main.robotInfoA.getX(), Main.robotInfoA.getY());
        drawLabel(gfx, Main.robotInfoA.getName() + " " + Main.robotInfoA.getHealth() + "%", Main.robotInfoA.getX(), Main.robotInfoA.getY());

        drawImage(gfx, robot2, Main.robotInfoB.getX(), Main.robotInfoB.getY());
        drawLabel(gfx, Main.robotInfoB.getName() + " " + Main.robotInfoB.getHealth() + "%", Main.robotInfoB.getX(), Main.robotInfoB.getY());

        layoutChildren();
    }

    /** This method is used by the AIs to fire shots on the opponent.
     * Note that this does not detect whether the shot is struck on anything in the arena
     * Will be triggered only when the robots are just one cell away from each other */
    public void Shoot() {
        GraphicsContext gfx = canvas.getGraphicsContext2D();
        UpdateArena();
        if (((Main.robotInfoA.getY() - Main.robotInfoB.getY()) == 0) || ((Main.robotInfoB.getY() - Main.robotInfoA.getY()) == 0)) {
            if (((Main.robotInfoA.getX() - Main.robotInfoB.getX()) == -1)) {
                drawLine(gfx, Main.robotInfoA.getX(), Main.robotInfoA.getY(), Main.robotInfoA.getX() + 1, Main.robotInfoA.getY());
                drawLine(gfx, Main.robotInfoB.getX(), Main.robotInfoB.getY(), Main.robotInfoB.getX() - 1, Main.robotInfoB.getY());
            }
            else if ((Main.robotInfoA.getX() - Main.robotInfoB.getX()) == 1) {
                drawLine(gfx, Main.robotInfoA.getX(), Main.robotInfoA.getY(), Main.robotInfoA.getX() - 1, Main.robotInfoA.getY());
                drawLine(gfx, Main.robotInfoB.getX(), Main.robotInfoB.getY(), Main.robotInfoB.getX() + 1, Main.robotInfoB.getY());
            }
        }
        if (((Main.robotInfoA.getX() - Main.robotInfoB.getX()) == 0) || ((Main.robotInfoB.getX() - Main.robotInfoA.getX()) == 0)) {
            if ((Main.robotInfoA.getY() - Main.robotInfoB.getY()) == -1) {
                drawLine(gfx, Main.robotInfoA.getX(), Main.robotInfoA.getY(), Main.robotInfoA.getX(), Main.robotInfoA.getY() + 1);
                drawLine(gfx, Main.robotInfoB.getX(), Main.robotInfoB.getY(), Main.robotInfoB.getX(), Main.robotInfoB.getY() - 1);
            }
            else if ((Main.robotInfoA.getY() - Main.robotInfoB.getY()) == 1) {
                drawLine(gfx, Main.robotInfoA.getX(), Main.robotInfoA.getY(), Main.robotInfoA.getX(), Main.robotInfoA.getY() - 1);
                drawLine(gfx, Main.robotInfoB.getX(), Main.robotInfoB.getY(), Main.robotInfoB.getX(), Main.robotInfoB.getY() + 1);
            }
        }
    }

    /**
     * Draw an image in a specific grid location. *Only* call this from within layoutChildren().
     *
     * Note that the grid location can be fractional, so that (for instance), you can draw an image
     * at location (3.5,4), and it will appear on the boundary between grid cells (3,4) and (4,4).
     *
     * You shouldn't need to modify this method.
     */
    private void drawImage(GraphicsContext gfx, Image image, double gridX, double gridY)
    {
        // Get the pixel coordinates representing the centre of where the image is to be drawn.
        double x = (gridX + 0.5) * gridSquareSize;
        double y = (gridY + 0.5) * gridSquareSize;

        // We also need to know how "big" to make the image. The image file has a natural width
        // and height, but that's not necessarily the size we want to draw it on the screen. We
        // do, however, want to preserve its aspect ratio.
        double fullSizePixelWidth = robot1.getWidth();
        double fullSizePixelHeight = robot1.getHeight();

        double displayedPixelWidth, displayedPixelHeight;
        if(fullSizePixelWidth > fullSizePixelHeight)
        {
            // Here, the image is wider than it is high, so we'll display it such that it's as
            // wide as a full grid cell, and the height will be set to preserve the aspect
            // ratio.
            displayedPixelWidth = gridSquareSize;
            displayedPixelHeight = gridSquareSize * fullSizePixelHeight / fullSizePixelWidth;
        }
        else
        {
            // Otherwise, it's the other way around -- full height, and width is set to
            // preserve the aspect ratio.
            displayedPixelHeight = gridSquareSize;
            displayedPixelWidth = gridSquareSize * fullSizePixelWidth / fullSizePixelHeight;
        }

        // Actually put the image on the screen.
        gfx.drawImage(image,
                x - displayedPixelWidth / 2.0,  // Top-left pixel coordinates.
                y - displayedPixelHeight / 2.0,
                displayedPixelWidth,              // Size of displayed image.
                displayedPixelHeight);
    }


    /**
     * Displays a string of text underneath a specific grid location. *Only* call this from within
     * layoutChildren().
     *
     * You shouldn't need to modify this method.
     */
    private void drawLabel(GraphicsContext gfx, String label, double gridX, double gridY)
    {
        gfx.setTextAlign(TextAlignment.CENTER);
        gfx.setTextBaseline(VPos.TOP);
        gfx.setStroke(Color.BLUE);
        gfx.strokeText(label, (gridX + 0.5) * gridSquareSize, (gridY + 1.0) * gridSquareSize);
    }

    /**
     * Draws a (slightly clipped) line between two grid coordinates.
     *
     * You shouldn't need to modify this method.
     */
    private void drawLine(GraphicsContext gfx, double gridX1, double gridY1,
                          double gridX2, double gridY2)
    {
        gfx.setStroke(Color.RED);

        // Recalculate the starting coordinate to be one unit closer to the destination, so that it
        // doesn't overlap with any image appearing in the starting grid cell.
        final double radius = 0.5;
        double angle = Math.atan2(gridY2 - gridY1, gridX2 - gridX1);
        double clippedGridX1 = gridX1 + Math.cos(angle) * radius;
        double clippedGridY1 = gridY1 + Math.sin(angle) * radius;

        gfx.strokeLine((clippedGridX1 + 0.5) * gridSquareSize,
                (clippedGridY1 + 0.5) * gridSquareSize,
                (gridX2 + 0.5) * gridSquareSize,
                (gridY2 + 0.5) * gridSquareSize);
    }
}
