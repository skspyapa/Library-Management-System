package ControllerLibrary;

import business.BOFactory;
import business.BOTypes;
import business.custom.DepartmentBO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import dto.DepartmentDTO;
import javafx.beans.property.ReadOnlyObjectWrapper;
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
import utilTM.Department;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DepartmentController {

    public TableView<Department> tblDepartment;
    public JFXTextField txtDepID;
    public JFXTextField txtDepName;
    public ImageView imgBack;
    public JFXButton btnSave;
    public ObservableList<Department> departmentDetails;
    private DepartmentBO departmentBO = BOFactory.getInstance().getBO(BOTypes.DEPARTMENT);
public void initialize(){
    txtDepID.setEditable(false);
    tblDepartment.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("departmentId"));
    tblDepartment.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
    departmentDetails = tblDepartment.getItems();
    txtDepName.requestFocus();
    txtDepID.setEditable(false);
    try {
        txtDepID.setText(Integer.toString(departmentBO.getMaximumBorrowId()));
        List<DepartmentDTO> allDepartments = departmentBO.getAll();
        for (DepartmentDTO departmentDTO : allDepartments) {
            departmentDetails.add(new Department("D" + departmentDTO.getDepartmentId(), departmentDTO.getName()));
        }
    } catch (SQLException e) {
        Logger.getLogger("lk.ac.mrt.library").log(Level.SEVERE, null,e);
    }
    tblDepartment.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Department>() {
        @Override
        public void changed(ObservableValue<? extends Department> observable, Department oldValue, Department newValue) {
            if (newValue != null) {
                txtDepID.setText(newValue.getDepartmentId());
                txtDepName.setText(newValue.getName());
                btnSave.setText("Update");

            }
            if(newValue==null){
                btnSave.setText("Save");
            }
        }
    });
    deleteButton();
}
    public void deleteButton() {
        //delete button
        TableColumn<Department, Department> unfriendCol = new TableColumn<>("");
        unfriendCol.setMinWidth(90);
        unfriendCol.setCellValueFactory(
                param -> new ReadOnlyObjectWrapper<>(param.getValue())
        );
        unfriendCol.setCellFactory(param -> new TableCell<Department, Department>() {
            private final Button deleteButton = new Button("Delete");

            @Override
            protected void updateItem(Department department, boolean empty) {
                super.updateItem(department, empty);
                if (department == null) {
                    setGraphic(null);
                    return;
                }
                setGraphic(deleteButton);
                deleteButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        try {
                            boolean result = departmentBO.remove(Integer.parseInt(department.getDepartmentId()));
                            if(result){
                                new Alert(Alert.AlertType.INFORMATION,"Updated",ButtonType.OK).show();
                            }else{
                                new Alert(Alert.AlertType.INFORMATION,"Failed To Updated",ButtonType.OK).show();
                            }
                        } catch (SQLException e) {
                            Logger.getLogger("lk.ac.mrt.library").log(Level.SEVERE, null,e);
                        }
                    }
                });
            }
        });
        tblDepartment.getColumns().add(unfriendCol);
    }
    public void imgBack_Action(MouseEvent mouseEvent) throws IOException {
        Parent root = (Parent) FXMLLoader.load(this.getClass().getResource("/view/AdminDashboard.fxml"));
        Scene newScene = new Scene(root);
        Stage stage = (Stage)imgBack.getScene().getWindow();
        stage.setScene(newScene);
        stage.centerOnScreen();
        stage.setTitle("Admin Dashboard");
        stage.show();
    }

    public void txtDepID_Action(ActionEvent actionEvent) {
    txtDepName.requestFocus();
    }

    public void txtDepName_Action(ActionEvent actionEvent) {
    btnSave.requestFocus();
    }

    public void btnSave_Action(ActionEvent actionEvent) {
        boolean result;
        String txtDeptName = txtDepName.getText();
        if(txtDeptName.matches("^[\\sa-zA-z]{1,100}$\n")) {
            if (btnSave.getText().equals("Save")) {
                try {
                    result = departmentBO.save(new DepartmentDTO(Integer.parseInt(txtDepID.getText().substring(1)), txtDepName.getText()));
                    if (result) {
                        new Alert(Alert.AlertType.INFORMATION, "Updated", ButtonType.OK).show();
                    } else {
                        new Alert(Alert.AlertType.INFORMATION, "Failed To Updated", ButtonType.OK).show();
                    }
                } catch (SQLException e) {
                    Logger.getLogger("lk.ac.mrt.library").log(Level.SEVERE, null,e);
                }
            } else {
                try {
                    DepartmentDTO departmentDTO = departmentBO.get(Integer.parseInt(txtDepID.getText().substring(1)));
                    departmentDTO.setName(txtDepName.getText());
                    result = departmentBO.update(departmentDTO);
                    if (result) {
                        new Alert(Alert.AlertType.INFORMATION, "Updated", ButtonType.OK).show();
                        btnSave.setText("Save");
                    } else {
                        new Alert(Alert.AlertType.INFORMATION, "Failed To Updated", ButtonType.OK).show();
                    }
                } catch (SQLException e) {
                    Logger.getLogger("lk.ac.mrt.library").log(Level.SEVERE, null,e);
                }

            }
        }else{
            new Alert(Alert.AlertType.INFORMATION, "Enter Valid Category Name", ButtonType.OK).show();
        }
    }
    private int generateCategoryId() throws SQLException {
        return departmentBO.getMaximumBorrowId();
    }

    public void lblDepartment_Action(MouseEvent mouseEvent) {
        try {
            txtDepID.setText(Integer.toString(generateCategoryId()));
            txtDepName.clear();
            tblDepartment.getSelectionModel().clearSelection();
        } catch (SQLException e) {
            Logger.getLogger("lk.ac.mrt.library").log(Level.SEVERE, null,e);
        }
    }
}
