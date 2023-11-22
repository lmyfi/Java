package com.lm.tank.game6;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Scanner;

public class LmyTankGame06 extends JFrame {
    //定义MyPanel
    MyPanel mp = null;
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        LmyTankGame06 lmytankGame01 = new LmyTankGame06();
    }
    public LmyTankGame06() {
        System.out.println("请输入选择 1: 新游戏 2: 继续上局游戏");
        String key = scanner.next();
        mp = new MyPanel(key);
        //将mp 放入到Thread , 并启动
        Thread thread = new Thread(mp);
        thread.start();
        this.add(mp);//游戏的绘图区

        this.setSize(1300,950);;
        this.addKeyListener(mp);//让JFrame监听mp
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//点击窗口x号，完全退出
        this.setVisible(true);//显示图形

        //在JFrame 中增加相应关闭窗口的处理
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Recorder.keepRecord();
                System.exit(0);
            }
        });
    }
}
