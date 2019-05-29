package ada.malik.novruzov.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long CRN;

    @NotNull
    private String courseName;

    @NotNull
    private String courseDesc;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "enrolledCourses")
    private List<Student> enrolledStudents = new ArrayList<Student>();

    public Long getCRN() {
        return CRN;
    }

    public void setCRN(Long CRN) {
        this.CRN = CRN;
    }

    public String getCourseDesc() {
        return courseDesc;
    }

    public void setCourseDesc(String courseDesc) {
        this.courseDesc = courseDesc;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public List<Student> getEnrolledStudents() {
        return enrolledStudents;
    }

    public void setEnrolledStudents(List<Student> enrolledStudents) {
        this.enrolledStudents = enrolledStudents;
    }

    public void enrollStudent(Student std) {
        enrolledStudents.add(std);
    }
    public void removeStudent(Student std) {
        enrolledStudents.remove(std);
    }
}
