package com.dange.tanmay.controller;


import com.dange.tanmay.service.GoogleAuthenticatorService;
import com.dange.tanmay.dao.ValidateCodeDao;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/code")
public class CodeController {

    private final GoogleAuthenticator gAuth;

    @Autowired
    private GoogleAuthenticatorService googleAuthenticatorService;

    @SneakyThrows
    @GetMapping("/generate/{username}")
    public byte[]  generate(@PathVariable String username, HttpServletResponse response) {

        BitMatrix bitMatrix = googleAuthenticatorService.generate(username);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", byteArrayOutputStream);
        return  byteArrayOutputStream.toByteArray();
    }

    @PostMapping("/validate/key")
    public boolean validateKey(@RequestBody ValidateCodeDao body) {
        return googleAuthenticatorService.validate(body.getUsername(), body.getCode());
    }







}
