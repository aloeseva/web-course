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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import webcourses.webcourse.auth.AuthenticationFacade;
import webcourses.webcourse.entity.Course;
import webcourses.webcourse.entity.User;
import webcourses.webcourse.entity.enums.Role;
import webcourses.webcourse.repos.UserRepo;
import webcourses.webcourse.service.UserServ;
import webcourses.webcourse.util.ControllerUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServImpl implements UserServ, UserDetailsService {
    private static final String RETURN_ALL_USER_VIEW = "user/all";
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServImpl.class);

    private final UserRepo userRepo;
    private final AuthenticationFacade facade;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServImpl(UserRepo userRepo, AuthenticationFacade facade, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.facade = facade;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String getAllUsers(Model model) {
        model.addAttribute("users", userRepo.findAll());
        model.addAttribute("roles", Role.values());
        return RETURN_ALL_USER_VIEW;
    }

    @Override
    public String deleteUser(User user) {
        userRepo.delete(user);
        return "redirect:/" + RETURN_ALL_USER_VIEW;
    }

    @Override
    public void saveUser(User user) {
        userRepo.save(user);
    }

    @Override
    public String editUser(Map<String, String> form, User user, Model model) {
        String resultView = "redirect:/user";
        //todo: переименовать значения из form, добавить проверки в валидацию
        if (true) {
            user.setUsername(form.get("loginChange"));
            user.setEmail(form.get("emailChange"));
            String firstName = form.get("firstNameChange");
            String lastName = form.get("lastNameChange");
            if (!firstName.equals("")) {
                user.setFirstName(firstName);
            } else {
                user.setFirstName(null);
            }
            if (!lastName.equals("")) {
                user.setLastName(lastName);
            } else {
                user.setLastName(null);
            }
        } else {
//            fillUserModel(model, user);
//            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
//            model.mergeAttributes(errors);
//            resultView = "user/homePage";
        }

        saveUser(user);
        return resultView;
    }

    //todo: сделвть

//    private boolean isNameValid(String name) {
//        if (name.length() > 5)
//    }

    @Override
    public User findByName(String userName) {
        return userRepo.findByUsername(userName);
    }

    @Override
    public String userPage(Model model) {
        User user = findByName(getCurrUser().getUsername());
        fillUserModel(model, user);
        return "user/homePage";
    }

    private void fillUserModel(Model model, User user) {
        boolean isAdmin = user.isAdmin();
        boolean isTeacher = user.isTeacher();
        model.addAttribute("username", user.getUsername());
        model.addAttribute("email", user.getEmail());
        model.addAttribute("firstName", user.getFirstName());
        model.addAttribute("lastName", user.getLastName());
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("isTeacher", isTeacher);
    }

    @Override
    public User getCurrUser() {
        return userRepo.findByUsername(facade.getAuthentication().getName());
    }

    @Override
    public String changeRole(Map<String, String> form, User user) {
        final Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());

        user.getRoles().clear();

        for (final String key : form.keySet()) {
            if (roles.contains(key)) {
                user.getRoles().add(Role.valueOf(key));
            }
        }

        saveUser(user);
        return "redirect:/" + RETURN_ALL_USER_VIEW;
    }

    @Override
    public boolean isCreator(Course course) {
        Set<User> creators = course.getAuthors();
        boolean isCreator = false;
        for (User creator :
                creators) {
            if (creator.getId().equals(getCurrUser().getId())) {
                isCreator = true;
                break;
            }
        }
        return isCreator;
    }

    @Override
    public boolean addUser(User user) {
        User userFromDb = userRepo.findByUsername(user.getUsername());

        if (userFromDb != null || user.getUsername().equals("")) {
            return false;
        }

        Date date = new Date();
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRegistrationDate(date);

        userRepo.save(user);

        return true;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);

        if (user == null) {
            try {
                throw new UsernameNotFoundException("User not found");
            } catch (UsernameNotFoundException ex) {
                LOGGER.error("Error while searching user", ex);
            }
        }

        return user;
    }
}
