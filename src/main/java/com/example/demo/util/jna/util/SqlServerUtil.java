package com.example.demo.util.jna.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

public class SqlServerUtil {

    // JDBC 驱动名及数据库 URL
    public static final String DRIVER = "com.microsoft.jdbc.sqlserver.SQLServerDriver";//定义驱动程序的路径
    public static final String URL = "jdbc:sqlserver://10.30.100.110:1433;DatabaseName=erbs";//配置数据库连接池
    public static final String USER = "sa";//数据库用户名
    public static final String PASSWORD  = "Sa123456";//密码

    public static void insertDao(Map map){
        Connection conn = null;
        //Statement stmt = null;
        PreparedStatement pstmt = null;
        try{
            // 注册 JDBC 驱动
            Class.forName(DRIVER);

            // 打开链接
            System.out.println("连接数据库...");
            conn = DriverManager.getConnection(URL,USER,PASSWORD);

            // 执行查询
            System.out.println(" 实例化Statement对象...");
           // stmt = conn.createStatement();
            String sql;
            sql = " insert into OrigDL(ErtuNum,MeterNum,TimeTag,ZxygZ,Source,Status) values (1018,?,?,?,4,1)";

            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            pstmt.setString(1, map.get("MeterNum").toString());
            pstmt.setString(2, map.get("TimeTag").toString());
            pstmt.setString(3, map.get("ZxygZ").toString());
            pstmt.executeUpdate();
           /*ResultSet rs = stmt.executeQuery(sql);

            // 展开结果集数据库
            while(rs.next()){
                // 通过字段检索
                //int id  = rs.getInt("LineNum");
                String geNumber =rs.getString("MeterNum");
                // 输出数据
                System.out.print("ID: " + geNumber);
                //  System.out.print(", 站点名称: " + geNumber);
                System.out.print("\n");
            }*/
            // 完成后关闭
           // rs.close();
            pstmt.close();
           // stmt.close();
            conn.close();
        }catch(SQLException se){
            // 处理 JDBC 错误
            se.printStackTrace();
        }catch(Exception e){
            // 处理 Class.forName 错误
            e.printStackTrace();
        }finally{
            // 关闭资源
            try{
                if(pstmt!=null) pstmt.close();
            }catch(SQLException se2){
            }// 什么都不做
            try{
                if(conn!=null) conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
        System.out.println("Goodbye!");
    }
}