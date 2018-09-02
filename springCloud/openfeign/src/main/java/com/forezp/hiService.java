package com.forezp;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value="service-hi")
public interface hiService {

    @RequestMapping(value = "/hi",method = RequestMethod.GET)
    String sayHiFromeClientOne(@RequestParam(value = "name")String name);
}
