package ControllerLibrary;

import business.BOFactory;
import business.BOTypes;
import business.custom.StudentsBO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import dto.StudentsDTO;
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
import utilTM.Students;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StudentController {
    public ImageView lblBack;
    public JFXTextField txtStudentID;
    public JFXTextField txtName;
    public JFXTextField txtBirthday;
    public JFXTextField txtDepartment;
    public JFXTextField txtGender;
    public Label lblNewStudent;
    public JFXButton btnAdd;
    public TableView<Students> tblStudent;
    public ObservableList<Students> tblList;
    public Students selectedrow;
    private StudentsBO studentsBO = BOFactory.getInstance().getBO(BOTypes.STUDENTS);

    public void initialize() {
        tblStudent.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("studentId"));
        tblStudent.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblStudent.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("birthDay"));
        tblStudent.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("gender"));
        tblStudent.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("departmentId"));
        tblList = tblStudent.getItems();
        try {
            List<StudentsDTO> all = studentsBO.getAll();
            for (StudentsDTO studentsDTO : all) {
                tblList.add(new Students("S" + studentsDTO.getStudenetId(), studentsDTO.getName(), studentsDTO.getBirthDay(), studentsDTO.getGender(), studentsDTO.getDepartmentId()));
            }
        } catch (SQLException e) {
            Logger.getLogger("lk.ac.mrt.library").log(Level.SEVERE, null,e);
        }
        txtStudentID.setEditable(false);
        generateStudentId();
        txtName.requestFocus();
        tblStudent.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Students>() {
            @Override
            public void changed(ObservableValue<? extends Students> observable, Students oldValue, Students newValue) {
                if (newValue != null) {
                    txtStudentID.setText(newValue.getStudentId());
                    txtName.setText(newValue.getName());
                    txtBirthday.setText(newValue.getBirthDay().toString());
                    txtGender.setText(newValue.getGender());
                    txtDepartment.setText(Integer.toString(newValue.getDepartmentId()));
                    selectedrow = newValue;
                    btnAdd.setText("Update");
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

    public void generateStudentId() {
        try {
            int maximumId = studentsBO.getMaximumId();
            txtStudentID.setText("S" + ++maximumId);
        } catch (SQLException e) {
            Logger.getLogger("lk.ac.mrt.library").log(Level.SEVERE, null,e);
        }
    }

    public void txtName_Action(ActionEvent actionEvent) {
        String txtName = this.txtName.getText();
        if (!txtName.matches("^[\\sa-zA-z\\d]{1,100}$") && txtName.isEmpty() == false) {
            new Alert(Alert.AlertType.INFORMATION, "Incorrect Student Name").show();
        } else {
            txtBirthday.requestFocus();
        }
    }

    public void txtGender_Action(ActionEvent actionEvent) {
        String txtGender = this.txtGender.getText();
        if (!txtGender.matches("^male$|^female$|^Male$|^Female$") && txtGender.isEmpty() == false) {
            new Alert(Alert.AlertType.INFORMATION, "Incorrect Gender").show();
        } else {
            txtDepartment.requestFocus();
        }

    }

    public void txtBirthday_Action(ActionEvent actionEvent) {
        String txtBirthday = this.txtBirthday.getText();
        if (!txtBirthday.matches("([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))") && txtBirthday.isEmpty() == false) {
            new Alert(Alert.AlertType.INFORMATION, "Incorrect BirthDay").show();
        } else {
            txtGender.requestFocus();
        }
    }

    public void txtDepartment_Action(ActionEvent actionEvent) {
        String txtDepartment = this.txtDepartment.getText();
        if (!txtDepartment.matches("\\d{1,}") && txtDepartment.isEmpty() == false) {
            new Alert(Alert.AlertType.INFORMATION, "Incorrect Department").show();
        } else {
            btnAdd.requestFocus();
        }
    }

    public void lblNewStudent(MouseEvent mouseEvent) {
        resetFields();
        initialize();
    }

    public void btnAdd_Action(ActionEvent actionEvent) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(txtBirthday.getText(), dateTimeFormatter);
        if (btnAdd.getText().equals("Update")) {
            selectedrow.setName(txtName.getText());
            selectedrow.setBirthDay(date);
            selectedrow.setGender(txtGender.getText());
            selectedrow.setDepartmentId(Integer.parseInt(txtDepartment.getText()));
            tblStudent.refresh();
        } else {
            tblList.add(new Students("S" + txtStudentID.getText().substring(1), txtName.getText(), date, txtGender.getText(), Integer.parseInt(txtDepartment.getText())));
        }
        try {
            if (btnAdd.getText().equals("Add")) {
                boolean save = studentsBO.save(new StudentsDTO(Integer.parseInt(txtStudentID.getText().substring(1)), txtName.getText(), date, txtGender.getText(), Integer.parseInt(txtDepartment.getText())));
                if (save) {
                    new Alert(Alert.AlertType.INFORMATION, "Student Successfully Saved", ButtonType.OK).show();
                } else {
                    new Alert(Alert.AlertType.INFORMATION, "Failed To Save Student", ButtonType.OK).show();
                }
            } else {
                boolean update = studentsBO.update(new StudentsDTO(Integer.parseInt(txtStudentID.getText().substring(1)), txtName.getText(), date, txtGender.getText(), Integer.parseInt(txtDepartment.getText())));
                if (update) {
                    new Alert(Alert.AlertType.INFORMATION, "Student Successfully Updated", ButtonType.OK).show();
                } else {
                    new Alert(Alert.AlertType.INFORMATION, "Failed To Update Student", ButtonType.OK).show();
                }
                btnAdd.setText("Add");
            }

        } catch (SQLException e) {
            Logger.getLogger("lk.ac.mrt.library").log(Level.SEVERE, null,e);
        }
        resetFields();
        lblNewStudent.requestFocus();
    }

    public void txtStudentID_Action(ActionEvent actionEvent) {
        txtStudentID.requestFocus();

    }

    public void resetFields() {
        txtStudentID.clear();
        txtName.clear();
        txtBirthday.clear();
        txtGender.clear();
        txtDepartment.clear();
        btnAdd.setText("Add");
    }
}
