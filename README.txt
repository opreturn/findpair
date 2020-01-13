java version 13

Build:
mvn package

Run jar:
java -jar find-pair-1.0-RELEASE.jar prices.txt 2500
java -jar find-pair-1.0-RELEASE.jar --bonus prices.txt 2500

Run wrapper script:
chmod +x find-pair
./find-pair prices.txt 2500
./find-pair --bonus prices.txt 2500

findPair Runtime: O(n)
findPair Space: O(n)

findTriad Runtime: O(n^2)
findTriad Space: O(n)

"There may be many rows in the file" is quite open ended.

Based on the sample data, a file with 50 Bytes per line with 100 million lines is only 5GB.
I would consider 10^8 rows as "many rows", but 5GB can easily fit into RAM on my machine.
BufferedReader should work well for something like this.

On the other end, increasing the lines by just a factor of 10, and it's possible the whole file
does not fit into RAM all at once anymore. This would require a different implementation as you
would only be able to hold a portion of the file in memory. The overall approach is the same, except
you experience more overhead due to multiple reads from disk.

If you really want to get nuts here with 100GB+ files, you can read the whole file into RAM spread across
multiple networked machines. Now it's a question of disk read vs network latency which becomes hardware dependent.

Java File Read Benchmarks:
https://funnelgarden.com/java_read_file/#Performance_Rankings
https://alvinalexander.com/scala/different-ways-read-large-text-file-with-scala-performance
https://www.quora.com/What-is-the-fastest-way-to-read-a-large-file-in-Java-3-4gb-line-by-line
