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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import webcourses.webcourse.entity.Course;
import webcourses.webcourse.entity.Lesson;
import webcourses.webcourse.entity.Test;
import webcourses.webcourse.service.serviceImplementation.UserServImpl;

/**
 * Rest implementation of Test controller.
 *
 * @since 0.0.1
 */
@Controller
public class TestController {
    private final UserServImpl userServ;

    @Autowired
    public TestController(UserServImpl userServ) {
        this.userServ = userServ;
    }

    @PreAuthorize("hasAuthority('ADMIN' || 'TEACHER')")
    @PostMapping("/courses/{course}/lesson/{lesson}/test/create")
    public String creatTest(
            @PathVariable Course course,
            @PathVariable Lesson lesson,
            @RequestParam("testName") String testName,
            @RequestParam("expDate") String expDate
    ) {

        if (userServ.isCreator(course)) {
            Test test = new Test();
            test.setName(testName);
            test.setExpDate(expDate);
            test.setLesson(lesson);
        }
        return "redirect:/courses/"+ course.getId() +"/lesson/" + lesson.getId();
    }


}
