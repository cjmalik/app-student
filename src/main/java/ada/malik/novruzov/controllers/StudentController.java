package ada.malik.novruzov.controllers;


import ada.malik.novruzov.domain.Course;
import ada.malik.novruzov.domain.Student;
import ada.malik.novruzov.repositories.CourseRepository;
import ada.malik.novruzov.repositories.StudentRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class StudentController {
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public StudentController(StudentRepository studentRepository, CourseRepository courseRepository) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    @GetMapping("/students/{id}")
    public String showStudent(@PathVariable(name="id", required = true) String stId, Model model) {
        Optional<Student> std = studentRepository.findById(Long.parseLong(stId));
        model.addAttribute("studentInfo",std.get());
        Iterable<Course> cls = courseRepository.findAll();
        model.addAttribute("all_courses",cls);
        return "student_info";
    }

    @RequestMapping("/courses/enroll/{stId}/{CRN}")
    public String enrollCourse(@PathVariable(name="stId", required=true) Student std,
                                  @PathVariable(name="CRN", required=true) Course crn,
                                  Model model) {
        std.enrollToCourse(crn);
        crn.enrollStudent(std);
        studentRepository.save(std);
        courseRepository.save(crn);

        return "redirect:/students/"+std.getId();
    }

    @RequestMapping("/courses/drop/{stId}/{CRN}")
    public String dropCourse(@PathVariable(name="stId", required=true) Student std,
                               @PathVariable(name="CRN", required=true) Course crn,
                               Model model) {
        std.removeCourse(crn);
        crn.removeStudent(std);
        studentRepository.save(std);
        courseRepository.save(crn);

        return "redirect:/students/"+std.getId();
    }
    @RequestMapping("/students/delete/{id}")
    public String deleteStudent(@PathVariable(name="id", required = true) String stId, Model model) {
        Optional<Student> std = studentRepository.findById(Long.parseLong(stId));
        for(Course crs: std.get().getEnrolledCourses()) {
            crs.removeStudent(std.get());
            courseRepository.save(crs);
        }
        studentRepository.delete(std.get());
        return "redirect:/students/all";
    }

    @GetMapping("/students/all")
    public String showAllStudents(Model model) {
        Iterable<Student> stdList = studentRepository.findAll();
        model.addAttribute("studentList",stdList);

        return "student_list";
    }

    @RequestMapping("/students/new")
    public String newStudent(Model model) {
        Student std = new Student();
        model.addAttribute("student",std);

        return "std_data";
    }

    @RequestMapping("/students/update/{id}")
    public String updateStudent(@PathVariable(name="id", required = true) Student std,Model model) {
        model.addAttribute("student",std);

        return "std_data";
    }

    @PostMapping("/students/save/")
    public String saveStudent(@ModelAttribute Student std) {
        studentRepository.save(std);

        return "redirect:/students/all";
    }
}
