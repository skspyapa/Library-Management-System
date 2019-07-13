package ControllerLibrary;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class FineController {
    public static boolean consumed;
    public JFXTextField txtBorrowID;
    public JFXTextField txtFine;
    public JFXButton btnPay;

    public void initialize() {
        txtBorrowID.setText(ReturnController.borrowId);
        txtFine.setText(Integer.toString((int) (ReturnController.between * DashBoardController.dayRate)));
        txtBorrowID.setEditable(false);
        txtFine.setEditable(false);
        btnPay.requestFocus();
    }

    public void btnPay_Action(ActionEvent actionEvent) {
        Button button = (Button) actionEvent.getSource();
        consumed = true;
        Stage window = (Stage) btnPay.getScene().getWindow();
        window.close();
    }
}
