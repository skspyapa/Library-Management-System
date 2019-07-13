package ControllerLibrary;

import business.BOFactory;
import business.BOTypes;
import business.SearchType;
import business.custom.BooksBO;
import business.custom.CategoryBO;
import com.jfoenix.controls.JFXTextField;
import dto.BooksDTO;
import dto.CategoryDTO;
import dto.QueryDTO;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import utilTM.Book;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SearchTableController {

    public JFXTextField txtSearch;
    public Label lblSearch;
    public ImageView lblBack;
    @FXML
    private TableView<Book> tblSearch;
    @FXML
    private TableColumn<Book, String> bookId;
    @FXML
    private TableColumn<Book, String> name;
    @FXML
    private TableColumn<Book, String> authorName;
    @FXML
    private TableColumn<Book, String> categoryName;
    @FXML
    private TableColumn<Book, String> edition;
    @FXML
    private TableColumn<Book, Integer> qty;

    private BooksBO booksBO = BOFactory.getInstance().getBO(BOTypes.BOOKS);
    private CategoryBO categoryBO = BOFactory.getInstance().getBO(BOTypes.CATEGORY);

    public void initialize() {

        tblSearch.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("bookId"));
        tblSearch.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("bookName"));
        tblSearch.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("author"));
        tblSearch.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("category"));
        tblSearch.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("edition"));
        tblSearch.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("qty"));
        ObservableList<QueryDTO> queryDTOS = FXCollections.observableArrayList(BookController.tableList);
        for (QueryDTO queryDTO : queryDTOS) {
            tblSearch.getItems().add(new Book("B" + queryDTO.getBookId(), queryDTO.getName(), queryDTO.getAuthorName(), queryDTO.getCategoryName(), queryDTO.getEdition(), queryDTO.getQty()));
        }
        txtSearch.setText(BookController.searchKey);
        txtSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                try {
                    List<QueryDTO> queryByName = booksBO.searchBy(SearchType.BOOK_NAME, newValue);
                    List<QueryDTO> queryByAuthor = booksBO.searchBy(SearchType.BOOK_AUTHOR, newValue);
                    List<QueryDTO> queryByCategory = booksBO.searchBy(SearchType.CATEGORY, newValue);
                    tblSearch.getItems().clear();
                    tblSearch.refresh();

                    if (queryByName != null) {
//                        tblSearch.getItems().clear();
//                        tblSearch.refresh();
                        for (QueryDTO queryDTO : queryByName) {
                            tblSearch.getItems().add(new Book("B" + queryDTO.getBookId(), queryDTO.getName(), queryDTO.getAuthorName(), queryDTO.getCategoryName(), queryDTO.getEdition(), queryDTO.getQty()));
                        }
                    }
                    if (queryByAuthor != null) {
//                        tblSearch.getItems().clear();
//                        tblSearch.refresh();
                        for (QueryDTO queryDTO : queryByAuthor) {
                            tblSearch.getItems().add(new Book("B" + queryDTO.getBookId(), queryDTO.getName(), queryDTO.getAuthorName(), queryDTO.getCategoryName(), queryDTO.getEdition(), queryDTO.getQty()));
                        }
                    }
                    if (queryByCategory != null) {
//                        tblSearch.getItems().clear();
//                        tblSearch.refresh();
                        for (QueryDTO queryDTO : queryByCategory) {
                            tblSearch.getItems().add(new Book("B" + queryDTO.getBookId(), queryDTO.getName(), queryDTO.getAuthorName(), queryDTO.getCategoryName(), queryDTO.getEdition(), queryDTO.getQty()));
                        }
                    }
                } catch (SQLException e) {
                    Logger.getLogger("lk.ac.mrt.library").log(Level.SEVERE, null,e);
                } finally {
                    if (txtSearch.getLength() == 0) {
                        tblSearch.getItems().clear();
                        tblSearch.refresh();
                    }
                }
            }
        });
        editableRows();
//        tblSearch.setRowFactory(new Callback<TableView<QueryDTO>, TableRow<QueryDTO>>() {
//            @Override
//            public TableRow<QueryDTO> call(TableView<QueryDTO> param) {
//                TableRow<QueryDTO> queryDTOTableRow = new TableRow<>();
//                TableColumn<Object, QueryDTO> queryDTOTableColumn = new TableColumn<>();
//                queryDTOTableRow.setOnMouseClicked(new EventHandler<MouseEvent>() {
//                    @Override
//                    public void handle(MouseEvent event) {
//                        if (event.getClickCount() == 2){
//                            System.out.println(queryDTOTableRow.getIndex());
//                            queryDTOTableRow.setEditable(true);
//                        }
//                    }
//                });
//                return queryDTOTableRow;
//            }
//        });

        tblSearch.setEditable(true);

    }

    public void editableRows() {

//        name.setCellFactory(new Callback<TableColumn<QueryDTO, String>, TableCell<QueryDTO, String>>() {
//            @Override
//            public TableCell<QueryDTO, String> call(TableColumn<QueryDTO, String> param) {
//                TextFieldTableCell<QueryDTO, String> txt = new TextFieldTableCell<>();
//                return  txt;
//            }
//        });
        name.setCellFactory(TextFieldTableCell.forTableColumn());
        name.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Book, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Book, String> event) {
                event.getTableView().getItems().get(event.getTablePosition().getRow()).setBookName(event.getNewValue());
                int row = event.getTableView().getEditingCell().getRow();
                Book book = event.getTableView().getItems().get(row);
                BooksBO booksBO = BOFactory.getInstance().getBO(BOTypes.BOOKS);
                CategoryBO categoryBO = BOFactory.getInstance().getBO(BOTypes.CATEGORY);
                try {
                    List<CategoryDTO> all = categoryBO.getAll();
                    for (CategoryDTO categoryDTO : all) {
                        if (categoryDTO.getName().equals(book.getCategory())) {
                            booksBO.update(new BooksDTO(Integer.parseInt(book.getBookId().substring(1)), book.getBookName(), book.getAuthor(), categoryDTO.getCategoryId(), book.getEdition(), book.getQty()));
                            new Alert(Alert.AlertType.INFORMATION, "Updated", ButtonType.OK).show();
                            break;
                        }
                    }
                } catch (SQLException e) {
                    new Alert(Alert.AlertType.INFORMATION, "Not Updated", ButtonType.OK).show();
                    Logger.getLogger("lk.ac.mrt.library").log(Level.SEVERE, null,e);
                }
            }
        });
        authorName.setCellFactory(TextFieldTableCell.forTableColumn());
        authorName.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Book, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Book, String> event) {
                event.getTableView().getItems().get(event.getTablePosition().getRow()).setAuthor(event.getNewValue());
                int row = event.getTableView().getEditingCell().getRow();
                Book book = event.getTableView().getItems().get(row);
                BooksBO booksBO = BOFactory.getInstance().getBO(BOTypes.BOOKS);
                CategoryBO categoryBO = BOFactory.getInstance().getBO(BOTypes.CATEGORY);
                try {
                    List<CategoryDTO> all = categoryBO.getAll();
                    for (CategoryDTO categoryDTO : all) {
                        if (categoryDTO.getName().equals(book.getCategory())) {
                            booksBO.update(new BooksDTO(Integer.parseInt(book.getBookId().substring(1)), book.getBookName(), book.getAuthor(), categoryDTO.getCategoryId(), book.getEdition(), book.getQty()));
                            new Alert(Alert.AlertType.INFORMATION, "Updated", ButtonType.OK).show();
                            break;
                        }
                    }
                } catch (SQLException e) {
                    new Alert(Alert.AlertType.INFORMATION, "Not Updated", ButtonType.OK).show();
                    Logger.getLogger("lk.ac.mrt.library").log(Level.SEVERE, null,e);
                }
            }
        });
        categoryName.setCellFactory(TextFieldTableCell.forTableColumn());
        categoryName.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Book, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Book, String> event) {
                event.getTableView().getItems().get(event.getTablePosition().getRow()).setCategory(event.getNewValue());
                int row = event.getTableView().getEditingCell().getRow();
                Book book = event.getTableView().getItems().get(row);
                BooksBO booksBO = BOFactory.getInstance().getBO(BOTypes.BOOKS);
                CategoryBO categoryBO = BOFactory.getInstance().getBO(BOTypes.CATEGORY);
                try {
                    List<CategoryDTO> all = categoryBO.getAll();
                    for (CategoryDTO categoryDTO : all) {
                        if (categoryDTO.getName().equals(book.getCategory())) {
                            booksBO.update(new BooksDTO(Integer.parseInt(book.getBookId().substring(1)), book.getBookName(), book.getAuthor(), categoryDTO.getCategoryId(), book.getEdition(), book.getQty()));
                            new Alert(Alert.AlertType.INFORMATION, "Updated", ButtonType.OK).show();
                            break;
                        }
                    }
                } catch (SQLException e) {
                    new Alert(Alert.AlertType.INFORMATION, "Not Updated", ButtonType.OK).show();
                    Logger.getLogger("lk.ac.mrt.library").log(Level.SEVERE, null,e);
                }
            }
        });
        edition.setCellFactory(TextFieldTableCell.forTableColumn());
        edition.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Book, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Book, String> event) {
                event.getTableView().getItems().get(event.getTablePosition().getRow()).setEdition(event.getNewValue());
                int row = event.getTableView().getEditingCell().getRow();
                Book book = event.getTableView().getItems().get(row);
                BooksBO booksBO = BOFactory.getInstance().getBO(BOTypes.BOOKS);
                CategoryBO categoryBO = BOFactory.getInstance().getBO(BOTypes.CATEGORY);
                try {
                    List<CategoryDTO> all = categoryBO.getAll();
                    for (CategoryDTO categoryDTO : all) {
                        if (categoryDTO.getName().equals(book.getCategory())) {
                            booksBO.update(new BooksDTO(Integer.parseInt(book.getBookId().substring(1)), book.getBookName(), book.getAuthor(), categoryDTO.getCategoryId(), book.getEdition(), book.getQty()));
                            new Alert(Alert.AlertType.INFORMATION, "Updated", ButtonType.OK).show();
                            break;
                        }
                    }
                } catch (SQLException e) {
                    new Alert(Alert.AlertType.INFORMATION, "Not Updated", ButtonType.OK).show();
                    Logger.getLogger("lk.ac.mrt.library").log(Level.SEVERE, null,e);
                }
            }
        });
        qty.setCellFactory(new Callback<TableColumn<Book, Integer>, TableCell<Book, Integer>>() {
            @Override
            public TableCell<Book, Integer> call(TableColumn<Book, Integer> param) {
                return new TextFieldTableCell<>();
            }
        });
        qty.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Book, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Book, Integer> event) {
                event.getTableView().getItems().get(event.getTablePosition().getRow()).setQty(event.getNewValue());
                int row = event.getTableView().getEditingCell().getRow();
                Book book = event.getTableView().getItems().get(row);
                BooksBO booksBO = BOFactory.getInstance().getBO(BOTypes.BOOKS);
                CategoryBO categoryBO = BOFactory.getInstance().getBO(BOTypes.CATEGORY);
                try {
                    List<CategoryDTO> all = categoryBO.getAll();
                    for (CategoryDTO categoryDTO : all) {
                        if (categoryDTO.getName().equals(book.getBookName())) {
                            booksBO.update(new BooksDTO(Integer.parseInt(book.getBookId().substring(1)), book.getBookName(), book.getAuthor(), categoryDTO.getCategoryId(), book.getEdition(), book.getQty()));
                            new Alert(Alert.AlertType.INFORMATION, "Updated", ButtonType.OK).show();
                            break;
                        }
                    }
                } catch (SQLException e) {
                    new Alert(Alert.AlertType.INFORMATION, "Not Updated", ButtonType.OK).show();
                    Logger.getLogger("lk.ac.mrt.library").log(Level.SEVERE, null,e);
                }
            }
        });
    }

    public void btnBack_Action(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(this.getClass().getResource("/view/BookManager.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) lblSearch.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("");
        stage.centerOnScreen();

    }

    public void lblBack_Clicked(MouseEvent mouseEvent) throws IOException {
        BookController.tableList.clear();
        Parent root = (Parent) FXMLLoader.load(this.getClass().getResource("/view/BookManager.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) lblBack.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("DashBoard Form");
        stage.show();
    }

}
