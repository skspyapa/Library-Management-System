package business.custom.impl;

import business.custom.FeeBO;
import dao.DAOFactory;
import dao.DAOTypes;
import dao.custom.FeeDAO;
import dto.FeeDTO;
import entity.Fee;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class FeeBOimpl implements FeeBO {
    private FeeDAO feeDAO = DAOFactory.getInstance().getDAO(DAOTypes.FEE);

    @Override
    public List<FeeDTO> getAll() throws SQLException {
        return feeDAO.findAll().stream().map(fee -> new FeeDTO(fee.getBorrowId(), fee.getFine())).collect(Collectors.toList());
    }

    @Override
    public boolean save(FeeDTO dto) throws SQLException {
        return feeDAO.save(new Fee(dto.getBorrowId(), dto.getFine()));
    }

    @Override
    public boolean remove(Integer id) throws SQLException {
        return feeDAO.delete(id);
    }

    @Override
    public boolean update(FeeDTO dto) throws SQLException {
        return feeDAO.update(new Fee(dto.getBorrowId(), dto.getFine()));
    }

    @Override
    public FeeDTO get(Integer id) throws SQLException {
        Fee fee = feeDAO.find(id);
        return new FeeDTO(fee.getBorrowId(), fee.getFine());
    }
}
