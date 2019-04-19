package com.test.bank.github.service;

import com.jcabi.github.*;
import com.test.bank.github.dto.PullRequestDTO;
import com.test.bank.github.response.PullRequestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class PullRequestService {

    @Autowired
    private Github github;

    @Autowired
    private Repo repo;

    @Value("${github.baseBranch}")
    private String baseBranch;

    public PullRequestResponse createPullRequest(PullRequestDTO pullRequestDTO) {
        try {
            Pull pullRequest = repo.pulls().create(pullRequestDTO.getTitle(), pullRequestDTO.getBranchName(), baseBranch);

            return new PullRequestResponse(pullRequest.number(), pullRequest.json().toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public MergeState mergePullRequest(int id) {
        try {
            Pull pull = repo.pulls().get(id);

            return pull.merge("Merge " + id, pull.head().sha());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
