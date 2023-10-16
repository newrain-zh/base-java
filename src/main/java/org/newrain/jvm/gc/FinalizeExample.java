package org.newrain.jvm.gc;

/**
 * @author newRain
 * @description finalize方法执行示例
 */
public class FinalizeExample {
    public static FinalizeExample SAVE_HOOK = null;

    public void isAlive() {
        System.out.println("year i am still alive: ");
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("finalize method excuted");
        FinalizeExample.SAVE_HOOK = this;
    }

    public static void main(String[] args) throws InterruptedException {
        SAVE_HOOK = new FinalizeExample();
        System.gc();
        Thread.sleep(4000);
        if (SAVE_HOOK != null) {
            SAVE_HOOK.isAlive();
        } else {
            System.out.println("no ,I am dead");
        }
        SAVE_HOOK = null;
    }
}
