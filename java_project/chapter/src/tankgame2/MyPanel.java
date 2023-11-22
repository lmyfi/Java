package tankgame2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.security.Key;
import java.util.Vector;

//坦克大战的绘图区
public class MyPanel extends JPanel implements KeyListener {
    //定义我的坦克
    MyTank tank = null;
    //定义敌人坦克  放入集合Vector 因为敌人坦克有多个
    Vector<EnemyTank> enemyTanks = new Vector<>();
   int enemyTankSize = 3; //坦克数量
    public MyPanel() {
        tank = new MyTank(100,100); //初始化自己的坦克
        //初始化敌人坦克
        for(int i=0;i < enemyTankSize; i++){
            EnemyTank enemyTank = new EnemyTank(100*(i+1),0);
            enemyTank.setDirect(2);
            enemyTanks.add(enemyTank);
        }
    }

    @Override
    public void paint(Graphics g) {
        int x=0;
        int y=0;
        super.paint(g);
        g.fillRect(x,y,1000,750);//填充矩形，默认黑色

        //画出自己坦克-封装方法
        //drawTank(tank.getX(), tank.getY(),g,0,0); //向上
        //drawTank(tank.getX(), tank.getY(),g,1,0); //向右
        //drawTank(tank.getX(), tank.getY(),g,2,0);//向下
        //drawTank(tank.getX(), tank.getY(),g,3,0);//向左
        drawTank(tank.getX(), tank.getY(),g,tank.getDirect(),1);

        //画出敌人的坦克，遍历Vector
        for(int i=0;i < enemyTanks.size();i++){
            //取出坦克
            EnemyTank enemyTank = enemyTanks.get(i);
            drawTank(enemyTank.getX(), enemyTank.getY(),g,enemyTank.getDirect(),0);
        }


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

        //根据坦克方向，来绘制不同形状坦克
        //direct 表示方向（0:向上  1；向右 2，向下 3，向左）


        switch (direct) {
            case 0: //表示坦克方向向上
                g.fill3DRect(x,y ,10,60,false);//画出坦克左边轮子
                g.fill3DRect(x+30,y,10,60,false); //右边轮子
                g.fill3DRect(x+10,y+10,20,40,false);//中间
                g.fillOval(x+10,y+20,20,20); //圆盖子
                g.drawLine(x+20,y+30,x+20,y);//画出炮筒
               break;
            case 1://表示向右
                g.fill3DRect(x,y ,60,10,false);//画出坦克左边轮子
                g.fill3DRect(x,y+30,60,10,false); //右边轮子
                g.fill3DRect(x+10,y+10,40,20,false);//中间
                g.fillOval(x+20,y+10,20,20); //圆盖子
                g.drawLine(x+30,y+20,x+60,y+20);//画出炮筒
                break;
            case 2://表示向下
                g.fill3DRect(x,y ,10,60,false);//画出坦克左边轮子
                g.fill3DRect(x+30,y,10,60,false); //右边轮子
                g.fill3DRect(x+10,y+10,20,40,false);//中间
                g.fillOval(x+10,y+20,20,20); //圆盖子
                g.drawLine(x+20,y+30,x+20,y+60);//画出炮筒
                break;
            case 3: //表示向左
                g.fill3DRect(x,y ,60,10,false);//画出坦克左边轮子
                g.fill3DRect(x,y+30,60,10,false); //右边轮子
                g.fill3DRect(x+10,y+10,40,20,false);//中间
                g.fillOval(x+20,y+10,20,20); //圆盖子
                g.drawLine(x+30,y+20,x,y+20);//画出炮筒
                break;
            default:
                System.out.println("暂时没有处理");
        }
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_W)
        {//向上
            //改变坦克方向
            tank.setDirect(0);
            //改变坦克坐标
            //tank.setY(tank.getY()-5);
            tank.moveUp();
        }
        else if(e.getKeyCode() == KeyEvent.VK_D){
            tank.setDirect(1);
            //tank.setX(tank.getX()+5);
            tank.moveRight();
        }
        else if(e.getKeyCode() == KeyEvent.VK_S){
            tank.setDirect(2);
            //tank.setY(tank.getY()+5);
            tank.moveDown();
        }
        else if(e.getKeyCode() == KeyEvent.VK_A){
            tank.setDirect(3);
            //tank.setX(tank.getX()-5);
            tank.moveLeft();
        }
        this.repaint();//面板重画


    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
