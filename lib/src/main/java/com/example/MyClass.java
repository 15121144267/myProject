package com.example;

abstract class Demo1 {

}

class Demo2 extends Demo1 {
    public Demo2() {
        System.out.println("111");
    }
}

class Demo3 extends Demo1 {

    public Demo3() {
        System.out.println("222");
    }
}

interface getDemo {
    Demo1 getDemo1();
}

class GetDemo4 implements getDemo {
    @Override
    public Demo2 getDemo1() {
        return new Demo2();
    }
}

class GetDemo5 implements getDemo {
    @Override
    public Demo3 getDemo1() {
        return new Demo3();
    }
}


class Factory {
    public Demo1 getDemo(int type) {
        Demo1 demo1 = null;
        switch (type) {
            case 1:
                demo1 = new Demo2();
                break;
            case 2:
                demo1 = new Demo3();
                break;
            default:
                break;
        }
        return demo1;
    }
}

public class MyClass {
    public static void main(String[] arg) {
        //简单工厂模式
       /* Factory factory = new Factory();
        Demo1 demo2 = factory.getDemo(1);
        Demo1 demo3 = factory.getDemo(2);*/
        //工厂模式
        GetDemo4 getDemo4 = new GetDemo4();
        getDemo4.getDemo1();
        GetDemo5 getDemo5 = new GetDemo5();
        getDemo5.getDemo1();
    }
}
