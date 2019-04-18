package com.test.bank.github.web;

import com.test.bank.github.dto.PullRequestDTO;
import com.test.bank.github.response.PullRequestResponse;
import com.test.bank.github.service.PullRequestService;
import lombok.RequiredArgsConstructor;
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
}
