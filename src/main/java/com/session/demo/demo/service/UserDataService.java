package com.session.demo.demo.service;

import com.session.demo.demo.dto.internalDTO.UserDataDTO;
import com.session.demo.demo.dto.thirdPartyDTO.LoginDTO;
import com.session.demo.demo.dto.thirdPartyDTO.SampleUserDTO;
import com.session.demo.demo.entity.Account;
import com.session.demo.demo.entity.LoginData;
import com.session.demo.demo.entity.UserData;
import com.session.demo.demo.helper.StringUtils;
import com.session.demo.demo.repository.UserDataRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

@Slf4j
@Service
public class UserDataService {

    private static final ModelMapper MDL_MAPPER = new ModelMapper();

    @Autowired
    private ThirdPartyUserService thirdPartyUserService;

    @Autowired
    private LoginDataService loginDataService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private UserDataRepository userDataRepository;

    @Transactional
    public UserDataDTO create() {
        SampleUserDTO sampleUserDTO = thirdPartyUserService.getSampleUser();
        log.info("sample user : {}", StringUtils.convertObjToStringValues(sampleUserDTO));

        UserData userData = buildUserData(sampleUserDTO);
        userData.setLoginData(buildLoginData(sampleUserDTO.getLogin()));
        Account account = accountService.create(userData);
        save(userData);
        UserDataDTO userDataDTO = buildUserDataDTO(userData, account, sampleUserDTO.getLogin());

        log.info("userDataDTO : {}", StringUtils.convertObjToStringValues(userDataDTO));
        return userDataDTO;
    }

    private UserDataDTO buildUserDataDTO(UserData userData, Account account, LoginDTO loginDTO) {
        UserDataDTO userDataDTO = MDL_MAPPER.map(userData, UserDataDTO.class);
        MDL_MAPPER.map(loginDTO, userDataDTO);
        userDataDTO.setAccountId(account.getId());
        userDataDTO.setAccountType(account.getAccountType().getName());
        return userDataDTO;
    }

    public UserData save(UserData userData) {
        userData =  userDataRepository.save(userData);
        return userData;
    }

    public LoginData buildLoginData(LoginDTO loginDTO) {
        return loginDataService.create(loginDTO);
    }

    public UserData buildUserData(SampleUserDTO sampleUserDTO) {
        ModelMapper mapper = configureMapper(new ModelMapper());
        UserData userData = new UserData();
        mapper.map(sampleUserDTO, userData);
        return userData;
    }

    private ModelMapper configureMapper(ModelMapper modelMapper) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        modelMapper.typeMap(SampleUserDTO.class, UserData.class)
        .addMapping(src ->src.getName().getFirst(), UserData::setFirstName)
        .addMapping(src ->src.getName().getLast(), UserData::setLastName);
        return modelMapper;
    }

}
