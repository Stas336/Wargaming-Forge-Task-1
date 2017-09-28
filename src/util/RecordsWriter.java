package util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class RecordsWriter
{
    public static void writePairsToFile(Integer[][] teamsScores, String testName) throws IOException
    {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("output/" + testName + "_pairs.txt"));
        int match = 0;
        for (int i = 0;i < teamsScores.length - 1;i+=2)
        {
            if (i > 0)
            {
                bufferedWriter.newLine();
            }
            match++;
            System.out.printf("Match %d: Team %d, Score %d vs Team %d, Score %d\n", match, teamsScores[i][0], teamsScores[i][1],
                    teamsScores[i+1][0], teamsScores[i+1][1]);
            bufferedWriter.write(teamsScores[i][0] + " " + teamsScores[i+1][0]);
        }
        System.out.printf("Total amount of teams %d, Total amount of matches %d\n", teamsScores.length, match);
        if (teamsScores.length % 2 != 0)
        {
            System.out.printf("Team %d with Score %d didn't find partner\n", teamsScores[teamsScores.length - 1][0],
                    teamsScores[teamsScores.length - 1][1]);
            bufferedWriter.newLine();
            bufferedWriter.write(teamsScores[teamsScores.length - 1][0].toString());
        }
        bufferedWriter.close();
    }
}
