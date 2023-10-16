package org.newrain.base.lambda.completableFuture;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.stream.Collectors;

/** 最佳价格查询器 */
public class StreamEqualsExample {

  List<CompletableFutureExample> completableFutureExamples =
      Arrays.asList(
          new CompletableFutureExample("A"),
          new CompletableFutureExample("B"),
          new CompletableFutureExample("C"),
          new CompletableFutureExample("D"),
          new CompletableFutureExample("E"),
          new CompletableFutureExample("F"),
          new CompletableFutureExample("G"),
          new CompletableFutureExample("H"),
          new CompletableFutureExample("I"),
          new CompletableFutureExample("J"));

  /** 顺序查询 */
  public List<String> findPrices(String product) {
    return completableFutureExamples.stream()
        .map(completableFutureExample -> String.format("%s price is %.2f", completableFutureExample.getName(), completableFutureExample.getPrice(product)))
        .collect(Collectors.toList());
  }

  /** 并行流查询 */
  public List<String> findPricesParallel(String product) {
    return completableFutureExamples
        .parallelStream()
        .map(completableFutureExample -> String.format("%s price is %.2f", completableFutureExample.getName(), completableFutureExample.getPrice(product)))
        .collect(Collectors.toList());
  }

  /** 异步查询 相比并行流的话CompletableFuture更有优势：可以对执行器配置，设置线程池大小 */
  @SuppressWarnings("all")
  private final Executor myExecutor =
      Executors.newFixedThreadPool(
          Math.min(completableFutureExamples.size(), 800),
          new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
              Thread t = new Thread(r);
              // 使用守护线程保证不会阻止程序的关停
              t.setDaemon(true);
              return t;
            }
          });

  @SuppressWarnings("all")
  public List<String> findPricesAsync(String product) {
    List<CompletableFuture<String>> priceFuctures =
        completableFutureExamples.stream()
            .map(
                    completableFutureExample ->
                    CompletableFuture.supplyAsync(
                        () ->
                            String.format(
                                "%s price is %.2f", completableFutureExample.getName(), completableFutureExample.getPrice(product)),
                        myExecutor))
            .collect(Collectors.toList());
    /**
     * 这里需要使用新的stream来等待所有的子线程执行完，
     * 因为：如果在一个stream中使用两个map: List<CompletableFuture<String>>
     * priceFuctures = shops.parallelStream() .map(shop -> CompletableFuture.supplyAsync(() ->
     * String.format("%s price is %.2f",shop.getName(),shop.getPrice(product)))) .map(c ->
     * c.join()).collect(Collectors.toList()) .collect(Collectors.toList());
     * 考虑到流操作之间的延迟特性。如果你在单一的流水线中处理流，发向不同商家的请求只能以同步顺序的方式执行才会成功。因此每个创建CompletableFuture
     * 对象只能在前一个操作结束之后执行查询商家动作。
     */
    return priceFuctures.stream().map(c -> c.join()).collect(Collectors.toList());
  }
}
