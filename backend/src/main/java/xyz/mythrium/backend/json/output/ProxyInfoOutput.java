package xyz.mythrium.backend.json.output;

public class ProxyInfoOutput extends ApiOutput {
    public final double MegabytesTransferred;
    public final double KilobytesPerSecond;
    public final double MegabytesPerMinute;
    public final boolean isRunning;
    public final long connectionsMade;
    public final long connectionsLeft;

    public ProxyInfoOutput(boolean success, String information, double megabytesTransferred, double kilobytesPerSecond, double megabytesPerMinute, boolean isRunning, long connectionsMade, long connectionsLeft) {
        super(success, information);
        MegabytesTransferred = megabytesTransferred;
        KilobytesPerSecond = kilobytesPerSecond;
        MegabytesPerMinute = megabytesPerMinute;
        this.isRunning = isRunning;
        this.connectionsMade = connectionsMade;
        this.connectionsLeft = connectionsLeft;
    }
}
