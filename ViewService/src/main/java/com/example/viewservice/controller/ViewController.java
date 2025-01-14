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

    @GetMapping("/home")
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
                              BindingResult bindingResult,
                              Model model) {
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

    @GetMapping("/groups/{groupId}/edit")
    public String editGroupForm(@PathVariable Long groupId, Model model) {
        GroupResponse groupResponse = viewService.findGroupById(groupId);
        model.addAttribute("group", groupResponse);
        return "group/edit";
    }

    @PutMapping("/groups/{groupId}/update")
    public String updateGroup(@PathVariable Long groupId,
                              @Valid @ModelAttribute("group") GroupRequest groupRequest,
                              BindingResult bindingResult,
                              Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("group", groupRequest);
            return "group/edit";
        }
        viewService.updateGroup(groupId, groupRequest);
        return "redirect:/university/groups/all-groups";
    }

    @DeleteMapping("/groups/{groupId}/delete")
    public String deleteGroup(@PathVariable Long groupId) {
        viewService.deleteGroup(groupId);
        return "redirect:/university/groups/all-groups";
    }

    @GetMapping("/{groupId}/students/all-students")
    public String listStudents(@PathVariable Long groupId, Model model) {
        List<StudentResponse> students = viewService.getAllStudents(groupId);
        model.addAttribute("group", viewService.findGroupById(groupId));
        model.addAttribute("students", students);
        return "student/list";
    }

    @PostMapping("/{groupId}/students/create")
    public String createStudent(@PathVariable Long groupId,
                                @Valid @ModelAttribute("student") StudentRequest studentRequest,
                                BindingResult bindingResult,
                                Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("groupId", groupId);
            return "student/create";
        }
        viewService.createStudent(groupId, studentRequest);
        return "redirect:/university/{groupId}/students/all-students";
    }

    @GetMapping("/{groupId}/students/save")
    @PreAuthorize("hasAnyAuthority('DEAN', 'ADMIN')")
    public String createStudentForm(@PathVariable Long groupId, Model model) {
        StudentRequest studentRequest = new StudentRequest();
        model.addAttribute("student", studentRequest);
        model.addAttribute("groupId", groupId);
        return "student/create";
    }

    @GetMapping("/{groupId}/students/{id}/edit")
    @PreAuthorize("hasAnyAuthority('DEAN', 'ADMIN')")
    public String editStudentForm(@PathVariable Long id, @PathVariable Long groupId, Model model) {
        StudentResponse studentResponse = viewService.findByIdAndGroupId(id, groupId);
        model.addAttribute("student", studentResponse);
        model.addAttribute("groupId", groupId);
        return "student/edit";
    }

    @PutMapping("/{groupId}/students/{id}/update")
    public String updateStudent(@PathVariable Long groupId,
                                @PathVariable Long id,
                                @Valid @ModelAttribute("student") StudentRequest studentRequest,
                                BindingResult bindingResult,
                                Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("groupId", groupId);
            return "student/edit";
        }
        viewService.updateStudent(id, groupId, studentRequest);
        return "redirect:/university/{groupId}/students/all-students";
    }

    @DeleteMapping("/{groupId}/students/{id}/delete")
    public String deleteStudent(@PathVariable Long id, @PathVariable Long groupId) {
        viewService.deleteStudent(id, groupId);
        return "redirect:/university/{groupId}/students/all-students";
    }
}
