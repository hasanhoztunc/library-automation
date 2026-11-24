package com.hasanoztunc.enumerations;

import lombok.Getter;

@Getter
public enum MessageType {

    GENERAL_EXCEPTION("1000", "Genel Bir Hata Oluştu"),
    USERNAME_NOT_FOUND("1001", "Kullanıcı Bulunamadı"),
    USERNAME_ALREADY_EXISTS("1002", "Bu Kullanıcı Adı Kullanılıyor"),
    USERNAME_OR_PASSWORD_INVALID("1003", "Kullanıcı Adı veya Şifre Hatalı"),
    TOKEN_IS_EXPIRED("1004", "Token Süresi Bitmiştir"),
    BOOK_ALREADY_EXISTS("1005", "Kitap Zaten Kayıtlı"),
    NO_RECORD_EXISTS("1006", "Kayıt Bulunamadı");

    private String code;
    private String message;

    MessageType(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
