package com.vinimanfrin.inscreening.Resource;

import java.util.Locale;
import java.util.ResourceBundle;

public class InternationalizationExample {
    public static void main(String[] args) {
        Locale locale = new Locale("pt", "BR"); // Define a localidade para português do Brasil
        ResourceBundle messages = ResourceBundle.getBundle("messages", locale);

        System.out.println(messages.getString("welcome_message"));
    }
}
