package com.hspedu.tankgame3;

import javax.swing.*;
import java.awt.*;

public class LmyTankGame3 extends JFrame {
    //定义MyPanel
    MyPanel mp = null;
    public static void main(String[] args) {
        LmyTankGame3 lmytankGame01 = new LmyTankGame3();
    }
    public LmyTankGame3() {
        mp = new MyPanel();
        Thread thread = new Thread(mp);
        thread.start();
        this.add(mp);//游戏的绘图区
        this.setSize(1000,750);;
        this.addKeyListener(mp);//让JFrame监听mp
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//点击窗口x号，完全退出
        this.setVisible(true);//显示图形
    }
}
