package com.test.bank.github.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class ProjectDTO {

    @ApiModelProperty(example = "Test project")
    @NotBlank
    String name;
    @ApiModelProperty(example = "false")
    boolean deleted;
    @ApiModelProperty(example = "Test project description")
    private String description;
    @ApiModelProperty(example = "false")
    private boolean isPrivate = false;
}
