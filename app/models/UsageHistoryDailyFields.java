package models;

import java.util.ArrayList;
import java.util.List;

public class UsageHistoryDailyFields {

	public String customerName;

	public Double bodyTemperature;

	public Integer lowBloodPressure;

	public Integer highBloodPressure;

	public Integer heartRate;

	public String serviceNames;

	public List<String> serviceName = new ArrayList<>();
}
