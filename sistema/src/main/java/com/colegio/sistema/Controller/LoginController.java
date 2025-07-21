package com.colegio.sistema.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.Authentication;

@Controller
public class LoginController {
    
    @GetMapping("/login")
    public String mostrarLogin(){
        return "login";
    }

    
}
