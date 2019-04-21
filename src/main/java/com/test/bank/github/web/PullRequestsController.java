package com.test.bank.github.web;

import com.jcabi.github.MergeState;
import com.test.bank.github.dto.MergeRequestDTO;
import com.test.bank.github.dto.PullRequestDTO;
import com.test.bank.github.response.PullRequestMergeResponse;
import com.test.bank.github.response.PullRequestResponse;
import com.test.bank.github.service.PullRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PullRequestsController {

    private final PullRequestService pullRequestService;

    @PostMapping("/pull-request")
    public PullRequestResponse createPullRequest(@RequestBody PullRequestDTO pullRequestDTO) {
        return pullRequestService.createPullRequest(pullRequestDTO);
    }

    @PostMapping("/pull-request/merge")
    public PullRequestMergeResponse mergePullRequest(@RequestBody MergeRequestDTO mergeRequestDTO) {
        MergeState merged = pullRequestService.mergePullRequest(mergeRequestDTO);
        return new PullRequestMergeResponse(merged);
    }
}
