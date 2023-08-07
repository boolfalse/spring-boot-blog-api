package am.github.springbootblogapi.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordGenerator {

    public static void main(String[] args) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println("Admin's password:");
        System.out.println(passwordEncoder.encode("password"));
        System.out.println("Client's password:");
        System.out.println(passwordEncoder.encode("password"));
    }

}
