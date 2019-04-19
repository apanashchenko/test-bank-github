package com.test.bank.github.web;

import com.test.bank.github.dto.TestCaseDTO;
import com.test.bank.github.service.TestCaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.json.JsonObject;

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

    @GetMapping("/cases")
    public List<JsonObject> getAllCases() {
        return testCaseService.getAllCases();
    }

}
