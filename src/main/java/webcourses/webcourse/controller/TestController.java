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
import webcourses.webcourse.entity.*;
import webcourses.webcourse.service.TestServ;
import webcourses.webcourse.service.serviceImplementation.TestServImpl;

import javax.servlet.http.HttpServletRequest;

/**
 * Rest implementation of Test controller.
 *
 * @since 0.0.1
 */
@Controller
public class TestController {
    private final TestServ testServ;

    @Autowired
    public TestController(TestServImpl testServ) {
        this.testServ = testServ;
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER')")
    @GetMapping("/courses/{course}/lesson/{lesson}/test/create")
    public String createTestPage(
            @PathVariable Course course,
            @PathVariable Lesson lesson,
            Model model
    ) {
        return testServ.createTest(course, lesson, model);
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER')")
    @PostMapping("/courses/{course}/lesson/{lesson}/test/create")
    public String createTest(
            @PathVariable Course course,
            @PathVariable Lesson lesson,
            @RequestParam("testName") String testName,
            @RequestParam("expDate") String expDate
    ) {
        return testServ.createTest(course, lesson, testName, expDate);
    }

    @GetMapping("/courses/{course}/lesson/{lesson}/test/{test}")
    public String testHomePage(
            @PathVariable Course course,
            @PathVariable Lesson lesson,
            @PathVariable Test test,
            Model model
    ) {
        return testServ.homePage(course, lesson, test, model);
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER')")
    @GetMapping("/courses/{course}/lesson/{lesson}/test/{test}/question/create")
    public String createQuestionPage(
            @PathVariable Course course,
            @PathVariable Lesson lesson,
            @PathVariable Test test,
            Model model
    ) {
        return testServ.createQuestion(course, lesson, test, model);
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER')")
    @PostMapping("/courses/{course}/lesson/{lesson}/test/{test}/question/create")
    public String createQuestion(
            HttpServletRequest request,
            @PathVariable Course course,
            @PathVariable Lesson lesson,
            @PathVariable Test test,
            @RequestParam("questionText") String questionText,
            @RequestParam("maxVal") String maxValue,
            @RequestParam("questionType") String questionType,
            @RequestParam("count") String count
    ) {
        return testServ.createQuestion(request, course, lesson, test, questionText, maxValue, questionType, count);
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER')")
    @GetMapping("/courses/{course}/lesson/{lesson}/test/{test}/question/{question}/edit")
    public String editQuestionPage(
            @PathVariable Course course,
            @PathVariable Lesson lesson,
            @PathVariable Test test,
            @PathVariable Question question,
            Model model
    ) {
        return testServ.editQuestion(course, lesson, test, question, model);
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER')")
    @PostMapping("/courses/{course}/lesson/{lesson}/test/{test}/question/{question}/edit")
    public String editQuestion(
            HttpServletRequest request,
            @PathVariable Course course,
            @PathVariable Lesson lesson,
            @PathVariable Test test,
            @PathVariable Question question,
            @RequestParam("questionText") String questionText,
            @RequestParam("maxVal") String maxValue,
            @RequestParam("questionType") String questionType
    ) {
        return testServ.editQuestion(request, course, lesson, test, question, questionText, maxValue, questionType);
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER')")
    @GetMapping("/courses/{course}lesson/{lesson}/test/{test}/question/{question}/delete")
    public String deleteQuestion(
            @PathVariable Course course,
            @PathVariable Lesson lesson,
            @PathVariable Test test,
            @PathVariable Question question
    ) {
        return testServ.deleteQuestion(course, lesson, test, question);
    }

    @PostMapping("/courses/{course}/lesson/{lesson}/test/{test}/result")
    public String testCheck(
            HttpServletRequest request,
            @PathVariable Course course,
            @PathVariable Lesson lesson,
            @PathVariable Test test
    ) {
        return testServ.check(request, course, lesson, test);
    }

    @GetMapping("/courses/{course}/lesson/{lesson}/test/{test}/result/{attempt}")
    public String result(
            @PathVariable Course course,
            @PathVariable Lesson lesson,
            @PathVariable Test test,
            @PathVariable int attempt,
            Model model
    ) {

        return testServ.result(course, lesson, test, attempt, model);
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER')")
    @GetMapping("/courses/{course}/lesson/{lesson}/test/{test}/result/all")
    public String resultForTeacher(
            @PathVariable Course course,
            @PathVariable Lesson lesson,
            @PathVariable Test test,
            Model model
    ) {
        return testServ.resultForTeacher(course, lesson, test, model);
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER')")
    @GetMapping("/courses/{course}/lesson/{lesson}/test/{test}/user/{user}/result/teacher/{attempt}")
    public String userResultForTeacher(
            @PathVariable Course course,
            @PathVariable Lesson lesson,
            @PathVariable Test test,
            @PathVariable User user,
            @PathVariable int attempt,
            Model model
    ) {
        return testServ.userResultForTeacher(course, lesson, test, user, attempt, model);
    }


}
