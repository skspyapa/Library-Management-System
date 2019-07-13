package ControllerLibrary;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import main.AppInitializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;

public class LoginFormController {

    public JFXTextField txtUserName;
    public JFXButton btnLogin;
    public static String login;
    public JFXPasswordField txtPassword;
    public void btnLogin_Action(ActionEvent actionEvent) {

        try {
            File file = new File("resources/application.properties");
            FileInputStream is = new FileInputStream(file);
            Properties prop = new Properties();
            prop.load(is);
//            TranslateTransition tt1 = new TranslateTransition(Duration.millis(700), AppInitializer.root.lookup("AnchorPane"));
//            tt1.setToX(+(btnLogin.getScene()).getWidth());
//            tt1.setFromX(0);
//            tt1.play();

            if(prop.getProperty("admin_username").equals(txtUserName.getText())&&prop.getProperty("admin_password").equals(txtPassword.getText())){
                login="admin_username";
                Parent root = (Parent) FXMLLoader.load(this.getClass().getResource("/view/DashBoard.fxml"));
                Scene scene = new Scene(root);
                Stage stage = (Stage)btnLogin.getScene().getWindow();
                stage.setScene(scene);
                stage.centerOnScreen();
                stage.setTitle("Admin Dashboard");
                stage.show();
            }else if(prop.getProperty("username").equals(txtUserName.getText())&&prop.getProperty("password").equals(txtPassword.getText())){
                login="username";
                Parent root = (Parent) FXMLLoader.load(this.getClass().getResource("/view/DashBoard.fxml"));
                Scene scene = new Scene(root);
                Stage stage = (Stage)btnLogin.getScene().getWindow();
                stage.setScene(scene);
                stage.centerOnScreen();
                stage.setTitle("Admin Dashboard");
                stage.show();
            }else {
                new Alert(Alert.AlertType.INFORMATION,"Please Enter Correct Username Or Password").show();
            }

        } catch (FileNotFoundException e) {
            //Logger.getLogger("lk.ac.mrt.library.controller").log(Level.WARNING, null,e);
            AppInitializer.logger.log(Level.SEVERE,null,e);
        } catch (IOException e) {
            //Logger.getLogger("lk.ac.mrt.library.controller").log(Level.WARNING, null,e);
            AppInitializer.logger.log(Level.SEVERE,null,e);
        }

    }

    public void txtUserName_Action(ActionEvent actionEvent) {
       txtPassword.requestFocus();
    }

    public void txtPassword_Action(ActionEvent actionEvent) {
        btnLogin.requestFocus();
    }
}
