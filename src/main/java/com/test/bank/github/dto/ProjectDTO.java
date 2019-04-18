package com.test.bank.github.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectDTO {

    @ApiModelProperty(example = "https://github.com/SergeyPirogov/test-bank.git")
    private String remoteUrl;
}
