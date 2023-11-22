package com.lmy.draw;

import javax.swing.*;
import java.awt.*;
//演示如何在面板上画一个圆
public class DrawCircle extends JFrame{ //JFrame窗口

    private  MyPanel mp = null;  //定义一个面板
    public static void main(String[] args){
        new DrawCircle();
    }
    public DrawCircle() {  //构造器
        //初始化面板
        mp = new MyPanel();
        //把面板放入窗口
        this.add(mp);
        //设置面板的大小
        this.setSize(400,300);
        //当点击窗口x号时，程序完全退出
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setVisible(true); //可以显示
    }
}
//1.先定义一个面板MyPanel,继承JPanel类；画图形就在这上面画
class MyPanel extends JPanel{
    //说明：
    //1.MyPanel对象是一个画板
    //2.Graphics g 把g 理解成一支画笔
    //3.Graphics 提供了许多的画图工具
    @Override
    public void paint(Graphics g) {
        super.paint(g);//调父类的方法初始化
        //g.drawOval(10,10,100,100);
        g.setColor(Color.blue);
        g.drawRect(120,120,10,80);
        g.fillRect(120,120,10,80);
        g.drawRect(200,120,10,80);
        g.fillRect(200,120,10,80);
        g.drawRect(130,130,80,60);
        g.fillRect(130,130,80,60);
        g.setColor(Color.gray);
        g.drawOval(150,140,30,30);
        g.fillOval(150,140,30,30);
        g.drawLine(165,155,165,110);
    }
}
