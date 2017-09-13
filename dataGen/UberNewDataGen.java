package dataGen;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

public class UberNewDataGen {
  
  public static int currTime = 1494884599;
  public static int threeYearsAgo = currTime - 94672800;
 
  
  public static long tourNum;
  public static long userNum;
  public static long driverNum;
  public static long riderPerDriver = 0;
  public static long TripPerDriver = 0;
  public static String serverId; 
  
  static String[] countyArray;
  
  public static void main(String[] args) throws FileNotFoundException, IOException {
    
    tourNum = Long.parseLong(args[1]);
    userNum = Long.parseLong(args[2]);
    driverNum = Long.parseLong(args[3]);
    serverId = args[4];
    
    riderPerDriver = userNum/driverNum;
    TripPerDriver = tourNum/driverNum;

    BufferedWriter buffer = null;
    FileWriter writer = null;
    
    countyArray = generateCountyArray();
    
    try {
      writer = new FileWriter(args[0] + "/trip.csv");
      buffer = new BufferedWriter(writer);

      buffer.write("TRIP_ID,TRIP_STATUS,TRIP_REQUEST_TIME,TRIP_FARE,USER_ID,DRIVER_ID,CITY_ID\n");
      
      int tripId = 0;
      for (int j=0; j < driverNum; j ++) {
        long driverId = j;
        long driverIndex = riderPerDriver * driverId;
        for (int k=0; k < TripPerDriver; k ++) {
          
          String line = 
              tripId              + ","  // TRIP_ID
           + "complete,"                 // TRIP_STATUS
           + getRandomTimestamp() + ","  // TRIP_REQUEST_TIME
           + getRandomMoney()     + ","  // TRIP_FARE
           + (driverIndex + k) + "@test.org,"    // USER_ID
           + driverId             + "@test.org," // DRIVER_ID
           + getRandomCity()      + "\n";        // CITY_ID

          if (tripId%10000000 == 0) {
            System.out.println(tripId);
          }
          
          buffer.write(line);
          tripId++;
        }
      }
      
      if (buffer != null)
        buffer.close();
      if (writer != null)
        writer.close();
      
      writer = new FileWriter(args[0] + "/user.csv");
      buffer = new BufferedWriter(writer);
      
      buffer.write("USER_ID,REFERED_BY_USER_ID,DRIVER_STATUS,IS_BANNED_CLIENT\n");
      for (int i = 0; i < userNum; ++i) {
        /*Users (string Email, string registered_phone_number,  boolean is_EatClub_memeber, 
         * String  my_invite_code, String used_whose_invite_code, String City_name, float trust_score)*/
        int userId = i;
        String line = 
              i           + "@test.org,"  // USER_ID
            + getRandomuserId()    + "@test.org," // REFERED_BY_USER_ID
            + userId%2             + "xx,"        // DRIVER_STATUS
            + userId               + "false\n";   // IS_BANNED_CLIENT
            
        if (i%1000000 == 0) {
          System.out.println(i);
        }
        
        buffer.write(line);
      }
      
      if (buffer != null)
        buffer.close();
      if (writer != null)
        writer.close();
      
      writer = new FileWriter(args[0] + "/device.csv");
      buffer = new BufferedWriter(writer);
      
      buffer.write("UBER_USER_ID,DEVICE_ID\n");
      for (int i = 0; i < userNum; ++i) {
        String line = 
              i    + "@test.org,"   // UBER_USER_ID
            + i    + "d\n";         // DEVICE_ID
        buffer.write(line);
        
        int randomInt = ThreadLocalRandom.current().nextInt(0,30);
        while (randomInt == 1) {
           line = 
               i + "@test.org,"   // UBER_USER_ID
            + (i + randomInt) + "d\n";         // DEVICE_ID
          buffer.write(line);
        }
        
        if (randomInt == 2) {
          long driverId = i/riderPerDriver;
          line = 
              i + "@test.org,"   
           + driverId + "d\n";      
          buffer.write(line);
        }
            
        if (i%1000000 == 0) {
          System.out.println(i);
        }
      }
      
      if (buffer != null)
        buffer.close();
      if (writer != null)
        writer.close();
      
      writer = new FileWriter(args[0] + "/document.csv");
      buffer = new BufferedWriter(writer);
      
      buffer.write("UBER_USER_ID,DOC_ID\n");
      for (int i = 0; i < userNum; ++i) {
        String line = 
              i    + "@test.org,"   
            + i    + "doc\n";       
        buffer.write(line);
        
        int randomInt = ThreadLocalRandom.current().nextInt(0,30);
        while (randomInt == 1) {
           line = 
               i + "@test.org,"   
            + (i + randomInt) + "doc\n";      
          buffer.write(line);
          randomInt = ThreadLocalRandom.current().nextInt(0,30);
        }
        
        if (randomInt == 2) {
          long driverId = i/riderPerDriver;
          line = 
              i + "@test.org,"   
           + driverId + "doc\n";      
          buffer.write(line);
        }
            
        if (i%1000000 == 0) {
          System.out.println(i);
        }
      }

      if (buffer != null)
        buffer.close();
      if (writer != null)
        writer.close();
      
      writer = new FileWriter(args[0] + "/payment.csv");
      buffer = new BufferedWriter(writer);
      
      buffer.write("UBER_USER_ID,PAYMENT_ID\n");
      for (int i = 0; i < userNum; ++i) {
        String line = 
              i    + "@test.org,"   
            + i    + "p\n";       
        buffer.write(line);
        
        int randomInt = ThreadLocalRandom.current().nextInt(0,30);
        while (randomInt == 1) {
           line = 
               i + "@test.org,"   
            + (i + randomInt) + "p\n";      
          buffer.write(line);
        }
        
        if (randomInt == 2) {
          long driverId = i/riderPerDriver;
          line = 
              i + "@test.org,"   
           + driverId + "p\n";      
          buffer.write(line);
        }
            
        if (i%1000000 == 0) {
          System.out.println(i);
        }
      }
      
      if (buffer != null)
        buffer.close();
      if (writer != null)
        writer.close();

    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        if (buffer != null)
          buffer.close();
        if (writer != null)
          writer.close();
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
  
  public static long getRandomDriver() {
    return ThreadLocalRandom.current().nextLong(0, driverNum);
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
