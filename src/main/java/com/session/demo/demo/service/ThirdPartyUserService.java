package com.session.demo.demo.service;

import com.session.demo.demo.dto.thirdPartyDTO.SampleResourceDTO;
import com.session.demo.demo.dto.thirdPartyDTO.SampleUserDTO;
import com.session.demo.demo.feing.RandomUserGeneratorAPI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ThirdPartyUserService {

    @Autowired
    private RandomUserGeneratorAPI randomUserGeneratorAPI;

    public SampleUserDTO getSampleUser() {
        ResponseEntity<SampleResourceDTO> userDataSampleDTO = randomUserGeneratorAPI.getSampleUser();

        log.info("response getStatusCode : {}", userDataSampleDTO.getStatusCode());
        log.info("response getStatusCodeValue : {}", userDataSampleDTO.getStatusCodeValue());
        log.info("response getStatusCodeValue : {}", userDataSampleDTO.getBody());

        if(HttpStatus.OK == userDataSampleDTO.getStatusCode()) {
            return userDataSampleDTO.getBody().getResults().get(0);
        } else {
            throw new RuntimeException("SAMPLE USER NOT FOUND");
        }
    }
}
