package ControllerLibrary;

import business.BOFactory;
import business.BOTypes;
import business.SearchType;
import business.custom.BooksBO;
import business.custom.CategoryBO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import dto.BooksDTO;
import dto.CategoryDTO;
import dto.QueryDTO;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BookController {
    public static ArrayList<QueryDTO> tableList = new ArrayList<>();
    public static String searchKey;
    public JFXListView<String> lstPopup = new JFXListView<>();
    private ArrayList<String> itemList = new ArrayList<>();
    @FXML
    private JFXButton btnAddBook;
    @FXML
    private JFXButton btnSearchByBook;
    @FXML
    private JFXButton btnSearchByAuthor;
    @FXML
    private JFXButton btnSearchByCategory;
    @FXML
    private JFXButton btnEnter;
    @FXML
    private JFXTextField txtID;
    @FXML
    private JFXTextField txtName;
    @FXML
    private JFXTextField txtAuthorID;
    @FXML
    private JFXTextField txtCategoryID;
    @FXML
    private JFXTextField txtEdition;
    @FXML
    private JFXTextField txtQty;
    @FXML
    private Label lblDetail;
    @FXML
    private ImageView lblBack;
    @FXML
    private ImageView imgCategorySelect;
    private ObservableList<String> items;
    private String checker;
    private BooksBO booksBO = BOFactory.getInstance().getBO(BOTypes.BOOKS);
    private CategoryBO categoryBO = BOFactory.getInstance().getBO(BOTypes.CATEGORY);

    public void initialize() {
        txtID.setEditable(false);
        txtName.setEditable(false);
        txtAuthorID.setEditable(false);
        txtCategoryID.setEditable(false);
        txtEdition.setEditable(false);
        txtQty.setEditable(false);
        txtID.clear();
        txtName.clear();
        txtAuthorID.clear();
        txtCategoryID.clear();
        txtEdition.clear();
        txtQty.clear();
        lstPopup.setVisible(false);
        btnAddBook.requestFocus();
        lstPopup.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (txtCategoryID.isEditable() != false && newValue != null) {
                    System.out.println(newValue);
                    try {
                        List<CategoryDTO> all = categoryBO.getAll();
                        for (CategoryDTO categoryDTO : all) {
                            if (categoryDTO.getName().equals(newValue)) {
                                txtCategoryID.setText(Integer.toString(categoryDTO.getCategoryId()));
                            }
                        }
                    } catch (SQLException e) {
                        Logger.getLogger("lk.ac.mrt.library").log(Level.SEVERE, null,e);
                    }
                }
            }
        });
    }

    public void btnAddBook_Clicked(javafx.event.ActionEvent actionEvent) {
        txtName.setEditable(true);
        txtAuthorID.setEditable(true);
        txtCategoryID.setEditable(true);
        txtEdition.setEditable(true);
        txtQty.setEditable(true);
        checker = "Add";
        generateBookId();
        txtName.requestFocus();
    }

    public void btnSearchByBook_Clicked(javafx.event.ActionEvent actionEvent) {
        initialize();
        txtName.setEditable(true);
        txtName.requestFocus();
        checker = "ByName";
    }

    public void btnSearchByAuthor_Clicked(javafx.event.ActionEvent actionEvent) {
        initialize();
        txtAuthorID.setEditable(true);
        txtAuthorID.requestFocus();
        checker = "ByAuthor";
    }

    public void btnSearchByCategory_Clicked(javafx.event.ActionEvent actionEvent) {
        initialize();
        txtCategoryID.setEditable(true);
        txtCategoryID.requestFocus();
        checker = "ByCategory";
    }

    public void btnEnter_Clicked(javafx.event.ActionEvent actionEvent) {
        manageBy();
        initialize();
    }

    private void addBook() {
        try {
            boolean b = booksBO.save(new BooksDTO(Integer.parseInt(txtID.getText().substring(1)), txtName.getText(), txtAuthorID.getText(), Integer.parseInt(txtCategoryID.getText()), txtEdition.getText(), Integer.parseInt(txtQty.getText())));
            if (b) {
                new Alert(Alert.AlertType.INFORMATION, "Book Added Successfully").show();
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Failed To Add The Book").show();
            }
        } catch (SQLException e) {
            Logger.getLogger("lk.ac.mrt.library").log(Level.SEVERE, null,e);
            new Alert(Alert.AlertType.INFORMATION, "Failed To Add The Book").show();
        }
    }

    private void manageBy() {
        if (checker == null) {
            new Alert(Alert.AlertType.INFORMATION, "Please Enter Something").show();
        } else {
            try {
                switch (checker) {

                    case "Add":
                        //add book
                        if (txtID.getLength() > 0 && txtName.getLength() > 0 && txtAuthorID.getLength() > 0 && txtCategoryID.getLength() > 0 && txtEdition.getLength() > 0 && txtQty.getLength() > 0) {
                            addBook();
                        } else {
                            new Alert(Alert.AlertType.INFORMATION, "Please Enter All The Data").show();
                        }
                        break;
                    case "ByName":
                        //search by name
                        if (txtName.getLength() > 0) {

                            List<QueryDTO> queryDTOSName = booksBO.searchBy(SearchType.BOOK_NAME, txtName.getText());
                            for (QueryDTO queryDTO : queryDTOSName) {
                                tableList.add(queryDTO);
                            }
                            searchKey = txtName.getText();
                            loadSearchForm();
                        } else {
                            new Alert(Alert.AlertType.INFORMATION, "Please Enter All The Data").show();
                        }

                        break;
                    case "ByAuthor":
                        //search by author
                        if (txtAuthorID.getLength() > 0) {
                            List<QueryDTO> queryDTOSAurhor = booksBO.searchBy(SearchType.BOOK_AUTHOR, txtAuthorID.getText());
                            for (QueryDTO queryDTO : queryDTOSAurhor) {
                                tableList.add(queryDTO);
                            }
                            searchKey = txtAuthorID.getText();
                            loadSearchForm();
                        } else {
                            new Alert(Alert.AlertType.INFORMATION, "Please Enter All The Data").show();
                        }
                        break;
                    case "ByCategory":
                        //search by category
                        if (txtCategoryID.getLength() > 0) {
                            List<QueryDTO> queryDTOSCategory = booksBO.searchBy(SearchType.CATEGORY, txtCategoryID.getText().substring(1));
                            for (QueryDTO queryDTO : queryDTOSCategory) {
                                tableList.add(queryDTO);
                            }
                            searchKey = txtCategoryID.getText();
                            loadSearchForm();
                        } else {
                            new Alert(Alert.AlertType.INFORMATION, "Please Enter All The Data").show();
                        }
                        break;
                    default:
                        new Alert(Alert.AlertType.INFORMATION, "Something Went Wrong").show();
                }
                initialize();
            } catch (SQLException e) {
                Logger.getLogger("lk.ac.mrt.library").log(Level.SEVERE, null,e);
            } catch (IOException e) {
                Logger.getLogger("lk.ac.mrt.library").log(Level.SEVERE, null,e);
            }
        }
    }

    public void loadSearchForm() throws IOException {
        Parent root = (Parent) FXMLLoader.load(this.getClass().getResource("/view/SearchTable.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) lblDetail.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Search Form");
        stage.show();
    }

    public void lblBack_Clicked(MouseEvent mouseEvent) throws IOException {
        Parent root = (Parent) FXMLLoader.load(this.getClass().getResource("/view/DashBoard.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) lblDetail.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("DashBoard Form");
        stage.show();
    }

    public void imgCategorySelect(MouseEvent mouseEvent) {
        lstPopup.setVisible(true);
        categoryList();
    }

    private void categoryList() {
        try {
            List<CategoryDTO> categoryDTOS = categoryBO.getAll();
            for (CategoryDTO categoryDTO : categoryDTOS) {
                itemList.add(categoryDTO.getName());

            }
            items = FXCollections.observableArrayList(itemList);
            lstPopup.setItems(items);
        } catch (SQLException e) {
            Logger.getLogger("lk.ac.mrt.library").log(Level.SEVERE, null,e);
        }
    }

    private void generateBookId() {
        try {
            int maximumId = booksBO.getMaximumId();
            txtID.setText("B" + ++maximumId);
        } catch (SQLException e) {
            Logger.getLogger("lk.ac.mrt.library").log(Level.SEVERE, null,e);
        }
    }

    public void txtQty_Action(KeyEvent keyEvent) {
        if ((keyEvent.getCode() == KeyCode.ENTER)) {
            String txtqty = txtQty.getText();
            if (!txtqty.matches("\\d{1,}") && txtqty.isEmpty() == false) {
                txtQty.clear();
                new Alert(Alert.AlertType.INFORMATION, "Incorrect Quantity").showAndWait();
            } else if (checker.equals("Add")) {
                btnEnter.requestFocus();
            } else {
                btnEnter.requestFocus();
            }
        }
    }

    public void txtID_Action(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            btnAddBook.requestFocus();
        }
    }

    public void txtName_Action(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            String txtBookName = txtName.getText();
            if (!txtBookName.matches("^[\\sa-zA-z\\d]{1,100}$")) {
                txtName.clear();
                new Alert(Alert.AlertType.INFORMATION, "Incorrect Type Of Book Name").showAndWait();
            } else if (checker.equals("Add")) {
                txtAuthorID.requestFocus();
            } else {
                btnEnter.requestFocus();
            }
        }
    }

    public void txtAuthorID_Action(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            String txtAuthorName = txtAuthorID.getText();
            if (!txtAuthorName.matches("^[\\sa-zA-z]{1,50}$")) {
                txtAuthorID.clear();
                new Alert(Alert.AlertType.INFORMATION, "Incorrect Type Of Author Name").showAndWait();
            } else if (checker.equals("Add")) {
                txtCategoryID.requestFocus();
            } else {
                btnEnter.requestFocus();
            }
        }
    }

    public void txtCategoryID_Action(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            String txtCategoryId = txtCategoryID.getText();
            if (!txtCategoryId.matches("\\d{1,}") && txtCategoryId.isEmpty() == false) {
                txtCategoryID.clear();
                new Alert(Alert.AlertType.INFORMATION, "Incorrect Category ID").showAndWait();
            } else if (checker.equals("Add")) {
                txtEdition.requestFocus();
            } else {
                btnEnter.requestFocus();
            }
        }
    }

    public void txtEdition_Action(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            String txtedition = txtEdition.getText();
            if (!txtedition.matches("^[\\sa-zA-z\\d]{1,20}$")) {
                txtEdition.clear();
                new Alert(Alert.AlertType.INFORMATION, "Incorrect Type Of Edition").showAndWait();
            } else if (checker.equals("Add")) {
                txtQty.requestFocus();
            } else {
                btnEnter.requestFocus();
            }
        }
    }

    public void lstPopup_OnMouseExit(MouseEvent mouseEvent) {
        lstPopup.getItems().clear();
        lstPopup.refresh();
        lstPopup.setVisible(false);
    }
}
