package com.session.demo.demo.controller;

import com.session.demo.demo.dto.internalDTO.UserDataDTO;
import com.session.demo.demo.entity.UserData;
import com.session.demo.demo.helper.StringUtils;
import com.session.demo.demo.service.UserDataService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/user-data")
public class UserDataController {

    @Autowired
    private UserDataService userDataService;

    @ApiOperation(value = "Create and generate sample User Data from 3rd party", notes = "SAMPLE SAMPLE")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 400, message = "Invalid parameters supplied"),
                    @ApiResponse(code = 500, message = "server error")
            })
    @PostMapping("/create")
    public ResponseEntity<UserDataDTO> create() {
        UserDataDTO userDataDTO = userDataService.create();
        log.info("will return created user data : {}", StringUtils.convertObjToStringValues(userDataDTO));
        ResponseEntity<UserDataDTO> userDataDTOResponseEntity = ResponseEntity.of(Optional.of(userDataDTO));
        return userDataDTOResponseEntity;
    }
}
