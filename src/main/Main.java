package main;

import util.RecordsReader;
import util.RecordsWriter;
import util.TeamCounter;

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
    public static long start;
    public static long end;

    public static void main(String args[]) throws InterruptedException
    {
        start = System.nanoTime();
        for (File test:RecordsReader.getTests())
        {
            System.out.printf("Running test %s", test.getName());
            try {
                playersScores = RecordsReader.getAllPlayersScoresFromFile("data/" + test.getName() + "/players.txt");
                teams = RecordsReader.getAllTeamsFromFile("data/" + test.getName() + "/teams.txt");
                teamsScores = new Integer[teams.size()][2];
                for (int i = 0;i < teams.size();i++)
                {
                    teamsScores[i][0] = teams.get(i).get(0);
                    teamsScores[i][1] = TeamCounter.getTeamScore(teams.get(i));
                }
                Arrays.parallelSort(teamsScores, Comparator.comparingInt(a -> a[1])); //Arrays.parallelSort(teamsScores, (a, b) -> Integer.compare(a[0], b[0]));
                RecordsWriter.writePairsToFile(teamsScores, test.getName());
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        end = System.nanoTime();
        System.out.printf("Time elapsed %f seconds", (end - start) * 0.000000001);
    }
}
