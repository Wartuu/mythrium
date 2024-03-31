package xyz.mythrium.backend.json.output;

public class ProxyInfoOutput extends ApiOutput {
    public final double GigabytesTransferred;
    public final double MegabytesPerSecond;
    public final boolean isRunning;
    public final long connectionsMade;
    public final double transferLeft;

    public ProxyInfoOutput(boolean success, String information, double gigabytesTransferred, double megabytesPerSecond, boolean isRunning, long connectionsMade, double transferLeft) {
        super(success, information);
        GigabytesTransferred = gigabytesTransferred;
        MegabytesPerSecond = megabytesPerSecond;
        this.isRunning = isRunning;
        this.connectionsMade = connectionsMade;
        this.transferLeft = transferLeft;
    }
}
