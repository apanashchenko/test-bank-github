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

    @Test
    public void name2() {
        byte[] decode = Base64.getDecoder().decode("LS0tCmlkOiA3CnRpdGxlOiAiRmlzdCB0ZXN0IGRlbW8gMyIKcmVmZXJlbmNl\\nOiAiSmlyYS02NTgiCmxhYmVsczogIlNtb2tlIgpzdGF0dXM6ICJQQVNTRUQi\\nCmNoYW5nZWRCeTogInNwaXJvZ292QGdtYWlsLmNvbSIKcHJvamVjdElkOiAx\\nCmNyZWF0ZWRBdDogMTU1NjAxNzU4NzAwMAo=\\n");
        String s = new String(decode);
        System.out.println(s);
    }
}
