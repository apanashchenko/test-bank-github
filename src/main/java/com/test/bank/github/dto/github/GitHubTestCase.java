package com.test.bank.github.dto.github;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GitHubTestCase {

    private String message;
    private String branch;
    private Committer committer;
    private String content;
    private String path;


}
