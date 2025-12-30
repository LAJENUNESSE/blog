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
    public String forwardWeb() {
        return "forward:/index.html";
    }

    /**
     * Forward admin routes to admin/index.html
     */
    @GetMapping(value = {
            "/admin",
            "/admin/",
            "/admin/login",
            "/admin/dashboard",
            "/admin/articles",
            "/admin/articles/**",
            "/admin/categories",
            "/admin/tags",
            "/admin/comments",
            "/admin/users",
            "/admin/settings"
    })
    public String forwardAdmin() {
        return "forward:/admin/index.html";
    }
}
