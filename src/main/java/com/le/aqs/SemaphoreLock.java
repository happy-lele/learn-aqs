package com.le.aqs;

import java.util.concurrent.Semaphore;

/**
 * @Author happy_le
 * @date 2020/12/14 上午11:24
 * Semaphore用来控制同时访问某个特定资源的操作数量,
 * 或者同时执行某个指定操作的数量。还可以用来实现某种资源池限制，或者对容器施加边界。
 * (假设有10个人在银行办理业务，只有2个工作窗口)。
 */
public class SemaphoreLock {

    public static void main(String[] args) {
        Output o = new Output();
        for (int i = 0; i < 5; i++) {
            new Thread(() -> o.output()).start();
        }
    }
}

class Output {
    //1、信号量为 1 时，相当于普通的锁 信号量大于 1 时，共享锁
    Semaphore semaphore = new Semaphore(1);

    public void output() {
        try {
            semaphore.acquire();
            System.out.println(Thread.currentThread().getName() + " start at " + System.currentTimeMillis());
            Thread.sleep(1000);
            System.out.println(Thread.currentThread().getName() + " stop at " + System.currentTimeMillis());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release();
        }
    }
}
