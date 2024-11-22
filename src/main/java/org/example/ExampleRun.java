package org.example;

import java.io.File;

public class ExampleRun {

    public static void run(GemsHelper gemsHelper ,int i) {
        File gemFile = new File("src/main/resources/rubygems-update-3.5.23.gem");
        System.out.println(i + ":" + gemsHelper.getGemNameVersionPlatform(gemFile));
    }
}
