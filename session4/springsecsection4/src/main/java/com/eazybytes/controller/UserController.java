package com.eazybytes.controller;

import com.eazybytes.model.Customer;
import com.eazybytes.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class UserController {

    private CustomerRepository customerRepository;
    private PasswordEncoder passwordEncoder;




    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody Customer customer) {
        // ...
        try {
            // 1. 將傳入的明文密碼進行雜湊
            String hashPwd = passwordEncoder.encode(customer.getPwd());
            customer.setPwd(hashPwd); // 更新 customer 物件中的密碼為雜湊值

            // 2. 儲存使用者到資料庫
            Customer savedCustomer = customerRepository.save(customer);

            // 3. 根據儲存結果回傳不同的回應
            if (savedCustomer.getId() > 0) {
                return ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body("Given user details are successfully registered.");
            } else {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body("User registration failed.");
            }
        } catch (Exception ex) {
            // 處理例外情況
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An exception occurred: " + ex.getMessage());
        }


    }


}
