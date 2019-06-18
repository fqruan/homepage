package com.orange.homepage.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.orange.homepage.bean.GameIdiom;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

@Service
public class GameIdiomService {

    private static final Logger LOGGER = Logger.getLogger(HelloService.class);

    private static HashMap<Character, ArrayList<String>> characterMap = new HashMap<>();

    static {
        processJSON();
    }

    private static void processJSON() {
        File file = new File("D:\\projects\\IDEA\\homepage\\src\\main\\java\\com\\orange\\homepage\\file\\idiom.json");
        String sfile;
        try {
            sfile = FileUtils.readFileToString(file);
            JSONArray jsonArray = JSON.parseArray(sfile);
            int len = jsonArray.size();

            for (int i = 0; i < len; i++) {
                String word = jsonArray.getJSONObject(i).getString("word");
                for (int j = 0; j < word.length(); j++) {
                    if (!characterMap.containsKey(word.charAt(j))) {
                        characterMap.put(word.charAt(j), new ArrayList<>());
                    }
                    characterMap.get(word.charAt(j)).add(word);
                }
            }

            LOGGER.info("json parsed success");
        } catch (IOException e) {
            LOGGER.error("io exception : %s", e);
        }
    }

    public String[] getTwoIdiomWithSameCharacter() {
        if (characterMap.size() <= 0) {
            return new String[]{"一二三四", "一六七八", "一"};
        }

        ArrayList<Character> mapCopy = new ArrayList<>(characterMap.keySet());
        Random random = new Random();
        char sameChar = mapCopy.get(random.nextInt(mapCopy.size()));
        while (characterMap.get(sameChar).size() < 2) {
            sameChar = mapCopy.get(random.nextInt(mapCopy.size()));
        }

        ArrayList<String> idioms = characterMap.get(sameChar);
        int randomIdiom1 = random.nextInt(idioms.size());
        int randomIdiom2 = random.nextInt(idioms.size());
        while (randomIdiom2 == randomIdiom1) {
            randomIdiom2 = random.nextInt(idioms.size());
        }

        return new String[]{idioms.get(randomIdiom1), idioms.get(randomIdiom2), String.valueOf(sameChar)};
    }

    public GameIdiom getFinalTwoIdiomAndOption(String[] idiomAndSameChar) {
        String idiom1 = idiomAndSameChar[0];
        String idiom2 = idiomAndSameChar[1];
        String sameChar = idiomAndSameChar[2];
        idiom1 = idiom1.replaceFirst(sameChar, "*");
        idiom2 = idiom2.replaceFirst(sameChar, "*");

        ArrayList<Character> mapCopy = new ArrayList<>(characterMap.keySet());
        Random random = new Random();
        char option1 = mapCopy.get(random.nextInt(mapCopy.size()));
        char option2 = mapCopy.get(random.nextInt(mapCopy.size()));
        char option3 = mapCopy.get(random.nextInt(mapCopy.size()));

        while (option1 == sameChar.charAt(0)) {
            option1 = mapCopy.get(random.nextInt(mapCopy.size()));
        }

        while (option2 == sameChar.charAt(0) || option2 == option1) {
            option2 = mapCopy.get(random.nextInt(mapCopy.size()));
        }

        while (option3 == sameChar.charAt(0) || option3 == option1 || option3 == option2) {
            option3 = mapCopy.get(random.nextInt(mapCopy.size()));
        }

        int rightOption = random.nextInt(4);

        GameIdiom gameIdiom = new GameIdiom();
        gameIdiom.setIdiomA(idiom1);
        gameIdiom.setIdiomB(idiom2);

        if (rightOption == 0) {
            gameIdiom.setOptionA(sameChar.charAt(0));
            gameIdiom.setOptionB(option1);
            gameIdiom.setOptionC(option2);
            gameIdiom.setOptionD(option3);
        }else if (rightOption == 1) {
            gameIdiom.setOptionA(option1);
            gameIdiom.setOptionB(sameChar.charAt(0));
            gameIdiom.setOptionC(option2);
            gameIdiom.setOptionD(option3);
        }else if (rightOption == 2) {
            gameIdiom.setOptionA(option2);
            gameIdiom.setOptionB(option1);
            gameIdiom.setOptionC(sameChar.charAt(0));
            gameIdiom.setOptionD(option3);
        }else {
            gameIdiom.setOptionA(option3);
            gameIdiom.setOptionB(option1);
            gameIdiom.setOptionC(option2);
            gameIdiom.setOptionD(sameChar.charAt(0));
        }

        return gameIdiom;
    }

    public String finalString(GameIdiom gameIdiom) {
        StringBuilder sb = new StringBuilder();
        sb.append(gameIdiom.getIdiomA()).append("  ");
        sb.append(gameIdiom.getIdiomB()).append("\n");
        sb.append("A: ").append(gameIdiom.getOptionA()).append("  ");
        sb.append("B: ").append(gameIdiom.getOptionB()).append("  ");
        sb.append("C: ").append(gameIdiom.getOptionC()).append("  ");
        sb.append("D: ").append(gameIdiom.getOptionD()).append("  ");

        return sb.toString();
    }
}
