package com.example.viewservice.controller;

import com.example.viewservice.dto.group.GroupRequest;
import com.example.viewservice.dto.group.GroupResponse;
import com.example.viewservice.dto.student.StudentRequest;
import com.example.viewservice.dto.student.StudentResponse;
import com.example.viewservice.dto.user.*;
import com.example.viewservice.service.ViewService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@Controller
@AllArgsConstructor
@RequestMapping("/university")
public class ViewController {
    private final ViewService viewService;

    @GetMapping("")
    public String login(Model model) {
        model.addAttribute("user", new LoginUserRequest());
        return "login/login";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute LoginUserRequest loginUserRequest,
                        BindingResult bindingResult,
                        Model model,
                        HttpSession session) {
        try {
            UserResponse userResponse = viewService.login(loginUserRequest);
            String userRole = viewService.getRoleForUser(userResponse.getUsername());
            session.setAttribute("role", userRole);
            return "redirect:/university/home";
        } catch (NoSuchElementException e) {
            throw e;
        }
    }

    @GetMapping("/home")
    public String home(HttpSession session, Model model) {
        model.addAttribute("session", session);
        return "home/home";
    }




    @GetMapping("/groups/all-groups")
    public String listGroups(@RequestParam(required = false, defaultValue = "name") String sortBy,
                             @RequestParam(required = false, defaultValue = "asc") String order,
                             Model model) {
        List<GroupResponse> groups = viewService.getAllGroupsSorted(sortBy, order);
        model.addAttribute("groups", groups);
        model.addAttribute("currentSort", sortBy);
        model.addAttribute("currentOrder", order);
        return "group/list";
    }

    @PostMapping("/groups/create")
    public String createGroup(@Valid @ModelAttribute("group") GroupRequest groupRequest,
                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "group/create";
        }
        viewService.createGroup(groupRequest);
        return "redirect:/university/groups/all-groups";
    }

    @GetMapping("/groups/create")
    public String createGroupForm(Model model) {
        model.addAttribute("group", new GroupRequest());
        return "group/create";
    }

    @GetMapping("/groups/{groupName}/edit")
    public String editGroupForm(@PathVariable String groupName,
                                Model model) {
        GroupResponse groupResponse = viewService.getGroupByName(groupName);
        model.addAttribute("group", groupResponse);
        return "group/edit";
    }

    @PutMapping("/groups/{groupName}/edit")
    public String updateGroup(@PathVariable String groupName,
                              @Valid @ModelAttribute("group") GroupRequest groupRequest,
                              BindingResult bindingResult,
                              Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("group", groupRequest);
            return "group/edit";
        }
        viewService.updateGroup(groupName, groupRequest);
        return "redirect:/university/groups/all-groups";
    }

    @DeleteMapping("/groups/{groupName}/delete")
    public String deleteGroup(@PathVariable String groupName) {
        viewService.deleteGroup(groupName);
        return "redirect:/university/groups/all-groups";
    }

    @GetMapping("/{groupName}/students/all-students")
    public String listStudents(@PathVariable String groupName,
                               @RequestParam(required = false, defaultValue = "surname") String sortBy,
                               @RequestParam(required = false, defaultValue = "asc") String order,
                               Model model) {
        List<StudentResponse> students = viewService.getAllStudentsSorted(groupName, sortBy, order);
        model.addAttribute("group", viewService.getGroupByName(groupName));
        model.addAttribute("students", students);
        model.addAttribute("currentSort", sortBy);
        model.addAttribute("currentOrder", order);
        return "student/list";
    }


    @PostMapping("/{groupName}/students/create")
    public String createStudent(@PathVariable String groupName,
                                @Valid @ModelAttribute("student") StudentRequest studentRequest,
                                BindingResult bindingResult,
                                Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("groupId", groupName);
            return "student/create";
        }
        viewService.createStudent(groupName, studentRequest);
        return "redirect:/university/{groupName}/students/all-students";
    }

    @GetMapping("/{groupName}/students/create")
    public String createStudentForm(@PathVariable String groupName,
                                    Model model) {
        StudentRequest studentRequest = new StudentRequest();
        model.addAttribute("student", studentRequest);
        model.addAttribute("groupName", groupName);
        return "student/create";
    }

    @GetMapping("/{groupName}/students/{id}/edit")
    public String editStudentForm(@PathVariable Long id,
                                  @PathVariable String groupName,
                                  Model model) {
        StudentResponse studentResponse = viewService.getByIdAndGroupName(id, groupName);
        model.addAttribute("student", studentResponse);
        model.addAttribute("groupName", groupName);
        return "student/edit";
    }

    @PutMapping("/{groupName}/students/{id}/update")
    public String updateStudent(@PathVariable String groupName,
                                @PathVariable Long id,
                                @Valid @ModelAttribute("student") StudentRequest studentRequest,
                                BindingResult bindingResult,
                                Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("groupName", groupName);
            return "student/edit";
        }
        viewService.updateStudent(id, groupName, studentRequest);
        return "redirect:/university/{groupName}/students/all-students";
    }

    @DeleteMapping("/{groupName}/students/{id}/delete")
    public String deleteStudent(@PathVariable Long id,
                                @PathVariable Long groupName) {
        viewService.deleteStudent(id, groupName);
        return "redirect:/university/{groupName}/students/all-students";
    }



    @GetMapping("/users/all-users")
    public String listUsers(Model model) {
        model.addAttribute("users", viewService.getAllUsers());
        return "user/list";
    }

    @GetMapping("/users/{id}/edit")
    public String editUserForm(@PathVariable Long id,
                               Model model) {
        UserResponse userResponse = viewService.getUserById(id);
        model.addAttribute("user", userResponse);
        model.addAttribute("role", Role.values());
        return "user/edit";
    }

    @PutMapping("/users/{id}/update")
    public String updateUser(@PathVariable Long id,
                             @ModelAttribute("user") UpdateUserRequest updateUserRequest,
                             BindingResult bindingResult,
                             Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("error", bindingResult.getAllErrors());
            model.addAttribute("role", Role.values());
            return "user/edit";
        }
        viewService.updateUser(id, updateUserRequest);
        return "redirect:/university/{groupName}/users/all-users";
    }

    @GetMapping("/users/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new CreateUserRequest());
        return "user/register";
    }

    @PostMapping("/users/register")
    public String registerUser(@ModelAttribute("user") CreateUserRequest createUserRequest,
                               Model model) {
        try {
            viewService.register(createUserRequest);
            return "redirect:/users/all";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "user/register";
        }
    }

    @DeleteMapping("/users/{id}/delete")
    public String deleteUser(@PathVariable Long id) {
        viewService.deleteUser(id);
        return "redirect:/users/all";
    }

}
