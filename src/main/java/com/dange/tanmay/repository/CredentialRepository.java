package com.dange.tanmay.repository;

import com.dange.tanmay.dao.User;
import com.dange.tanmay.security.CustomEncryptionService;
import com.dange.tanmay.service.UserService;
import com.warrenstrange.googleauth.ICredentialRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

@Component
public class CredentialRepository implements ICredentialRepository {

    @Autowired
    UserService userService;
    @Autowired
    CustomEncryptionService encryptionService;

    @Override
    public String getSecretKey(String username) {
        try {
            return encryptionService.decryptValue(userService.getUserByUsername(username).getCode());
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    @Override
    public void saveUserCredentials(String userName, String secretKey, int validationCode, List<Integer> scratchCodes) {

        try {
            User user = userService.getUserByUsername(userName);
            //This class is required by google authentication internally.
            UserTOTP userTOTP = new UserTOTP(userName, secretKey, validationCode, scratchCodes);
            user.setCode(encryptionService.encryptValue(userTOTP.secretKey));
            user.setMfaEnabled(false);
            userService.saveOrUpdate(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    class UserTOTP implements Serializable {

        private String username;
        private String secretKey;
        private int validationCode;
        private List<Integer> scratchCodes;

    }

}