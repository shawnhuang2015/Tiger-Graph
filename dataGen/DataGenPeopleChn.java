// package dataGen;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class DataGenPeopleChn {

  public static String cityFile = "./sourceData/district-standard.csv";
  
  public static long N = 1110;
  public static long personNum     = 100 * N;
  public static long busTripNum    = 2 * N;
  public static long trainTripNum  = 2 * N;
  public static long flightTripNum = 3 * N;
  public static long AddrNum       = 80 * N;
  public static long hotelNum      = 500 * N;
  public static long caseNum       = 300 * N;
  public static long maxBankAccNum = 4;
  public static long bankTransNum  = 2000 * N;
  public static long maxPhoneAccNum= 3;
  public static long phoneCallNum  = 3000 * N;
  
  public static void main(String[] args) throws FileNotFoundException, IOException {
    long startTime = System.currentTimeMillis();

    BufferedWriter fileBuffer = null;
    FileWriter fileWriter = null;
    
    // String[] countyArray = generateCountyArray();
    
    // ChnRandomDataGen generator = new ChnRandomDataGen();
    EnglishRandomDataGen generator = new EnglishRandomDataGen();
    String[] countyArray = generator.cityArray;

    try {
      fileWriter = new FileWriter(args[0] + "/常住人口.csv");
      fileBuffer = new BufferedWriter(fileWriter);
      fileBuffer.write("姓名,性别,民族,身份证,出生日期,手机号码,邮箱,家庭住址\n");
      
      String addrOld = "";
      
      for (int i = 0; i < personNum; ++i) {
        if (i%2 == 0) {
          addrOld = generator.getRoad(); // + "@" + i + "@";
        }
        
        int sex = generator.getNum(0, 1);
        String line = 
           generator.getChineseName(sex) + ","  // 姓名
         + generator.getSex(sex) + ","          // 性别
         + generator.getEthic() + ","           // 民族
         + i + ","                                     // 身份证
         + generator.getAgeTs() + ","           // 出生日期
         + generator.getTel(Integer.toUnsignedLong(i)) + ","            // 手机号码
         + generator.getEmail(5, 8) + ","       // 邮箱
         + addrOld + "\n";                             // 家庭住址
        
        fileBuffer.write(line);
      }
      
      if (fileBuffer != null)
        fileBuffer.close();
      if (fileWriter != null)
        fileWriter.close();
      
      /******
       * 开房记录.csv
       */
      fileWriter = new FileWriter(args[0] + "/开房记录.csv");
      fileBuffer = new BufferedWriter(fileWriter);
      fileBuffer.write("身份证号,酒店名称,入住时间,入住房号,退房时间,入住城市\n");
      
      while (hotelNum > 0) {

        int num = generator.getNum(0, 3);
        hotelNum -= num;

        int ts = generator.getRandomTimestamp();

        String lineSndPart = 
            generator.road[generator.getNum(0, 
                generator.road.length - 1)] + " Hotel,"          // 酒店名称
            + ts + ","                                               // 入住时间
            + generator.getNum(100, 999) + ","                // 入住房号
            + (ts + 20000) + ","                                     // 退房时间
            + countyArray[generator.
                          getNum(0, countyArray.length - 1)] + "\n"; // 入住城市

        for (int i = 0; i < num; ++i) {

          String line = generator.getLongNum(0, personNum) + ","  // 身份证号
              + lineSndPart;

          fileBuffer.write(line);
        }
      }
      
      if (fileBuffer != null)
        fileBuffer.close();
      if (fileWriter != null)
        fileWriter.close();
      
      
      /*****
       * 汽车出行数据.csv
       */
      fileWriter = new FileWriter(args[0] + "/汽车出行数据.csv");
      fileBuffer = new BufferedWriter(fileWriter);
      fileBuffer.write("身份证号,出发车站,出发时间,到达车站\n");
      
      while (busTripNum > 0) {
        
        int num = generator.getNum(10, 30);
        busTripNum -= num;
        int ts = generator.getRandomTimestamp();

        String lineSndPart = 
            countyArray[generator.
                getNum(0, countyArray.length - 1)] + "汽车站,"            // 出发车站
            + ts + ","                                                   // 出发时间
            + countyArray[generator.
                getNum(0, countyArray.length - 1)] + "汽车站" + "\n";     // 到达车站

        for (int i = 0; i < num; ++i) {

          String line = generator.getLongNum(0, personNum) + ","  // 身份证号
              + lineSndPart;

          fileBuffer.write(line);
        }
      }
      
      if (fileBuffer != null)
        fileBuffer.close();
      if (fileWriter != null)
        fileWriter.close();
      
      /*****
       * 火车出行数据.csv
       */
      fileWriter = new FileWriter(args[0] + "/火车出行数据.csv");
      fileBuffer = new BufferedWriter(fileWriter);
      fileBuffer.write("身份证号,乘车班次,出发车站,出发时间,到达车站\n");
      
      while (trainTripNum > 0) {
        
        int num = generator.getNum(100, 200);
        trainTripNum -= num;
        int ts = generator.getRandomTimestamp();

        String lineSndPart = 
            "D" + trainTripNum + ","                                     // 乘车班次
            + countyArray[generator.
                getNum(0, countyArray.length - 1)] + "火车站,"            // 出发车站
            + ts + ","                                                   // 出发时间
            + countyArray[generator.
                getNum(0, countyArray.length - 1)] + "火车站" + "\n";     // 到达车站

        for (int i = 0; i < num; ++i) {

          String line = generator.getLongNum(0, personNum) + ","  // 身份证号
              + lineSndPart;

          fileBuffer.write(line);
        }
      }
      
      if (fileBuffer != null)
        fileBuffer.close();
      if (fileWriter != null)
        fileWriter.close();
      
      /*****
       * 通话信息.csv
       */
      fileWriter = new FileWriter(args[0] + "/通话信息.csv");
      fileBuffer = new BufferedWriter(fileWriter);
      fileBuffer.write("主叫,被叫,通话时间,通话时长,通话类型\n");
      
      while (phoneCallNum > 0) {
        // phone call number made by per person
        int num = generator.getNum(0, 100);
        phoneCallNum -= num;
        
        String userId = generator.getTel(generator.getLongNum(0, personNum)) + ",";  // 主叫

        for (int i = 0; i < num; ++i) {

          String line = userId  // 主叫
              + generator.getTel(generator.getLongNum(0, personNum)) + "," // 被叫
              + generator.getRandomTimestamp() + ","  // 通话时间
              + generator.getLongNum(10, 3600) + ",本地\n"; // 通话时间
          fileBuffer.write(line);
        }
      }
      
      if (fileBuffer != null)
        fileBuffer.close();
      if (fileWriter != null)
        fileWriter.close();
      
      
      /******
       * 银行账单明细.csv
       */
      fileWriter = new FileWriter(args[0] + "/银行账单明细.csv");
      fileBuffer = new BufferedWriter(fileWriter);
      fileBuffer.write("转出账号,转入账号,转账金额,交易类型,转账时间\n");
      
      while (bankTransNum > 0) {
        bankTransNum --;
        String line = generator.getBankAccount(generator.getLongNum(0, personNum)) + "," // 转出账号
            + generator.getBankAccount(generator.getLongNum(0, personNum)) + "," // 转入账号
            + generator.getLongNum(10, 100000) + ","// 转账金额
            + "转账," // 交易类型
            + generator.getRandomTimestamp() + "\n"; // 转账时间
        fileBuffer.write(line);
      }
      
      if (fileBuffer != null)
        fileBuffer.close();
      if (fileWriter != null)
        fileWriter.close();
      
      /******
       * 银行账号.csv
       */
      fileWriter = new FileWriter(args[0] + "/银行账号.csv");
      fileBuffer = new BufferedWriter(fileWriter);
      fileBuffer.write("开户行,开户人身份证,卡号,开户日期\n");
      
      for (int i=0; i < personNum; i ++) {
        int accountNUm = i%10/3 + 1;
        for (int j=0; j < accountNUm; j++) {
          String line = "Bank of America," // 开户行
              + i + "," // 开户人身份证
              + "000000" + (i*10 + j + 1) + ","// 卡号
              + generator.getRandomTimestamp() + "\n"; // 开户日期
          fileBuffer.write(line);
        }
      }
      
      if (fileBuffer != null)
        fileBuffer.close();
      if (fileWriter != null)
        fileWriter.close();
      
      /*****
       * 飞机出行数据.csv
       */
      fileWriter = new FileWriter(args[0] + "/飞机出行数据.csv");
      fileBuffer = new BufferedWriter(fileWriter);
      fileBuffer.write("身份证号码,航班号,航班时间,起飞站,目的站\n");
      
      while (flightTripNum > 0) {
        
        int num = generator.getNum(30, 100);
        flightTripNum -= num;
        int ts = generator.getRandomTimestamp();

        String lineSndPart = 
            generator.getLongNum(0, personNum) + ","  // 身份证号码
            + "UA" + flightTripNum + ","// 航班号
            +  ts + ","                                                 // 航班时间
            + countyArray[generator.
                getNum(0, countyArray.length - 1)] + "机场,"            // 起飞站
            + countyArray[generator.
                getNum(0, countyArray.length - 1)] + "机场" + "\n";     // 目的站

        for (int i = 0; i < num; ++i) {

          String line = generator.getLongNum(0, personNum) + ","  // 身份证号
              + lineSndPart;

          fileBuffer.write(line);
        }
      }
      
      /*****
       * 涉案人员信息.csv
       */
      fileWriter = new FileWriter(args[0] + "/涉案人员信息.csv");
      fileBuffer = new BufferedWriter(fileWriter);
      fileBuffer.write("案事件编号,身份证号\n");
      
      while (caseNum > 0) {
        int num = generator.getNum(1, 3);
        caseNum -= num;
        String line = 
            caseNum + "," // 案事件编号
            + generator.getLongNum(0, personNum) + "\n";     // 身份证号
        fileBuffer.write(line);
      }

    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        if (fileBuffer != null)
          fileBuffer.close();
        if (fileWriter != null)
          fileWriter.close();
      } catch (IOException ex) {
        ex.printStackTrace();
      }
    }

    long endTime   = System.currentTimeMillis();
    long totalTime = endTime - startTime;
    System.out.println("Done in " + totalTime);
  }
  
  public static String[] generateCountyArray () throws FileNotFoundException, IOException {
    String[] res = new String[689];
    try (BufferedReader br = new BufferedReader(new FileReader(cityFile))) {
    // try (BufferedReader br = new BufferedReader(new FileReader("./sourceData/district-standard.csv"))) {
      String line;
      int lineNum = 0;
      while ((line = br.readLine()) != null) {
        String[] cols = line.split("\t");
        res[lineNum] = cols[1];
        lineNum ++;
      }
    }
    
    return res;
  }
}
