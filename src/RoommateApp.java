import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.HashMap;

public class RoommateApp {
	public static ArrayList<Roommate> maleY2 = new ArrayList<>();  //Contains Male Roommate Objects in Y2-Program
	public static ArrayList<Roommate> femaleY2 = new ArrayList<>();  //Contains Female Roommate Objects in Y2-Program
	public static ArrayList<Roommate> maleY4 = new ArrayList<>();  //Contains Male Roommate Objects in Y4-Program
	public static ArrayList<Roommate> femaleY4 = new ArrayList<>();  //Contains Female Roommate Objects in Y4-Program

	static ArrayList<ArrayList<String>> maleRankings = new ArrayList<>();  //Contains ArrayLists of Male Roommate Objects in Y2-Program
	static ArrayList<ArrayList<String>> maleRankings2 = new ArrayList<>();
	static ArrayList<ArrayList<String>>  femaleRankings = new ArrayList<>();  //Contains ArrayLists of Female Roommate Objects in Y2-Program
	static ArrayList<ArrayList<String>>  femaleRankings2 = new ArrayList<>();
	
	public static String[] anglophoneCountries = {"Botswana","Cameroon","Eritrea","Gambia","Ghana","Kenya",
			"Lesotho","Liberia","Malawi","Mauritius","Namibia","Nigeria","Rwanda","Seychelles",
			"Sierra Leone","Somaliland,","South Africa","South Sudan","Swaziland","Tanzania","Uganda",
			"Zambia","Zimbabwe"};
	
	public static String[] northAfricanCountries = {"Algeria","Egypt","Libya","Morocco","Sudan","Tunisia"};
	public static String[] westAfricanCountries = {"Benin","Burkina Faso","Gambia","Ghana","Guinea-Bissau",
			"Guinea","Ivory Coast","Liberia","Mali","Mauritania","Nigeria","Niger","Senegal","Sierra Leone",
			"Togo"};
	public static String[] centralAfricanCountries = {"Angola","Cameroon","Central African Republic","Chad",
			"Republic of Congo","Democratic Republic of Congo","Equatorial Guinea",
			"Gabon","Sao Tome and Principe"};
	public static String[] eastAfricanCountries = {"Burundi","Djibouti","Eritrea","Ethiopia","Kenya","Rwanda",
			"Seychelles","Somalia","Somaliland","South Sudan","Tanzania","Uganda"};
	
	public static boolean isAnglophone(String country) {
		if (Arrays.asList(anglophoneCountries).contains(country)) {
			return true;
		} else {
			return false;
		}
	}
	
	public static String whichAfricanRegion(String country) {
		if (Arrays.asList(northAfricanCountries).contains(country)) {
			return "North Africa";
		} else if (Arrays.asList(westAfricanCountries).contains(country)) {
			return "West Africa";
		} else if (Arrays.asList(centralAfricanCountries).contains(country)) {
			return "Central Africa";
		} else if (Arrays.asList(eastAfricanCountries).contains(country)) {
			return "East Africa";
		} else {
			return "South Africa";
		}
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList<Roommate> db = createRoommateDatabase();
		groupIntoGender(db);
		makeListsEqual();
		sortBasedOnCompatibility();
		/**String[][] finalMaleRankings_Y4 = generateMaleArrayRankings();
		String[][] finalMaleRankings_Y2 = generateMaleArrayRankings2();
		String[][] finalFemaleRankings_Y4 = generateFemaleArrayRankings();
		String[][] finalFemaleRankings_Y2 = generateFemaleArrayRankings2();*/
		
		/**System.out.println("---Boys---");
		PrintStableMatching(finalMaleRankings_Y2,finalMaleRankings_Y4);
		System.out.println("---Girls---");
		PrintStableMatching(finalFemaleRankings_Y2,finalFemaleRankings_Y4);*/
		System.out.println("Ghana is in: "+ whichAfricanRegion("Ghana"));
		System.out.println("Is Ghana anglophone? " + isAnglophone("Ghana"));
	}

	/**
	 * Returns an ArrayList of Roommates after database is populated from csv File
	 * @return
	 */
	public static ArrayList<Roommate> createRoommateDatabase(){
		System.out.print("Extracting from csv file...0%");
		String csvFile = "LeafDatabase.csv";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		System.out.print("...20%...");
		try {
			ArrayList<Roommate> applicants = new ArrayList<Roommate>();
			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {
				// use comma as separator
				String[] data = line.split(cvsSplitBy);
				applicants.add(new Roommate(data));
			}
			System.out.print("...98%...");

			return applicants;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		System.out.println("Extraction Done..100%. Database is Ready");
		return new ArrayList<>();

	}

	/**
	 * Groups all the Roommate objects in the database into their 
	 * respective gender and year groups
	 * @param allRoommates
	 */
	public static void groupIntoGender(ArrayList<Roommate> allRoommates){
		System.out.print("Grouping by Gender and Year...");

		for(Roommate roommie: allRoommates){

			if(roommie.getSex().equals("Male")){
				if(roommie.getProgramType().equals("2-Year Program")){
					maleY2.add(roommie);
				}else{
					maleY4.add(roommie);
				}
			}else{
				if(roommie.getProgramType().equals("2-Year Program")){
					femaleY2.add(roommie);
				}else{
					femaleY4.add(roommie);
				}
			}
		}
		System.out.println("Grouping Done...100%");
	}

	@SuppressWarnings("unused")
	public static void makeListsEqual(){
		System.out.print("Making the Lists Equal...");

		//make sure to have an even number of boys altogether
		//make sure to have an even number of girls too
		int differenceMales = Math.abs(maleY2.size() - maleY4.size());
		int differenceFemales = Math.abs(femaleY2.size() - femaleY4.size());

		while(differenceMales > 0){
			boolean Y2IsMore = maleY2.size() > maleY4.size() ;
			Roommate excess = (Y2IsMore ? maleY2.remove(0) : maleY4.remove(0));
			boolean status = (Y2IsMore? maleY4.add(excess): maleY2.add(excess));
			differenceMales = Math.abs(maleY2.size() - maleY4.size());
		}

		while(differenceFemales > 0){
			boolean Y2IsMore = femaleY2.size() > femaleY4.size() ;
			Roommate excess = (Y2IsMore ? femaleY2.remove(0) : femaleY4.remove(0));
			boolean status = (Y2IsMore? femaleY4.add(excess): femaleY2.add(excess));
			differenceFemales = Math.abs(femaleY2.size() - femaleY4.size());
		}
		System.out.print("Done...There are now: "+maleY2.size() +" Y2 boys "
				+maleY4.size()+" Y4 boys " +femaleY2.size()+" Y2 girls "
				+femaleY4.size()+" Y4 girls ");

	}

	/**
	 * Sorts the roommate objects in their various groups by compatibility
	 */
	@SuppressWarnings("null")
	public static void sortBasedOnCompatibility(){
		System.out.println("...Sorting Boys... ");

		//For Males Y4 Preferences
		for(Roommate roommie4: maleY4){
			SortedMap<Double, String> hisRankings = new TreeMap<Double, String>();
			hisRankings.put(0.0, roommie4.getName());

			for(Roommate roommie2: maleY2 ){
				double value = 100 -roommie4.checkCompatibility(roommie2);
				if(!hisRankings.containsKey(value)){
					hisRankings.put(value, roommie2.getName());
				}else{
					hisRankings.put(value*1.00000001, roommie2.getName());
				}
			}
			ArrayList<String> addOn = new ArrayList<String>();
			addOn.addAll(hisRankings.values());
			maleRankings.add(addOn);
		}
		
		//For Males Y2 Preferences
		for(Roommate roommie2: maleY2){
			SortedMap<Double, String> hisRankings = new TreeMap<Double, String>();
			hisRankings.put(0.0, roommie2.getName());

			for(Roommate roommie4: maleY4 ){
				double value = 100 -roommie2.checkCompatibility(roommie4);
				if(!hisRankings.containsKey(value)){
					hisRankings.put(value, roommie4.getName());
				}else{
					hisRankings.put(value*1.00000001, roommie4.getName());
				}
			}
			ArrayList<String> addOn = new ArrayList<String>();
			addOn.addAll(hisRankings.values());
			maleRankings2.add(addOn);
		}
		
		System.out.println("...Sorting Females...  ");
		//For Females Y4 Preferences
		for(Roommate roommie4: femaleY4){
			SortedMap<Double, String> herRankings = new TreeMap<Double, String>();
			herRankings.put(0.0, roommie4.getName());
			for(Roommate roommie2: femaleY2 ){
				double value = 100 -roommie4.checkCompatibility(roommie2);
				if(!herRankings.containsKey(value)){
					herRankings.put(value, roommie2.getName());
				}else{
					herRankings.put(value*1.00000001, roommie2.getName());
				}
			}
			ArrayList<String> addOn = new ArrayList<>();
			addOn.addAll(herRankings.values());
			femaleRankings.add(addOn);

		}
		
		//For Females Y2 Preferences
		for(Roommate roommie2: femaleY2){
			SortedMap<Double, String> herRankings = new TreeMap<Double, String>();
			herRankings.put(0.0, roommie2.getName());
			for(Roommate roommie4: femaleY4 ){
				double value = 100 -roommie2.checkCompatibility(roommie4);
				if(!herRankings.containsKey(value)){
					herRankings.put(value, roommie4.getName());
				}else{
					herRankings.put(value*1.00000001, roommie4.getName());
				}
			}
			ArrayList<String> addOn = new ArrayList<>();
			addOn.addAll(herRankings.values());
			femaleRankings2.add(addOn);
		}

	}

	public static String[][] generateMaleArrayRankings(){
		ArrayList<String[]> males = new ArrayList<>();

		for(ArrayList<String> m: maleRankings){
			males.add(m.toArray(new String[m.size()]));
		}
		
		String[][] finalMaleRankings  = males.toArray(new String[males.size()][]);
		System.out.println("---Boys---");
		//printStringArray(finalMaleRankings);

		return finalMaleRankings;

	}
	public static String[][] generateMaleArrayRankings2(){
		ArrayList<String[]> males = new ArrayList<>();

		for(ArrayList<String> m: maleRankings2){
			males.add(m.toArray(new String[m.size()]));
		}
		
		String[][] finalMaleRankings2  = males.toArray(new String[males.size()][]);
		System.out.println("---Boys---");
		//printStringArray(finalMaleRankings2);

		return finalMaleRankings2;

	}
	public static String[][] generateFemaleArrayRankings(){
		ArrayList<String[]> females = new ArrayList<>();

		//for females
		for(ArrayList<String> f: femaleRankings){
			females.add(f.toArray(new String[f.size()]));
		}
		
		String[][] finalFemaleRankings  = females.toArray(new String[females.size()][]);
		System.out.println("---Girls---");
		//printStringArray(finalFemaleRankings);
		return finalFemaleRankings;
	}
	public static String[][] generateFemaleArrayRankings2(){
		ArrayList<String[]> females = new ArrayList<>();

		//for females
		for(ArrayList<String> f: femaleRankings2){
			females.add(f.toArray(new String[f.size()]));
		}
		
		String[][] finalFemaleRankings2  = females.toArray(new String[females.size()][]);
		System.out.println("---Girls---");
		//printStringArray(finalFemaleRankings2);
		return finalFemaleRankings2;
	}
	
	public static void printStringArray(String[][] array){
		for(int i=0; i<array.length;i++){
			System.out.println(Arrays.toString(array[i]));
		}
	}
	
	public static void PrintStableMatching(String[][] twoYearPrefs, String[][] fourYearPrefs) {
		HashMap<String, Integer> nameMap = new HashMap<>();
		HashMap<Integer, String> twoReverseMap = new HashMap<>();
		HashMap<Integer, String> fourReverseMap = new HashMap<>();
		//Creating name to number maps
		for (int i=0; i<twoYearPrefs.length; i++) {
			nameMap.put(twoYearPrefs[i][0],i+1);
			nameMap.put(fourYearPrefs[i][0],i+1);
			twoReverseMap.put(i+1,twoYearPrefs[i][0]);
			fourReverseMap.put(i+1,fourYearPrefs[i][0]);
		}
		//initiating arrays
		int n = twoYearPrefs.length;
		int[][] twoYearPrefss = new int[n+1][n];
		int[][] fourYearPrefss = new int[n+1][n];
		//filling the arrays
		for (int i=1; i<=n; i++) {
			for (int j=0; j<n; j++) {
				twoYearPrefss[i][j] = nameMap.get(twoYearPrefs[i-1][j+1]);
				fourYearPrefss[i][j] = nameMap.get(fourYearPrefs[i-1][j+1]);
			}
		}
		//initiating algorithm arrays
		int [] engaged=new int[n+1];
		int [] menpointer=new int[n+1];
		int [] mlist=new int [(n+1)*(n+1)];
		int be=0;
		int en=n;
		//random filling of mlist
		for(int i=0;i<n;i++)
			mlist[i]=i+1;
		//initiating ranking array
		int [][] wrank=new int[n+1][n+1];
		//creating 2D array for women prefs ranking
		for(int i=1;i<=n;i++)
		{
			wrank[i][0]=n+1;
			for(int j=0;j<n;j++)
			{
				wrank[i][fourYearPrefss[i][j]]=j;
			}
		}
		//iterative algorithm to match people
		while(be!=en)
		{
			int a=mlist[be];
			be++;
			while(menpointer[a]<n)
			{
				int h=twoYearPrefss[a][menpointer[a]];
				menpointer[a]++;
				if (wrank[h][a]<wrank[h][engaged[h]])
				{
					if(engaged[h]!=0)
					{
						mlist[en]=engaged[h];
						en++;
					}
					
					engaged[h]=a;
					break;
				}
			}
		}
		//printing results of the matching
		for(int i=1;i<=n;i++)
		{
			if(engaged[i]!=0)
			{
				System.out.println(
						twoReverseMap.get(engaged[i]) + " matched with " + fourReverseMap.get(i));
			}
		}
	}

}
