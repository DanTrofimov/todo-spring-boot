package ru.itis.trofimoff.todoapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class SignOutController {
    @GetMapping(value = "/sign-out")
    public String getSignOutPage(HttpServletRequest request){
        return "redirect:/sign-in";
    }
}
