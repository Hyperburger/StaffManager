package hyperburger.me.staffmanager.report.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hyperburger.me.staffmanager.StaffManager;
import hyperburger.me.staffmanager.report.ReportManager;
import hyperburger.me.staffmanager.report.api.request.AddReportRequest;
import hyperburger.me.staffmanager.report.api.request.DeleteReportRequest;
import hyperburger.me.staffmanager.report.api.request.SelectReportsRequest;
import org.bukkit.Bukkit;
import spark.Spark;

public class ReportController {

	public static void init() {
		final ReportManager reportManager = StaffManager.getInstance().getReportManager();
		final ObjectMapper objectMapper = StaffManager.getObjectMapper();

		// an example for the json needed to add reports
		// { "key": "configuration", "reporter": "Test", "reported": "Tested", "reason": "No Reason" }

		Spark.post("/api/add_report", (request, response) -> {
			try {
				var body = request.body();
				var report = objectMapper.readValue(body, AddReportRequest.class);

				if (ReportHelper.validateKey(report.getKey()))
					return ReportResponse.builder()
						.setMessage("The key you provided isn't valid.")
						.build();

				var reporter = Bukkit.getPlayer(report.getReporter());
				var reported = Bukkit.getPlayer(report.getReported());

				if (reporter == null || reported == null)
					return ReportResponse.builder()
							.setMessage("You have inserted invalid players.")
							.build();

				var uuid = reportManager.addReport(reporter, reported, report.getReason());

				return ReportResponse.builder().setMessage(uuid.toString()).build();

			} catch (JsonProcessingException exception) {
				return ReportResponse.builder()
						.setMessage(AddReportRequest.EMPTY.toString())
						.build();
			}
		});

		// an example for the json needed to delete reports
		// { "key": "configuration", "id": "Report Id" }

		Spark.post("/api/delete_report", (request, response) -> {
			try {
				var body = request.body();
				var report = objectMapper.readValue(body, DeleteReportRequest.class);

				if (ReportHelper.validateKey(report.getKey()))
					return ReportResponse.builder()
						.setMessage("The key you provided isn't valid.")
						.build();

				reportManager.deleteReport(report.getId());
				return null;

			} catch (JsonProcessingException exception) {
				return ReportResponse.builder()
						.setMessage(DeleteReportRequest.EMPTY.toString())
						.build();
			}
		});

		// an example for the json needed to select reports with a limit
		// { "key": "configuration", "limit": "number" }

		Spark.post("/api/all_reports", (request, response) -> {
			try {
				var body = request.body();
				var report = objectMapper.readValue(body, SelectReportsRequest.class);

				if (ReportHelper.validateKey(report.getKey()))
					return ReportResponse.builder()
							.setMessage("The key you provided isn't valid.")
							.build();

				return reportManager.all(report.getLimit());

			} catch (JsonProcessingException exception) {
				return ReportResponse.builder()
						.setMessage(SelectReportsRequest.EMPTY.toString())
						.build();
			}
		});
	}
}
