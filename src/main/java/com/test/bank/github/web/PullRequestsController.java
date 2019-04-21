package com.test.bank.github.web;

import com.jcabi.github.MergeState;
import com.test.bank.github.dto.PullRequestDTO;
import com.test.bank.github.response.PullRequestMergeResponse;
import com.test.bank.github.response.PullRequestResponse;
import com.test.bank.github.service.PullRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PullRequestsController {

    private final PullRequestService pullRequestService;

    @PostMapping("/pull-request")
    public PullRequestResponse createPullRequest(PullRequestDTO pullRequestDTO) {
        return pullRequestService.createPullRequest(pullRequestDTO);
    }

    @PostMapping("/pull-request/merge")
    public PullRequestMergeResponse mergePullRequest(PullRequestDTO pullRequestDTO) {
        MergeState merged = pullRequestService.mergePullRequest(pullRequestDTO);
        return new PullRequestMergeResponse(merged);
    }
}
