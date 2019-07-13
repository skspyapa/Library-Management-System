package business;


//import business.custom.impl.*;

import business.custom.impl.*;

public class BOFactory {

    private static BOFactory boFactory;

    private BOFactory() {
    }

    public static BOFactory getInstance() {
        if (boFactory == null) {
            boFactory = new BOFactory();
        }
        return boFactory;
    }

    public <T> T getBO(BOTypes boTypes) {
        switch (boTypes) {
            case BOOKS:
                return (T) new BooksBOimpl();
            case BORROW:
                return (T) new BorrowBOimpl();
            case BOOK_BROUGHT:
                return (T) new BookBroughtBOimpl();
            case STUDENTS:
                return (T) new StudentsBOimpl();
            case DEPARTMENT:
                return (T) new DepartmentBOimpl();
            case RETURN:
                return (T) new BookBroughtBOimpl();
            case CATEGORY:
                return (T) new CategoryBOimpl();
            case FEE:
                return (T) new FeeBOimpl();
            default:
                return null;
        }
    }
}
