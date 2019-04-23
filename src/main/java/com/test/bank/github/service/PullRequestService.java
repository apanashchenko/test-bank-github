package com.test.bank.github.service;

import com.google.common.collect.Iterables;
import com.jcabi.github.*;
import com.test.bank.github.dto.MergeRequestDTO;
import com.test.bank.github.dto.PullRequestDTO;
import com.test.bank.github.response.PullRequestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.json.JsonObject;
import java.io.IOException;

@Service
public class PullRequestService {

    @Autowired
    private ProjectService projectService;

    @Value("${github.baseBranch}")
    private String baseBranch;

    public PullRequestResponse createPullRequest(PullRequestDTO pullRequestDTO) {
        try {
            Repo repo = projectService.getRepo(pullRequestDTO.getRepoName());
            Pull pullRequest = repo.pulls().create(pullRequestDTO.getTitle(), pullRequestDTO.getBranchName(), baseBranch);

            String diffText = extractDiffText(repo, pullRequest, pullRequestDTO);

            return new PullRequestResponse(pullRequest.number(), diffText);
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

    private String extractDiffText(Repo repo, Pull pullRequest, PullRequestDTO pullRequestDTO) throws IOException {
        Iterable<Commit> commits = repo.pulls().get(pullRequest.number()).commits();

        Commit lastCommit = Iterables.getLast(commits);
        JsonObject commitJson = lastCommit.json();
        int defaultIndex = 0; //TODO need to test, maybe change in feature
        String parentSha = commitJson.getJsonArray("parents").getJsonObject(defaultIndex).getString("sha");
        String commitSha = commitJson.getString("sha");

        return projectService.getRepo(pullRequestDTO.getRepoName()).commits().diff(parentSha, commitSha);
    }
}
