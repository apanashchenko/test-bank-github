package com.test.bank.github.web;

import com.test.bank.github.dto.TestCaseDTO;
import com.test.bank.github.dto.github.GitHubTestCaseDTO;
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
    public ResponseEntity createTestCase(@RequestBody GitHubTestCaseDTO testCaseDTO) {
        String path = testCaseService.createTestCase(testCaseDTO);
        return new ResponseEntity<>(singletonMap("path", path), HttpStatus.OK);
    }

    @GetMapping("/{repoName}/cases")
    public List<TestCaseResponse> getAllCases(@PathVariable String repoName) {
        return testCaseService.getAllCases(repoName);
    }

    @GetMapping("/{repoName}/case")
    public TestCaseResponse getTestCase(@PathVariable String repoName, @RequestParam String fileName) {
        return testCaseService.getTestCase(repoName, fileName);
    }


}
