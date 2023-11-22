package com.lmy.tankgame;

import javax.swing.*;

public class LmyTankGame01 extends JFrame {
    //定义MyPanel
    MyPanel mp = null;
    public static void main(String[] args) {
        LmyTankGame01 lmytankGame01 = new LmyTankGame01();
    }
    public LmyTankGame01() {
        mp = new MyPanel();
        this.add(mp);//游戏的绘图区
        this.setSize(1000,750);;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//点击窗口x号，完全退出
        this.setVisible(true);//显示图形
    }
}
