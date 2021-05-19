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

import com.ibm.icu.text.Transliterator;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import webcourses.webcourse.util.FilesUtils;
import webcourses.webcourse.entity.Course;
import webcourses.webcourse.entity.Lesson;
import webcourses.webcourse.entity.Material;
import webcourses.webcourse.repos.MaterialRepo;
import webcourses.webcourse.service.MaterialServ;
import webcourses.webcourse.service.UserServ;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MaterialServImpl implements MaterialServ {
    private static final Logger LOGGER = LoggerFactory.getLogger(MaterialServImpl.class);

    private final MaterialRepo materialRepo;
    private final UserServ userServ;

    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    public MaterialServImpl(MaterialRepo materialRepo, UserServ userServ) {
        this.materialRepo = materialRepo;
        this.userServ = userServ;
    }

    @Override
    public List<Material> getAllMaterials(Lesson lesson) {
        List<Material> materials = materialRepo.findAll();

        return materials.stream()
                .filter(material -> {
                    Lesson materialLesson = material.getLesson();
                    return materialLesson.getId().equals(lesson.getId());
                })
                .collect(Collectors.toList());
    }

    @Override
    public void save(Material material) {
        materialRepo.save(material);
    }

    @Override
    public void download(HttpServletResponse response, Material material) {
        Path file = Paths.get(uploadPath, material.getFileName());
        if (Files.exists(file)) {
            Transliterator toLatinTrans = Transliterator.getInstance("Russian-Latin/BGN");
            String name = toLatinTrans.transliterate(material.getName().replace(' ', '_'));
            response.addHeader("Content-Disposition", "attachment; filename=" + name);

            try {
                Files.copy(file, response.getOutputStream());
                response.getOutputStream().flush();
                LOGGER.info("Success file download!");
            } catch (IOException ex) {
                LOGGER.error("Something went wrong", ex);
            }
        }
    }

    @Override
    public String addMaterial(Course course, Lesson lesson, Model model) {
        model.addAttribute("course", course);
        model.addAttribute("lesson", lesson);

        return "course/lesson/material/create";
    }

    @Override
    public String addMaterial(Course course, Lesson lesson, String name, MultipartFile file, Model model) {
        String resultView = "redirect:/courses/" + course.getId() + "/lesson/" + lesson.getId();
        if (!StringUtils.isEmpty(file.getOriginalFilename()) && userServ.isCreator(course)) {
            Material material = new Material();
            String fileName = FilesUtils.getFileNameByFile(file, null, uploadPath);
            String mType = FilenameUtils.getExtension(fileName);

            if (name.equals("")) {
                name = file.getOriginalFilename();
            } else {
                name += "." + mType;
            }

            material.setName(name);
            material.setFileName(fileName);
            material.setExtension(mType);
            material.setLesson(lesson);
            save(material);
        } else {
            model.addAttribute("materialError", "Material file couldn't be null!");
            resultView = "course/lesson/material/create";
        }

        return resultView;
    }
}
