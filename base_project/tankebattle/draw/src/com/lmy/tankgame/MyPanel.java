package com.lmy.tankgame;

import javax.swing.*;
import java.awt.*;

//坦克大战的绘图区
public class MyPanel extends JPanel {
    //定义我的坦克
    MyTank tank = null;
    public MyPanel() {
        tank = new MyTank(100,100); //初始化自己的坦克
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.fillRect(0,0,1000,750);//填充矩形，默认黑色

        //画出坦克-封装方法
        drawTank(tank.getX(), tank.getY(),g,0,0);

    }
    //编写方法，画出坦克

    /**
     *
     * @param x 坦克的左上角x坐标
     * @param y 坦克的左上角y坐标
     * @param g 画笔
     * @param direct  坦克方向（上下左右）
     * @param type   坦克的类型
     */
    public void drawTank(int x, int y, Graphics g, int direct, int type){
        switch (type) {
            case 0: //我们的坦克
                g.setColor(Color.cyan);
                break;
            case 1:
                g.setColor(Color.yellow);
                break;
        }
        switch (direct) {
            case 0: //表示向上
                g.fill3DRect(x,y ,10,60,false);//画出坦克左边轮子
                g.fill3DRect(x+30,y,10,60,false); //右边轮子
                g.fill3DRect(x+10,y+10,20,40,false);//中间
                g.fillOval(x+10,y+20,20,20); //圆盖子
                g.drawLine(x+20,y+30,x+20,y);//画出炮筒
               break;
            default:
                System.out.println("暂时没有处理");
        }
    }
}
