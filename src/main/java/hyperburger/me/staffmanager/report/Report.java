package hyperburger.me.staffmanager.report;

import lombok.Getter;
import org.bukkit.entity.Player;
import java.util.UUID;

public class Report {

    @Getter
    private final UUID id;
    @Getter
    private final Player reporter;
    @Getter
    private final Player reported;
    @Getter
    private final String reason;
    @Getter
    private final long timestamp;

    public Report(Player reporter, Player reported, String reason, long timestamp){
        this.id = UUID.randomUUID();
        this.reporter = reporter;
        this.reported = reported;
        this.reason = reason;
        this.timestamp = System.currentTimeMillis();

    }
}
