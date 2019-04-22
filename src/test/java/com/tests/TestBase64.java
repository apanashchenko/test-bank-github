package com.tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.test.bank.github.dto.TestCaseDTO;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.Base64;

public class TestBase64 {

    @Test
    public void name() throws JsonProcessingException {
        TestCaseDTO testCase = new TestCaseDTO();
        testCase.setTitle("Demo");
        testCase.setUserName("Steve Ivanov");

        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

        String s = Base64.getEncoder().encodeToString(mapper.writeValueAsBytes(testCase));

        System.out.println(s);
    }
}
