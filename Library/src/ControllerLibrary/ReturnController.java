package ControllerLibrary;

import business.BOFactory;
import business.BOTypes;
import business.custom.BookBroughtBO;
import business.custom.BorrowBO;
import com.jfoenix.controls.JFXTextField;
import dto.BookBroughtDTO;
import dto.FeeDTO;
import dto.QueryDTO;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import utilTM.Custom;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.time.temporal.ChronoUnit.DAYS;

public class ReturnController {
    public static long between;
    public static String borrowId;
    public ImageView lblBack;
    public JFXTextField txtStudentID;
    public JFXTextField txtDate;
    public TableView<Custom> tblReturn;
    public JFXTextField txtBorrowID;
    public JFXTextField txtBookID;
    private BorrowBO borrowBO = BOFactory.getInstance().getBO(BOTypes.BORROW);
    private BookBroughtBO returnBO = BOFactory.getInstance().getBO(BOTypes.BOOK_BROUGHT);
    private ObservableList<Custom> tblDetails;
    private LocalDate date;

    public void initialize() {
        txtDate.setEditable(false);
        txtStudentID.requestFocus();
        date = LocalDate.now();
        txtDate.setText(date.toString());
        tblReturn.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("borrowId"));
        tblReturn.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("bookId"));
        tblReturn.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblReturn.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("borrowDate"));
        tblReturn.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        tblReturn.getColumns().add(new TableColumn<Custom, Button>());
        tblReturn.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("btnReturn"));
        tblDetails = tblReturn.getItems();
        txtStudentID.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                String txtStuID = txtStudentID.getText();
                if (!txtStuID.matches("[S]\\d*") && txtStudentID.getLength() > 0) {
                    new Alert(Alert.AlertType.INFORMATION, "Incorrect Student ID").show();
                } else {
                    try {
                        if (txtStudentID.getLength() > 1) {
                            if (!tblReturn.getItems().isEmpty()) {
                                tblReturn.getItems().clear();
                            }
                            int id = Integer.parseInt(txtStudentID.getText().substring(1));
                            List<QueryDTO> borrowDetails = borrowBO.getBorrowDetails(id);
                            for (QueryDTO queryDTO : borrowDetails) {
                                Custom custom = new Custom("BID" + queryDTO.getBorrowId(), "B" + queryDTO.getBookId(), queryDTO.getName(), queryDTO.getBorrowDate(), queryDTO.getDueDate());
                                tblDetails.add(custom);
                                //custom.setBtnReturn(new Button("Fee"));
                            }
                        } else {
                            if (txtStudentID.getLength() <= 1) {
                                tblReturn.getItems().clear();
                            }
                        }
                    } catch (SQLException e) {
                        Logger.getLogger("lk.ac.mrt.library").log(Level.SEVERE, null,e);
                    }
                }
            }
        });
        tblReturn.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Custom>() {
            @Override
            public void changed(ObservableValue<? extends Custom> observable, Custom oldValue, Custom newValue) {
                if (newValue != null) {
                    borrowId = newValue.getBorrowId();
                    txtBorrowID.setText(newValue.getBorrowId());
                    txtBookID.setText(newValue.getBookId());
                    LocalDate dueDate = tblReturn.getSelectionModel().getSelectedItem().getDueDate();
                    between = DAYS.between(date, dueDate);
                    between = -between;
                    if (between > 0) {
                        newValue.setBtnReturn(new Button("Fine"));
                        tblReturn.refresh();
                        newValue.getBtnReturn().setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                try {
                                    if (!newValue.getBtnReturn().getText().equals("Fine Paid")) {
                                        fineForm();
                                    } else {
                                        new Alert(Alert.AlertType.INFORMATION, "You Already Paid The Fine").show();
                                    }
                                    boolean consumed = FineController.consumed;
                                    if (consumed) {
                                        newValue.getBtnReturn().setText("Fine Paid");
                                    }
                                } catch (IOException e) {
                                    Logger.getLogger("lk.ac.mrt.library").log(Level.SEVERE, null,e);
                                }
                            }
                        });
                    }
                    if (!newValue.equals(oldValue)) {
                        if (oldValue != null) {
                            if (observable.getValue().getBtnReturn() == null) {
                                if (oldValue.getBtnReturn() != null) {
                                    oldValue.getBtnReturn().setVisible(false);
                                }
                            }
                        }
                    }
                }
            }
        });
    }

    public void lblBack_Clicked(MouseEvent mouseEvent) throws IOException {
        Parent root = (Parent) FXMLLoader.load(this.getClass().getResource("/view/DashBoard.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) lblBack.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("DashBoard Form");
        stage.show();
    }

    public void txtStudentID_Action(ActionEvent actionEvent) {
    }

    public void txtBorrowID_Action(ActionEvent actionEvent) {
    }

    public void txtBookID_Action(ActionEvent actionEvent) {
    }

    public void txtDate_Action(ActionEvent actionEvent) {
    }

    public void btnReturn_Action(ActionEvent actionEvent) {

        if ((tblReturn.getSelectionModel().getSelectedItem().getBtnReturn() == null) || ((tblReturn.getSelectionModel().getSelectedItem().getBtnReturn() != null) && (tblReturn.getSelectionModel().getSelectedItem().getBtnReturn().getText().equals("Fine Paid")))) {
            BookBroughtDTO bookBroughtDTO = new BookBroughtDTO(Integer.parseInt(txtBorrowID.getText().substring(3)), date);
            FeeDTO feeDTO = new FeeDTO(Integer.parseInt(txtBorrowID.getText().substring(3)), (between * DashBoardController.dayRate));
            try {
                boolean result = returnBO.returnBook(bookBroughtDTO, feeDTO);
                if (result) {
                    tblDetails.remove(tblReturn.getSelectionModel().getSelectedIndex());
                    tblReturn.refresh();
                    new Alert(Alert.AlertType.INFORMATION, "Updated Successfully", ButtonType.OK).show();
                }
            } catch (Exception e) {
                Logger.getLogger("lk.ac.mrt.library").log(Level.SEVERE, null,e);
            }
        } else if ((tblReturn.getSelectionModel().getSelectedItem().getBtnReturn() != null) && (tblReturn.getSelectionModel().getSelectedItem().getBtnReturn().getText().equals("Fine"))) {
            new Alert(Alert.AlertType.INFORMATION, "Can't Return The Book Without Paying The Fine").show();
        }
    }

    //    public void calculateFine(long between){
//        if(between<0){
//            between=-between;
//            System.out.println("Fine You Have To Pay :"+between*dayRate);
//            System.out.println(tblReturn.getSelectionModel().getSelectedIndex());
//        }
//    }
    public void fineForm() throws IOException {
        Parent root = (Parent) FXMLLoader.load(this.getClass().getResource("/view/FineForm.fxml"));
        Scene fineScene = new Scene(root);
        Stage newStage = new Stage();
        newStage.setScene(fineScene);
        newStage.centerOnScreen();
        newStage.centerOnScreen();
        newStage.setTitle("Fine Form");
        newStage.showAndWait();
    }

}
