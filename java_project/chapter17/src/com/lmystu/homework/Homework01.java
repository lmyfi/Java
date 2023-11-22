package com.lmystu.homework;

//例题
//在main方法中启动两个线程
//第一个线程循环随机打印100一内的整数
//直到第二个线程从键盘读取“Q”命令
import java.awt.*;
import java.awt.event.KeyListener;
import java.util.Locale;
import java.util.Scanner;

public class Homework01 {
    public static void main(String[] args) {
        A a = new A();
        B b = new B(a);//注意
        a.start();
        b.start();

    }
}

//创建A线程
class A extends Thread{
    private boolean loop = true;
    @Override
    public void run() {
        while(loop){
            //随机输出打印1——100的整数
            System.out.println((int) (Math.random()*100 + 1));
            //休眠
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public void setLoop(boolean loop) {
        this.loop = loop;
    }
}

//直到第二个线程从键盘输入“Q”
class B extends Thread{
    private A a;
    private Scanner scanner = new Scanner(System.in);

    public B(A a) {
        this.a = a;
    }

    @Override
    public void run() {
        while(true) {
            System.out.println("请输入指令（Q）表示推出");
            char Key = scanner.next().toUpperCase().charAt(0);
            if(Key == 'Q') {
                a.setLoop(false);
                System.out.println("b线程退出，");
                break;
            }

        }

    }
}









