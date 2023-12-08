package hyperburger.me.staffmanager.report.api.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class SelectReportsRequest {

	public static final SelectReportsRequest EMPTY = new SelectReportsRequest();

	private String key;
	private Integer limit;

	@Override
	public String toString() {
		return "{ \"key\": %s, \"limit\": %s }"
				.formatted(
						this.getKey(),
						this.getLimit()
				);
	}
}
