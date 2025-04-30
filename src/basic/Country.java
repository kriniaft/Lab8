package basic;

import java.time.ZoneId;

public enum Country {
    USA("America/New_York"),
    GERMANY("Europe/Berlin"),
    SPAIN("Europe/Madrid"),
    CHINA("Asia/Shanghai");

    private final String zone;

    Country(String zone) {
        this.zone = zone;
    }

    public ZoneId getZoneId() {
        return ZoneId.of(zone);
    }
}
