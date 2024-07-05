package com.hspedu.tankgame5;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import java.util.Vector;

//坦克大战的绘图区
public class MyPanel extends JPanel implements KeyListener,Runnable {
    //定义我的坦克
    Hero tank = null;
    //定义敌人坦克  放入集合Vector 因为敌人坦克有多个
    Vector<EnemyTank> enemyTanks = new Vector<>();
    //定义一个存放的Node 对象的Vector， 用于恢复敌人坦克的坐标和方向
    Vector<Node> nodes = new Vector<>();
    //定义一个Vector ,用于存放炸弹
    //说明：当子弹击中坦克是，加入一个Bomb对象到bombs
    Vector<Bomb> bombs = new Vector<>();
   int enemyTankSize = 3; //坦克数量
    //定义三张爆炸图片,用于显示爆炸效果
    Image image1 = null;
    Image image2 = null;
    Image image3 = null;

    public MyPanel(String key) {
        nodes = Recorder.getNodesAndEnemyTankRec();
        //将Mypanel对象的 enemyTank 设置给 Recorder 的 enemyTanks
        Recorder.setEnemyTanks(enemyTanks);
        tank = new Hero(500,10); //初始化自己的坦克

        switch (key) {
            case "1" :
                //初始化敌人坦克
                for(int i=0;i < enemyTankSize; i++){
                    //创建一个敌人坦克
                    EnemyTank enemyTank = new EnemyTank(100*(i+1),0);
                    //将enemyTank 设置给 enemyTank
                    enemyTank.setEnemyTanks(enemyTanks);
                    //设置方向
                    enemyTank.setDirect(2);
                    //启动敌人坦克线程,让它动起来
                    new Thread(enemyTank).start();
                    //给该enemyTank 加一个子弹
                    Shot shot = new Shot(enemyTank.getX()+20,enemyTank.getY()+60,enemyTank.getDirect());
                    //加入enemyTank的Vector 成员
                    enemyTank.shots.add(shot);
                    //启动 shot 对象
                    new Thread(shot).start();
                    //加入坦克集合
                    enemyTanks.add(enemyTank);
                }
                break;
            case "2": //继续上局游戏
                //初始化敌人坦克
                for(int i=0;i < nodes.size(); i++){
                    Node node = nodes.get(i);
                    //创建一个敌人坦克
                    EnemyTank enemyTank = new EnemyTank(node.getX(),node.getY());
                    //将enemyTank 设置给 enemyTank
                    enemyTank.setEnemyTanks(enemyTanks);
                    //设置方向
                    enemyTank.setDirect(node.getDirect());
                    //启动敌人坦克线程,让它动起来
                    new Thread(enemyTank).start();
                    //给该enemyTank 加一个子弹
                    Shot shot = new Shot(enemyTank.getX()+20,enemyTank.getY()+60,enemyTank.getDirect());
                    //加入enemyTank的Vector 成员
                    enemyTank.shots.add(shot);
                    //启动 shot 对象
                    new Thread(shot).start();
                    //加入坦克集合
                    enemyTanks.add(enemyTank);
                }
                break;
            default:
                System.out.println("你的输入有误。。。。");
        }


        //初始化图片对象
        image1 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_1.gif"));
        image2 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_2.gif"));
        image3 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_3.gif"));
    }

    //编写方法， 显示我方击毁敌方坦克的信息
    public void showInfo(Graphics g) {

        //画出玩家总成绩
        g.setColor(Color.BLACK);
        Font font= new Font("宋体", Font.BOLD, 25);
        g.setFont(font);

        g.drawString("你累积击毁敌方坦克", 1020, 30);
        drawTank(1020, 60, g, 0, 0);
        g.setColor(Color.BLACK);
        g.drawString(Recorder.getAllEnemyTankNum() + "", 1080, 100);
    }



    @Override
    public void paint(Graphics g) {
        int x=0;
        int y=0;
        super.paint(g);
        showInfo(g);
        g.fillRect(x,y,1000,750);//填充矩形，默认黑色
        if ( tank != null && tank.isLive) {
            //画出自己坦克-封装方法
            drawTank(tank.getX(), tank.getY(), g, tank.getDirect(), 1);
        }
//        //画出Hero射击的子弹
//        if(tank.shot != null && tank.shot.islive == true) {
//           g.draw3DRect(tank.shot.x,tank.shot.y,1,1,false);
//        }
        for(int i = 0; i < tank.shots.size(); i++) {
            Shot shot = tank.shots.get(i);
            if(shot != null && shot.islive) {
                g.draw3DRect(shot.x, shot.y, 1,1,false);
            }else { // 如果shot对象以及无效，就从shots集合中拿掉
                tank.shots.remove(shot);
            }
        }

        //如果bombs 集合中有对象， 就画出
        for(int i=0; i < bombs.size(); i++) {
            //取出炸弹
            final Bomb bomb = bombs.get(i);
            //根据当前这个bomb的对象的life值去画出对应的图片
            if(bomb.life > 6) {
                g.drawImage(image1, bomb.x, bomb.y, 60 ,60 ,this);
            }else if(bomb.life > 3) {
                g.drawImage(image2, bomb.x, bomb.y, 60,60, this);
            }else {
                g.drawImage(image3, bomb.x, bomb.y, 60,60, this);
            }
            //让炸弹的生命值减少
            bomb.lifeDown();
            //如果bomb life 为0 ， 就从bombs 的集合中删除
            if (bomb.life == 0) {
                bombs.remove(bomb);
            }
        }

        //画出敌人的坦克，遍历Vector
        for(int i=0;i < enemyTanks.size();i++) {
            //取出坦克
            EnemyTank enemyTank = enemyTanks.get(i);
            //判断当前坦克是否存活
            if(enemyTank.isLive) {
                drawTank(enemyTank.getX(), enemyTank.getY(), g, enemyTank.getDirect(), 0);
                //画出 enemyTank 所有子弹
                for (int j = 0; j < enemyTank.shots.size(); j++) {
                    //取出子弹
                    Shot shot = enemyTank.shots.get(j);
                    //绘制子弹
                    if (shot.islive) {
                        g.fill3DRect(shot.x, shot.y, 1, 1, false);
                    } else {
                        //从 Vector 移除
                        enemyTank.shots.remove(shot);
                    }
                }
            }
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
            case 1://敌人坦克
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
        //如果用户按下J,就发射
        if(e.getKeyCode() == KeyEvent.VK_J) { //J键
            System.out.println("用户按下J键");
            //发射一颗子弹
//            if ( tank.shot == null  || !tank.shot.islive) {
//                tank.shotEnemyTank();
//            }
            //发射多颗子弹
            tank.shotEnemyTank();
        }
        this.repaint();//面板重画


    }

    //发射多颗子弹时，要取出所有的子弹与敌方坦克作比较
    public void hitEnemyTank() {
        for (int i = 0; i < tank.shots.size(); i++ ) {
            Shot shot = tank.shots.get(i);
            if(shot != null && shot.islive) {//当我的子弹还存活
                //遍历敌人所有坦克
                for(int j=0; j < enemyTanks.size(); j++) {
                    EnemyTank enemyTank = enemyTanks.get(j);
                    hitTank(shot, enemyTank);
                }
            }
        }
    }

    //编写方法，判断我方的子弹是否击中敌人坦克
    //
    public  void hitTank(Shot s, Tank enemyTank) {
        //判断子弹s 击中坦克
        switch (enemyTank.getDirect()) {
            case 0://坦克向上
            case 2://坦克向下
                if(s.x > enemyTank.getX() && s.x < enemyTank.getX()+40
                    && s.y > enemyTank.getY() && s.y < enemyTank.getY()+60) {
                    s.islive = false;
                    enemyTank.isLive = false;
                    enemyTanks.remove(enemyTank);
                    //当我的子弹击中敌人坦克时， 就对数据allEnemyTankNum++
                    //因为 enemyTank 可以是 Hero 也可以是 EnemyTank
                    if(enemyTank instanceof EnemyTank) {
                        Recorder.addAllEnemyTankNUm();
                    }
                    //创建Bomb对象，加入到bombs集合
                    Bomb bomb = new Bomb(enemyTank.getX(), enemyTank.getY());
                    bombs.add(bomb);
                }
                break;
            case 1://坦克向右
            case 3://坦克向左
                if(s.x > enemyTank.getX() && s.x < enemyTank.getX()+60
                        && s.y > enemyTank.getY() && s.y < enemyTank.getY()+40) {
                    s.islive = false;
                    tank.shots.remove(s);
                    enemyTank.isLive = false;
                    enemyTanks.remove(enemyTank);
                    //当我的子弹击中敌人坦克时， 就对数据allEnemyTankNum++
                    //因为 enemyTank 可以是 Hero 也可以是 EnemyTank
                    if(enemyTank instanceof EnemyTank) {
                        Recorder.addAllEnemyTankNUm();
                    }
                    //创建Bomb对象，加入到bombs集合
                    Bomb bomb = new Bomb(enemyTank.getX(), enemyTank.getY());
                    bombs.add(bomb);
                }
                break;
        }
    }

    public  void hitHero() {
        //遍历所有敌人坦克
        for (int i = 0; i < enemyTanks.size(); i++) {
            //取出敌人坦克
            EnemyTank enemyTank = enemyTanks.get(i);
            //遍历enemyTank 对象的所有子弹
            for(int j = 0; j < enemyTank.shots.size(); j++) {
                //取出子弹
                Shot shot = enemyTank.shots.get(j);
                //判断是否击中我的坦克
                if (tank.isLive && shot.islive) {
                    hitTank(shot , tank);
                }

            }

        }

    }
    @Override
    public void keyReleased(KeyEvent e) {

    }


    @Override
    public void run() { //重绘区域

        while (true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
           //判断是否击中了敌人坦克，单颗子弹
//            if(tank.shot != null && tank.shot.islive) {//当我的子弹还存活
//                //遍历敌人所有坦克
//                for(int i=0; i < enemyTanks.size(); i++) {
//                    EnemyTank enemyTank = enemyTanks.get(i);
//                    hitTank(tank.shot, enemyTank);
//                }
//            }
            hitHero();
            hitEnemyTank();
            this.repaint();
        }
    }
}
