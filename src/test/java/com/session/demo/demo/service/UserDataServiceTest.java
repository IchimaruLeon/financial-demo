package com.session.demo.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.session.demo.demo.dto.internalDTO.UserDataDTO;
import com.session.demo.demo.dto.thirdPartyDTO.LoginDTO;
import com.session.demo.demo.dto.thirdPartyDTO.SampleUserDTO;
import com.session.demo.demo.entity.Account;
import com.session.demo.demo.entity.AccountType;
import com.session.demo.demo.entity.LoginData;
import com.session.demo.demo.entity.UserData;
import com.session.demo.demo.repository.UserDataRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;

@ExtendWith(MockitoExtension.class)
public class UserDataServiceTest {

    private static String jsonStringSampleFromPartner = "{\"gender\":\"male\"," +
            "\"name\":{\"title\":\"mr\",\"first\":\"ricardo\",\"last\":\"das neves\"}," +
            "\"location\":{\"street\":\"2022 rua maranhão \",\"city\":\"limeira\",\"state\":\"distrito federal\",\"postcode\":88143," +
            "\"coordinates\":{\"latitude\":\"-72.3222\",\"longitude\":\"-155.7434\"}," +
            "\"timezone\":{\"offset\":\"-1:00\",\"description\":\"Azores, Cape Verde Islands\"}}," +
            "\"email\":\"ricardo.dasneves@example.com\"," +
            "\"login\":{\"uuid\":\"c563673d-0e36-4a4c-9a39-58acd22971e7\",\"username\":\"brownmeercat390\",\"password\":\"myxworld\",\"salt\":\"b6nBPsVM\",\"md5\":\"301ce2f580c2b2c400de3840f3005d6b\",\"sha1\":\"697081ccce1dfcd5ef6674b70204c5376026a53b\",\"sha256\":\"8aca861b91e310327208bd01d35d3509feaec148f1e79708e21a7190cf10b4ad\"}" +
            ",\"dob\":{\"date\":\"1972-03-10T09:51:02Z\",\"age\":47}," +
            "\"registered\":{\"date\":\"2012-05-26T08:34:45Z\",\"age\":7}," +
            "\"phone\":\"(81) 1540-9470\",\"cell\":\"(64) 7341-3324\"," +
            "\"id\":{\"name\":\"\",\"value\":null}," +
            "\"picture\":{\"large\":\"https://randomuser.me/api/portraits/men/76.jpg\",\"medium\":\"https://randomuser.me/api/portraits/med/men/76.jpg\",\"thumbnail\":\"https://randomuser.me/api/portraits/thumb/men/76.jpg\"}," +
            "\"nat\":\"BR\"}";

    private static String jsonStringSampleLoginDataFromPartner = "{\"uuid\":\"44cef1a5-b532-448b-9a73-3638100c967b\",\"username\":\"brownduck480\",\"password\":\"iawgk2\",\"salt\":\"unkpFSJQ\",\"md5\":\"ec2731194e34bdfd5671f5de62a341fe\",\"sha1\":\"c2c6df4f07a45378a17f6cc095ab5cb05b28da34\",\"sha256\":\"f970a8e8f28d28c1739592fa70d147f6c510d2a2d0ad846933bab245b554ae38\"}";

    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private LoginDataService loginDataService;

    @Mock
    private UserDataRepository userDataRepository;

    @Mock
    private AccountService accountService;

    @Mock
    private AccountTypeService accountTypeService;

    @Mock
    private ThirdPartyUserService thirdPartyUserService;

    @InjectMocks
    private UserDataService userDataService;

    @Test
    public void testBuildUserDataShouldReturnUserDataWhenValidSampleUserDTOGiven() throws IOException {
        SampleUserDTO sampleUserDTO = objectMapper.readValue(jsonStringSampleFromPartner, SampleUserDTO.class);
        UserData userData = userDataService.buildUserData(sampleUserDTO);

        assertNotNull(userData);
        assertEquals("ricardo", userData.getFirstName());
        assertEquals("das neves", userData.getLastName());
        assertEquals("ricardo.dasneves@example.com", userData.getEmail());
        assertEquals("male", userData.getGender());
        assertEquals("(81) 1540-9470", userData.getPhoneNo());
        assertEquals("c563673d-0e36-4a4c-9a39-58acd22971e7", userData.getLoginData().getUuid());
        assertEquals("brownmeercat390", userData.getLoginData().getUsername());
        assertEquals("myxworld", userData.getLoginData().getPassword());
        assertEquals("b6nBPsVM", userData.getLoginData().getSalt());
        assertEquals("301ce2f580c2b2c400de3840f3005d6b", userData.getLoginData().getMd5());
        assertEquals("697081ccce1dfcd5ef6674b70204c5376026a53b", userData.getLoginData().getSha1());
        assertEquals("8aca861b91e310327208bd01d35d3509feaec148f1e79708e21a7190cf10b4ad", userData.getLoginData().getSha256());
    }

    @Test
    public void testBuildLoginDataShouldReturnLoginDataWhenValidSampleLoginDTOGiven() throws IOException {
        LoginDTO loginDTO = objectMapper.readValue(jsonStringSampleLoginDataFromPartner, LoginDTO.class);
        Mockito.when(loginDataService.create(Mockito.any(LoginDTO.class))).thenCallRealMethod();
        LoginData loginData = userDataService.buildLoginData(loginDTO);

        assertNotNull(loginData);
        assertEquals("44cef1a5-b532-448b-9a73-3638100c967b", loginData.getUuid());
        assertEquals("brownduck480", loginData.getUsername());
        assertEquals("iawgk2", loginData.getPassword());
        assertEquals("unkpFSJQ", loginData.getSalt());
        assertEquals("ec2731194e34bdfd5671f5de62a341fe", loginData.getMd5());
        assertEquals("c2c6df4f07a45378a17f6cc095ab5cb05b28da34", loginData.getSha1());
        assertEquals("f970a8e8f28d28c1739592fa70d147f6c510d2a2d0ad846933bab245b554ae38", loginData.getSha256());
    }

    @Test
    public void testCallMethodSaveShouldCallUserDataRepositoryAsExpected() {
        UserData userData = new UserData();
        userDataService.save(userData);
        Mockito.verify(userDataRepository, Mockito.times(1)).save(Mockito.eq(userData));
    }

    @Test
    public void testCallMethodCreateShouldReturnUserDataDTOAsExpected() throws IOException {
        SampleUserDTO sampleUserDTO = objectMapper.readValue(jsonStringSampleFromPartner, SampleUserDTO.class);
        Mockito.when(thirdPartyUserService.getSampleUser()).thenReturn(sampleUserDTO);
        Mockito.when(loginDataService.create(Mockito.any(LoginDTO.class))).thenCallRealMethod();
        Mockito.when(accountService.create(Mockito.any(UserData.class))).thenReturn(buildDummyAccount());

        accountService.setAccountTypeService(accountTypeService);

        UserDataDTO userDataDTO = userDataService.create();
        ArgumentCaptor<UserData> userDataArgumentCaptor = ArgumentCaptor.forClass(UserData.class);
        Mockito.verify(userDataRepository, Mockito.times(1)).save(userDataArgumentCaptor.capture());

        assertNotNull(userDataArgumentCaptor.getValue());
        UserData userData = userDataArgumentCaptor.getValue();

        assertNotNull(userData);
        assertEquals("ricardo", userData.getFirstName());
        assertEquals("das neves", userData.getLastName());
        assertEquals("ricardo.dasneves@example.com", userData.getEmail());
        assertEquals("male", userData.getGender());
        assertEquals("(81) 1540-9470", userData.getPhoneNo());
        assertEquals("c563673d-0e36-4a4c-9a39-58acd22971e7", userData.getLoginData().getUuid());
        assertEquals("brownmeercat390", userData.getLoginData().getUsername());
        assertEquals("myxworld", userData.getLoginData().getPassword());
        assertEquals("b6nBPsVM", userData.getLoginData().getSalt());
        assertEquals("301ce2f580c2b2c400de3840f3005d6b", userData.getLoginData().getMd5());
        assertEquals("697081ccce1dfcd5ef6674b70204c5376026a53b", userData.getLoginData().getSha1());
        assertEquals("8aca861b91e310327208bd01d35d3509feaec148f1e79708e21a7190cf10b4ad", userData.getLoginData().getSha256());

        assertNotNull(userDataDTO);
    }

    private Account buildDummyAccount(){
        Account account = new Account();
        account.setCreatedDate(Instant.now());
        account.setTotalActivity(0);
        account.setActiveBalance(BigDecimal.ZERO);
        account.setAccountType(buildDummyAccountType());
        account.setUserData(new UserData());
        return account;
    }

    private AccountType buildDummyAccountType(){
        AccountType accountType = new AccountType();
        accountType.setId("ACT-01-ID");
        accountType.setCode("ACT-01");
        accountType.setName("ACT-01 NAME");
        accountType.setCreatedDate(Instant.now());
        return accountType;
    }
}