package com.example.basic.jdbc;


import com.example.basic.jdbc.mapper.PersonMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;

/*
4、MyBatis执行过程
第一步：MyBatis通过读取配置文件信息（全局配置文件和映射文件），构造出SqlSessionFactory，即会话工厂。
MyBatis配置文件，包括MyBatis全局配置文件和MyBatis映射文件，其中全局配置文件配置了数据源、事务等信息；映射文件配置了SQL执行相关的信息。
第二步：通过SqlSessionFactory，MyBatis可以创建SqlSession（即会话），MyBatis是通过SqlSession来操作数据库的。
第三步：MyBatis操作数据库。SqlSession本身不能直接操作数据库，它是通过底层的Executor执行器接口来操作数据库的。
Executor接口有两个实现类，一个是普通执行器，一个是缓存执行器（默认）。Executor执行器要处理的SQL信息是封装到一个底层对象MappedStatement中。
该对象包括：SQL语句、输入参数映射信息、输出结果集映射信息。其中输入参数和输出结果的映射类型包括Java的简单类型、HashMap集合对象、POJO对象类型。
 */
public class MyBatisTest {
    public static void main(String[] args) throws Exception
    {
        // 读取mybatis-config.xml文件
        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");

        // 初始化mybatis，创建SqlSessionFactory类的实例
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        // 创建Session实例
        SqlSession session = sqlSessionFactory.openSession();

        // 操作数据库方法一：获得xml映射文件中定义的操作语句
        Person p = session.selectOne("cn.mybatis.mydemo.mapper.PersonMapper.selectPersonById", 1);
        // 打印Peson对象
        System.out.println(p);

        // 操作数据库方法二：获得mapper接口的代理对象
        PersonMapper pm = session.getMapper(PersonMapper.class);
        // 直接调用接口的方法，查询id为1的Peson数据
//        Person p2 = pm.selectPersonById(1);
//         打印Peson对象
//        System.out.println(p2);

        // 提交事务
        session.commit();
        // 关闭Session
        session.close();
    }
}
