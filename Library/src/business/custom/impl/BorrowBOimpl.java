package business.custom.impl;

import business.QueryType;
import business.custom.BorrowBO;
import dao.DAOFactory;
import dao.DAOTypes;
import dao.custom.BooksDAO;
import dao.custom.BorrowDAO;
import dao.custom.QueryDAO;
import dbLibrary.DBConnection;
import dto.BorrowDTO;
import dto.QueryDTO;
import entity.Books;
import entity.Borrow;
import entity.CustomEntity;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BorrowBOimpl implements BorrowBO {
    BorrowDAO borrowDAO = DAOFactory.getInstance().getDAO(DAOTypes.BORROW);
    BooksDAO booksDAO = DAOFactory.getInstance().getDAO(DAOTypes.BOOKS);
    QueryDAO queryDAO = DAOFactory.getInstance().getDAO(DAOTypes.QUERY);

    public int getMaximumBorrowId() throws SQLException {
        int maxId = borrowDAO.maximumBorrowId();
        return maxId + 1;
    }

    @Override
    public List<QueryDTO> getBorrowDetails(Integer id) throws SQLException {
        List<CustomEntity> borrowdetails = queryDAO.borrowdetails(id);
        List<QueryDTO> queryDTOS = new ArrayList<>();
        for (CustomEntity customEntity : borrowdetails) {
            queryDTOS.add(new QueryDTO(customEntity.getBorrowId(), customEntity.getBookId(), customEntity.getName(), customEntity.getBorrowDate(), customEntity.getDueDate()));
        }
        return queryDTOS;
    }

    @Override
    public boolean placeBorrow(QueryType queryType, BorrowDTO borrowDTO) throws Exception {
        Connection connection = DBConnection.getInstance().getConnection();
        boolean result = false;
        connection.setAutoCommit(false);
        if (queryType == QueryType.SAVE) {
            try {
                result = borrowDAO.save(new Borrow(borrowDTO.getBorrowId(), borrowDTO.getBorrowDate(), borrowDTO.getStudentId(), borrowDTO.getBookId()));
                if (!result) {
                    connection.rollback();
                    return false;
                }
                Books books = booksDAO.find(borrowDTO.getBookId());
                System.out.println(books.getQty());
                books.setQty(books.getQty() - 1);
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
        } else if (queryType == QueryType.UPDATE) {
            try {
                Borrow previousBorrow = borrowDAO.find(borrowDTO.getBorrowId());
                if (previousBorrow.getBookId() != borrowDTO.getBorrowId()) {
                    //update previous book qty
                    Books books = booksDAO.find(previousBorrow.getBookId());
                    books.setQty(books.getQty() + 1);
                    result = booksDAO.update(books);
                    if (!result) {
                        connection.rollback();
                        return false;
                    }
                    //update new book qty
                    Books books1 = booksDAO.find(borrowDTO.getBookId());
                    books1.setQty(books1.getQty() - 1);
                    result = booksDAO.update(books1);
                    if (!result) {
                        connection.rollback();
                        return false;
                    }
                    result = borrowDAO.update(new Borrow(borrowDTO.getBorrowId(), borrowDTO.getBorrowDate(), borrowDTO.getStudentId(), borrowDTO.getBookId()));

                    if (!result) {
                        connection.rollback();
                        return false;
                    }
                } else {
                    connection.commit();
                    return true;
                }
            } catch (Throwable e) {
                connection.rollback();
                throw e;
            } finally {
                connection.setAutoCommit(true);
            }
        }
        return result;
    }

    @Override
    public List<BorrowDTO> getAll() throws SQLException {
        return borrowDAO.findAll().stream().map(borrow -> new BorrowDTO(borrow.getBorrowId(), borrow.getBorrowDate(), borrow.getStudentId(), borrow.getBookId())).collect(Collectors.toList());
    }

    @Override
    public boolean save(BorrowDTO dto) throws SQLException {
        return borrowDAO.save(new Borrow(dto.getBorrowId(), dto.getBorrowDate(), dto.getStudentId(), dto.getBookId()));
    }

    @Override
    public boolean remove(Integer id) throws SQLException {
        return borrowDAO.delete(id);
    }

    @Override
    public boolean update(BorrowDTO borrowDTO) throws SQLException {
        return borrowDAO.update(new Borrow(borrowDTO.getBorrowId(), borrowDTO.getBorrowDate(), borrowDTO.getStudentId(), borrowDTO.getBookId()));
    }

    @Override
    public BorrowDTO get(Integer id) throws SQLException {
        Borrow borrow = borrowDAO.find(id);
        return new BorrowDTO(borrow.getBorrowId(), borrow.getBorrowDate(), borrow.getStudentId(), borrow.getBookId());
    }
}
