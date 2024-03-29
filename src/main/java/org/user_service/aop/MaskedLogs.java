package org.user_service.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.user_service.dto.request.UserRequestDTO;


@Slf4j
@Aspect
@Component
public class MaskedLogs {

    @Pointcut("@within(org.springframework.web.bind.annotation.RestController)")
    public void isController(){
    }

    @Pointcut("isController() && execution(public * org.user_service.controller.UserController.getUserById(*))")
    public void anyFindByIdMethod(){
    }

    @Pointcut("isController() && execution(public * org.user_service.controller.UserController.updateUserByID(..))")
    public void anyUpdateUserMethod(){
    }

    @Pointcut("isController() && execution(public * org.user_service.controller.UserController.deleteUser(*))")
    public void anyDeleteUserMethod(){
    }

    @Pointcut("isController() && execution(public * org.user_service.controller.UserController.createUser(*))")
    public void anyCreateUser(){
    }

    @Before("anyFindByIdMethod() && args(id)")
    public void addLogBeforeFind( Object id){
        log.info("Finding user with id = {}", id);
    }

    @Before("anyDeleteUserMethod() && args(id)")
    public void addLogBeforeDelete( Object id){
        log.info("Deleting user with id = {}", id);
    }

    @Before("anyCreateUser()  && args(userRequestDTO)")
    public void addLogBeforeCreate( Object userRequestDTO){
        log.info("Adding requested user {}", userRequestDTO);
    }

    @Before(value = "anyUpdateUserMethod() && args(id, userRequestDTO)", argNames = "id,userRequestDTO")
    public void addLoggBeforeUpdate( Long id, UserRequestDTO userRequestDTO){
        log.info("Updating user with id = {}, requested dto {}", id , userRequestDTO);
    }

    @AfterReturning(value = "anyFindByIdMethod() || anyUpdateUserMethod() || anyCreateUser()", returning = "result")
    public void addLoggingAfter(Object result){
        log.info("Returned user with params {}", result);
    }
    @After("anyDeleteUserMethod() && args(id)")
    public void logAfterDelete(Object id){
        log.info("User with id {} marked as deleted", id);
    }

}