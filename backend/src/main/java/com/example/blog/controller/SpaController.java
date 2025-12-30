package com.example.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SpaController {

    /**
     * Forward all non-API and non-static resource requests to index.html
     * This enables Vue Router to handle client-side routing
     */
    @GetMapping(value = {
            "/",
            "/login",
            "/register",
            "/about",
            "/search",
            "/article/**",
            "/category/**",
            "/tag/**"
    })
    public String forward() {
        return "forward:/index.html";
    }
}
