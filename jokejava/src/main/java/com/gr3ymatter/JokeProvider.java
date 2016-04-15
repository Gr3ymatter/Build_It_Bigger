package com.gr3ymatter;

import java.util.Random;

public class JokeProvider {



    public static String[] jokesArr = new String[]{
            "It’s hard to explain puns to kleptomaniacs because they always take things literally."
            ," I used to think the brain was the most important organ. Then I thought, look what’s telling me that."

            ," The midget fortune teller who kills his customers is a small medium at large."

            ," A farmer in the field with his cows counted 196 of them, but when he rounded them up he had 200."

            ," What does a nosey pepper do? Get jalapeño business.",

             " What is Bruce Lee’s favorite drink? Wataaaaah!",

           " The dyslexic devil worshipper sold his soul to Santa.",

            " You kill vegetarian vampires with a steak to the heart.",

           " There was a prison break and I saw a midget climb up the fence. As he jumped down her sneered at me and I thought, well that’s a little condescending.",

          "If you want to catch a squirrel just climb a tree and act like a nut."
                };


    public static String getJoke(){

        Random rand = new Random();
        return jokesArr[rand.nextInt(10)];
    }


}
