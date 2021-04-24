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

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import webcourses.webcourse.entity.Course;
import webcourses.webcourse.entity.Lesson;
import webcourses.webcourse.entity.Material;
import webcourses.webcourse.service.MaterialServ;
import webcourses.webcourse.service.serviceImplementation.UserServImpl;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

/**
 * Rest implementation of Material controller.
 *
 * @since 0.0.1
 */
@Controller
public class MaterialController {
    private final UserServImpl userServ;
    private final MaterialServ materialServ;

    @Autowired
    public MaterialController(UserServImpl userServ, MaterialServ materialServ) {
        this.userServ = userServ;
        this.materialServ = materialServ;
    }

    @Value("${upload.path}")
    private String uploadPath;

    @PreAuthorize("hasAuthority('ADMIN' || 'TEACHER')")
    @GetMapping("/courses/{course}/lesson/{lesson}/create/material")
    public String addMaterailsPage(
        @PathVariable Course course,
        @PathVariable Lesson lesson,
        Model model) {
        model.addAttribute("course", course);
        model.addAttribute("lesson", lesson);
        return "course/lesson/material/create";
    }

    @PreAuthorize("hasAuthority('ADMIN' || 'TEACHER')")
    @PostMapping("/courses/{course}/lesson/{lesson}/create/material")
    public String addMaterails(
        @PathVariable Course course,
        @PathVariable Lesson lesson,
        @RequestParam String materialName,
        @RequestParam("file") MultipartFile file
    ) {

        if (userServ.isCreator(course)) {
            Material material = new Material();
            String fileName = file.getName();
            if (materialName.equals("")) {
                materialName = fileName;
            }

            String m_type = FilenameUtils.getExtension(fileName);
            int nextMaterialId = lesson.getMaterials().size() + 1;
            fileName = lesson.getId().toString() + '_' + nextMaterialId + '_' + fileName;

            if (file != null && !Objects.requireNonNull(file.getOriginalFilename()).isEmpty()) {
                File uploadDir = new File(uploadPath);

                if (!uploadDir.exists()) {
                    uploadDir.mkdir();
                }

                String uuidFile = UUID.randomUUID().toString();
                String resultFileName = uuidFile + "." + fileName;

                try {
                    file.transferTo(new File(uploadPath + "/" + resultFileName));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                material.setName(materialName);
                material.setFileName(resultFileName);
                material.setExtension(m_type);
                material.setLesson(lesson);
            }

            materialServ.save(material);
        }

        return "redirect:/courses/"+ course.getId() +"/lesson/" + lesson.getId() ;
    }

}
