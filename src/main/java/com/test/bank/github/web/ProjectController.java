package com.test.bank.github.web;

import com.test.bank.github.dto.ProjectDTO;
import com.test.bank.github.response.InitProjectResponse;
import com.test.bank.github.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.json.JsonObject;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping("/project")
    public InitProjectResponse initProject(@RequestBody ProjectDTO projectDTO) {
        return projectService.initProject(projectDTO);
    }

    @GetMapping("/project/{repoName}")
    public JsonObject getProject(@PathVariable String repoName) {
        try {
            return projectService.getRepo(repoName).json();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
