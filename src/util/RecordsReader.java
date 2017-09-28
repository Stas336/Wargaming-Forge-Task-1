package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class RecordsReader
{
    public static List<Integer> getAllPlayersScoresFromFile(String filePath) throws IOException
    {
        List<Integer> players = new ArrayList<>();
        String[] temp;
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
        String line;
        while((line = bufferedReader.readLine()) != null)
        {
            temp = line.split(" ");
            players.add(Integer.parseInt(temp[0]), Integer.parseInt(temp[1]));
            System.out.printf("Player %s, Score %s\n", temp[0], temp[1]);
        }
        bufferedReader.close();
        return players;
    }
    public static List<List<Integer>> getAllTeamsFromFile(String filePath) throws IOException {
        List<List<Integer>> teams = new ArrayList<>();
        String[] temp;
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
        String line;
        while((line = bufferedReader.readLine()) != null)
        {
            temp = line.substring(0, line.length()).split(" ");
            List<Integer> temp1 = new ArrayList<>();
            for (String value:temp)
            {
                temp1.add(Integer.parseInt(value));
            }
            teams.add(temp1);
            System.out.printf("Team %s, Players %s\n", temp1.get(0), temp1.subList(1,temp1.size()));
        }
        bufferedReader.close();
        return teams;
    }
    public static File[] getTests()
    {
        return new File("data").listFiles(File::isDirectory);
    }
}
