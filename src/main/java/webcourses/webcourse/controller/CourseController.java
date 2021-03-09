/*
 * Copyright (c) 2011-2012, Qulice.com
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met: 1) Redistributions of source code must retain the above
 * copyright notice, this list of conditions and the following
 * disclaimer. 2) Redistributions in binary form must reproduce the above
 * copyright notice, this list of conditions and the following
 * disclaimer in the documentation and/or other materials provided
 * with the distribution. 3) Neither the name of the Qulice.com nor
 * the names of its contributors may be used to endorse or promote
 * products derived from this software without specific prior written
 * permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT
 * NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL
 * THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package webcourses.webcourse.controller;

import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import webcourses.webcourse.entity.Course;
import webcourses.webcourse.entity.User;
import webcourses.webcourse.service.CourseServ;

/**
 * Rest implementation of Course controller.
 *
 * @since 0.0.1
 */
@RestController
public class CourseController {
    private final CourseServ courseServ;

    @Autowired
    public CourseController(CourseServ courseServ) {
        this.courseServ = courseServ;
    }

    @GetMapping("/courses")
    public String allCourses(@RequestParam(required = false, defaultValue = "") String filter, Model model) {
        List<Course> courses;

        if (filter != null && !filter.isEmpty()) {
            courses = courseServ.findByName(filter);
        } else {
            courses = courseServ.getAllCourses();
        }

        model.addAttribute("courses", courses);
        model.addAttribute("filter", filter);

        return "course/all";
    }

    @GetMapping("/courses/create")
    public String createCoursePage() {
        return "course/create";
    }

    @PostMapping("/courses/create")
    public String createCourse(
        @RequestParam final String course_name,
        @RequestParam final String description,
        @RequestParam final String file
        ) {
        Course course = new Course();
        String filename = null;

        if (file != null && !file.isEmpty()) {
            //todo: сохранение файлов filename
        } else {
            filename = "course.png";
        }

        //todo: создать абстракцию для создания.изменения курса?
        course.setName(course_name);
        course.setDescription(description);
        course.setImageName(filename);

        //todo: узнать как брать текущего User
        //todo: внести в бд новые курсы текущего User

        return "course/all";
    }

    @GetMapping("/courses/{course}")
    public String showCourse(@PathVariable Course course, Model model) {
        model.addAttribute("course", course);
        return "course/info";
    }

    @GetMapping("/courses/createdCourses")
    public String createdCourses() {
        return "course/createdCourses";
    }

    @GetMapping("/courses/{course}/enroll")
    public String enroll(@PathVariable Course course) {
        //todo: course.setUsers(currentUser);
        return "redirect:";
    }

    @GetMapping("/courses/my")
    public String userCourses() {


        return "course/myCourses";
    }

    @GetMapping("/courses/{course}/home")
    public String courseHomePage(@PathVariable Course course) {

        return "course/homePage";
    }

    private boolean isCreator(Course course) {
        Set<User> creators = course.getAuthors();
        boolean is_creator = false;
        for (User creator:
             creators) {
            //todo: понять, как брать текущего пользователя
            // if (creator.getId() == currUser.getId());
        }
        return is_creator;
    }
}
