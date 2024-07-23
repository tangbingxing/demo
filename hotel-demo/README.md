# Hotel-demo
## 导入数据
使用提供的tb_hotel.sql文件创建示例数据
## 创建索引库
在hotel-demo的com.tangbingxing.hotel.constants包下，创建一个类，定义mapping映射的JSON字符串常量

在hotel-demo中的HotelIndexTest测试类中，编写单元测试，实现创建索引的[官方文档](https://www.elastic.co/guide/en/elasticsearch/client/java-rest/7.12/java-rest-high-create-index.html)

代码分为三步：

- 1）创建Request对象。因为是创建索引库的操作，因此Request是CreateIndexRequest。
- 2）添加请求参数，其实就是DSL的JSON参数部分。因为json字符串很长，这里是定义了静态字符串常量MAPPING_TEMPLATE，让代码看起来更加优雅。
- 3）发送请求，client.indices()方法的返回值是IndicesClient类型，封装了所有与索引库操作有关的方法。

## 删除索引库
与创建索引库相比：

- 请求方式从PUT变为DELTE
- 请求路径不变
- 无请求参数

在hotel-demo中的HotelIndexTest测试类中，编写单元测试，实现删除索引

代码分为三步：
- 1）创建Request对象。这次是DeleteIndexRequest对象
- 2）准备参数。这里是无参
- 3）发送请求。改用delete方法