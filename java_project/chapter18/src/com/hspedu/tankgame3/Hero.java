package com.hspedu.tankgame3;

import java.util.Vector;

//自己的坦克
public class Hero extends Tank {
    //定义一个Shot对象，表示一个射击(线程)
    Shot shot = null;
    //定义多个子弹,可以发射多颗子弹
    Vector<Shot> shots = new Vector<>();

    public Hero(int x, int y){
        super(x,y);
    }

    //射击行为
    public void shotEnemyTank() {
        //限制子弹数
        if (shots.size() == 5) {
            return;
        }
        //创建 Shot 对象， 根据当前 Hero 对象的位置和方向来创建 Shot 对象
        switch (getDirect()) { // getDirect() 获取坦克方向
            case 0://上
                shot =  new Shot(getX()+20,getY(),0);
                break;
            case 1://右
                shot = new Shot(getX()+60,getY()+20,1);
                break;
            case 2://下
                shot = new Shot(getX()+20,getY()+60,2);
                break;
            case 3://左
                shot = new Shot(getX(),getY()+20,3);
                break;
        }
        //把新创建的子弹存入
        shots.add(shot);
        //启动线程
        new Thread(shot).start();
    }
}
