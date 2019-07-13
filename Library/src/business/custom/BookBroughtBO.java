package business.custom;

import business.SuperBO;
import dto.BookBroughtDTO;
import dto.FeeDTO;

public interface BookBroughtBO extends SuperBO<BookBroughtDTO, Integer> {

    boolean returnBook(BookBroughtDTO bookBroughtDTO, FeeDTO feeDTO) throws Exception;

}
