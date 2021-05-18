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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import webcourses.webcourse.entity.Course;
import webcourses.webcourse.service.CourseServ;

/**
 * Implementation of Course controller.
 *
 * @since 0.0.1
 */
@Controller
public class CourseController {
    private final CourseServ courseServ;

    @Autowired
    public CourseController(CourseServ courseServ) {
        this.courseServ = courseServ;
    }

    @GetMapping("/courses")
    public String allCourses(
            @RequestParam(required = false, defaultValue = "") String filter,
            Model model
    ) {
        return courseServ.allCourses(filter, model);
    }

    @GetMapping("/courses/my")
    public String userCourses(Model model) {
        return courseServ.userCourses(model);
    }

    @GetMapping("/courses/{course}")
    public String showCourse(@PathVariable Course course, Model model) {
        return courseServ.showCourse(course, model);
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER')")
    @GetMapping("/courses/create")
    public String createCoursePage() {
        return "course/create";
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER')")
    @PostMapping("/courses/create")
    public String createCourse(
            @RequestParam("courseName") final String courseName,
            @RequestParam("description") final String description,
            @RequestParam("file") MultipartFile file
    ) {
        return courseServ.createCourse(courseName, description, file);
    }

    @GetMapping("/courses/{course}/home")
    public String courseHomePage(@PathVariable Course course, Model model) {
        return courseServ.courseHomePage(course, model);
    }

    @GetMapping("/courses/{course}/enroll")
    public String enroll(@PathVariable Course course) {
        return courseServ.enroll(course);
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER')")
    @GetMapping("/courses/createdCourses")
    public String createdCourses(Model model) {
        return courseServ.createdCourses(model);
    }
}
