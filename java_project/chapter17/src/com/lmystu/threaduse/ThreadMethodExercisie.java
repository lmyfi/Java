package com.lmystu.threaduse;

public class ThreadMethodExercisie {
    public static void main(String[] args) throws InterruptedException {
        T t = new T();
        Thread thread2 = new Thread(t);


        for(int i=1;i<=10;i++){
            System.out.println("hi "+ i);
            if(i==5){
                  thread2.start();
                thread2.join();
            }
        }

    }
}

class T implements Runnable {

    int count = 0;

        @Override
        public void run () {
            while(true){
            System.out.println("hello " + (++count));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(count == 10){
                System.out.println("子线程结束。。。");
                break;
            }
        }
    }
}
