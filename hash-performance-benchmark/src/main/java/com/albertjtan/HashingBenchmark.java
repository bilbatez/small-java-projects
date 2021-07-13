package com.albertjtan;

import com.albertjtan.data.SampleString;
import net.jpountz.xxhash.XXHash32;
import net.jpountz.xxhash.XXHash64;
import net.jpountz.xxhash.XXHashFactory;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.digest.MurmurHash3;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.zip.CRC32;

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
public class HashingBenchmark {

  @State(Scope.Benchmark)
  public static class HashAlgo {
    public static final MessageDigest md5 = DigestUtils.getMd5Digest();
    public static final MessageDigest sha1 = DigestUtils.getSha1Digest();
    public static final MessageDigest sha256 = DigestUtils.getSha256Digest();
    public static final CRC32 crc32 = new CRC32();
    public static final XXHash32 xxHash32 = XXHashFactory.fastestInstance().hash32();
    public static final XXHash64 xxHash64 = XXHashFactory.fastestInstance().hash64();
  }

  @State(Scope.Benchmark)
  public static class SampleData {
    public static byte[] sampleByte = SampleString.LOREM_20kb;
    public static ByteBuffer sampleByteBuffer = ByteBuffer.wrap(sampleByte);
    public static final int seed32 = Long.valueOf(new Date().getTime()).intValue();
    public static final long seed = new Date().getTime();
  }

  @Benchmark
  public void md5Benchmark(Blackhole blackhole) {
    blackhole.consume(HashAlgo.md5.digest(SampleData.sampleByte));
  }

  @Benchmark
  public void sha1Benchmark(Blackhole blackhole) {
    blackhole.consume(HashAlgo.sha1.digest(SampleData.sampleByte));
  }

  @Benchmark
  public void sha256Benchmark(Blackhole blackhole) {
    blackhole.consume(HashAlgo.sha256.digest(SampleData.sampleByte));
  }

  @Benchmark
  public void crc32Benchmark(Blackhole blackhole) {
    HashAlgo.crc32.reset();
    HashAlgo.crc32.update(SampleData.sampleByte);
    blackhole.consume(HashAlgo.crc32.getValue());
  }

  @Benchmark
  public void XXHash32Benchmark(Blackhole blackhole) {
    blackhole.consume(HashAlgo.xxHash32.hash(SampleData.sampleByteBuffer, SampleData.seed32));
  }

  @Benchmark
  public void XXHash64Benchmark(Blackhole blackhole) {
    blackhole.consume(HashAlgo.xxHash64.hash(SampleData.sampleByteBuffer, SampleData.seed));
  }

  @Benchmark
  public void murmur32Benchmark(Blackhole blackhole) {
    blackhole.consume(MurmurHash3.hash32x86(SampleData.sampleByte));
  }

  @Benchmark
  public void murmur128Benchmark(Blackhole blackhole) {
    blackhole.consume(MurmurHash3.hash128(SampleData.sampleByte));
  }

  public static void main(String[] args) throws RunnerException {
    Options opts = new OptionsBuilder()
        .include(HashingBenchmark.class.getSimpleName())
        .build();
    new Runner(opts).run();
  }

}
