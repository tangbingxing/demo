package com.tangbingxing.hotel;

import com.alibaba.fastjson.JSON;
import com.tangbingxing.hotel.pojo.Hotel;
import com.tangbingxing.hotel.pojo.HotelDoc;
import com.tangbingxing.hotel.service.IHotelService;
import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class HotelDocumentTest {

    @Autowired
    private RestHighLevelClient client;

    @Autowired
    private IHotelService hotelService;

    //新增文档
    @Test
    void testAddDocument() throws IOException {
        // 1.根据id查询酒店数据
        Hotel hotel = hotelService.getById(61083L);
        // 2.转换为文档类型
        HotelDoc hotelDoc = new HotelDoc(hotel);
        // 3.将HotelDoc转json
        String json = JSON.toJSONString(hotelDoc);

        // 1.准备Request对象
        IndexRequest request = new IndexRequest("hotel").id(hotelDoc.getId().toString());
        // 2.准备Json文档
        request.source(json, XContentType.JSON);
        // 3.发送请求
        client.index(request, RequestOptions.DEFAULT);
    }

    @Test
    void testGetDocumentById() throws IOException {
    }

    @Test
    void testDeleteDocumentById() throws IOException {
    }

    @Test
    void testUpdateById() throws IOException {
    }


    //批量添加数据到es中
    @Test
    void testBulkRequest() throws IOException {
    }
    //todo 连接es数据库
    @BeforeEach
    void setUp() {
        client = new RestHighLevelClient(RestClient.builder(
                HttpHost.create("http://10.147.18.147:9200")
        ));
    }

    @AfterEach
    void tearDown() throws IOException {
        client.close();
    }
}
