package com.eazybytes.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AccountController {

    @GetMapping("/myAccount")
    public String getAccountDetails(HttpSession session) {
        System.out.println("Session ID: " + session.getId());
        return "Here are the account details from the DB";
    }
}
