package cc.sybx.measure;

import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

/**
 * @author : yuanzp
 * @Date : 2021/6/29 下午9:03
 *
 * @BenchmarkMode(Mode.AverageTime)
 *  测量用于执行基准测试目标方法所花费的平均时间
 * @OutputTimeUnit(TimeUnit.MILLISECONDS)
 *  以毫秒为单位，打印输出基准测试的结果
 * @Fork(value = 2, jvmArgs = {"-Xms4G", "-Xmx4G"})
 *  采用4Gb的堆，执行基准测试两次以获得更可靠的结果
 */
@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(value = 2, jvmArgs = {"-Xms4G", "-Xmx4G"})
public class ParallelStreamBenchmark {
    private static final long N = 10_000_000L;

    /**
     * 基准测试的目标方法
     * @return
     */
    @Benchmark
    public long sequtntialSum() {
        return Stream.iterate(1L, i -> i + 1).limit(N)
                .reduce(0L, Long::sum);
    }

    /**
     * 尽量在每次基准测试迭代结束后都进行一次垃圾回收
     */
    @TearDown(Level.Invocation)
    public void tearDown() {
        System.gc();
    }
}
