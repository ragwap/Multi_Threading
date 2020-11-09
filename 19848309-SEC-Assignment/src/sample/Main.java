package sample;

import ai_implementations.AIImplementationRobA;
import ai_implementations.AIImplementationRobB;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import main_application.RobotControlImpl;
import main_application.RobotInfo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main extends Application {

    //    private static Object monitor = new Object();
    public static RobotInfo robotInfoA = new RobotInfo();
    public static RobotInfo robotInfoB = new RobotInfo();

    public static RobotControlImpl robotControlA = new RobotControlImpl();
    public static RobotControlImpl robotControlB = new RobotControlImpl();

    public static RobotInfo[] rbArray = new RobotInfo[2];
    public static JFXArena arena = new JFXArena();
    public static TextArea logger = new TextArea();

    @Override
    public void start(Stage primaryStage) throws Exception{

        AIImplementationRobA robA = new AIImplementationRobA();
        AIImplementationRobB robB = new AIImplementationRobB();

        robotInfoA.setName("A");
        robotInfoA.setX(0);
        robotInfoA.setY(5);
        robotInfoA.setHealth(100);

        robotInfoB.setName("B");
        robotInfoB.setX(4);
        robotInfoB.setY(7);
        robotInfoB.setHealth(100);

        rbArray[0]=robotInfoA;
        rbArray[1]=robotInfoB;

        robotControlA.setRobot(robotInfoA);
        robotControlB.setRobot(robotInfoB);

        Thread threadA = new Thread(() -> {
            robA.runAI(robotControlA);
            notifyAll();
        });

        Thread threadB = new Thread(() -> {
            robB.runAI(robotControlB);
            notifyAll();
        });

        primaryStage.setTitle("Robot AI Test (JavaFX)");


        ToolBar toolbar = new ToolBar();
        Button btn1 = new Button("Start");
        Button btn2 = new Button("Stop");
        toolbar.getItems().addAll(btn1, btn2);

        ExecutorService exService = Executors.newFixedThreadPool(2);
        btn1.setOnAction((event) ->
        {
//            threadA.start();
//            threadB.start();

            exService.execute(threadA);
            exService.execute(threadB);
        });

        btn2.setOnAction((event) ->
        {
//            try {
//                Thread.sleep(100000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }

//            exService.shutdown();
        });

        logger.appendText("Welcome to Robofest\n");
//        logger.appendText("World\n");

        SplitPane splitPane = new SplitPane();
        splitPane.getItems().addAll(arena, logger);
        arena.setMinWidth(300.0);

        BorderPane contentPane = new BorderPane();
        contentPane.setTop(toolbar);
        contentPane.setCenter(splitPane);

        Scene scene = new Scene(contentPane, 800, 800);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
