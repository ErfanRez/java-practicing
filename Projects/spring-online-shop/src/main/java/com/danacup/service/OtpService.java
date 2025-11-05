package com.danacup.service;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class OtpService {

    private final Map<String, String> otpStorage = new ConcurrentHashMap<>();

    public void storeOtp(String phoneNumber, String otp) {
        otpStorage.put(phoneNumber, otp);
    }

    public boolean isValidOtp(String phoneNumber, String otp) {
        String storedOtp = otpStorage.get(phoneNumber);
        return otp.equals(storedOtp);
    }

    public void removeOtp(String phoneNumber) {
        otpStorage.remove(phoneNumber);
    }
}

