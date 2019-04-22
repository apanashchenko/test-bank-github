package com.test.bank.github.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PullRequestDTO {

    private String branchName;
    public String title;
    public String repoName;
}
