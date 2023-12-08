package hyperburger.me.staffmanager.report.api.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeleteReportRequest {

	public static final AddReportRequest EMPTY = new AddReportRequest();

	private String key;
	private UUID id;

	@Override
	public String toString() {
		return "{ \"key\": %s, \"id\": %s }"
				.formatted(
						this.getKey(),
						this.getId()
				);
	}
}
