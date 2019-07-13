package dto;

import java.time.LocalDate;

public class StudentsDTO {
    private int studenetId;
    private String name;
    private LocalDate birthDay;
    private String gender;
    private int departmentId;

    public StudentsDTO() {
    }

    public StudentsDTO(int studenetId, String name, LocalDate birthDay, String gender, int departmentId) {
        this.studenetId = studenetId;
        this.name = name;
        this.birthDay = birthDay;
        this.gender = gender;
        this.departmentId = departmentId;
    }

    public int getStudenetId() {
        return studenetId;
    }

    public void setStudenetId(int studenetId) {
        this.studenetId = studenetId;
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
