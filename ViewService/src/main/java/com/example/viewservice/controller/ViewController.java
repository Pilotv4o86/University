package com.example.viewservice.controller;

import com.example.viewservice.dto.GroupRequest;
import com.example.viewservice.dto.GroupResponse;
import com.example.viewservice.dto.StudentRequest;
import com.example.viewservice.dto.StudentResponse;
import com.example.viewservice.service.ViewService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/university")
public class ViewController {
    private final ViewService viewService;

    @GetMapping("")
    public String home() {
        return "home/home";
    }

    @GetMapping("/groups/all-groups")
    public String listGroups(Model model) {
        List<GroupResponse> groups = viewService.getAllGroups();
        model.addAttribute("groups", groups);
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

    @GetMapping("/groups/save")
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

    @PutMapping("/groups/{groupName}/update")
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
                               Model model) {
        List<StudentResponse> students = viewService.getAllStudents(groupName);
        model.addAttribute("group", viewService.getGroupByName(groupName));
        model.addAttribute("students", students);
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

    @GetMapping("/{groupName}/students/save")
    @PreAuthorize("hasAnyAuthority('DEAN', 'ADMIN')")
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
}
