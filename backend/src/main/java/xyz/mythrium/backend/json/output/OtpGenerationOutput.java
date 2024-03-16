package xyz.mythrium.backend.json.output;

public class OtpGenerationOutput extends ApiOutput {
    private final String key;
    private final String qrKey;

    public OtpGenerationOutput(boolean success, String information, String key, String qrKey) {
        super(success, information);
        this.key = key;
        this.qrKey = qrKey;
    }

    public String getKey() {
        return key;
    }

    public String getQrKey() {
        return qrKey;
    }
}
