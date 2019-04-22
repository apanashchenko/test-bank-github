package com.test.bank.github.dto.github;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Committer {

    @ApiModelProperty(example = "Ivan Petrov")
    private String name;
    @ApiModelProperty(example = "ivan_89@gmail.com")
    private String email;

}
