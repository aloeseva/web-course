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
import org.springframework.http.MediaType;
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

import javax.servlet.http.HttpServletResponse;

/**
 * Rest implementation of Material controller.
 *
 * @since 0.0.1
 */
@Controller
public class MaterialController {
    private final MaterialServ materialServ;

    @Autowired
    public MaterialController(MaterialServ materialServ) {
        this.materialServ = materialServ;
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER')")
    @GetMapping("/courses/{course}/lesson/{lesson}/create/material")
    public String addMaterialsPage(
            @PathVariable Course course,
            @PathVariable Lesson lesson,
            Model model
    ) {
        return materialServ.addMaterial(course, lesson, model);
    }

    @GetMapping(
            value = "/material/{material}",
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE
    )
    public void downloadMaterial(
            HttpServletResponse response,
            @PathVariable Material material
    ) {
        materialServ.download(response, material);
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER')")
    @PostMapping("/courses/{course}/lesson/{lesson}/create/material")
    public String addMaterials(
            @PathVariable Course course,
            @PathVariable Lesson lesson,
            @RequestParam String materialName,
            @RequestParam("file") MultipartFile file
    ) {
        return materialServ.addMaterial(course, lesson, materialName, file);
    }

}
