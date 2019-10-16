package com.example.basic.jdbc;

import java.sql.*;

/*
（1）数据库连接频繁开启和关闭，会严重影响数据库的性能。

（2）代码中存在硬编码，分别是数据库部分的硬编码和SQL执行部分的硬编码。
 */
public class JDBCTest {

    public static void main(String[] args) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            //1、加载数据库驱动
            Class.forName("com.mysql.jdbc.Driver");

            //2、通过驱动管理类获取数据库链接
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mybatis?characterEncoding=utf-8", "root", "root");

            //3、定义sql语句，?表示占位符
            String sql = "select * from user where username = ?";

            //4、获取预处理statement
            preparedStatement = connection.prepareStatement(sql);

            //5、设置参数，第一个参数为sql语句中参数的序号（从1开始），第二个参数为设置的参数值
            preparedStatement.setString(1, "admin");

            //6、向数据库发出sql执行查询，查询出结果集
            resultSet = preparedStatement.executeQuery();

            //7、遍历查询结果集
            while (resultSet.next()) {
                System.out.println(resultSet.getString("id") + "  " + resultSet.getString("username"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //8、释放资源
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {

                    e.printStackTrace();
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }

    }
}
