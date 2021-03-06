package ru.itis.trofimoff.todoapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.itis.trofimoff.todoapp.api.ApiService;
import ru.itis.trofimoff.todoapp.dto.SignUpFormDto;
import ru.itis.trofimoff.todoapp.services.user.UserService;

import javax.validation.Valid;
import java.util.Objects;

@Controller
public class SignUpController {

    @Autowired
    public UserService userService;

    @Autowired
    public ApiService apiService;

    @GetMapping(value = "/")
    public String getRegistrationPageEmptyPathMapping(Model model){
        model.addAttribute("weatherData", apiService.convertData(apiService.getData()));
        model.addAttribute("signUpFormDto", new SignUpFormDto());
        return "redirect:/registration";
    }

    @GetMapping(value = "/registration")
    public String getRegistrationPageDefaultMapping(Model model){
        model.addAttribute("weatherData", apiService.convertData(apiService.getData()));
        model.addAttribute("signUpFormDto", new SignUpFormDto());
        return "registration";
    }

    @PostMapping(value = "/registration")
    public String postRegistrationPageDefaultMapping(@Valid SignUpFormDto signUpForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().stream().anyMatch(error -> {
                if (Objects.requireNonNull(error.getCodes())[0].equals("signUpFormDto.ValidFields")) {
                    model.addAttribute("namesErrorMessage", error.getDefaultMessage());
                }
                return true;
            });
            model.addAttribute("weatherData", apiService.convertData(apiService.getData()));
            model.addAttribute("signUpFormDto", signUpForm);
            return "registration";
        }
        userService.saveUser(signUpForm);
        return "redirect:/sign-in";
    }
}
