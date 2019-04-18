package com.test.bank.github.payload;

import com.test.bank.github.dto.TestCaseDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateTestCasePayload {

    private String branchName;
    private String fileName;

    private TestCaseDTO testCaseDTO;
}
