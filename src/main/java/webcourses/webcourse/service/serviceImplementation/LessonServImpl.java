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

package webcourses.webcourse.service.serviceImplementation;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import webcourses.webcourse.entity.Course;
import webcourses.webcourse.entity.Lesson;
import webcourses.webcourse.entity.Material;
import webcourses.webcourse.entity.Test;
import webcourses.webcourse.entity.dto.SolvedTest;
import webcourses.webcourse.repos.LessonRepo;
import webcourses.webcourse.service.*;

@Service
public class LessonServImpl implements LessonServ {
    private final LessonRepo lessonRepo;
    private final UserServ userServ;
    private final TestServ testServ;
    private final AttemptServ attemptServ;
    private final MaterialServ materialServ;

    @Autowired
    public LessonServImpl(LessonRepo lessonRepo, UserServ userServ, TestServ testServ, AttemptServ attemptServ, MaterialServ materialServ) {
        this.lessonRepo = lessonRepo;
        this.userServ = userServ;
        this.testServ = testServ;
        this.attemptServ = attemptServ;
        this.materialServ = materialServ;
    }


    @Override
    public List<Lesson> getAllLessons() {
        return lessonRepo.findAll();
    }

    @Override
    public void saveLesson(Lesson lesson) {
        lessonRepo.save(lesson);
    }

    @Override
    public String creatLesson(Course course, Model model) {

        model.addAttribute("course", course);

        return "course/lesson/create";
    }

    @Override
    public String creatLesson(Course course, String name, String description, Integer difficulty) {
        if (userServ.isCreator(course)) {
            Lesson lesson = new Lesson();
            lesson.setName(name);
            lesson.setDescription(description);
            lesson.setDifficulty(difficulty);
            lesson.setCourse(course);
            saveLesson(lesson);
        }
        return "redirect:/courses/" + course.getId() + "/home";
    }

    @Override
    public String homePage(Lesson lesson, Course course, Model model) {
        List<Test> tests = testServ.getAllTests(lesson);
        List<SolvedTest> solvedTests = new LinkedList<>();

        boolean isSolved;
        for (Test test :
                tests) {
            isSolved = attemptServ.getTestAttempt(userServ.getCurrUser(), test) > 0;
            SolvedTest solvedTest = new SolvedTest();
            solvedTest.setSolved(isSolved);
            solvedTest.setTest(test);
            solvedTests.add(solvedTest);
        }

        List<Material> materials = materialServ.getAllMaterials(lesson);

        model.addAttribute("lesson", lesson);
        model.addAttribute("course", course);
        model.addAttribute("isCreator", userServ.isCreator(course));
        model.addAttribute("tests", solvedTests);
        model.addAttribute("materials", materials);

        return "course/lesson/homePage";
    }
}
