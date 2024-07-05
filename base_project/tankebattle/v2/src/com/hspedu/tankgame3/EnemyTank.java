package com.hspedu.tankgame3;

import java.util.Random;
import java.util.Vector;

public class EnemyTank extends Tank implements Runnable{
    Vector<Shot> shots = new Vector<>();
    boolean isLive = true;
    Shot shot = null;
    public EnemyTank(int x, int y) {
        super(x, y);
    }

    //编写方法，让敌方坦克动起来
    @Override
    public void run() {
            while (true) {
                //设置敌人一颗子弹消亡后，继续发射一颗子弹
                if (isLive && shots.size() == 0) {
                    Shot s = null;

                    //判断坦克的方向，创建对应的子弹
                    switch (getDirect()) {
                        case 0:
                            s = new Shot(getX() + 20 , getY(),0);
                            break;
                        case 1:
                            s = new Shot(getX() + 60, getY() + 20,1);
                            break;
                        case 2:
                            s = new Shot(getX() + 20, getY() + 60, 2);
                            break;
                        case 3:
                            s = new Shot(getX() + 60, getY() + 20, 3);
                            break;
                    }
                    shots.add(s);
                    //启动
                    new Thread(s).start();
                }

                switch (getDirect()) {
                    case 0:
                        //让坦克保持一个方向走30步
                        for(int i = 0; i < 30 ; i++) {
                                moveUp();
                            //休眠50毫秒
                            try {
                                Thread.sleep(50);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    case 1:
                        //让坦克保持一个方向走30步
                        for(int i = 0; i < 30 ; i++) {
                                moveRight();
                            //休眠50毫秒
                            try {
                                Thread.sleep(50);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    case 2:
                        //让坦克保持一个方向走30步
                        for(int i = 0; i < 30 ; i++) {
                                moveDown();
                            //休眠50毫秒
                            try {
                                Thread.sleep(50);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    case 3:
                        //让坦克保持一个方向走30步
                        for(int i = 0; i < 30 ; i++) {
                                moveLeft();
                            //休眠50毫秒
                            try {
                                Thread.sleep(50);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                }
                //然后随机改变坦克方向 0 —— 3
                setDirect((int)(Math.random() * 4));
                if(isLive == false) {
                    break; //退出线程
                }

            }
    }
}
