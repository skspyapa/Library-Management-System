package business.custom;

import business.SuperBO;
import dto.StudentsDTO;

import java.sql.SQLException;

public interface StudentsBO extends SuperBO<StudentsDTO, Integer> {
    String getStudentName(Integer id) throws SQLException;

    int getMaximumId() throws SQLException;
}
