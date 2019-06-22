package com.session.demo.demo.service;

import com.session.demo.demo.dto.thirdPartyDTO.LoginDTO;
import com.session.demo.demo.entity.LoginData;
import com.session.demo.demo.repository.LoginDataRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LoginDataService {

    private static final ModelMapper MDL_MAPPER = new ModelMapper();

    @Autowired
    private LoginDataRepository loginDataRepository;

    public LoginData create(LoginDTO loginDTO) {
        return MDL_MAPPER.map(loginDTO, LoginData.class);
    }

    public LoginData save(LoginData loginData) {
        return loginDataRepository.save(loginData);
    }
}
