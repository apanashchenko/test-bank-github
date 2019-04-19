package com.test.bank.github.response;

import com.jcabi.github.MergeState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PullRequestMergeResponse {

    private MergeState state;
}
