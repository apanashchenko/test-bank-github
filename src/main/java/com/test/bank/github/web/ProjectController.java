package com.test.bank.github.web;

import com.test.bank.github.dto.ProjectDTO;
import com.test.bank.github.response.InitProjectResponse;
import com.test.bank.github.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping("/project")
    public InitProjectResponse initProject(@RequestBody ProjectDTO projectDTO) {
        return InitProjectResponse.of(projectService.initProject(projectDTO).getAbsolutePath());
    }

}
