package dao;

import dao.custom.impl.*;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory() {
    }

    public static DAOFactory getInstance() {
        if (daoFactory == null) {
            daoFactory = new DAOFactory();
        }
        return daoFactory;
    }

    public <T extends SuperDAO> T getDAO(DAOTypes daoTypes) {
        switch (daoTypes) {
            case BOOK_BROUGHT:
                return (T) new BookBroughtDAOimpl();
            case BOOKS:
                return (T) new BooksDAOimpl();
            case BORROW:
                return (T) new BorrowDAOimpl();
            case CATEGORY:
                return (T) new CategoryDAOimpl();
            case DEPARTMENT:
                return (T) new DepartmentDAOimpl();
            case FEE:
                return (T) new FeeDAOimpl();
            case QUERY:
                return (T) new QueryDAOimpl();
            case STUDENTS:
                return (T) new StudentsDAOimpl();
            default:
                return null;
        }

    }
}
