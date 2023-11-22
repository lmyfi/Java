package com.lmystu.homework;

public class Homework02 {
    public static void main(String[] args) {

        T t = new T();
        Thread thread1 = new Thread(t);
        thread1.setName("t1");
        Thread thread2 = new Thread(t);
        thread2.setName("t2");
        thread2.start();
        thread1.start();

    }
}

//编程作业2
//使用implements是因为。需要用到线程共享机制
//
class T implements Runnable {

    private  int money = 8000;
    @Override
    public void run() {
        while(true) {

            //1.使用synchronized,实现线程同步，针对处理超买超卖问题
            //2.当多个线程执行到这里时，就回去争夺this对象锁
            //3.哪个线程夺到（获取）this对象锁，就执行synchronized 代码块，执行完后，会释放this对象锁
            //4.争夺不到this对象锁，就blocked，准备继续争夺
            //5.this对象锁是非公平锁（可能会被一个线程，重复抢夺成功）

            synchronized (this) {
                if(money < 1000) {
                    System.out.println("余额不足");
                    break;
                }

                money -=1000;
                System.out.println(Thread.currentThread().getName() + "取出1000，当前余额" + money);
            }
            
        }
    }
}
