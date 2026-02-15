package com.example.checkcard.services;

public interface QrCodeService {
    byte[] generateQrCode(String data) throws Exception;
}
