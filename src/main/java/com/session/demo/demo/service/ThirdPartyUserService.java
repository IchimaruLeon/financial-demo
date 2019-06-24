package com.session.demo.demo.service;

import com.session.demo.demo.dto.thirdPartyDTO.SampleResourceDTO;
import com.session.demo.demo.dto.thirdPartyDTO.SampleUserDTO;
import com.session.demo.demo.feing.RandomUserGeneratorAPI;
import com.session.demo.demo.handler.AppException;
import com.session.demo.demo.handler.ResponseCodeEnum;
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

    public SampleUserDTO getSampleUser() throws AppException {
        try {
            ResponseEntity<SampleResourceDTO> userDataSampleDTO = randomUserGeneratorAPI.getSampleUser();

            log.info("response getStatusCode : {}", userDataSampleDTO.getStatusCode());
            log.info("response getStatusCodeValue : {}", userDataSampleDTO.getStatusCodeValue());

            if (HttpStatus.OK == userDataSampleDTO.getStatusCode()
                    && null != userDataSampleDTO.getBody()
                    && null != userDataSampleDTO.getBody().getResults()) {
                return userDataSampleDTO.getBody().getResults().get(0);
            } else {
                throw new AppException(ResponseCodeEnum.FAILED, "SAMPLE USER NOT FOUND");
            }
        } catch (Exception e){
            if(e instanceof AppException) {
                throw e;
            }
            throw new AppException(ResponseCodeEnum.FAILED, String.format("Cant Contact Third Party, with error : %s", e.getMessage()));
        }
    }
}
