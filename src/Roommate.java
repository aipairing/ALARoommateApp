import java.awt.List;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class Roommate {
	public String name,sex,country, programType, facebookUrl, messageToFutureRoommate;
	public ArrayList<String> attitude, roomConditions;

	static final String[] countries = {"Slovakia","Germany"};
	static final int FULL_POINTS = 5;

	public Roommate(String[] dataInitial){
		ArrayList<String> data = new ArrayList<String>(Arrays.asList(dataInitial));
		name = data.get(0);
		country = data.get(1);
		facebookUrl = data.get(2);
		sex = data.get(3);
		programType = data.get(4);
		messageToFutureRoommate = data.get(5);
		attitude = new ArrayList<String>(data.subList(6, 11));
		roomConditions = new ArrayList<String>(data.subList(12, 21));
	}

	public double compareAttitude(Roommate roommie){
		int totalPointsAvailable = FULL_POINTS*getAttitude().size();
		double sum = 0.0;
		for(int i = 0; i< getAttitude().size();i++){
			int me = Integer.valueOf(getAttitude().get(i));
			int other = Integer.valueOf(roommie.getAttitude().get(i));
			sum = sum+ (FULL_POINTS - Math.abs(me - other));
		}
		return sum/totalPointsAvailable;
	}

	public double compareRoomConditions(Roommate roommie){
		int totalPointsAvailable = FULL_POINTS*getRoomConditions().size();
		double sum = 0.0;
		for(int i = 0; i< getRoomConditions().size();i++){
			int me = Integer.valueOf(getRoomConditions().get(i));
			int other = Integer.valueOf(roommie.getRoomConditions().get(i));
			sum = sum+ (FULL_POINTS - Math.abs(me - other));
		}
		return sum/totalPointsAvailable;
	}

	public double checkCompatibility(Roommate roommie){
		int wgtOfAttitude = 50 ;
		int wgtOfRoomConditions =50;
		double result =  compareAttitude(roommie)*wgtOfAttitude + compareRoomConditions(roommie)*wgtOfRoomConditions;
		//System.out.println(getName()+" vrs "+roommie.getName()+" "+ result);
		return result;
	}

	public String getName(){
		return name;
	}
	public String getSex(){
		return sex;
	}

	public String getCountry(){
		return country;
	}

	public String getProgramType(){
		return programType;
	}

	public String getFacebookURL(){
		return facebookUrl;
	}

	public String getMessageToFutureRoommate(){
		return messageToFutureRoommate;
	}

	public ArrayList<String> getAttitude(){
		return attitude;
	}

	public ArrayList<String> getRoomConditions(){
		return roomConditions;
	}

	public String toString(){
		return getName();
	}



}
