package org.newrain.base.callback;

/**
 * @author newRain
 * @description 使用回调方式
 */
public class TestCallBack {

    /**
     * 使用回调方法
     *
     * @param n
     * @param callback 回调接口
     */
    public void compute(int n, ComputeCallBack callback) {
        for (int i = 0; i < n; i++) {
            System.out.println(i);
        }
        callback.onComputeEnd();
    }

    public static void main(String[] args) {
        new TestCallBack().compute(1000, new ComputeCallBack() {
            @Override
            public void onComputeEnd() {
                System.out.println("end back!!!");
            }
        });
    }
}
