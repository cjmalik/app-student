package ada.malik.novruzov.controllers;


import ada.malik.novruzov.domain.Course;
import ada.malik.novruzov.domain.Student;
import ada.malik.novruzov.repositories.CourseRepository;
import ada.malik.novruzov.repositories.StudentRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class CourseController {
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public CourseController(StudentRepository studentRepository, CourseRepository courseRepository) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    @GetMapping("/courses/{id}")
    public String showCourses(@PathVariable(name="id", required = true) String cid, Model model) {
        Optional<Course> cls = courseRepository.findById(Long.parseLong(cid));
        model.addAttribute("courseInfo",cls.get());
        Iterable<Student> std = studentRepository.findAll();
        model.addAttribute("all_students",std);
        return "course_info";
    }

    @RequestMapping("/courses/delete/{id}")
    public String deleteCourse(@PathVariable(name="id", required = true) String cid, Model model) {
        Optional<Course> cls = courseRepository.findById(Long.parseLong(cid));

            for(Student stud: cls.get().getEnrolledStudents()) {
                stud.removeCourse(cls.get());
                studentRepository.save(stud);
            }
        courseRepository.delete(cls.get());
        return "redirect:/courses/all";
        }

    @GetMapping("/courses/all")
    public String showAllCourses(Model model) {
        Iterable<Course> cls = courseRepository.findAll();
        model.addAttribute("courseList",cls);

        return "course_list";
    }

    @RequestMapping("/courses/new")
    public String newCourse(Model model) {
        Course cls = new Course();
        model.addAttribute("course",cls);

        return "cls_data";
    }

    @RequestMapping("/courses/update/{id}")
    public String updateCourse(@PathVariable(name="id", required = true) Course cid,Model model) {
        model.addAttribute("course",cid);

        return "cls_data";
    }

    @PostMapping("/courses/save/")
    public String saveCourse(@ModelAttribute Course cid) {
        courseRepository.save(cid);

        return "redirect:/courses/all";
    }
}
