package com.hspedu.tankgame3;

import java.awt.*;

public class Shot implements Runnable{
    int x;//横坐标
    int y;//纵坐标
    int direct = 0; //方向
    int speed = 1;// 子弹速度
    boolean islive= true; //子弹是否存活
    Graphics g;
    public Shot(int x, int y, int direct) {
        this.x = x;
        this.y = y;
        this.direct = direct;
    }

    @Override
    public void run() {
        while(true) {
            //线程休眠50毫秒
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //选择方向
            switch (direct) {
                case 0://上
                    y -=speed;
                    break;
                case 1://右
                    x +=speed;
                    break;
                case 2://下
                    y +=speed;
                    break;
                case 3://左
                    x -=speed;
                    break;
            }
            System.out.println("子弹坐标" + "x="+ x + "y="+ y);
            //当子弹碰到面板边界时，就应该销毁（把启动的子弹线程销毁）
            //当子弹碰到敌人坦克是，也应该结束线程
            if ( !(x >= 0 && x <= 1000 && y >= 0 && y <=750 && islive) ) {
                islive = false;
                System.out.println("子弹线程结束。。。");
                break;
            }
        }
    }

}

