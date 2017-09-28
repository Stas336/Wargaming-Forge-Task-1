package util;

import main.Main;

import java.util.List;

public class TeamCounter
{
    public static int getTeamScore(List<Integer> team)
    {
        int finalScore = 0;
        for (int i = 1;i < team.size();i++) // ignore team number
        {
            finalScore += Main.playersScores.get(team.get(i));
        }
        return finalScore;
    }
}
