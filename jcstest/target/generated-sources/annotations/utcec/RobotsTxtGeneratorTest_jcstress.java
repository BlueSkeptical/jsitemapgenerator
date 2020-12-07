package utcec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.openjdk.jcstress.infra.runners.TestConfig;
import org.openjdk.jcstress.infra.collectors.TestResultCollector;
import org.openjdk.jcstress.infra.runners.Runner;
import org.openjdk.jcstress.infra.runners.WorkerSync;
import org.openjdk.jcstress.util.Counter;
import org.openjdk.jcstress.vm.WhiteBoxSupport;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Callable;
import java.util.Collections;
import java.util.List;
import utcec.RobotsTxtGeneratorTest;
import org.openjdk.jcstress.infra.results.ZZ_Result;

public final class RobotsTxtGeneratorTest_jcstress extends Runner<ZZ_Result> {

    volatile WorkerSync workerSync;
    RobotsTxtGeneratorTest[] gs;
    ZZ_Result[] gr;

    public RobotsTxtGeneratorTest_jcstress(TestConfig config, TestResultCollector collector, ExecutorService pool) {
        super(config, collector, pool, "utcec.RobotsTxtGeneratorTest");
    }

    @Override
    public Counter<ZZ_Result> sanityCheck() throws Throwable {
        Counter<ZZ_Result> counter = new Counter<>();
        sanityCheck_API(counter);
        sanityCheck_Footprints(counter);
        return counter;
    }

    private void sanityCheck_API(Counter<ZZ_Result> counter) throws Throwable {
        final RobotsTxtGeneratorTest s = new RobotsTxtGeneratorTest();
        final ZZ_Result r = new ZZ_Result();
        Collection<Future<?>> res = new ArrayList<>();
        res.add(pool.submit(() -> s.actor1()));
        res.add(pool.submit(() -> s.actor2()));
        for (Future<?> f : res) {
            try {
                f.get();
            } catch (ExecutionException e) {
                throw e.getCause();
            }
        }
        try {
            pool.submit(() ->s.arbiter(r)).get();
        } catch (ExecutionException e) {
            throw e.getCause();
        }
        counter.record(r);
    }

    private void sanityCheck_Footprints(Counter<ZZ_Result> counter) throws Throwable {
        config.adjustStrides(size -> {
            RobotsTxtGeneratorTest[] ls = new RobotsTxtGeneratorTest[size];
            ZZ_Result[] lr = new ZZ_Result[size];
            workerSync = new WorkerSync(false, 2, config.spinLoopStyle);
            for (int c = 0; c < size; c++) {
                RobotsTxtGeneratorTest s = new RobotsTxtGeneratorTest();
                ZZ_Result r = new ZZ_Result();
                lr[c] = r;
                ls[c] = s;
                s.actor1();
                s.actor2();
                s.arbiter(r);
                counter.record(r);
            }
        });
    }

    @Override
    public Counter<ZZ_Result> internalRun() {
        gs = new RobotsTxtGeneratorTest[config.minStride];
        gr = new ZZ_Result[config.minStride];
        for (int c = 0; c < config.minStride; c++) {
            gs[c] = new RobotsTxtGeneratorTest();
            gr[c] = new ZZ_Result();
        }
        workerSync = new WorkerSync(false, 2, config.spinLoopStyle);

        control.isStopped = false;

        List<Callable<Counter<ZZ_Result>>> tasks = new ArrayList<>();
        tasks.add(this::actor1);
        tasks.add(this::actor2);
        Collections.shuffle(tasks);

        Collection<Future<Counter<ZZ_Result>>> results = new ArrayList<>();
        for (Callable<Counter<ZZ_Result>> task : tasks) {
            results.add(pool.submit(task));
        }

        if (config.time > 0) {
            try {
                TimeUnit.MILLISECONDS.sleep(config.time);
            } catch (InterruptedException e) {
            }
        }

        control.isStopped = true;

        waitFor(results);

        Counter<ZZ_Result> counter = new Counter<>();
        for (Future<Counter<ZZ_Result>> f : results) {
            try {
                counter.merge(f.get());
            } catch (Throwable e) {
                throw new IllegalStateException(e);
            }
        }
        return counter;
    }

    private void jcstress_consume(Counter<ZZ_Result> cnt, int a) {
        RobotsTxtGeneratorTest[] ls = gs;
        ZZ_Result[] lr = gr;
        int len = ls.length;
        int left = a * len / 2;
        int right = (a + 1) * len / 2;
        for (int c = left; c < right; c++) {
            ZZ_Result r = lr[c];
            RobotsTxtGeneratorTest s = ls[c];
            s.arbiter(r);
            ls[c] = new RobotsTxtGeneratorTest();
            cnt.record(r);
            r.r1 = false;
            r.r2 = false;
        }
    }

    private void jcstress_update(WorkerSync sync) {
        RobotsTxtGeneratorTest[] ls = gs;
        ZZ_Result[] lr = gr;
        int len = ls.length;

        int newLen = sync.updateStride ? Math.max(config.minStride, Math.min(len * 2, config.maxStride)) : len;

        if (newLen > len) {
            ls = Arrays.copyOf(ls, newLen);
            lr = Arrays.copyOf(lr, newLen);
            for (int c = len; c < newLen; c++) {
                ls[c] = new RobotsTxtGeneratorTest();
                lr[c] = new ZZ_Result();
            }
            gs = ls;
            gr = lr;
         }

        workerSync = new WorkerSync(control.isStopped, 2, config.spinLoopStyle);
   }
    private void sink(int v) {};
    private void sink(short v) {};
    private void sink(byte v) {};
    private void sink(char v) {};
    private void sink(long v) {};
    private void sink(float v) {};
    private void sink(double v) {};
    private void sink(Object v) {};

    private Counter<ZZ_Result> actor1() {
        Counter<ZZ_Result> counter = new Counter<>();
        while (true) {
            WorkerSync sync = workerSync;
            if (sync.stopped) {
                return counter;
            }

            RobotsTxtGeneratorTest[] ls = gs;
            ZZ_Result[] lr = gr;
            int size = ls.length;

            sync.preRun();

            for (int c = 0; c < size; c++) {
                RobotsTxtGeneratorTest s = ls[c];
                s.actor1();
            }

            sync.postRun();

            jcstress_consume(counter, 0);
            if (sync.tryStartUpdate()) {
                jcstress_update(sync);
            }

            sync.postUpdate();
        }
    }

    private Counter<ZZ_Result> actor2() {
        Counter<ZZ_Result> counter = new Counter<>();
        while (true) {
            WorkerSync sync = workerSync;
            if (sync.stopped) {
                return counter;
            }

            RobotsTxtGeneratorTest[] ls = gs;
            ZZ_Result[] lr = gr;
            int size = ls.length;

            sync.preRun();

            for (int c = 0; c < size; c++) {
                RobotsTxtGeneratorTest s = ls[c];
                s.actor2();
            }

            sync.postRun();

            jcstress_consume(counter, 1);
            if (sync.tryStartUpdate()) {
                jcstress_update(sync);
            }

            sync.postUpdate();
        }
    }

}
