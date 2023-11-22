package com.hspedu.tankgame5;


import java.io.*;
import java.util.Vector;
import com.hspedu.tankgame5.Node;

public class Recorder {

    //定义变量， 记录我方击毁敌人坦克数
    private static int allEnemyTankNum = 0;
    //定义IO对象， 准备写数据到文件中
    private static BufferedWriter bw = null;
    private static BufferedReader br = null;
    private static String recordFile = "e:\\myRecord.txt";
    //定义Vector ,指向MyPanel 对象的 敌人坦克Vector
    private static  Vector<EnemyTank> enemyTanks = null;
    //定义一个Node 的Vector , 用于保存敌人的信息node
    private static Vector<Node> nodes = new Vector<>();
    public static void setEnemyTanks(Vector<EnemyTank> enemyTanks) {
        Recorder.enemyTanks = enemyTanks;
    }

    public static int getAllEnemyTankNum() {
        return allEnemyTankNum;
    }

    public static void setAllEnemyTankNum(int allEnemyTankNum) {
        Recorder.allEnemyTankNum = allEnemyTankNum;
    }

    //增加一个方法， 用于读取recordFile, 恢复相关信息
    public static Vector<Node> getNodesAndEnemyTankRec() {
        try {
            br = new BufferedReader(new FileReader(recordFile));
            allEnemyTankNum = Integer.parseInt(br.readLine());
            //循环读取文件， 生成nodes 集合
            String line = ""; // 255 40 0
            while ((line = br.readLine()) != null) {
                String[] xyd = line.split(" ");
                Node node = new Node(Integer.parseInt(xyd[0]), Integer.parseInt(xyd[1]), Integer.parseInt(xyd[2]));
                nodes.add(node);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br!=null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return nodes;
    }
    //增加一个方法， 当游戏退出时， 我们将allEnemytankNum 保存到 recorFile
    public static void keepRecord() {
        try {
            bw = new BufferedWriter(new FileWriter(recordFile));
            bw.write(allEnemyTankNum + "\r\n");
            //遍历敌人坦克的Vector ， 然后根据情况保存即可。
            //oop思想, 定义一个属性， 然后通过setXxx 的到 敌人坦克的Vector
            for (int i = 0; i < enemyTanks.size(); i++) {
                //取出敌人坦克
                EnemyTank enemyTank = enemyTanks.get(i);
                if (enemyTank.isLive) { //判断敌人坦克是否存活
                    //保存该enemyTank信息
                    String record = enemyTank.getX() + " " + enemyTank.getY() + " " + enemyTank.getDirect();
                    //写入到文件
                    bw.write(record + "\r\n");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bw != null) {
                try {
                    bw.close(); //关闭流
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }



    //当我方坦克击毁一个敌方坦克， 就应当 allEnemyTankNum++\
    public  static void addAllEnemyTankNUm() {
        Recorder.allEnemyTankNum++;
    }
}
