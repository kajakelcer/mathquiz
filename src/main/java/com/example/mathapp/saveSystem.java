package com.example.mathapp;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;

public class saveSystem extends mainScene {

    private static String username;
    private static int score;

    public static void saving() throws IOException {
        FileWriter fw = new FileWriter("mathScores.txt", true);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter out = new PrintWriter(bw);



        //PrintWriter newWriter = new PrintWriter("mathScores.txt");
        username = getUserName();
        score = getPoints();
        String content = "User " + username + " achived " + score + " points.";
        Path path = Path.of("mathScores.txt");
        //     newWriter.print(content);
        //}else {
            //oldWriter.print(content);
       //}
        out.println(content);

        fw.close();
        bw.close();
        out.close();
        }

    }





