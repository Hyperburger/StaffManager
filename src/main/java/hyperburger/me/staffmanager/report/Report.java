package hyperburger.me.staffmanager.report;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class Report {

    private UUID id;
    private Player reporter;
    private Player reported;

    private String reason;
    private Long timestamp;

    public Report(Player reporter, Player reported, String reason) {
        this(UUID.randomUUID(), reporter, reported, reason, System.currentTimeMillis());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;

        return obj instanceof Report report && report.getId().equals(this.getId());
    }
}
