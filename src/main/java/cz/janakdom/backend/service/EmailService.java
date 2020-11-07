package cz.janakdom.backend.service;

import org.springframework.stereotype.Service;

@Service(value = "emailService")
public class EmailService {
    public static boolean isValidEmail(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }
}
