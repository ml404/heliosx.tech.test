package org.ml404.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class WebController {

    public WebController() {
    }

    @RequestMapping(method = RequestMethod.GET, value = "/")
    public String index() {
        return "index";  // Assuming you have a view called 'index'
    }



}
