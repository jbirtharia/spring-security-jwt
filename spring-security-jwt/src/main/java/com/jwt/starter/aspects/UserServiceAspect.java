package com.jwt.starter.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Aspect
@Slf4j
public class UserServiceAspect {

//    @Before("execution(* com.jwt.starter.service.UserService.getAllUsers())")
//    public void logBeforeGetAllUsersService() {
//        log.info("Getting all users from DB...");
//    }
//
//    @AfterReturning(value = "execution(* com.jwt.starter.service.UserService.getAllUsers())", returning = "result")
//    public void logAfterGetAllUserService(Object result) {
//        log.info("Getting all users after getting from DB...");
//        if (result instanceof List<?> users) {
//            users.forEach(user -> log.info("User: {}", user));
//        }else {
//            log.info("Service method returned a non-list object: {}", result);
//        }
//    }
}
