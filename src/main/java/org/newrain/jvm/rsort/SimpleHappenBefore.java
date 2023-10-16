package org.newrain.jvm.rsort;

/**
 * Java重排序验证示例代码
 * A线程去修改变量
 * B线程去读取变量
 *
 * @author newrain
 * @description Java重排序验证示例代码
 */
public class SimpleHappenBefore {

    public static void main(String[] args) throws InterruptedException {

        for (int i = 0; i < 5000000; i++) {
            State state = new State();
            ThreadA threadA = new ThreadA(state);
            ThreadB threadB = new ThreadB(state);
            threadA.start();
            threadB.start();

            threadA.join();
            threadB.join();
        }
    }

    static class ThreadA extends Thread {

        private final SimpleHappenBefore.State state;

        ThreadA(SimpleHappenBefore.State state) {
            this.state = state;
        }

        @Override
        public void run() {
            state.a = 1;
            state.b = 1;
            state.c = 1;
            state.d = 1;
        }
    }

    static class ThreadB extends Thread {

        private final SimpleHappenBefore.State state;

        ThreadB(SimpleHappenBefore.State state) {
            this.state = state;
        }

        @Override
        public void run() {
            if (state.b == 1 && state.a == 0) {
                System.out.println("b=1");
            }

            if (state.c == 1 && (state.b == 0 || state.a == 0)) {
                System.out.println("c=1");
            }

            if (state.d == 1 && (state.a == 0 || state.b == 0 || state.c == 0)) {
                System.out.println("d==1");
            }
        }
    }

    static class State {
        public int a = 0;
        public int b = 0;
        public int c = 0;
        public int d = 0;
    }
}
