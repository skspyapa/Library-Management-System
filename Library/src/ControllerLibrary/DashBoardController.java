package ControllerLibrary;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import main.AppInitializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;

public class DashBoardController {
    public static int dayRate;
    public ImageView imgAdmin;
    public ImageView imgManageBooks;
    public ImageView imgManageStudents;
    public ImageView imgReturn;
    public ImageView imgBorrow;
    public static Properties dbprop;
    public static File file;
    public static FileInputStream is;
    public JFXButton btnLogout;
    public Label lblAdmin;
    public Label lblManageBooks;
    public Label lblManageStudents;
    public Label lblBorrow;

    public void initialize() {
        try {
            file = new File("resources/application.properties");
            is = new FileInputStream(file);
            dbprop = new Properties();
            dbprop.load(is);
            dayRate = Integer.parseInt(dbprop.getProperty("dayrate"));
            is.close();
        } catch (FileNotFoundException e) {
            //Logger.getLogger("lk.ac.mrt.library.controller").log(Level.WARNING, null,e);
            AppInitializer.logger.log(Level.SEVERE,null,e);
        } catch (IOException e) {
            //Logger.getLogger("lk.ac.mrt.library.controller").log(Level.WARNING, null,e);
            AppInitializer.logger.log(Level.SEVERE,null,e);
        }
    }

    public void imgAdmin_Clicked(MouseEvent mouseEvent) throws IOException {
       if(LoginFormController.login.equals("admin_username")) {
           Parent root = (Parent) FXMLLoader.load(this.getClass().getResource("/view/AdminDashboard.fxml"));
           Scene scene = new Scene(root);
           Stage stage = (Stage) imgAdmin.getScene().getWindow();
           stage.setScene(scene);
           stage.centerOnScreen();
           stage.setTitle("Admin Dashboard");
           stage.show();
       }else{
           new Alert(Alert.AlertType.INFORMATION,"You Are Not The Super User", ButtonType.OK).show();
       }
    }

    public void imgManageBooks_Clicked(MouseEvent mouseEvent) throws IOException {
        Parent root = (Parent) FXMLLoader.load(this.getClass().getResource("/view/BookManager.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) imgManageBooks.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Book Manager");
        stage.show();

    }

    public void imgManageStudents_Clicked(MouseEvent mouseEvent) throws IOException {
        Parent root = (Parent) FXMLLoader.load(this.getClass().getResource("/view/Student.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) imgManageStudents.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Student Form");
        stage.show();
    }

    public void imgReturn_Clicked(MouseEvent mouseEvent) throws IOException {
        Parent root = (Parent) FXMLLoader.load(this.getClass().getResource("/view/Return.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) imgReturn.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Return Form");
        stage.show();
    }

    public void imgBorrow_Clicked(MouseEvent mouseEvent) throws IOException {
        Parent root = (Parent) FXMLLoader.load(this.getClass().getResource("/view/Borrow.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) imgBorrow.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Borrow Form");
        stage.show();
    }

    public void btnLogout(ActionEvent actionEvent) throws IOException {
        Parent root = (Parent) FXMLLoader.load(this.getClass().getResource("/view/LoginForm.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage)btnLogout.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Login Form");
        stage.show();
    }
}
