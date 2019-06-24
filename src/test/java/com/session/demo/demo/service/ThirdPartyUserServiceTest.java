package com.session.demo.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.session.demo.demo.dto.thirdPartyDTO.SampleResourceDTO;
import com.session.demo.demo.dto.thirdPartyDTO.SampleUserDTO;
import com.session.demo.demo.feing.RandomUserGeneratorAPI;
import com.session.demo.demo.handler.AppException;
import com.session.demo.demo.handler.ResponseCodeEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.http.ResponseEntity;
import javax.servlet.ServletException;
import java.io.IOException;

@ExtendWith(MockitoExtension.class)
public class ThirdPartyUserServiceTest {

    private static String jsonStringSample = "{\"results\":[{\"gender\":\"male\",\"name\":{\"title\":\"mr\",\"first\":\"ricardo\",\"last\":\"das neves\"},\"location\":{\"street\":\"2022 rua maranhão \",\"city\":\"limeira\",\"state\":\"distrito federal\",\"postcode\":88143,\"coordinates\":{\"latitude\":\"-72.3222\",\"longitude\":\"-155.7434\"},\"timezone\":{\"offset\":\"-1:00\",\"description\":\"Azores, Cape Verde Islands\"}},\"email\":\"ricardo.dasneves@example.com\",\"login\":{\"uuid\":\"c563673d-0e36-4a4c-9a39-58acd22971e7\",\"username\":\"brownmeercat390\",\"password\":\"myxworld\",\"salt\":\"b6nBPsVM\",\"md5\":\"301ce2f580c2b2c400de3840f3005d6b\",\"sha1\":\"697081ccce1dfcd5ef6674b70204c5376026a53b\",\"sha256\":\"8aca861b91e310327208bd01d35d3509feaec148f1e79708e21a7190cf10b4ad\"},\"dob\":{\"date\":\"1972-03-10T09:51:02Z\",\"age\":47},\"registered\":{\"date\":\"2012-05-26T08:34:45Z\",\"age\":7},\"phone\":\"(81) 1540-9470\",\"cell\":\"(64) 7341-3324\",\"id\":{\"name\":\"\",\"value\":null},\"picture\":{\"large\":\"https://randomuser.me/api/portraits/men/76.jpg\",\"medium\":\"https://randomuser.me/api/portraits/med/men/76.jpg\",\"thumbnail\":\"https://randomuser.me/api/portraits/thumb/men/76.jpg\"},\"nat\":\"BR\"}],\"info\":{\"seed\":\"e1be8f0ca1c50dd2\",\"results\":1,\"page\":1,\"version\":\"1.2\"}}";

    private static final ObjectMapper OBJ_MAPPER = new ObjectMapper();

    @Mock
    private RandomUserGeneratorAPI randomUserGeneratorAPI;

    @InjectMocks
    private ThirdPartyUserService thirdPartyUserService;

    @BeforeEach
    public void setup() {
    }

    @Test
    public void testGetSampleShouldReturnObjectAsExpected() throws IOException {
        when(randomUserGeneratorAPI.getSampleUser()).thenReturn(ResponseEntity.ok(getDummySampleResourceDTO(jsonStringSample)));
        SampleUserDTO sampleUserDTO = thirdPartyUserService.getSampleUser();
        assertNotNull(sampleUserDTO);
        assertEquals("male", sampleUserDTO.getGender());
        assertEquals("ricardo", sampleUserDTO.getName().getFirst());
        assertNotNull(sampleUserDTO.getDob().getAge());
        assertNotNull(sampleUserDTO.getEmail());
    }

    @Test
    public void testGetSampleShouldThrowRuntimeExceptionAsExpectedWhenUserDataSampleDTOWithResponseHttpIsOkGivenButTheBodyIsNull() throws IOException {
        when(randomUserGeneratorAPI.getSampleUser()).thenReturn(ResponseEntity.ok(new SampleResourceDTO()));

        AppException ex;

        ex = assertThrows(AppException.class, () ->{
            thirdPartyUserService.getSampleUser();
        });
        assertEquals(ResponseCodeEnum.FAILED, ex.getCode());
        assertEquals("SAMPLE USER NOT FOUND", ex.getMessageKey());
    }

    @Test
    public void testGetSampleShouldThrowRuntimeExceptionAsExpectedWhenUserDataSampleDTOWithResponseHttpIsOkGivenButTheBodyResultsIsNull() throws IOException {
        when(randomUserGeneratorAPI.getSampleUser()).thenReturn(ResponseEntity.ok(new SampleResourceDTO()));

        AppException ex;

        ex = assertThrows(AppException.class, () ->{
            thirdPartyUserService.getSampleUser();
        });
        assertEquals(ResponseCodeEnum.FAILED, ex.getCode());
        assertEquals("SAMPLE USER NOT FOUND", ex.getMessageKey());
    }

    @Test
    public void testGetSampleContactThirdPartyButGotConnectionTimeOutShouldThrowErrorRuntime() {
        when(randomUserGeneratorAPI.getSampleUser()).thenAnswer(new Answer() {
            public Object answer(InvocationOnMock invocation) throws ServletException {
                throw new ServletException("FAKE ERROR CONNECTION");
            }
        });
        AppException ex;

        ex = assertThrows(AppException.class, () -> {
            thirdPartyUserService.getSampleUser();
        });

        assertEquals(ResponseCodeEnum.FAILED, ex.getCode());
        assertEquals("Cant Contact Third Party, with error : FAKE ERROR CONNECTION", ex.getMessageKey());
    }

    private SampleResourceDTO getDummySampleResourceDTO(String JsonString) throws IOException {
        return OBJ_MAPPER.readValue(JsonString, SampleResourceDTO.class);
    }
}