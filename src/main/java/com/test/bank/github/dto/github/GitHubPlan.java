package com.test.bank.github.dto.github;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GitHubPlan {

    @JsonProperty("name")
    private String name;
    @JsonProperty("space")
    private Integer space;
    @JsonProperty("collaborators")
    private Integer collaborators;
    @JsonProperty("private_repos")
    private Integer privateRepos;

}

