package xyz.mythrium.backend.json.output;

public class ProxyInfoOutput extends ApiOutput {
    public final double MegabytesTransferred;
    public final double KilobytesPerSecond;
    public final boolean isRunning;
    public final long connectionsMade;
    public final long connectionsLeft;

    public ProxyInfoOutput(boolean success, String information, double megabytesTransferred, double kilobytesPerSecond, boolean isRunning, long connectionsMade, long connectionsLeft) {
        super(success, information);
        MegabytesTransferred = megabytesTransferred;
        KilobytesPerSecond = kilobytesPerSecond;
        this.isRunning = isRunning;
        this.connectionsMade = connectionsMade;
        this.connectionsLeft = connectionsLeft;
    }
}
