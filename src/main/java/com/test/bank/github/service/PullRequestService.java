package com.test.bank.github.service;

import com.jcabi.github.*;
import com.test.bank.github.dto.PullRequestDTO;
import com.test.bank.github.response.PullRequestResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class PullRequestService {

    @Value("${github.username}")
    private String userName;

    @Value("${github.password}")
    private String password;

    @Value("${github.repoName}")
    private String repoName;

    @Value("${github.baseBranch}")
    private String baseBranch;

    public PullRequestResponse createPullRequest(PullRequestDTO pullRequestDTO) {
        Github github = new RtGithub(userName, password);
        Repo repo = github.repos().get(new Coordinates.Simple(repoName));

        try {
            Pull pullRequest = repo.pulls().create(pullRequestDTO.getTitle(), pullRequestDTO.getBranchName(), baseBranch);

            return new PullRequestResponse(pullRequest.number(), pullRequest.json().toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
