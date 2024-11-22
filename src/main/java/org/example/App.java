package org.example;


/**
 * Hello world!
 */
public class App {

    static int counter = 0;

    public static void main(String[] args) {
        GemsHelper gemsHelper = new GemsHelper();
        for(int i=0;i<=3;i++){
            Thread t = new Thread(() -> new ExampleRun().run(gemsHelper, ++counter));
            t.start();
        }
    }
}
