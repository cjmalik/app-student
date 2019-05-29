package ada.malik.novruzov.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String firstName;

    private String lastName;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "student_course",
            joinColumns = @JoinColumn(name="CRN", referencedColumnName="id"),
            inverseJoinColumns = @JoinColumn(name = "id", referencedColumnName="CRN")
    )
    private List<Course> enrolledCourses = new ArrayList<Course>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public List<Course> getEnrolledCourses() {
        return enrolledCourses;
    }

    public void setEnrolledCourses(List<Course> enrolledCourses) {
        this.enrolledCourses = enrolledCourses;
    }

    public void enrollToCourse(Course course) {
        enrolledCourses.add(course);
    }
    public void removeCourse(Course course) {
        enrolledCourses.remove(course);
    }
}
