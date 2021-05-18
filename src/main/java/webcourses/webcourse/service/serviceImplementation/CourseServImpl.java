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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import webcourses.webcourse.entity.Course;
import webcourses.webcourse.entity.Lesson;
import webcourses.webcourse.entity.User;
import webcourses.webcourse.repos.CourseRepo;
import webcourses.webcourse.repos.LessonRepo;
import webcourses.webcourse.service.CourseServ;
import webcourses.webcourse.service.UserServ;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CourseServImpl implements CourseServ {
    private static final Logger LOGGER = LoggerFactory.getLogger(CourseServImpl.class);

    private final CourseRepo courseRepo;
    private final LessonRepo lessonRepo;
    private final UserServ userServ;

    @Value("${img.path}")
    private String uploadPath;

    @Autowired
    public CourseServImpl(CourseRepo courseRepo, LessonRepo lessonRepo, UserServ userServ) {
        this.courseRepo = courseRepo;
        this.lessonRepo = lessonRepo;
        this.userServ = userServ;
    }

    @Override
    public List<Course> getAllCourses() {
        return courseRepo.findAll();
    }

    private boolean isEnrolled(Course course, User user) {
        boolean result = false;
        for (Course c : user.getCourses()) {
            if (c.getName().equals(course.getName())) {
                result = true;
                break;
            }
        }
        return result;
    }

    @Override
    public List<Lesson> getAllLessons(Course course) {
        return lessonRepo.findAllByCourse(course);
    }

    @Override
    public String allCourses(String filter, Model model) {

        List<Course> allCourses = getAllCourses();

        if (!StringUtils.isEmpty(filter)) {
            allCourses = allCourses.stream().filter(
                    course -> course.getName().toLowerCase(Locale.ROOT).contains(filter.toLowerCase(Locale.ROOT))
            ).collect(Collectors.toList());
        }

        model.addAttribute("courses", allCourses);
        model.addAttribute("filter", filter);

        return "course/all";
    }

    @Override
    public String userCourses(Model model) {
        model.addAttribute("courses", userServ.getCurrUser().getCourses());

        return "course/myCourses";
    }

    @Override
    public String showCourse(Course course, Model model) {
        boolean enroll = isEnrolled(course, userServ.getCurrUser());
        model.addAttribute("course", course);
        model.addAttribute("enrolled", enroll);
        return "course/info";
    }

    @Override
    public String createCourse(String name, String description, MultipartFile file) {
        Course course = new Course();
        String filename = getFileNameByFile(file);

        course.setName(name);
        course.setDescription(description);
        course.setImageName(filename);

        User currUser = userServ.getCurrUser();
        currUser.setCreatedCourses(course);
        currUser.setCourses(course);
        userServ.saveUser(currUser);

        return "redirect:/courses";
    }

    @Override
    public String courseHomePage(Course course, Model model) {
        List<Lesson> lessons = getAllLessons(course);
        model.addAttribute("isCreator", userServ.isCreator(course));
        model.addAttribute("course", course);
        model.addAttribute("lessons", lessons);

        return "course/homePage";
    }

    @Override
    public String enroll(Course course) {
        User currUser = userServ.getCurrUser();
        currUser.setCourses(course);
        userServ.saveUser(currUser);

        return "redirect:/courses/" + course.getId() + "/home";
    }

    @Override
    public String createdCourses(Model model) {
        model.addAttribute("user", userServ.getCurrUser());

        return "course/createdCourses";
    }

    private String getFileNameByFile(MultipartFile file) {
        String filename = "course.png";

        if (file != null && !StringUtils.isEmpty(file.getOriginalFilename())) {
            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) {
                if (uploadDir.mkdir()) {
                    LOGGER.info("Path for course image was successful create.");
                } else {
                    LOGGER.warn("Path creation was not successful.");
                }
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + filename;

            try {
                file.transferTo(new File(uploadPath + "/" + resultFileName));
                filename = resultFileName;
                LOGGER.info("Course image was received.");
            } catch (IOException e) {
                LOGGER.error("Something went wrong.", e);
            }
        }

        return filename;
    }
}
