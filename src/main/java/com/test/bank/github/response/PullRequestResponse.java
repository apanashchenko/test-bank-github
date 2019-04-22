package com.test.bank.github.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PullRequestResponse {

    private int id;
    private String diffUrl;
}
