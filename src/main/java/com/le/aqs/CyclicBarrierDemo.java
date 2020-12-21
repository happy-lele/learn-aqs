package com.le.aqs;

import java.util.concurrent.CyclicBarrier;

/**
 * @Author happy_le
 * @date 2020/12/14 下午11:05
 */
public class CyclicBarrierDemo {

    private static final int NUMS = 5;

    public static final CyclicBarrier cyclicBarrier = new CyclicBarrier(NUMS, new Master());

    public static void main(String[] args) {
        for (int i = 0; i < NUMS; i++) {
            Thread thread = new Thread(new Student(i, cyclicBarrier));
            thread.start();
        }
    }

}

class Student implements Runnable {

    private CyclicBarrier cyclicBarrier;

    private volatile Integer studentNo = 0;

    public Student(Integer studentNo, CyclicBarrier cyclicBarrier) {
        this.studentNo = studentNo;
        this.cyclicBarrier = cyclicBarrier;
    }

    @Override
    public void run() {
        try {
            System.out.println("学生" + studentNo + ", 已经上巴士。");
            cyclicBarrier.await();

            System.out.println("学生" + studentNo + ", 巴士已经到达目的地。");
            cyclicBarrier.await();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}

/**
 * barrierAction：每当计数器一次计数完成后——CyclicBarrier.await()时，系统会执行的动作。
 */
class Master implements Runnable {

    private static int step = 1;

    @Override
    public void run() {
        if (step == 1) {
            System.out.println("同学们都已经上大巴了，咱们出发！");
        } else if (step == 2) {
            System.out.println("所有大巴都到了，同学们开始春游！");
        }
        step++;
    }
}