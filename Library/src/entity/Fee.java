package entity;

public class Fee extends SuperEntity {
    private int borrowId;
    private double fine;

    public Fee() {
    }

    public Fee(int borrowId, double fine) {
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

    @Override
    public String toString() {
        return "Fee{" +
                "borrowId=" + borrowId +
                ", fine=" + fine +
                '}';
    }
}
