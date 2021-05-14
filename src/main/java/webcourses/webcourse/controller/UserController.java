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

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import webcourses.webcourse.entity.User;
import webcourses.webcourse.entity.enums.Role;
import webcourses.webcourse.service.UserServ;

/**
 * Rest implementation of User controller.
 *
 * @since 0.0.1
 */
@Controller
public class UserController {
    private final UserServ userServ;

    @Autowired
    public UserController(UserServ userServ) {
        this.userServ = userServ;
    }

    @GetMapping("/user")
    public String userPage(Model model) {
        return userServ.userPage(model);
    }

    @PostMapping("/user/edit")
    public String editUser(
            @RequestParam final Map<String, String> form,
            @RequestParam("userId") final User user
    ) {
        return userServ.editUser(form, user);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/user/all")
    public String allUsers(Model model) {
        return userServ.getAllUsers(model);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/user/changeRole")
    public String changeUserRole(
            @RequestParam final Map<String, String> form,
            @RequestParam("userId") final User user
    ) {
        return userServ.changeRole(form, user);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/user/deleteUser")
    public String deleteUser(@RequestParam("userId") final User user) {
        return userServ.deleteUser(user);
    }
}
