package hyperburger.me.staffmanager.report.api;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(setterPrefix = "set")
public class ReportResponse {

	private final String message;
}