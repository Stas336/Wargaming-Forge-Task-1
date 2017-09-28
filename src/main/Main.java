package main;

import util.RecordsReader;
import util.RecordsWriter;
import util.TeamCounter;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Main
{
    private static List<Integer> playersScores;
    private static List<List<Integer>> teams;
    private static Integer[][] teamsScores;

    public static void main(String args[]) throws InterruptedException
    {
        long start = System.nanoTime();
        for (File test:RecordsReader.getTests())
        {
            System.out.printf("Running test %s\n", test.getName());
            try
            {
                playersScores = RecordsReader.getAllPlayersScoresFromFile("data/" + test.getName() + "/players.txt");
                teams = RecordsReader.getAllTeamsFromFile("data/" + test.getName() + "/teams.txt");
                teamsScores = new Integer[teams.size()][2];
                for (int i = 0;i < teams.size();i++)
                {
                    teamsScores[i][0] = teams.get(i).get(0);
                    teamsScores[i][1] = TeamCounter.getTeamScore(teams.get(i));
                }
                Arrays.parallelSort(teamsScores, Comparator.comparingInt(a -> a[1]));
                RecordsWriter.writePairsToFile(teamsScores, test.getName());
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        long end = System.nanoTime();
        System.out.printf("Time elapsed %f seconds\n", (end - start) * 0.000000001);
    }

    public static List<Integer> getPlayersScoresUnmodifiable()
    {
        return Collections.unmodifiableList(playersScores);
    }
}
