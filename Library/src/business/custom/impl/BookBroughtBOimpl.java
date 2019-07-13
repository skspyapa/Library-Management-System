package business.custom.impl;

import business.custom.BookBroughtBO;
import dao.DAOFactory;
import dao.DAOTypes;
import dao.custom.*;
import dbLibrary.DBConnection;
import dto.BookBroughtDTO;
import dto.FeeDTO;
import entity.BookBrought;
import entity.Books;
import entity.Borrow;
import entity.Fee;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class BookBroughtBOimpl implements BookBroughtBO {
    private QueryDAO queryDAO = DAOFactory.getInstance().getDAO(DAOTypes.QUERY);
    private BookBroughtDAO bookBroughtDAO = DAOFactory.getInstance().getDAO(DAOTypes.BOOK_BROUGHT);
    private BooksDAO booksDAO = DAOFactory.getInstance().getDAO(DAOTypes.BOOKS);
    private BorrowDAO borrowDAO = DAOFactory.getInstance().getDAO(DAOTypes.BORROW);
    private FeeDAO feeDAO = DAOFactory.getInstance().getDAO(DAOTypes.FEE);

    @Override
    public List<BookBroughtDTO> getAll() throws SQLException {
        return null;
    }

    @Override
    public boolean save(BookBroughtDTO dto) throws SQLException {
        return false;
    }

    @Override
    public boolean remove(Integer dtoId) throws SQLException {
        return false;
    }

    @Override
    public boolean update(BookBroughtDTO dto) throws SQLException {
        return false;
    }

    @Override
    public BookBroughtDTO get(Integer dtoId) throws SQLException {
        return null;
    }


    @Override
    public boolean returnBook(BookBroughtDTO bookBroughtDTO, FeeDTO feeDTO) throws Exception {
        Connection connection = DBConnection.getInstance().getConnection();
        boolean result = false;
        try {
            connection.setAutoCommit(false);
            result = bookBroughtDAO.save(new BookBrought(bookBroughtDTO.getBorrowId(), bookBroughtDTO.getReturnDate()));
            if (!result) {
                connection.rollback();
                return false;
            }
            result = feeDAO.save(new Fee(feeDTO.getBorrowId(), feeDTO.getFine()));
            if (!result) {
                connection.rollback();
                return false;
            }
            Borrow borrow = borrowDAO.find(bookBroughtDTO.getBorrowId());
            Books books = booksDAO.find(borrow.getBookId());
            books.setQty(books.getQty() + 1);
            result = booksDAO.update(books);
            if (!result) {
                connection.rollback();
                return false;
            }
            connection.commit();
            return true;
        } catch (Throwable e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }
}
