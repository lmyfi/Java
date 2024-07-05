package tankgame2;

import javax.swing.*;

public class LmyTankGame02 extends JFrame {
    //定义MyPanel
    MyPanel mp = null;
    public static void main(String[] args) {

        LmyTankGame02 lmytankGame01 = new LmyTankGame02();
    }
    public LmyTankGame02() {
        mp = new MyPanel();
        this.add(mp);//游戏的绘图区
        this.setSize(1000,750);;
        this.addKeyListener(mp);//让JFrame监听mp
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//点击窗口x号，完全退出
        this.setVisible(true);//显示图形
    }
}
