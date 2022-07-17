package a1;

import java.io.IOException;
import java.nio.file.*;
import java.util.Scanner;
import java.util.concurrent.*;
import java.util.stream.Stream;

class CountTask implements Runnable {

    private Path path;
    private String key;

    public CountTask (Path path, String key) {
        this.path = path;
        this.key = key;
    }

    public void run() {
        try {
            long value = KeywordScanner.countWord(path, key);
            WordContainCountByThreadPool.addCounter(value);
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

}

class KeywordScanner {
    public static long countWord (Path path, String key) throws IOException {
        long keywordSum = 0;
        try (Scanner fileScanner = new Scanner(path);) {
            keywordSum = fileScanner.tokens().filter(a->a.contains(key)).count();
        }
        return keywordSum;

    }
}


public class WordContainCountByThreadPool {
    // Big counter and its methods
    public static long counter = 0;

    public static synchronized void addCounter(long addValue) {
        counter = counter + addValue;
    }

    public static synchronized long getCounter() {
        return counter;
    }

    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Usage: java a1.WordContainCountByThreadPool [src] [number of threads] [keyword]");
            return;
        }

        Path dir = Paths.get(args[0]);
        int threadNum = Integer.parseInt(args[1]);
        String keyInput = args[2];
        long totalTime = 0;
        
        ExecutorService executor = Executors.newFixedThreadPool(threadNum);

        long startTime = System.currentTimeMillis();

        try{
            //start the time counter

            
            // Stream of Path with .java extension files
            Stream <Path> pathQuery = Files.walk(dir).filter(Files::isRegularFile)
                                                     .filter(p->p.toString().endsWith(".java"));
                                                     
            //search work for each target files by thread pool
            pathQuery.forEach(p -> {
                                    Runnable r = new CountTask(p, keyInput);
                                    executor.execute(r);
                                    });


        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
        executor.shutdown();


        try {
            // 
            executor.awaitTermination(300, TimeUnit.SECONDS);
        }
        catch (InterruptedException ie) {
            ie.printStackTrace();
        }
        //after termination of blocks after shutdown, stop the timer.
        //the time will be shorter if I put this two lines of code after the forEach statement.
        //But I think we should stop the counter after completely termination of blocks.
        long endTime = System.currentTimeMillis();
        totalTime = endTime - startTime;

        System.out.println("Total number of words: " + getCounter());
        System.out.println("Execution time: " + totalTime + " ms");

    }
}