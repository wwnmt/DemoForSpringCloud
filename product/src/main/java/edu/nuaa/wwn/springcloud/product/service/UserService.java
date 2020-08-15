package edu.nuaa.wwn.springcloud.product.service;

import edu.nuaa.wwn.springcloud.product.pojo.UserPo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("user")
public interface UserService {

    @GetMapping("/user/{id}")
    public UserPo getUser(@PathVariable("id") Long id);

    @GetMapping("/timeout")
    public String timeout();
}
