package hyperburger.me.staffmanager.report.api;

import hyperburger.me.staffmanager.StaffManager;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ReportHelper {

	private static final String API_KEY = StaffManager.getInstance()
			.getConfig()
			.getString("api_settings.key");

	public boolean validateKey(String key) {
		if (API_KEY == null) return false;

		return API_KEY.equals(key);
	}
}
