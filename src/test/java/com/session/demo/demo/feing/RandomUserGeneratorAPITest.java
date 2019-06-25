package com.session.demo.demo.feing;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.session.demo.demo.dto.thirdPartyDTO.SampleResourceDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RandomUserGeneratorAPITest {

    private static String sampleJson = "{\"results\":[{\"gender\":\"male\",\"name\":{\"title\":\"mr\",\"first\":\"ricardo\",\"last\":\"das neves\"},\"location\":{\"street\":\"2022 rua maranhão \",\"city\":\"limeira\",\"state\":\"distrito federal\",\"postcode\":88143,\"coordinates\":{\"latitude\":\"-72.3222\",\"longitude\":\"-155.7434\"},\"timezone\":{\"offset\":\"-1:00\",\"description\":\"Azores, Cape Verde Islands\"}},\"email\":\"ricardo.dasneves@example.com\",\"login\":{\"uuid\":\"c563673d-0e36-4a4c-9a39-58acd22971e7\",\"username\":\"brownmeercat390\",\"password\":\"myxworld\",\"salt\":\"b6nBPsVM\",\"md5\":\"301ce2f580c2b2c400de3840f3005d6b\",\"sha1\":\"697081ccce1dfcd5ef6674b70204c5376026a53b\",\"sha256\":\"8aca861b91e310327208bd01d35d3509feaec148f1e79708e21a7190cf10b4ad\"},\"dob\":{\"date\":\"1972-03-10T09:51:02Z\",\"age\":47},\"registered\":{\"date\":\"2012-05-26T08:34:45Z\",\"age\":7},\"phone\":\"(81) 1540-9470\",\"cell\":\"(64) 7341-3324\",\"id\":{\"name\":\"\",\"value\":null},\"picture\":{\"large\":\"https://randomuser.me/api/portraits/men/76.jpg\",\"medium\":\"https://randomuser.me/api/portraits/med/men/76.jpg\",\"thumbnail\":\"https://randomuser.me/api/portraits/thumb/men/76.jpg\"},\"nat\":\"BR\"}],\"info\":{\"seed\":\"e1be8f0ca1c50dd2\",\"results\":1,\"page\":1,\"version\":\"1.2\"}}";

    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private RandomUserGeneratorAPI randomUserGeneratorAPI;

    @Test
    public void testCallMethodGetSampleUserFromThirdPartyShouldReturnObjectAsExpectedWhenValidResponseGiven() throws IOException {
        SampleResourceDTO sampleResourceDTO = objectMapper.readValue(sampleJson, SampleResourceDTO.class);
        Mockito.when(randomUserGeneratorAPI.getSampleUser()).thenReturn(ResponseEntity.ok(sampleResourceDTO));

        ResponseEntity<SampleResourceDTO> responseEntity = randomUserGeneratorAPI.getSampleUser();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(sampleResourceDTO.getInfo().getPage(), responseEntity.getBody().getInfo().getPage());
        assertEquals(sampleResourceDTO.getInfo().getResults(), responseEntity.getBody().getInfo().getResults());
        assertEquals(sampleResourceDTO.getInfo().getSeed(), responseEntity.getBody().getInfo().getSeed());
        assertEquals(sampleResourceDTO.getInfo().getVersion(), responseEntity.getBody().getInfo().getVersion());
        assertEquals(sampleResourceDTO.getResults().size(), responseEntity.getBody().getResults().size());
        assertEquals(sampleResourceDTO.getResults().get(0).getCell(), responseEntity.getBody().getResults().get(0).getCell());
        assertEquals(sampleResourceDTO.getResults().get(0).getEmail(), responseEntity.getBody().getResults().get(0).getEmail());
        assertEquals(sampleResourceDTO.getResults().get(0).getGender(), responseEntity.getBody().getResults().get(0).getGender());
        assertEquals(sampleResourceDTO.getResults().get(0).getNat(), responseEntity.getBody().getResults().get(0).getNat());
        assertEquals(sampleResourceDTO.getResults().get(0).getPhone(), responseEntity.getBody().getResults().get(0).getPhone());
        assertEquals(sampleResourceDTO.getResults().get(0).getDob().getAge(), responseEntity.getBody().getResults().get(0).getDob().getAge());
        assertEquals(sampleResourceDTO.getResults().get(0).getDob().getDate(), responseEntity.getBody().getResults().get(0).getDob().getDate());
        assertEquals(sampleResourceDTO.getResults().get(0).getId().getName(), responseEntity.getBody().getResults().get(0).getId().getName());
        assertEquals(sampleResourceDTO.getResults().get(0).getId().getValue(), responseEntity.getBody().getResults().get(0).getId().getValue());
        assertEquals(sampleResourceDTO.getResults().get(0).getLocation().getCity(), responseEntity.getBody().getResults().get(0).getLocation().getCity());
        assertEquals(sampleResourceDTO.getResults().get(0).getLocation().getCoordinates(), responseEntity.getBody().getResults().get(0).getLocation().getCoordinates());
        assertEquals(sampleResourceDTO.getResults().get(0).getLocation().getPostcode(), responseEntity.getBody().getResults().get(0).getLocation().getPostcode());
        assertEquals(sampleResourceDTO.getResults().get(0).getLocation().getState(), responseEntity.getBody().getResults().get(0).getLocation().getState());
        assertEquals(sampleResourceDTO.getResults().get(0).getLocation().getStreet(), responseEntity.getBody().getResults().get(0).getLocation().getStreet());
        assertEquals(sampleResourceDTO.getResults().get(0).getLocation().getTimezone(), responseEntity.getBody().getResults().get(0).getLocation().getTimezone());
        assertEquals(sampleResourceDTO.getResults().get(0).getLogin().getMd5(), responseEntity.getBody().getResults().get(0).getLogin().getMd5());
        assertEquals(sampleResourceDTO.getResults().get(0).getLogin().getUsername(), responseEntity.getBody().getResults().get(0).getLogin().getUsername());
        assertEquals(sampleResourceDTO.getResults().get(0).getLogin().getPassword(), responseEntity.getBody().getResults().get(0).getLogin().getPassword());
        assertEquals(sampleResourceDTO.getResults().get(0).getLogin().getSalt(), responseEntity.getBody().getResults().get(0).getLogin().getSalt());
        assertEquals(sampleResourceDTO.getResults().get(0).getLogin().getSha1(), responseEntity.getBody().getResults().get(0).getLogin().getSha1());
        assertEquals(sampleResourceDTO.getResults().get(0).getLogin().getSha256(), responseEntity.getBody().getResults().get(0).getLogin().getSha256());
        assertEquals(sampleResourceDTO.getResults().get(0).getLogin().getUuid(), responseEntity.getBody().getResults().get(0).getLogin().getUuid());
        assertEquals(sampleResourceDTO.getResults().get(0).getName().getFirst(), responseEntity.getBody().getResults().get(0).getName().getFirst());
        assertEquals(sampleResourceDTO.getResults().get(0).getName().getLast(), responseEntity.getBody().getResults().get(0).getName().getLast());
        assertEquals(sampleResourceDTO.getResults().get(0).getName().getTitle(), responseEntity.getBody().getResults().get(0).getName().getTitle());
        assertEquals(sampleResourceDTO.getResults().get(0).getPicture().getLarge(), responseEntity.getBody().getResults().get(0).getPicture().getLarge());
        assertEquals(sampleResourceDTO.getResults().get(0).getPicture().getMedium(), responseEntity.getBody().getResults().get(0).getPicture().getMedium());
        assertEquals(sampleResourceDTO.getResults().get(0).getPicture().getThumbnail(), responseEntity.getBody().getResults().get(0).getPicture().getThumbnail());
        assertEquals(sampleResourceDTO.getResults().get(0).getRegistered().getAge(), responseEntity.getBody().getResults().get(0).getRegistered().getAge());
        assertEquals(sampleResourceDTO.getResults().get(0).getRegistered().getDate(), responseEntity.getBody().getResults().get(0).getRegistered().getDate());
    }

}