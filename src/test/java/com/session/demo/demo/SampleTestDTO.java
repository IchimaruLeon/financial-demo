package com.session.demo.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.session.demo.demo.dto.thirdPartyDTO.SampleResourceDTO;
import org.junit.Test;

import java.io.IOException;

public class SampleTestDTO {

    @Test
    public void testAaa() throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        String sample = "{\"results\":[{\"gender\":\"female\",\"name\":{\"title\":\"mrs\",\"first\":\"heather\",\"last\":\"jacobs\"},\"location\":{\"street\":\"1456 park lane\",\"city\":\"loughrea\",\"state\":\"wexford\",\"postcode\":22538,\"coordinates\":{\"latitude\":\"23.2178\",\"longitude\":\"124.0192\"},\"timezone\":{\"offset\":\"-11:00\",\"description\":\"Midway Island, Samoa\"}},\"email\":\"heather.jacobs@example.com\",\"login\":{\"uuid\":\"6760299e-2ee5-4f16-bd27-7a2694cbfe7d\",\"username\":\"heavykoala663\",\"password\":\"bigfoot\",\"salt\":\"53w9gxl2\",\"md5\":\"9b6379b3662b98c245f2e76da5fc8376\",\"sha1\":\"07955e68392289e5c2319cdd4a2542940f6eec62\",\"sha256\":\"0e81665bfd67e8b21fa06843c6079914edc96effd688296b6b3c08277ac9d806\"},\"dob\":{\"date\":\"1953-04-07T10:56:58Z\",\"age\":66},\"registered\":{\"date\":\"2008-11-19T20:16:08Z\",\"age\":10},\"phone\":\"031-779-9486\",\"cell\":\"081-794-0428\",\"id\":{\"name\":\"PPS\",\"value\":\"2777638T\"},\"picture\":{\"large\":\"https://randomuser.me/api/portraits/women/60.jpg\",\"medium\":\"https://randomuser.me/api/portraits/med/women/60.jpg\",\"thumbnail\":\"https://randomuser.me/api/portraits/thumb/women/60.jpg\"},\"nat\":\"IE\"}],\"info\":{\"seed\":\"01016847f3851470\",\"results\":1,\"page\":1,\"version\":\"1.2\"}}";

        System.out.println("sample = " + sample);
        
        SampleResourceDTO sampleResourceDTO = mapper.readValue(sample, SampleResourceDTO.class);

        System.out.println("sampleResourceDTO = " + sampleResourceDTO);
    }
}
