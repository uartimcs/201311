package a1;

import java.io.IOException;
import java.nio.file.*;
import java.util.Scanner;
import java.util.stream.*;

class ParallelKeyWordScanner {
    public static long ParallelCountWord (Path path, String key) throws IOException {
        long keywordSumParallel = 0;
        try (Scanner fileScanner = new Scanner(path);) {
            keywordSumParallel = fileScanner.tokens().filter(a->a.contains(key)).count();
        }
        return keywordSumParallel;

    }
}

public class WordContainCountByParallelStream {
    public static void main(String[] args) {
        
        if (args.length != 2) {
            System.out.println("Usage: java a1.WordContainCountByParallelStream [src] [keyword]");
            return;    
        }
        //Assignments of attributes
        Path dirParallel = Paths.get(args[0]);
        String keyParallel = args[1];
        long totalTime = 0;
        long total = 0;

        try {
            //start the time counter            
            long startTime = System.currentTimeMillis();

            //Stream of path with .java extension files
            Stream <Path> pathQuery = Files.walk(dirParallel).filter(Files::isRegularFile)
                                        .filter(p->p.toString().endsWith(".java")).parallel();

            //I also find this version valid, but execution time a bit slower.
            // Stream <Path> pathQuery = Files.walk(dirParallel).filter(Files::isRegularFile)
            //                                         .filter(p->p.toString().endsWith(".java"))
            //                                         .collect(Collectors.toList()).parallelStream();
                                   

            //search work for each target files by parallel stream
            total = pathQuery.mapToLong(
                                            p -> {
                                                    try {
                                                        return ParallelKeyWordScanner.ParallelCountWord(p, keyParallel);
                                                    }
                                                    catch (IOException ioe) {
                                                        ioe.printStackTrace();
                                                        return -1;
                                                    }
                                                 }
                                             ).sum();

            long endTime = System.currentTimeMillis();
            totalTime = endTime - startTime;
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
        System.out.println("Total number of words: " + total); 
        System.out.println("Execution time: " + totalTime + " ms");   

    }
}
