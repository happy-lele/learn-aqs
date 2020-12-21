package com.le.aqs;

import java.util.concurrent.Semaphore;

/**
 * 测试Semaphore
 *
 * @author muse
 */
public class SemaphoreDemo implements Runnable {

    // 允许5个线程同时访问某个资源
    Semaphore semaphore = new Semaphore(5);

    public static void main(String[] args) {
        SemaphoreDemo semaphoreDemo = new SemaphoreDemo();
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(semaphoreDemo);
            thread.start();
        }
    }

    @Override
    public void run() {
        try {
            semaphore.acquire();
            Thread.sleep(1000);
            System.out.println(System.currentTimeMillis()/1000 + ", " + Thread.currentThread().getName() + ", 执行完毕！");
            semaphore.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
