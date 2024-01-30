package com.dange.tanmay.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import com.warrenstrange.googleauth.GoogleAuthenticatorQRGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GoogleAuthenticatorService {

    @Autowired
    private  GoogleAuthenticator gAuth;

    public BitMatrix generate(String username) throws WriterException {
        final GoogleAuthenticatorKey key = gAuth.createCredentials(username);

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        String otpAuthURL = GoogleAuthenticatorQRGenerator.getOtpAuthTotpURL("my-demo", username, key);
        return qrCodeWriter.encode(otpAuthURL, BarcodeFormat.QR_CODE, 200, 200);
    }

    public boolean validate(String username, int token) {
        return gAuth.authorizeUser(username, token);
    }

}
