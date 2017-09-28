package main;

import thread.Worker;
import util.CPUHelper;
import util.RecordsReader;
import util.RecordsWriter;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Main
{
    public static List<Integer> playersScores;
    public static List<List<Integer>> teams;
    public static Integer[][] teamsScores;
    public static int CPUCores = CPUHelper.getCPUCores();
    public static long start;
    public static long end;

    public static void main(String args[]) throws InterruptedException
    {
        start = System.nanoTime();
        for (File test:RecordsReader.getTests())
        {
            System.out.printf("Running test %s", test.getName());
            try {
                Thread t = new Thread(() ->
                {
                    try
                    {
                        playersScores = RecordsReader.getAllPlayersScoresFromFile("data/" + test.getName() + "/players.txt");
                    } catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                });
                t.start();
                teams = RecordsReader.getAllTeamsFromFile("data/" + test.getName() + "/teams.txt");
                teamsScores = new Integer[teams.size()][2];
                t.join();
                int tasksPerThread = teams.size() / CPUCores;
                Integer[][] tasksForThreads = new Integer[CPUCores][2];
                for (int i = 0;i < CPUCores;i++)
                {
                    if (i == 0)
                    {
                        tasksForThreads[i][0] = 0;
                        tasksForThreads[i][1] = tasksPerThread;
                    }
                    else
                    {
                        tasksForThreads[i][0] = tasksForThreads[i - 1][1] + 1;
                        if (i == CPUCores - 1)
                        {
                            tasksForThreads[i][1] = teams.size() - 1;
                        }
                        else
                        {
                            tasksForThreads[i][1] = tasksForThreads[i][0] + tasksPerThread;
                        }
                    }
                }
                for (int i = 0;i < CPUCores;i++)
                {
                    Worker worker = new Worker(tasksForThreads[i][0], tasksForThreads[i][1]);
                    worker.start();
                    worker.join();
                }
                Arrays.parallelSort(teamsScores, Comparator.comparingInt(a -> a[1])); //Arrays.parallelSort(teamsScores, (a, b) -> Integer.compare(a[0], b[0]));
                RecordsWriter.writePairsToFile(teamsScores, test.getName());
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        end = System.nanoTime();
        System.out.printf("Time elapsed %f seconds using %d threads", (end - start) * 0.000000001, CPUCores);
    }
}
