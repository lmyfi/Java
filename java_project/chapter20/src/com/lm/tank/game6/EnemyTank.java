package com.lm.tank.game6;

import java.util.Vector;

public class EnemyTank extends Tank {
    Vector<Shot> shots = new Vector<>();
    //增加成员，EnemyTank 可以得到敌人坦克的Vector
    Vector<EnemyTank> enemyTanks = new Vector<>();
    boolean isLive = true;
    Shot shot = null;
    public EnemyTank(int x, int y) {
        super(x, y);
    }

    //这里提供一个方法， 可以将MyPanel 的成员 Vector<EnemyTank> enemyTank = new Vector<>();
    //设置到 EnemyTank 的成员 enemyTanks
    public void setEnemyTanks(Vector<EnemyTank> enemyTanks) {
        this.enemyTanks = enemyTanks;
    }

    //编写方法， 判断当前这个敌人坦克 是否和 enemyTanks 集合中的其它坦克发生重叠或者碰撞
    public boolean isTouchEnemyTank() {

        //编写方法，判断当前敌人坦克方向
        switch (getDirect()) {
            case 0: //上
                for (int i = 0; i < enemyTanks.size(); i++) {
                    //从Vector集合中取出一个敌人坦克
                    EnemyTank enemyTank = enemyTanks.get(i);
                    //不和自己比较
                    if (enemyTank != this) {
                        //如果坦克是上/下
                        if (enemyTank.getDirect() == 0 || enemyTank.getDirect() == 2) {
                            //当前坦克 右上角的坐标 [this.getX() + 60  , this.getY() ]
                            if (this.getX() + 60 >= enemyTank.getX()
                                    && this.getX() + 60 <= enemyTank.getX() + 40
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 60 ) {
                                return true;
                            }
                            //当前坦克 右下角坐标  [this.getX[] + 60, this.getY() + 40]
                            if (this.getX() + 60 >= enemyTank.getX()
                                    && this.getX() + 60 <= enemyTank.getX() + 40
                                    && this.getY() + 40 >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 60 ) {
                                return true;
                            }
                        }
                        //如果坦克右/左
                        if (enemyTank.getDirect() == 1 || enemyTank.getDirect() == 3) {
                            //右上角
                            if (this.getX() + 60 >= enemyTank.getX()
                                    && this.getX() + 60 <= enemyTank.getX() + 60
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 40 ) {
                                return true;
                            }
                            //右下角
                            if (this.getX() + 60 >= enemyTank.getX()
                                    && this.getX() + 60 <= enemyTank.getX() + 60
                                    && this.getY() + 40 >= enemyTank.getY()
                                    && this.getY() + 40 <= enemyTank.getY() + 40 ) {
                                return true;
                            }
                        }
                    }

                }
                break;
            case 1:
                for (int i = 0; i < enemyTanks.size(); i++) {
                    //从Vector集合中取出一个敌人坦克
                    EnemyTank enemyTank = enemyTanks.get(i);
                    //不和自己比较
                    if (enemyTank != this) {
                        //如果坦克是上/下
                        if (enemyTank.getDirect() == 0 || enemyTank.getDirect() == 2) {
                            //当前坦克 右上角的坐标 [this.getX() + 60 , this.getY()]
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 40
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 60 ) {
                                return true;
                            }
                            //当前坦克 右下角坐标  [this.getX[] + 60, this.getY() + 40]
                            if (this.getX() + 60 >= enemyTank.getX()
                                    && this.getX() + 60 <= enemyTank.getX() + 40
                                    && this.getY() + 40 >= enemyTank.getY()
                                    && this.getY() + 40 <= enemyTank.getY() + 60 ) {
                                return true;
                            }
                        }
                        //如果坦克右/左
                        if (enemyTank.getDirect() == 1 || enemyTank.getDirect() == 3) {
                            //当前坦克 右上角的坐标 [this.getX() + 60 , this.getY()]
                            if (this.getX() + 60  >= enemyTank.getX()
                                    && this.getX() + 60 <= enemyTank.getX() + 60
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 40 ) {
                                return true;
                            }
                            //当前坦克 右下角坐标  [this.getX[] + 60, this.getY() + 40]
                            if (this.getX() + 60 >= enemyTank.getX()
                                    && this.getX() + 60 <= enemyTank.getX() + 60
                                    && this.getY() + 40 >= enemyTank.getY()
                                    && this.getY() + 40 <= enemyTank.getY() + 40 ) {
                                return true;
                            }
                        }
                    }

                }
                break;
            case 2:
                for (int i = 0; i < enemyTanks.size(); i++) {
                    //从Vector集合中取出一个敌人坦克
                    EnemyTank enemyTank = enemyTanks.get(i);
                    //不和自己比较
                    if (enemyTank != this) {
                        //如果坦克是上/下
                        if (enemyTank.getDirect() == 0 || enemyTank.getDirect() == 2) {
                            //当前坦克 左下角的坐标 [this.getX() , this.getY() + 60]
                            if (this.getX() + 60 >= enemyTank.getX()
                                    && this.getX() + 60 <= enemyTank.getX() + 40
                                    && this.getY()  >= enemyTank.getY()
                                    && this.getY()  <= enemyTank.getY() + 60 ) {
                                return true;
                            }
                            //当前坦克 右下角坐标  [this.getX[] + 40, this.getY() + 60]
                            if (this.getX()  >= enemyTank.getX()
                                    && this.getX()  <= enemyTank.getX() + 40
                                    && this.getY() + 60 >= enemyTank.getY()
                                    && this.getY() + 60 <= enemyTank.getY() + 60 ) {
                                return true;
                            }
                        }
                        //如果坦克右/左
                        if (enemyTank.getDirect() == 1 || enemyTank.getDirect() == 3) {
                            //当前坦克 左下角的坐标 [this.getX() , this.getY() + 60]
                            if (this.getX()  >= enemyTank.getX()
                                    && this.getX()  <= enemyTank.getX() + 60
                                    && this.getY()  + 60 >= enemyTank.getY()
                                    && this.getY() + 60 <= enemyTank.getY() + 40 ) {
                                return true;
                            }
                            //当前坦克 右下角坐标  [this.getX[] + 40, this.getY() + 60]
                            if (this.getX() + 40 >= enemyTank.getX()
                                    && this.getX() + 40 <= enemyTank.getX() + 60
                                    && this.getY() + 60 >= enemyTank.getY()
                                    && this.getY() + 60 <= enemyTank.getY() + 40 ) {
                                return true;
                            }
                        }
                    }

                }
                break;
            case 3:
                for (int i = 0; i < enemyTanks.size(); i++) {
                    //从Vector集合中取出一个敌人坦克
                    EnemyTank enemyTank = enemyTanks.get(i);
                    //不和自己比较
                    if (enemyTank != this) {
                        //如果坦克是上/下
                        if (enemyTank.getDirect() == 0 || enemyTank.getDirect() == 2) {
                            //当前坦克 左上角的坐标 [this.getX() , this.getY()]
                            if (this.getX()  >= enemyTank.getX()
                                    && this.getX()  <= enemyTank.getX() + 40
                                    && this.getY()  >= enemyTank.getY()
                                    && this.getY()  <= enemyTank.getY() + 60 ) {
                                return true;
                            }
                            //当前坦克 左下角坐标  [this.getX[] , this.getY() + 40]
                            if (this.getX()  >= enemyTank.getX()
                                    && this.getX()  <= enemyTank.getX() + 40
                                    && this.getY() + 40 >= enemyTank.getY()
                                    && this.getY() + 40 <= enemyTank.getY() + 60 ) {
                                return true;
                            }
                        }
                        //如果坦克右/左
                        if (enemyTank.getDirect() == 1 || enemyTank.getDirect() == 3) {
                            //当前坦克 左上角的坐标 [this.getX() , this.getY()]
                            if (this.getX()  >= enemyTank.getX()
                                    && this.getX()  <= enemyTank.getX() + 60
                                    && this.getY()  >= enemyTank.getY()
                                    && this.getY()  <= enemyTank.getY() + 40 ) {
                                return true;
                            }
                            //当前坦克 左下角坐标  [this.getX[] , this.getY() + 40]
                            if (this.getX()  >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 60
                                    && this.getY() + 40 >= enemyTank.getY()
                                    && this.getY() + 40 <= enemyTank.getY() + 40 ) {
                                return true;
                            }
                        }
                    }

                }
                break;
        }
        return false;
    }

}
