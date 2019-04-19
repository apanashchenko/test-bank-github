package com.test.bank.github.web;

import com.test.bank.github.dto.RepoDTO;
import com.test.bank.github.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.json.JsonObject;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping("/project")
    public JsonObject initProject(@RequestBody RepoDTO repoDTO) {
        return projectService.initProject(repoDTO);
    }

    @GetMapping("/project")
    public JsonObject getProject() {
        try {
            return projectService.getProject().json();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
