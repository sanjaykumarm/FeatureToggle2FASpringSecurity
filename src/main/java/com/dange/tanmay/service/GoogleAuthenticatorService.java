package com.dange.tanmay.service;

import com.dange.tanmay.dao.GenerateQRCode;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import com.warrenstrange.googleauth.GoogleAuthenticatorQRGenerator;
import lombok.RequiredArgsConstructor;
import org.apache.http.client.utils.URLEncodedUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.nio.charset.Charset;

@Service
@RequiredArgsConstructor
public class GoogleAuthenticatorService {

    @Autowired
    private  GoogleAuthenticator gAuth;

    public BitMatrix generate(String username, GenerateQRCode generateQRCode) throws WriterException, URISyntaxException {
        final GoogleAuthenticatorKey key = gAuth.createCredentials(username);

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        String otpAuthURL = GoogleAuthenticatorQRGenerator.getOtpAuthTotpURL("eSupplier", username, key);
        if(generateQRCode != null) {
            MultiValueMap<String, String> parameters =
                    UriComponentsBuilder.fromUriString(otpAuthURL).build().getQueryParams();

            generateQRCode.setupkey = parameters.get("secret").get(0).replaceAll("....", "$0 ").trim();
        }
        return qrCodeWriter.encode(otpAuthURL, BarcodeFormat.QR_CODE, 200, 200);
    }

    public boolean validate(String username, int token) {
        return gAuth.authorizeUser(username, token);
    }

}
