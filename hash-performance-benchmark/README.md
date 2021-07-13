# Hash Performance Benchmark

Tested Hash Algorithm:
- xxHash32
- xxHash64
- CRC32
- Murmur32
- Murmur128
- sha1
- sha256
- md5

Hardware Specification: Intel(R) Core(TM) i7-6600U CPU@2.60GHz; RAM 16GB  
Configuration: JMH Default

Results:

**Data size: 20kb**
```
Benchmark                             Mode  Cnt         Score        Error  Units
HashingBenchmark.XXHash32Benchmark   thrpt   25  25669255.726 ± 441703.275  ops/s
HashingBenchmark.XXHash64Benchmark   thrpt   25  26385140.303 ± 246556.445  ops/s
HashingBenchmark.crc32Benchmark      thrpt   25  21628192.662 ± 128740.455  ops/s
HashingBenchmark.md5Benchmark        thrpt   25   2578677.636 ±  17467.553  ops/s
HashingBenchmark.murmur128Benchmark  thrpt   25  15235167.638 ± 156648.460  ops/s
HashingBenchmark.murmur32Benchmark   thrpt   25  17477179.601 ± 272242.739  ops/s
HashingBenchmark.sha1Benchmark       thrpt   25   1561732.135 ± 332409.077  ops/s
HashingBenchmark.sha256Benchmark     thrpt   25   1192092.115 ±  15862.466  ops/s
```

**Data size: 125kb**
```
Benchmark                             Mode  Cnt         Score        Error  Units
HashingBenchmark.XXHash32Benchmark   thrpt   25  26188689.434 ± 400322.879  ops/s
HashingBenchmark.XXHash64Benchmark   thrpt   25  25776040.919 ± 935265.200  ops/s
HashingBenchmark.crc32Benchmark      thrpt   25  19592191.972 ± 816419.194  ops/s
HashingBenchmark.md5Benchmark        thrpt   25   2556598.076 ±  39224.550  ops/s
HashingBenchmark.murmur128Benchmark  thrpt   25  15031546.470 ±  75830.640  ops/s
HashingBenchmark.murmur32Benchmark   thrpt   25  17026938.160 ± 137191.611  ops/s
HashingBenchmark.sha1Benchmark       thrpt   25   1823311.688 ±  51451.375  ops/s
HashingBenchmark.sha256Benchmark     thrpt   25   1209641.591 ±   4603.195  ops/s
```

**Data size: 1mb**
```
Benchmark                             Mode  Cnt         Score        Error  Units
HashingBenchmark.XXHash32Benchmark   thrpt   25  26484011.704 ± 391237.520  ops/s
HashingBenchmark.XXHash64Benchmark   thrpt   25  26549106.732 ± 702994.560  ops/s
HashingBenchmark.crc32Benchmark      thrpt   25  20959998.483 ± 819936.951  ops/s
HashingBenchmark.md5Benchmark        thrpt   25   2555110.792 ±  18418.259  ops/s
HashingBenchmark.murmur128Benchmark  thrpt   25  15238307.516 ± 195522.970  ops/s
HashingBenchmark.murmur32Benchmark   thrpt   25  17603380.224 ± 267460.321  ops/s
HashingBenchmark.sha1Benchmark       thrpt   25   1830422.750 ±  16584.820  ops/s
HashingBenchmark.sha256Benchmark     thrpt   25   1159979.675 ±  55738.817  ops/s
```
