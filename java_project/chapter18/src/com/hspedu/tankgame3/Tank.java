package com.hspedu.tankgame3;

public class Tank {
    private int x; //坦克的横坐标
    private int y;//坦克的纵坐标
    private int direct; //坦克方向 0上 1右 2下 3左
    private int speed = 1;
    private int type = 0;
    boolean isLive = true;
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    //上右下左移动方法
    public void moveUp(){
        if(getY() > 0) {
            y -= speed;
        }
    }
    public void moveRight(){
        if (getX()+60 < 1000) {
            x += speed;
        }
    }
    public void moveDown(){
        if(getY() + 60 < 750) {
            y += speed;
        }
    }
    public void moveLeft(){
        if (getX() > 0) {
            x -= speed;
        }
    }
    public Tank(int x , int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getDirect() {
        return direct;
    }
    public void setDirect(int direct) {
        this.direct = direct;
    }


}
