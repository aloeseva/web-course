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
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import webcourses.webcourse.controller.ControllerUtils;
import webcourses.webcourse.entity.User;
import webcourses.webcourse.service.GeneralServ;
import webcourses.webcourse.service.UserServ;

import java.util.HashMap;
import java.util.Map;

@Service
public class GeneralServImpl implements GeneralServ {
    private static final String RETURN_REGISTER_VIEW = "general/register";
    private static final String RETURN_LOGIN_VIEW = "redirect:/login";
    private static final Logger LOGGER = LoggerFactory.getLogger(GeneralServImpl.class);

    private final UserServ userServ;

    @Autowired
    public GeneralServImpl(UserServImpl userServ) {
        this.userServ = userServ;
    }

    @Override
    public String registration(String passwordConfirm, User user, Model model, BindingResult bindingResult) {
        String resultView = RETURN_LOGIN_VIEW;
        Map<String, String> attributes = new HashMap<>();
        String userPassword = user.getPassword();

        boolean isConfirmEmpty = isConfirmEmpty(passwordConfirm, attributes);
        boolean isValidPassword = validatePassword(passwordConfirm, attributes, userPassword);

        boolean isInvalid = isConfirmEmpty || bindingResult.hasErrors() || !isValidPassword;
        if (isInvalid) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            attributes.putAll(errors);
            for (String error :
                    errors.values()) {
                LOGGER.error(error);
            }
            resultView = RETURN_REGISTER_VIEW;
        } else {
            resultView = validateUser(user, attributes, resultView);
        }

        model.mergeAttributes(attributes);

        return resultView;
    }

    private String validateUser(User user, Map<String, String> attributes, String resultView) {
        if (!userServ.addUser(user)) {
            attributes.put("usernameError", "User exists!");
            resultView = RETURN_REGISTER_VIEW;
            LOGGER.warn("User exists!");
        } else {
            LOGGER.info("User successfully validate. Registration complete.");
        }

        return resultView;
    }

    private boolean isConfirmEmpty(String passwordConfirm, Map<String, String> attributes) {
        boolean isConfirmEmpty = StringUtils.isEmpty(passwordConfirm);
        if (isConfirmEmpty) {
            attributes.put("password2Error", "Password confirmation cannot be empty");
        }

        return isConfirmEmpty;
    }

    private boolean validatePassword(String passwordConfirm, Map<String, String> attributes, String userPassword) {
        boolean isPasswordValidate = true;
        if (userPassword != null && !userPassword.equals(passwordConfirm)) {
            attributes.put("passwordError", "Passwords are different!");
            isPasswordValidate = false;
            LOGGER.warn("Passwords are different!");
        } else {
            LOGGER.info("Password was successfully confirmed.");
        }
        return isPasswordValidate;
    }
}
