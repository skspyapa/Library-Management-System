package dto;

public class FeeDTO {
    private int borrowId;
    private double fine;

    public FeeDTO() {
    }

    public FeeDTO(int borrowId, double fine) {
        this.borrowId = borrowId;
        this.fine = fine;
    }

    public int getBorrowId() {
        return borrowId;
    }

    public void setBorrowId(int borrowId) {
        this.borrowId = borrowId;
    }

    public double getFine() {
        return fine;
    }

    public void setFine(double fine) {
        this.fine = fine;
    }
}
