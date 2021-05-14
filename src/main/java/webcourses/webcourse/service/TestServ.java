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

package webcourses.webcourse.service;

import org.springframework.ui.Model;
import webcourses.webcourse.entity.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface TestServ {
    List<Test> getAllTests(Lesson lesson);

    String creatTest(Course course, Lesson lesson, Model model);

    String creatTest(Course course, Lesson lesson, String name, String date);

    String homePage(Course course, Lesson lesson, Test test, Model model);

    String creatQuestion(Course course, Lesson lesson, Test test, Model model);

    String creatQuestion(HttpServletRequest request, Course course, Lesson lesson, Test test, String text, String maxValue, String type, String count);

    String editQuestion(Course course, Lesson lesson, Test test, Question question, Model model);

    String editQuestion(HttpServletRequest request, Course course, Lesson lesson, Test test, Question question, String text, String maxValue, String type);

    String deleteQuestion(Course course, Lesson lesson, Test test, Question question);

    String check(HttpServletRequest request, Course course, Lesson lesson, Test test);

    String result(Course course, Lesson lesson, Test test, int attempt, Model model);

    String resultForTeacher(Course course, Lesson lesson, Test test, Model model);

    String userResultForTeacher(Course course, Lesson lesson, Test test, User user, int attempt, Model model);
}
