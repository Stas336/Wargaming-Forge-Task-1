package util;

public class CPUHelper
{
    public static int getCPUCores()
    {
        return Runtime.getRuntime().availableProcessors();
    }
}