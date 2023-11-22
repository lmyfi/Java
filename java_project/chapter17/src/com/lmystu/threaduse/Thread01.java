package com.lmystu.threaduse;

//线程
public class Thread01 {
    public static void main(String[] args) {

        //创建cat对象，可以当作线程使用
        Cat cat = new Cat();
        cat.start();//启动线程
    }
}

//1.当一个类继承了Thread类，该类就可以当做一个线程使用
//2。我们会重写run方法，写上自己的业务代码
//3.run Thread 类实现 Runable 接口的run方法
/*  源码
     @Override
    public void run() {
        if (target != null) {
            target.run();
        }
    }
 */
class Cat extends  Thread {
    @Override
    public void run() {//重写run方法，写上自己的业务逻辑
        int times = 0;
        while(true) {
            //该线程每隔1秒，在控制台输出“喵喵，我是一只猫”
            System.out.println("喵喵，我是一只猫" +
                    ""+ (++times));

            try {
                Thread.sleep(1000);//让线程休眠1秒  ctrl+alt+t 提示快捷键
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(times == 8){
                break;
            }
        }
    }
}
