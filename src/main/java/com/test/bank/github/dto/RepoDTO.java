package com.test.bank.github.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RepoDTO {

    private String description;
    @ApiModelProperty(example = "false")
    private boolean isPrivate;
}
