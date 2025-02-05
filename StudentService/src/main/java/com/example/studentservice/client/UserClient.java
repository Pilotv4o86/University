package com.example.studentservice.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "UserService")
public interface UserClient {
}
