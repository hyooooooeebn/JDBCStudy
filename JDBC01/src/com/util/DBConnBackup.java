/*=========================
        DBConn.java
 =========================*/

// ※ 싱클톤(singleton) 디자인 패턴을 이용한 DataBase 연결 객체 생성 전용 클래스
//    → DB연결 과정이 가장 부하가 크기 때문에
//       한 번 연결된 객체를 계속 사용하는 것이 좋다.

package com.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnBackup
{
	// 변수 선언
   private static Connection dbConn;
   //--자동으로 null 초기화
   
   // 메소드 정의 → 연결
   public static Connection getConnection()
   {
	   // 한 번 연결된 객체를 계속 사용
	   // 즉, 연결되지 않은 경우에만 연결을 시도하겠다는 의미
	   // → 싱글톤(디자인 패턴)
      if (dbConn == null) 
      {
         try
         {
            String url = "jdbc:oracle:thin:@localhost:1521:xe";
            //-- localhost는 오라클 서버의 ip주소를 기재하는 부분
            //	 1521은 오라클 Port Number
            //	 xe는 오라클 SID(Express Edition은 xe)
            
            String user = "scott";  	//--오라클 사용자 계정 이름 
            String pwd = "tiger";		//--오라클 사용자 계정 암호

            Class.forName("oracle.jdbc.driver.OracleDriver");
            //--OracleDreiver 클래스에 대한 객체 생성
            
            dbConn = DriverManager.getConnection(url, user, pwd);
            //-- 오라클 서버 실제 연결
            //	 갖고 있는 매개변수는 오라클주소, 계정명, 패스워드
            
         } catch (Exception e)   
         {
            System.out.println(e.toString());   
            //-- 오라클 서버 연결 실패 시 오류 메시지 출력 부분
         }
      }

      return dbConn;
      //-- 구성된 연결 객체 반환
   }

   // getConnection() 메소드의 오버로딩 → 연결
   public static Connection getConnection(String url, String user, String pwd)
   {
      if (dbConn == null)
      {
         try
         {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            dbConn = DriverManager.getConnection(url, user, pwd);
         } catch (Exception e)
         {
            System.out.println(e.toString());
         }

      }

      return dbConn;
   }

   // 메소드 정의 → 연결 종료
   public static void close()
   {
	   // dbConn 변수는
	   // DataBase가 연결된 상태일 경우 Connection을 갖는다.
	   // 연결되지 않은 상태라면 null을 갖는다.
      if (dbConn != null)
      {
         try
         {
        	// 연결 객체의 isClosed() 메소드를 통해 연결 상태 확인
        	//-- 연결이 닫혀있는 경우 true 반환
        	//   연결이 닫혀있지 않은 경우 false 반환
            if (!dbConn.isClosed()) 
               dbConn.close();
               //-- 연결 객체 close() 메소드 통해 연결 종료
            
         } catch (Exception e)
         {
            System.out.println(e.toString());
         }
      }

      dbConn = null;
      //-- 연결 객체 초기화

   }

}