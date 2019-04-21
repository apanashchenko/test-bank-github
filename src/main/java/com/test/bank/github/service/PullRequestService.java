package com.test.bank.github.service;

import com.jcabi.github.*;
import com.test.bank.github.dto.MergeRequestDTO;
import com.test.bank.github.dto.PullRequestDTO;
import com.test.bank.github.response.PullRequestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class PullRequestService {

    @Autowired
    private ProjectService projectService;

    @Value("${github.baseBranch}")
    private String baseBranch;

    public PullRequestResponse createPullRequest(PullRequestDTO pullRequestDTO) {
        try {
            Pull pullRequest = projectService.getRepo(pullRequestDTO.getRepoName()).pulls()
                    .create(pullRequestDTO.getTitle(), pullRequestDTO.getBranchName(), baseBranch);

            return new PullRequestResponse(pullRequest.number(), pullRequest.json().toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public MergeState mergePullRequest(MergeRequestDTO mergeRequestDTO) {
        try {
            Pull pull = projectService.getRepo(mergeRequestDTO.getRepoName()).pulls().get(mergeRequestDTO.getId());

            return pull.merge("Merge " + mergeRequestDTO.getId(), pull.head().sha());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
