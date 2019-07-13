package business.custom.impl;

import business.SearchType;
import business.custom.BooksBO;
import dao.DAOFactory;
import dao.DAOTypes;
import dao.custom.BooksDAO;
import dao.custom.CategoryDAO;
import dto.BooksDTO;
import dto.QueryDTO;
import entity.Books;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BooksBOimpl implements BooksBO {
    BooksDAO booksDAO = DAOFactory.getInstance().getDAO(DAOTypes.BOOKS);
    CategoryDAO categoryDAO = DAOFactory.getInstance().getDAO(DAOTypes.CATEGORY);

    @Override
    public String getBookName(Integer id) throws SQLException {
        return booksDAO.findName(id);
    }

    @Override
    public List<BooksDTO> getAll() throws SQLException {
        List<Books> all = booksDAO.findAll();
        List<BooksDTO> booksDTOS = new ArrayList<>();
        for (Books books : all) {
            booksDTOS.add(new BooksDTO(books.getBookId(), books.getName(), books.getAuthorId(), books.getCategoryId(), books.getEdition(), books.getQty()));
        }
        return booksDTOS;
    }

    @Override
    public boolean save(BooksDTO dto) throws SQLException {
        return booksDAO.save(new Books(dto.getBookId(), dto.getName(), dto.getAuthorId(), dto.getCategoryId(), dto.getEdition(), dto.getQty()));
    }

    @Override
    public boolean remove(Integer id) throws SQLException {
        return booksDAO.delete(id);
    }

    @Override
    public boolean update(BooksDTO dto) throws SQLException {
        return booksDAO.update(new Books(dto.getBookId(), dto.getName(), dto.getAuthorId(), dto.getCategoryId(), dto.getEdition(), dto.getQty()));
    }

    @Override
    public BooksDTO get(Integer id) throws SQLException {
        Books books = booksDAO.find(id);
        return new BooksDTO(books.getBookId(), books.getName(), books.getAuthorId(), books.getCategoryId(), books.getEdition(), books.getQty());
    }

    public <T> List<QueryDTO> searchBy(SearchType type, T parameter) throws SQLException {
        List<QueryDTO> alDTOS = new ArrayList<>();
        List<Books> booksByName = booksDAO.searchBy(dao.SearchType.BOOK_NAME, parameter);
        List<Books> booksByAuthor = booksDAO.searchBy(dao.SearchType.BOOK_AUTHOR, parameter);
        List<Books> booksByCategory = booksDAO.searchBy(dao.SearchType.CATEGORY, parameter);
        switch (type) {
            case BOOK_NAME:
                for (Books book : booksByName) {
                    String name = categoryDAO.find(book.getCategoryId()).getName();
                    alDTOS.add(new QueryDTO(book.getBookId(),
                            book.getName(),
                            book.getAuthorId(),
                            name,
                            book.getEdition(),
                            book.getQty()));
                }
                break;

            case BOOK_AUTHOR:
                for (Books book : booksByAuthor) {
                    String name = categoryDAO.find(book.getCategoryId()).getName();
                    alDTOS.add(new QueryDTO(book.getBookId(),
                            book.getName(),
                            book.getAuthorId(),
                            name,
                            book.getEdition(),
                            book.getQty()));
                }
                break;

            case CATEGORY:
                for (Books book : booksByCategory) {
                    String name = categoryDAO.find(book.getCategoryId()).getName();
                    alDTOS.add(new QueryDTO(book.getBookId(),
                            book.getName(),
                            book.getAuthorId(),
                            name,
                            book.getEdition(),
                            book.getQty()));
                }
                break;

            default:
                return null;
        }
        return alDTOS;

    }

    @Override
    public int getMaximumId() throws SQLException {
        return booksDAO.findMaximumId();
    }

//    {
//        this.<String>searchBy("Kasun");
//        this.<Integer>searchBy(10);
//    }
//
//    private <E> void searchBy(E parameter){
//
//    }

}
