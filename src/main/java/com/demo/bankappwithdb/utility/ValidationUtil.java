package com.demo.bankappwithdb.utility;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public class ValidationUtil {

    public static boolean rejectIfInvalidAmount(double amount, RedirectAttributes redirectAttributes) {
        if (amount < 50 || amount % 50 != 0) {
            redirectAttributes.addFlashAttribute("error", "Geçerli bir miktar giriniz.");
            return true;
        }
        return false;
    }
}
