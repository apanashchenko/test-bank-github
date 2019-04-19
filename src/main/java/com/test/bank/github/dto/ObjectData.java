package com.test.bank.github.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ObjectData {

    @JsonProperty("sha")
    private String sha;
    @JsonProperty("type")
    private String type;
    @JsonProperty("url")
    private String url;

}
