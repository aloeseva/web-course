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

package webcourses.webcourse.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public final class FilesUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(FilesUtils.class);

    private FilesUtils() {
    }

    public static String getFileNameByFile(MultipartFile file, String defaultFileName, String uploadPath) {
        String fileName = defaultFileName;

        if (file != null) {
            String originalFilename = file.getOriginalFilename();
            if (!StringUtils.isEmpty(originalFilename)) {
                File uploadDir = new File(uploadPath);

                if (!uploadDir.exists()) {
                    if (uploadDir.mkdir()) {
                        LOGGER.info("Path was successful create.");
                    } else {
                        LOGGER.warn("Path creation was not successful.");
                    }
                }

                String uuidFile = UUID.randomUUID().toString();
                String resultFileName = uuidFile + "." + originalFilename;

                try {
                    file.transferTo(new File(uploadPath + "/" + resultFileName));
                    fileName = resultFileName;
                    LOGGER.info("File was received.");
                } catch (IOException e) {
                    LOGGER.error("Something went wrong.", e);
                }
            }
        }

        return fileName;
    }
}
