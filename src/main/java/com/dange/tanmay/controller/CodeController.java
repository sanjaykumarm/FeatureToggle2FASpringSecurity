package com.dange.tanmay.controller;


import com.dange.tanmay.dao.GenerateQRCode;
import com.dange.tanmay.dao.User;
import com.dange.tanmay.repository.CredentialRepository;
import com.dange.tanmay.service.GoogleAuthenticatorService;
import com.dange.tanmay.dao.ValidateCodeDao;
import com.dange.tanmay.service.UserService;
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
import java.util.Base64;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/code")
public class CodeController {

    @Autowired
    UserService userService;

    private final GoogleAuthenticator gAuth;

    @Autowired
    private GoogleAuthenticatorService googleAuthenticatorService;

    @SneakyThrows
    @GetMapping("/generate/{username}")
    public byte[]  generate(@PathVariable String username, HttpServletResponse response) {

        BitMatrix bitMatrix = googleAuthenticatorService.generate(username, null);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", byteArrayOutputStream);
        return  byteArrayOutputStream.toByteArray();
    }

    @SneakyThrows
    @GetMapping("/generate2/{username}")
    public GenerateQRCode  generate2(@PathVariable String username, HttpServletResponse response) {
        GenerateQRCode generateQRCode = new GenerateQRCode();
        generateQRCode.username = username;

        BitMatrix bitMatrix = googleAuthenticatorService.generate(username, generateQRCode);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", byteArrayOutputStream);
        generateQRCode.base64QRCode = Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray());
        return generateQRCode;
    }

    @PostMapping("/validate/key")
    public boolean validateKey(@RequestBody ValidateCodeDao body) {
        String userName = body.getUsername();
        int code = body.getCode();

        boolean isValidated = googleAuthenticatorService.validate(userName, code);

        //Enable the mfa enable to true only if validated.
        if(isValidated) {
            User user = userService.getUserByUsername(userName);
            user.setMfaEnabled(true);
            userService.saveOrUpdate(user);
        }

        return isValidated;
    }







}
