package com.example.mathapp;

import java.io.IOException;
import java.io.PrintWriter;

public class saveSystem extends mainScene {

    private static String username;
    private static int score;

    public static void saving() throws IOException {
        PrintWriter newWriter = new PrintWriter("mathScores.txt");
        username = getUserName();
        score = getPoints();
        String content = "User " + username + " achived " + score + " points.";
        newWriter.println(content);
        newWriter.close();
        }
    }





