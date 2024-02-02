package com.dange.tanmay.dao;

import javax.persistence.Column;

public class GenerateQRCode {
    public String username;
    public Boolean mfaEnabled;
    public Boolean forceEnabled;
    public String base64QRCode;
    public String setupkey;
}
