package com.test.bank.github.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TestCaseDTO {

    @ApiModelProperty(example = "Create new test case")
    private String title;
    @ApiModelProperty(example = "Steve")
    private String userName;
    @ApiModelProperty(example = "steve@g.com")
    private String email;
    @ApiModelProperty(example = "login-test-case")
    private String fileName;
    @ApiModelProperty(example = "login-test-case")
    private String branch;

}
