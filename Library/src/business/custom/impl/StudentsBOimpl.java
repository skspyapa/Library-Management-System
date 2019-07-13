package business.custom.impl;

import business.custom.StudentsBO;
import dao.DAOFactory;
import dao.DAOTypes;
import dao.custom.StudentsDAO;
import dto.StudentsDTO;
import entity.Students;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentsBOimpl implements StudentsBO {
    StudentsDAO studentsDAO = DAOFactory.getInstance().getDAO(DAOTypes.STUDENTS);

    @Override
    public String getStudentName(Integer id) throws SQLException {
        return studentsDAO.findName(id);
    }

    @Override
    public int getMaximumId() throws SQLException {
        return studentsDAO.findMaximumId();
    }


    @Override
    public List<StudentsDTO> getAll() throws SQLException {
        List<Students> allStudents = studentsDAO.findAll();
        List<StudentsDTO> studentsDTOS = new ArrayList<>();
        for (Students students : allStudents) {
            studentsDTOS.add(new StudentsDTO(students.getStudentId(), students.getName(), students.getBirthDay(), students.getGender(), students.getDepartmentId()));
        }
        return studentsDTOS;
    }

    @Override
    public boolean save(StudentsDTO dto) throws SQLException {

        return studentsDAO.save(new Students(dto.getStudenetId(), dto.getName(), dto.getBirthDay(), dto.getGender(), dto.getDepartmentId()));
    }

    @Override
    public boolean remove(Integer id) throws SQLException {

        return studentsDAO.delete(id);
    }

    @Override
    public boolean update(StudentsDTO dto) throws SQLException {
        return studentsDAO.update(new Students(dto.getStudenetId(), dto.getName(), dto.getBirthDay(), dto.getGender(), dto.getDepartmentId()));
    }

    @Override
    public StudentsDTO get(Integer id) throws SQLException {
        Students students = studentsDAO.find(id);
        return new StudentsDTO(students.getStudentId(), students.getName(), students.getBirthDay(), students.getGender(), students.getDepartmentId());
    }
}
