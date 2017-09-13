package dataGen;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

public class UberDataGen {
  
  public static int currTime = 1494884599;
  public static int threeYearsAgo = currTime - 94672800;
  
  public static long tourNum;
  public static long foodNum;
  public static long userNum;
  
  static String[] countyArray;
  
  public static void main(String[] args) throws FileNotFoundException, IOException {
    
    tourNum = Long.parseLong(args[1]);
    foodNum = Long.parseLong(args[2]);
    userNum = Long.parseLong(args[3]);

    BufferedWriter tourBuffer = null;
    FileWriter tourWriter = null;
    BufferedWriter userBuffer = null;
    FileWriter userWriter = null;
    BufferedWriter foodBuffer = null;
    FileWriter foodWriter = null;
    
    countyArray = generateCountyArray();
    
    try {
      tourWriter = new FileWriter(args[0] + "/trip.csv");
      tourBuffer = new BufferedWriter(tourWriter);
      userWriter = new FileWriter(args[0] + "/user.csv");
      userBuffer = new BufferedWriter(userWriter);
      foodWriter = new FileWriter(args[0] + "/food.csv");
      foodBuffer = new BufferedWriter(foodWriter);
      
      tourBuffer.write("trip_id,driver_id,rider_id,timestamp,start_location_id,destination_id,"
          + "driver_Phone_number,rider_phone_number,credit_card_number,trip_cost\n");
      for (int i = 0; i < tourNum; ++i) {
        /*Trips ( int Driver_id,  int Rider_id, timestamp, int start_location_id,
         *  int destination_id, String Driver_Phone_number,  String Rider_phone_number, 
         *  int credit_card_number , float trip_cost) */
        long userId = getRandomuserId();
        
        String line = 
           i                    + ","  // trip id
         + getRandomDriver()    + "@test.org,"  // Driver_id
         + userId               + "@test.org,"  // Rider_id
         + getRandomTimestamp() + ","  // timestamp
         + getRandomInt()       + ","  // start_location_id
         + getRandomInt()       + ","  // destination_id
         + getRandomInt()       + ","  // Driver_Phone_number
         + userId               + ","  // Rider_phone_number
         + userId               + ","  // credit_card_number
         + getRandomMoney()     + "\n";  // trip_cost


        if (i%10000000 == 0) {
          System.out.println(i);
        }
        
        tourBuffer.write(line);
      }
      
      userBuffer.write("email,registered_phone_number,is_eatclub_memeber,my_invite_code,"
          + "used_whose_invite_code,city_name,trust_score\n");
      for (int i = 0; i < userNum; ++i) {
        /*Users (string Email, string registered_phone_number,  boolean is_EatClub_memeber, 
         * String  my_invite_code, String used_whose_invite_code, String City_name, float trust_score)*/
        int userId = i;
        String line = 
              userId      + "@test.org,"  // Rider_id
            + userId               + ","  // Rider_phone_number
            + userId%2             + ","  // is_EatClub_memeber
            + userId               + ","  // my_invite_code
            + getRandomuserId()    + ","  // used_whose_invite_code
            + getRandomCity()      + ","  // City_name
            + getRandomTrustScore()+ "\n";// trust_score
            
        if (i%1000000 == 0) {
          System.out.println(i);
        }

        userBuffer.write(line);
      }
      
      foodBuffer.write("order_id,user_id,driver_id,restaurat_id,paid_amount,ordertime\n");
      for (int i = 0; i < foodNum; ++i) {
        /*FoodOrders( String User_id,  String Driver_id,  String Restaurat_id, float paid_amount，int ordertime)*/
        String line = 
              i                    + "," // oder id
            + getRandomFoodUserId()+ "@test.org,"  // User_id
            + getRandomDriver()    + "@test.org,"    // Driver_id
            + getRandomRestId()    + ","    // Restaurat_id
            + getRandomMoney()     + ","    // paid_amount
            + getRandomTimestamp() + "\n";  // timestamp
        
        if (i%10000000 == 0) {
          System.out.println(i);
        }

        foodBuffer.write(line);
      }


    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        if (tourBuffer != null)
          tourBuffer.close();
        if (tourWriter != null)
          tourWriter.close();
        if (userBuffer != null)
          userBuffer.close();
        if (userWriter != null)
          userWriter.close();
        if (foodBuffer != null)
          foodBuffer.close();
        if (foodWriter != null)
          foodWriter.close();
      } catch (IOException ex) {
        ex.printStackTrace();
      }
    }
  }
  
  public static String[] generateCountyArray () throws FileNotFoundException, IOException {
    String[] res = new String[3];
    try (BufferedReader br = new BufferedReader(new FileReader("/home/graphsql/demo/uber/data/city.csv"))) {
      String line;
      int lineNum = 0;
      while ((line = br.readLine()) != null) {
        String[] cols = line.split(",");
        
        String col2 = "";
        if (!cols[1].contains(" of")) 
          col2 = cols[1];
        
        res[lineNum] = (cols[0] + " " + col2).trim();
        lineNum ++;
      }
    }
    System.out.println(res[0]);
    return res;
  }
  
  // timestamps within 3 years
  public static int getRandomTimestamp() {
    return ThreadLocalRandom.current().nextInt(threeYearsAgo, currTime);
  }
  
  public static long getRandomuserId() {
    return ThreadLocalRandom.current().nextLong(0, userNum);
  }
  
  public static int getRandomInt() {
    return ThreadLocalRandom.current().nextInt(0, Integer.MAX_VALUE);
  }
  
  public static int getRandomMoney() {
    return ThreadLocalRandom.current().nextInt(0, 100);
  }
  
  public static int getRandomDriver() {
    return ThreadLocalRandom.current().nextInt(0, 500000);
  }
  
  public static int getRandomBool() {
    return ThreadLocalRandom.current().nextInt(0, 2);
  }
  
  public static String getRandomCity() {
    return countyArray[ThreadLocalRandom.current().nextInt(0, 3)];
  }
  
  public static float getRandomTrustScore() {
    return (float) Math.random();
  }
  
  public static int getRandomRestId() {
    return ThreadLocalRandom.current().nextInt(0, 620000);
  }

  public static long getRandomFoodUserId() {
    long id = getRandomuserId();
    if (id%2==0) return id + 1; else return id;
  }
}
