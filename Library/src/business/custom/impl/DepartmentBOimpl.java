package business.custom.impl;

import business.custom.DepartmentBO;
import dao.DAOFactory;
import dao.DAOTypes;
import dao.custom.DepartmentDAO;
import dto.DepartmentDTO;
import entity.Department;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class DepartmentBOimpl implements DepartmentBO {
    private DepartmentDAO departmentDAO = DAOFactory.getInstance().getDAO(DAOTypes.DEPARTMENT);
    @Override
    public List<DepartmentDTO> getAll() throws SQLException {
        return departmentDAO.findAll().stream().map(department -> new DepartmentDTO(department.getDepartmentId(),department.getName())).collect(Collectors.toList());
    }

    @Override
    public boolean save(DepartmentDTO dto) throws SQLException {
        return departmentDAO.save(new Department(dto.getDepartmentId(),dto.getName()));
    }

    @Override
    public boolean remove(Integer dtoId) throws SQLException {
        return departmentDAO.delete(dtoId);
    }

    @Override
    public boolean update(DepartmentDTO dto) throws SQLException {
        return departmentDAO.update(new Department(dto.getDepartmentId(),dto.getName()));
    }

    @Override
    public DepartmentDTO get(Integer dtoId) throws SQLException {
        Department department = departmentDAO.find(dtoId);
        return new DepartmentDTO(department.getDepartmentId(),department.getName());
    }

    @Override
    public int getMaximumBorrowId() throws SQLException {
        int maxId = departmentDAO.maximumBorrowId();
        return maxId + 1;
    }
}
