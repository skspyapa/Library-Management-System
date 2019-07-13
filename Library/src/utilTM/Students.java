package utilTM;

import java.time.LocalDate;

public class Students {
    private String studentId;
    private String name;
    private LocalDate birthDay;
    private String gender;
    private int departmentId;

    public Students() {
    }

    public Students(String studentId, String name, LocalDate birthDay, String gender, int departmentId) {
        this.studentId = studentId;
        this.name = name;
        this.birthDay = birthDay;
        this.gender = gender;
        this.departmentId = departmentId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(LocalDate birthDay) {
        this.birthDay = birthDay;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }
}
