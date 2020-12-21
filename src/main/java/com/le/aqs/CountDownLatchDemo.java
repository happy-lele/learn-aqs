package com.le.aqs;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @Author happy_le
 * @date 2020/12/14 下午11:05
 */
public class CountDownLatchDemo {

    private static final int NUMS = 5;
    // 5个子线程都执行完毕后，再执行主线程
    public volatile static CountDownLatch countDownLatch = new CountDownLatch(NUMS);

    public static void main(String[] args) throws Throwable {
        ExecutorService es = Executors.newCachedThreadPool();
        int i = 0;
        // 一共生产15个线程。
        while (i < 15) {
            es.submit(new CountDownLatchRunning(countDownLatch, i));
            i++;
        }

        // 主线程等待5个子线程执行完毕后，继续往下执行
        countDownLatch.await();
        System.out.println("5个子任务全部执行完毕！");

        es.shutdown();
    }
}

class CountDownLatchRunning implements Runnable {

    private int i;
    private CountDownLatch countDownLatch;

    public CountDownLatchRunning(CountDownLatch countDownLatch, int i) {
        this.i = i;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        try {
            Random random = new Random();
            Integer sleepTime = random.nextInt(1000);
            TimeUnit.MILLISECONDS.sleep(sleepTime);
            /** 子任务发生异常，也被算作子任务执行完毕。不会影响其他线程和CountDownLatch */
//            if (i == 3) {
//                throw new RuntimeException();
//            }
            System.out.println(Thread.currentThread().getName() + ": 子任务执行完毕！sleepTime=" + sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            countDownLatch.countDown();
        }
    }
}
