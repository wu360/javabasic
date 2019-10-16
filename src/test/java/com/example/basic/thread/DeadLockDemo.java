package com.example.basic.thread;
/*
运行后aThread和bThread陷入了相互等待。怎么解决呢？
首先，座该尽量避免在持有一个锁的同时去中请另一个锁，如果确实需要多个锁，
所有代码都砹该按照相同的顺序去申请锁。比如，对于上面的例子，可以约定都先申请lockA，再申请lockB，而不是像代码中那样分开申请。

不过，在复杂的项目代码中，这种约定可能难以做到。还有一种方法是显示锁接口Lock，
它支持尝试获取锁（tryLock)和带时间限制的获取锁方法，使用这些方法可以在获取不到锁的时候释放已经持有的锁，
然后再次尝试获取锁或干脆放弃，以避免死锁。
如采还是出现了死锁，Java不会主动处理，不过借助一些工貝，我们可以发现运行中的死锁，比如，Java自带的jsiadc命令会报告发现的死锁。
 */
public class DeadLockDemo {
    private static Object lockA = new Object();
    private static Object lockB = new Object();

    private static void startThreadA() {
        Thread aThread = new Thread() {
            @Override
            public void run() {
                synchronized (lockA) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                    }
                    synchronized (lockB) {
                    }
                }
            }
        };
        aThread.start();
    }

    private static void startThreadB() {
        Thread bThread = new Thread(() -> {
            synchronized (lockB) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
                synchronized (lockA) {
                }
            }
        });
        bThread.start();
    }

    public static void main(String[] args) {
        startThreadA();
        startThreadB();
    }
}
