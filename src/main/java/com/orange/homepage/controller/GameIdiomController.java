package com.orange.homepage.controller;

import com.orange.homepage.bean.GameIdiom;
import com.orange.homepage.service.GameIdiomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameIdiomController {

    @Autowired
    private GameIdiomService gameIdiomService;

    @GetMapping(value = "/game/idiom/two")
    public String idiomGame() {
        String[] twoIdiom = gameIdiomService.getTwoIdiomWithSameCharacter();
        GameIdiom gameIdiom = gameIdiomService.getFinalTwoIdiomAndOption(twoIdiom);
        String display = gameIdiomService.finalString(gameIdiom);
        return display;
    }
}
