package hyperburger.me.staffmanager.report.api.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddReportRequest {

	public static final AddReportRequest EMPTY = new AddReportRequest();

	private String key;

	private String reporter;
	private String reported;

	private String reason;

	@Override
	public String toString() {
		return "{ \"key\": %s, \"reporter\": %s, \"reported\": %s, \"reason\": %s }"
				.formatted(
						this.getKey(),
						this.getReporter(),
						this.getReported(),
						this.getReason()
				);
	}
}
