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
    NO_RECORD_EXISTS("1006", "Kayıt Bulunamadı"),
    LOAN_LIMIT_EXCEEDED("1007", "Aynı Anda En Fazla 3 Kitap Ödünç Alınabilir"),
    BOOK_ALREADY_BORROWED("1008", "Bu Kitap Şu Anda Ödünç Verilmiş"),
    LOAN_NOT_FOUND("1009", "Bu Kitap İçin Aktif Ödünç Kaydı Bulunamadı");

    private String code;
    private String message;

    MessageType(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
