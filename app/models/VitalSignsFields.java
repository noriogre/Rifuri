package models;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VitalSignsFields {

	public Integer customerId;

	public String customerName;

	public String bodyTemperature;

	public String lowBloodPressure;

	public String highBloodPressure;

	public String heartRate;

	public String comaScale;

	public String respirationRate;

	public String createdAt;

	public List<String> bodyTemperatureList = new ArrayList<>();

	public List<String> lowBloodPressureList = new ArrayList<>();

	public List<String> highBloodPressureList = new ArrayList<>();

	public List<String> heartRateList = new ArrayList<>();

	public List<String> comaScaleList = new ArrayList<>();

	public List<String> respirationRateList = new ArrayList<>();

}
