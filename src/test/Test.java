import org.newrain.base.lambda.completableFuture.StreamEqualsExample;

public class Test {

  @org.junit.Test
  public void findPrices() {
    StreamEqualsExample streamEqualsExample = new StreamEqualsExample();
    long st = System.currentTimeMillis();
    System.out.println(streamEqualsExample.findPrices("iPhonX"));
    System.out.println("done : " + (System.currentTimeMillis() - st) + "msecs");
  }

  @org.junit.Test
  public void findPricesParallel() {
    StreamEqualsExample streamEqualsExample = new StreamEqualsExample();
    long st = System.currentTimeMillis();
    System.out.println(streamEqualsExample.findPricesParallel("iPhonX"));
    System.out.println(" parallel done : " + (System.currentTimeMillis() - st) + "msecs");
  }

  @org.junit.Test
  public void findPricesAsync() {
    StreamEqualsExample streamEqualsExample = new StreamEqualsExample();
    long st = System.currentTimeMillis();
    System.out.println(streamEqualsExample.findPricesAsync("iPhonX"));
    System.out.println("async done : " + (System.currentTimeMillis() - st) + "msecs");
  }

  public static void main(String[] args) {
    //
    Test test = new Test();
    test.findPrices();
    test.findPricesAsync();
    test.findPricesParallel();
  }
}
