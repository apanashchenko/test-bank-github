package com.test.bank.github.web;

import com.test.bank.github.dto.TestCaseDTO;
import com.test.bank.github.response.TestCaseResponse;
import com.test.bank.github.service.TestCaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.Collections.singletonMap;

@RestController
@RequiredArgsConstructor
public class TestCaseController {

    private final TestCaseService testCaseService;

    @PostMapping("/case")
    public ResponseEntity createTestCase(@RequestBody TestCaseDTO testCaseDTO) {
        String path = testCaseService.createTestCase(testCaseDTO).path();
        return new ResponseEntity<>(singletonMap("path", path), HttpStatus.OK);
    }

    @GetMapping("/cases/{projectName}")
    public List<TestCaseResponse> getAllCases(@PathVariable String repoName) {
        return testCaseService.getAllCases(repoName);
    }

}
