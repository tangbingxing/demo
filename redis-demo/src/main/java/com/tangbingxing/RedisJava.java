package com.tangbingxing;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;


public class RedisJava {
    private Jedis jedis;
    //todo 输入你的连接地址和密码
    String url = "";
    String password = "";
    int dataNum = 0; //数据库0-15,默认为0;
    @Before
    public void setUp() {
        //建立连接
        jedis = new Jedis(url);
        //设置密码
        jedis.auth(password);
        //选择库
        jedis.select(dataNum);
        System.out.println("连接成功");
        System.out.println("服务正在运行：" + jedis.ping());
    }

    @Test
    public void testString() {
        // 插入数据，方法名称就是redis命令名称
        String result = jedis.set("name","张三");
        System.out.println("result = " +result);
        // 获取数据
        String name = jedis.get("name");
        System.out.println("name = " + name);
    }

    @After
    public void tearDown() {
        //
        if (jedis != null) {
            jedis.close();
        }
    }
}
