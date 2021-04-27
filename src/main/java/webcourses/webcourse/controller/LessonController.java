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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import webcourses.webcourse.entity.Course;
import webcourses.webcourse.entity.Lesson;
import webcourses.webcourse.entity.Material;
import webcourses.webcourse.entity.Test;
import webcourses.webcourse.service.AttemptServ;
import webcourses.webcourse.service.LessonServ;
import webcourses.webcourse.service.MaterialServ;
import webcourses.webcourse.service.UserServ;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Rest implementation of Lesson controller.
 *
 * @since 0.0.1
 */
@Controller
public class LessonController {
    private final UserServ userServ;
    private final LessonServ lessonServ;
    private final AttemptServ attemptServ;
    private final MaterialServ materialServ;

    @Autowired
    public LessonController(UserServ userServ, LessonServ lessonServ, AttemptServ attemptServ, MaterialServ materialServ) {
        this.userServ = userServ;
        this.lessonServ = lessonServ;
        this.attemptServ = attemptServ;
        this.materialServ = materialServ;
    }

    @GetMapping("/courses/{course}/lesson/create")
    public String creatLessonPage(@PathVariable Course course, Model model) {
        model.addAttribute("course", course);

        return "course/lesson/create";
    }

    @PostMapping("/courses/{course}/lesson/create")
    public String creatLesson(@PathVariable Course course,
                              @RequestParam String lessonName,
                              @RequestParam String lessonDescription,
                              @RequestParam Integer lessonDifficulty
    ) {
        if (userServ.isCreator(course)) {
            Lesson lesson = new Lesson();
            lesson.setName(lessonName);
            lesson.setDescription(lessonDescription);
            lesson.setDifficulty(lessonDifficulty);
            lesson.setCourse(course);
            lessonServ.saveLesson(lesson);
        }
        return "redirect:/courses/{course}/home";
    }

    @GetMapping("/courses/{course}/lesson/{lesson}")
    public String lessonHomePage(
        @PathVariable Lesson lesson,
        Model model,
        @PathVariable Course course) {
        Set<Test> tests = lessonServ.getAllLessonsTests(lesson);
        Map<Long, Boolean> solvedTests = new HashMap<>();

        boolean isSolved;
        for (Test test :
            tests) {
            isSolved = attemptServ.getTestAttempt(userServ.getCurrUser(), test) > 0;
            solvedTests.put(test.getId(), isSolved);
        }

        Optional<Material> materials = materialServ.getAllMaterials(lesson);

        model.addAttribute("lesson", lesson);
        model.addAttribute("course", course);
        model.addAttribute("isCreator", userServ.isCreator(course));
        model.addAttribute("solvedTests", solvedTests);
        model.addAttribute("tests", tests);
        model.addAttribute("materials", materials);

        return "course/lesson/homePage";
    }
}
