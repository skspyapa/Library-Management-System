package ControllerLibrary;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import dbLibrary.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import main.AppInitializer;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;

public class AdminDashboardController {
    public JFXButton btnBookStock;
    public JFXButton btnBorrowedBook;
    public JFXButton btnAllReport;
    public Label lblFine;
    public ImageView lblBack;
    public JFXButton btnAllBooksReport;
    public JFXButton btnDepartDetails;
    public JFXButton btnCateDetails;
    public JFXTextField txtFineEnter;

    public void initialize(){

        lblFine.setText(Integer.toString(DashBoardController.dayRate));
}
    public void lblBack_Click(MouseEvent mouseEvent) throws IOException {
        Parent root = (Parent) FXMLLoader.load(this.getClass().getResource("/view/DashBoard.fxml"));
        Scene newScene = new Scene(root);
        Stage stage = (Stage) lblBack.getScene().getWindow();
        stage.setScene(newScene);
        stage.centerOnScreen();
        stage.setTitle("Department Form");
        stage.show();
    }

    public void btnBorrowedBook_Action(ActionEvent actionEvent) {
        try {
            JasperReport jasperReport  = (JasperReport) JRLoader.loadObject(this.getClass().getResource("/Reports/CurrentBorrowedBooks.jasper"));
            HashMap<String, Object> objectObjectHashMap = new HashMap<>();
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, objectObjectHashMap, DBConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    public void btnAllBooksReport_Action(ActionEvent actionEvent) {
    }

    public void btnBookStock_Action(ActionEvent actionEvent) {
        try {
            JasperReport jasperReport  = (JasperReport) JRLoader.loadObject(this.getClass().getResource("/Reports/BookStockReport.jasper"));
            HashMap<String, Object> objectObjectHashMap = new HashMap<>();
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, objectObjectHashMap, DBConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    public void btnDepartDetails_Action(ActionEvent actionEvent) throws IOException {
        Parent root = (Parent) FXMLLoader.load(this.getClass().getResource("/view/Department.fxml"));
        Scene newScene = new Scene(root);
        Stage stage = (Stage) lblBack.getScene().getWindow();
        stage.setScene(newScene);
        stage.centerOnScreen();
        stage.setTitle("Department Form");
        stage.show();
    }

    public void btnCateDetails_Action(ActionEvent actionEvent) throws IOException {
        Parent root = (Parent) FXMLLoader.load(this.getClass().getResource("/view/Category.fxml"));
        Scene newScene = new Scene(root);
        Stage stage = (Stage) lblBack.getScene().getWindow();
        stage.setScene(newScene);
        stage.centerOnScreen();
        stage.setTitle("Category Form");
        stage.show();

    }

    public void txtFineEnter_Action(ActionEvent actionEvent) {
        try {
            FileOutputStream fileOutput = new FileOutputStream(DashBoardController.file);
            DashBoardController.dbprop.setProperty("dayrate",txtFineEnter.getText());
            DashBoardController.dbprop.store(fileOutput,null);
            fileOutput.close();
            DashBoardController.dbprop.load(new FileInputStream(DashBoardController.file));
            lblFine.setText(DashBoardController.dbprop.getProperty("dayrate"));

        } catch (FileNotFoundException e) {
            //Logger.getLogger("lk.ac.mrt.library.controller").log(Level.WARNING, null,e);
            AppInitializer.logger.log(Level.SEVERE,null,e);
        } catch (IOException e) {
        //    Logger.getLogger("lk.ac.mrt.library.controller").log(Level.WARNING, null,e);
            AppInitializer.logger.log(Level.SEVERE,null,e);
        }finally {
            txtFineEnter.clear();
        }




    }
}
