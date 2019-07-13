package ControllerLibrary;

import business.BOFactory;
import business.BOTypes;
import business.custom.CategoryBO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import dto.CategoryDTO;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import utilTM.Category;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CategoryController {
    public TableView<Category> tblCategory;
    public JFXTextField txtCateID;
    public JFXTextField txtCateName;
    public ImageView imgBack;
    public JFXButton btnSave;
    public List<Category> categoryDetails;
    public Label lblCategory;
    private CategoryBO categoryBO = BOFactory.getInstance().getBO(BOTypes.CATEGORY);

    public void initialize() {
        txtCateID.setEditable(false);
        tblCategory.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("categoryId"));
        tblCategory.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        categoryDetails = tblCategory.getItems();
        txtCateName.requestFocus();
        txtCateID.setEditable(false);
        try {
            txtCateID.setText(Integer.toString(categoryBO.getMaximumBorrowId()));
            List<CategoryDTO> allCategory = categoryBO.getAll();
            for (CategoryDTO categoryDTO : allCategory) {
                categoryDetails.add(new Category("C" + categoryDTO.getCategoryId(), categoryDTO.getName()));
            }
        } catch (SQLException e) {
            Logger.getLogger("lk.ac.mrt.library").log(Level.SEVERE, null,e);
        }
        tblCategory.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Category>() {
            @Override
            public void changed(ObservableValue<? extends Category> observable, Category oldValue, Category newValue) {
                if (newValue != null) {
                    txtCateID.setText(newValue.getCategoryId());
                    txtCateName.setText(newValue.getName());
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
        TableColumn<Category, Category> unfriendCol = new TableColumn<>("");
        unfriendCol.setMinWidth(90);
        unfriendCol.setCellValueFactory(
                param -> new ReadOnlyObjectWrapper<>(param.getValue())
        );
        unfriendCol.setCellFactory(param -> new TableCell<Category, Category>() {
            private final Button deleteButton = new Button("Delete");

            @Override
            protected void updateItem(Category category, boolean empty) {
                super.updateItem(category, empty);
                if (category == null) {
                    setGraphic(null);
                    return;
                }
                setGraphic(deleteButton);
                deleteButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        try {
                            boolean result = categoryBO.remove(Integer.parseInt(category.getCategoryId()));
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
        tblCategory.getColumns().add(unfriendCol);
    }

    public void txtCateID_Acrion(ActionEvent actionEvent) {
        txtCateName.requestFocus();
    }

    public void txtCateName_Action(ActionEvent actionEvent) {
        btnSave.requestFocus();
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

    public void btnSave_Action(ActionEvent actionEvent) throws SQLException {
        boolean result;
        String txtcateName = txtCateName.getText();
        if(txtcateName.matches("^[\\sa-zA-z]{1,100}$\n")) {
            if (btnSave.getText().equals("Save")) {
                try {
                    result = categoryBO.save(new CategoryDTO(Integer.parseInt(txtCateID.getText().substring(1)), txtCateName.getText()));
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
                    CategoryDTO categoryDTO = categoryBO.get(Integer.parseInt(txtCateID.getText().substring(1)));
                    categoryDTO.setName(txtCateName.getText());
                    result = categoryBO.update(categoryDTO);
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
       return categoryBO.getMaximumBorrowId();
    }
    public void lblCategory_Action(MouseEvent mouseEvent) {
        try {
            txtCateID.setText(Integer.toString(generateCategoryId()));
            txtCateName.clear();
            tblCategory.getSelectionModel().clearSelection();
        } catch (SQLException e) {
            Logger.getLogger("lk.ac.mrt.library").log(Level.SEVERE, null,e);
        }
    }
}
