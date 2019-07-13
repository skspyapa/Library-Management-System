package main;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.logging.*;

public class AppInitializer extends Application {
public static Logger logger;
//public static Parent root;
    public static void main(String[] args) throws IOException {
        Logger logger1=Logger.getLogger("");
        FileHandler fileHandler = new FileHandler("error.log", true);
        //fileHandler.setLevel(Level.INFO);
        fileHandler.setFormatter(new SimpleFormatter());
        Logger.getLogger("").addHandler(fileHandler);
        Handler[] handlers = Logger.getLogger("").getHandlers();
         System.out.println( handlers[0].getLevel());
       System.out.println( handlers[1].getLevel());
        System.out.println(logger1.getLevel());
//        for (Handler handler:handlers) {
//            System.out.println(handler);
//        }

        logger = Logger.getLogger("lk.ac.mrt.library.controller");
        logger.setUseParentHandlers(false);
        //logger.setLevel(Level.INFO);
        FileHandler fileHandler1=new FileHandler("resourse.log",true);
        fileHandler1.setFormatter(new SimpleFormatter());
        logger.addHandler(fileHandler1);
        logger.addHandler(new ConsoleHandler());
        Handler[] handlers1 = logger.getHandlers();
        System.out.println(handlers1[0].getLevel());
        System.out.println(handlers1[1].getLevel());
        System.out.println("logger logLevel="+logger.getLevel());
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = (Parent) FXMLLoader.load(this.getClass().getResource("/view/LoginForm.fxml"));
        Scene mainScene = new Scene(root);
        primaryStage.setScene(mainScene);
        primaryStage.centerOnScreen();
        primaryStage.setTitle("Login Form");
        primaryStage.show();
        TranslateTransition tt1 = new TranslateTransition(Duration.millis(700), root.lookup("AnchorPane"));
        tt1.setToX(0);
        tt1.setFromX(-mainScene.getWidth());
        tt1.play();

    }

}
