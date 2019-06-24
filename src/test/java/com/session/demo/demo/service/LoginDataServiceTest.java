package com.session.demo.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.session.demo.demo.dto.thirdPartyDTO.LoginDTO;
import com.session.demo.demo.entity.LoginData;
import com.session.demo.demo.repository.LoginDataRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.io.IOException;

@ExtendWith(MockitoExtension.class)
public class LoginDataServiceTest {

    private static String sampleJsonLoginFromPartner = "{\"uuid\":\"f4422be3-45c1-44fd-9752-1879b636d178\",\"username\":\"goldendog194\",\"password\":\"jayjay\",\"salt\":\"qvXfp71f\",\"md5\":\"a6ba3f9efdfa563e967ea97a80ad750a\",\"sha1\":\"14ca751ff4cf0b1bb2802f1022cdd8a826d4eb3b\",\"sha256\":\"a91f16d09cb12bc7986e4b3e63ebe33bde7c0e76a05d1bc3e3eb550cb2447498\"}";

    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    public LoginDataRepository loginDataRepository;

    @InjectMocks
    private LoginDataService loginDataService;

    @Test
    public void testCallMethodCreateShouldReturnLoginDataWhenValidLoginDTOGiven() throws IOException {
        LoginDTO loginDTO = objectMapper.readValue(sampleJsonLoginFromPartner, LoginDTO.class);

        LoginData loginData = loginDataService.create(loginDTO);
        assertNotNull(loginData);
        assertEquals("f4422be3-45c1-44fd-9752-1879b636d178", loginData.getUuid());
        assertEquals("goldendog194", loginData.getUsername());
        assertEquals("jayjay", loginData.getPassword());
        assertEquals("qvXfp71f", loginData.getSalt());
        assertEquals("a6ba3f9efdfa563e967ea97a80ad750a", loginData.getMd5());
        assertEquals("14ca751ff4cf0b1bb2802f1022cdd8a826d4eb3b", loginData.getSha1());
        assertEquals("a91f16d09cb12bc7986e4b3e63ebe33bde7c0e76a05d1bc3e3eb550cb2447498", loginData.getSha256());
    }

    @Test
    public void testCallMethodCreateShouldReturnLoginDataWhenEmptyLoginDTOGiven() throws IOException {
        LoginDTO loginDTO = objectMapper.readValue("{}", LoginDTO.class);

        LoginData loginData = loginDataService.create(loginDTO);
        assertNotNull(loginData);
        assertNull(loginData.getUuid());
        assertNull(loginData.getUsername());
        assertNull(loginData.getPassword());
        assertNull(loginData.getSalt());
        assertNull(loginData.getMd5());
        assertNull(loginData.getSha1());
        assertNull(loginData.getSha256());
    }

    @Test
    public void testCallMethodSaveShouldCallSaveInRepository() throws IOException {
        LoginDTO loginDTO = objectMapper.readValue(sampleJsonLoginFromPartner, LoginDTO.class);
        LoginData loginData = loginDataService.create(loginDTO);
        loginDataService.save(loginData);
        Mockito.verify(loginDataRepository, Mockito.times(1)).save(Mockito.eq(loginData));
    }
}