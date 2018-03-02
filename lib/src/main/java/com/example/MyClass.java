package com.example;

import java.util.Timer;
import java.util.TimerTask;

public class MyClass {
    static int count = 0;

    public static void main(String[] strings) {
        class MyTimeClock extends TimerTask {
            @Override
            public void run() {
                System.out.println("count="+count);
                new Timer().schedule(new MyTimeClock(), 2000 + 1000 * (count++ % 2));
            }
        }
        new Timer().schedule(new MyTimeClock(), 3000);
    }
}
