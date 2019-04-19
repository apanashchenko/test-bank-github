package com.test.bank.github.service;

import com.jcabi.github.Coordinates;
import com.jcabi.github.Github;
import com.jcabi.github.Repo;
import com.jcabi.github.RtGithub;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    @Value("${github.username}")
    private String userName;

    @Value("${github.password}")
    private String password;

    @Value("${github.baseBranch}")
    private String baseBranch;

    @Value("${github.repoName}")
    private String repoName;

    public String getProject() {
        Github github = new RtGithub(userName, password);
        Repo gitHubRepo = github.repos().get(new Coordinates.Simple(repoName));
//        TODO implement show project logic
        return repoName;
    }

}
