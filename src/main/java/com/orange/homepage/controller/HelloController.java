package com.orange.homepage.controller;

import com.orange.homepage.bean.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class HelloController {

    @Autowired
    private Person person;

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello() {
        return "hello world!";
    }

    @RequestMapping(value = "/person", method = RequestMethod.GET)
    public String getValue() {
        return person.getName();
    }

    @RequestMapping(value = "/hi/{id}", method = RequestMethod.GET)
    public String hi(@PathVariable("id") int id) {
        return String.valueOf(id);
    }

//    @RequestMapping(value = "/hi2", method = RequestMethod.GET)
    @GetMapping(value = "/hi2")
    public String hi2(@RequestParam(value = "id", required = false, defaultValue = "0") int id) {
        return Integer.toString(id);
    }
}
