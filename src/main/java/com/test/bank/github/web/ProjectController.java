package com.test.bank.github.web;

import com.test.bank.github.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping("/project")
    public String getProject() {
//   TODO implement show project logic
         return "{\"name:\" \""+ projectService.getProject() +"\"}";
    }

}
