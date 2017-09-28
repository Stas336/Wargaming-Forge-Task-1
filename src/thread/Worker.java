package thread;

import util.TeamCounter;

import static main.Main.teams;
import static main.Main.teamsScores;

public class Worker extends Thread
{
    private int start;
    private int end;

    public Worker(int start, int end)
    {
        this.start = start;
        this.end = end;
    }

    public void run()
    {
        System.out.printf("Starting %s\n", this.getName());
        for (int i = start;i < end + 1;i++)
        {
            teamsScores[i][0] = teams.get(i).get(0);
            teamsScores[i][1] = TeamCounter.getTeamScore(teams.get(i));
            System.out.printf("%s: Team %d, Score %d\n", this.getName(), teamsScores[i][0], teamsScores[i][1]);
        }
        System.out.printf("Ending %s\n", this.getName());
    }
}
