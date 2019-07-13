package ControllerLibrary;

import business.BOFactory;
import business.BOTypes;
import business.QueryType;
import business.custom.BooksBO;
import business.custom.BorrowBO;
import business.custom.StudentsBO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import dto.BooksDTO;
import dto.BorrowDTO;
import dto.QueryDTO;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import utilTM.Custom;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BorrowController {
    public JFXTextField txtStudentID;
    public JFXTextField txtBookId;
    public JFXTextField txtDate;
    public TableView<Custom> tblBorrow;
    public JFXButton btnAdd;
    public JFXTextField txtBorrowId;
    public JFXTextField txtBookName;
    public JFXTextField txtStudentName;
    public ImageView lblBack;
    public Label lblNewBorrow;
    LocalDate localDate;
    private BorrowBO borrowBO = BOFactory.getInstance().getBO(BOTypes.BORROW);
    private BooksBO booksBO = BOFactory.getInstance().getBO(BOTypes.BOOKS);
    private StudentsBO studentsBO = BOFactory.getInstance().getBO(BOTypes.STUDENTS);
    private ObservableList<Custom> detailList;
    private List<QueryDTO> queryDTOS;

    public void initialize() {
        tblBorrow.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("bookId"));
        tblBorrow.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblBorrow.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("borrowDate"));
        tblBorrow.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        detailList = tblBorrow.getItems();
        txtBorrowId.setEditable(false);
        txtDate.setEditable(false);
        localDate = LocalDate.now();
        txtDate.setText(localDate.toString());
        txtBookId.requestFocus();

        try {
            txtBorrowId.setText("BID" + Integer.toString(generateBorrowId()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        tblBorrow.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Custom>() {
            @Override
            public void changed(ObservableValue<? extends Custom> observable, Custom oldValue, Custom newValue) {
                try {
                    if (newValue != null) {
                        List<QueryDTO> borrowDetails = borrowBO.getBorrowDetails(borrowBO.get(Integer.parseInt(newValue.getBorrowId().substring(3))).getStudentId());

                        for (QueryDTO queryDTO : borrowDetails) {
                            if (queryDTO.getBorrowDate().equals(localDate) && ((Integer.parseInt(newValue.getBookId().substring(1))) == (queryDTO.getBookId()))) {
                                txtBorrowId.setText("BID" + queryDTO.getBookId());
                                txtDate.setText(localDate.toString());
                                txtBookId.setText("B" + queryDTO.getBookId());
                                txtBookName.setText(booksBO.get(queryDTO.getBookId()).getName());
                                int stuId = borrowBO.get(queryDTO.getBorrowId()).getStudentId();
                                txtStudentID.setText("S" + Integer.toString(stuId));
                                txtStudentName.setText(studentsBO.getStudentName(stuId));
                                btnAdd.setText("Update");
                            } else {
                                resetField();
                            }
                        }
                    }
                } catch (SQLException e) {
                    Logger.getLogger("lk.ac.mrt.library").log(Level.SEVERE, null,e);
                }
            }
        });
    }

    public int generateBorrowId() throws SQLException {
        return borrowBO.getMaximumBorrowId();
    }

    public void txtBookId_Action(ActionEvent actionEvent) {
        //String txtBookId = this.txtBookId.getText();
        if (bookIdRegEx()) {
            try {
                BooksDTO booksDTO = booksBO.get(Integer.parseInt(txtBookId.getText().substring(1)));
                if (booksDTO.getQty() > 0) {
                    txtBookName.setText(booksDTO.getName());
                    txtStudentID.requestFocus();
                } else {
                    new Alert(Alert.AlertType.INFORMATION, "Books Unavailable", ButtonType.OK).show();
                    txtBookId.clear();
                }
            } catch (SQLException e) {
                Logger.getLogger("lk.ac.mrt.library").log(Level.SEVERE, null,e);
            }
        } else {
            new Alert(Alert.AlertType.INFORMATION, "Incorrect Book ID ").show();
        }
    }

    public void txtStudentID_Action(ActionEvent actionEvent) {
        //String txtStudentId = txtStudentID.getText();
        if (studentIdRegEx()) {

            btnAdd.requestFocus();
            if (!tblBorrow.getItems().isEmpty()) {
                tblBorrow.getItems().clear();
                tblBorrow.refresh();
            }
            try {
                txtStudentName.setText(studentsBO.getStudentName(Integer.parseInt(txtStudentID.getText().substring(1))));
                List<QueryDTO> borrowDetails = borrowBO.getBorrowDetails(Integer.parseInt(txtStudentID.getText().substring(1)));
                for (QueryDTO queryDTO : borrowDetails) {
                    if (txtBookId.getLength() > 0) {
                        if ((localDate.equals(queryDTO.getBorrowDate())) && (Integer.parseInt(txtBookId.getText().substring(1)) == queryDTO.getBookId())) {
                            new Alert(Alert.AlertType.INFORMATION, "Can't Issue Same Book In Same Day", ButtonType.OK).show();
                            resetField();
                            return;
                        }
                    }
                    detailList.add(new Custom("BID" + queryDTO.getBorrowId(), "B" + queryDTO.getBookId(), queryDTO.getName(), queryDTO.getBorrowDate(), queryDTO.getDueDate()));
                }
            } catch (SQLException e) {
                Logger.getLogger("lk.ac.mrt.library").log(Level.SEVERE, null,e);
            }
        } else {
            new Alert(Alert.AlertType.INFORMATION, "Incorrect Student ID ").show();
        }
    }

    public void txtDate_Action(ActionEvent actionEvent) {
        txtBookId.requestFocus();
    }

    public void txtBorrowId_Action(ActionEvent actionEvent) {
        txtDate.requestFocus();
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

    public void btnAdd_Action(ActionEvent actionEvent) {
        if (bookIdRegEx() || studentIdRegEx()) {
            if (btnAdd.getText().equals("Update")) {
                BorrowDTO saveBorrowDTO = new BorrowDTO(Integer.parseInt(txtBorrowId.getText().substring(3)), localDate, Integer.parseInt(txtStudentID.getText().substring(1)), Integer.parseInt(txtBookId.getText().substring(1)));

                try {
                    boolean updated = borrowBO.placeBorrow(QueryType.UPDATE, saveBorrowDTO);
                    if (updated) {
                        new Alert(Alert.AlertType.INFORMATION, "Updated Successfully", ButtonType.OK).show();
                    }
                } catch (Exception e) {
                    Logger.getLogger("lk.ac.mrt.library").log(Level.SEVERE, null,e);
                }
            }
            if (btnAdd.getText().equals("Add")) {
                //transaction needed **two table**
                BorrowDTO saveBorrowDTO = new BorrowDTO(Integer.parseInt(txtBorrowId.getText().substring(3)), localDate, Integer.parseInt(txtStudentID.getText().substring(1)), Integer.parseInt(txtBookId.getText().substring(1)));
                try {
                    boolean updated = borrowBO.placeBorrow(QueryType.SAVE, saveBorrowDTO);
                    if (updated) {
                        new Alert(Alert.AlertType.INFORMATION, "Updated Successfully", ButtonType.OK).show();
                    }
                } catch (Exception e) {
                    Logger.getLogger("lk.ac.mrt.library").log(Level.SEVERE, null,e);
                }
            }
            //add data to the table
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date = LocalDate.parse(txtDate.getText(), dateTimeFormatter);
            detailList.add(new Custom(txtBookId.getText(), txtBookName.getText(), date));
            resetField();
        } else {
            new Alert(Alert.AlertType.INFORMATION, "Please Fill All The Details", ButtonType.OK).show();
        }
    }

    public void resetField() {
        try {
            txtBorrowId.setText("BID" + Integer.toString(generateBorrowId()));
        } catch (SQLException e) {
            Logger.getLogger("lk.ac.mrt.library").log(Level.SEVERE, null,e);
        }
        if (btnAdd.getText().equals("Update")) {
            btnAdd.setText("Add");
        }
        txtBookId.clear();
        txtBookName.clear();
        txtStudentID.clear();
        txtStudentName.clear();
    }

    private boolean bookIdRegEx() {
        String txtBookId = this.txtBookId.getText();
        return txtBookId.matches("^B\\d{1,10}") ? true : false;
    }

    private boolean studentIdRegEx() {
        String txtStudentId = txtStudentID.getText();
        return txtStudentId.matches("^S\\d{1,10}") ? true : false;
    }

    public void lblNewBorrow_Click(MouseEvent mouseEvent) {
        resetField();
    }
}
