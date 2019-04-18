package com.test.bank.github.web;

import com.test.bank.github.dto.TestCaseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

import static java.util.Collections.singletonMap;

@Controller
public class TestCaseController {

    @PostMapping("/case")
    public ResponseEntity<Map<String, Integer>> createTestCase(@RequestBody TestCaseDTO testCaseDTO){
        return new ResponseEntity<>(singletonMap("id", 1), HttpStatus.OK);
    }

}
