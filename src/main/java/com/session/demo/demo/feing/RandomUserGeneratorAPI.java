package com.session.demo.demo.feing;

import com.session.demo.demo.dto.thirdPartyDTO.SampleResourceDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "external-random-user", url = "${external-api.endpoint}")
public interface RandomUserGeneratorAPI {

    @RequestMapping(method = RequestMethod.GET, value = "/api", consumes = "application/json", name = "SampleResourceDTO")
    ResponseEntity<SampleResourceDTO> getSampleUser();
}
