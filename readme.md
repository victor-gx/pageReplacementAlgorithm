## 题目要求
采用多道程序思想设计一个程序，模拟页存储管理地址变换的过程，可采用FIFO、LRU、LFU、OPT四页面置换算法。基本要求如下：

1. 需要建立访问页表线程、访问快表线程、缺页中断处理线程、访问内存线程等，协同这些线程模拟完成地址变换的过程。
2. 输入一个逻辑页面访问序列和随机产生逻辑页面访问序列，分别由四个算法完成页面置换；
3. 能够设定驻留内存页面的个数、内存的存取时间、缺页中断的时间、快表的时间，并提供合理省缺值，可以暂停和继续系统的执行；
4. 能够设定页号序列中逻辑页面个数和范围；
5. 能够设定有快表和没有快表的运行模式； 
6. 提供良好图形界面，同时能够展示四个算法运行的结果；
7. 给出每种页面置换算法每个页面的存取时间； 
8. 能够将每次的实验输入和实验结果存储起来，随时可查询； 
9. 完成多次不同设置的实验，总结实验数据，看看能得出什么结论。
## 运行界面

### 执行界面

![image-20221229161057165](https://victor-gx.oss-cn-beijing.aliyuncs.com/img/2022/linux/202212291611256.png)

### 保存界面

![image-20221229161155749](https://victor-gx.oss-cn-beijing.aliyuncs.com/img/2022/linux/202212291611826.png)

### 历史界面

![image-20221229161213272](https://victor-gx.oss-cn-beijing.aliyuncs.com/img/2022/linux/202212291612331.png)

**sql语句**
```sql
create table test
(
    memory_n         int           null,
    page_n           varchar(50)   null,
    page_list        varchar(50)   null,
    memory_t         varchar(50)   null,
    quick_table_t    varchar(50)   null,
    interrupt_t      varchar(50)   null,
    is_check         varchar(20)   null,
    fifo_total_t     varchar(50)   null,
    fifo_ave_t       varchar(50)   null,
    fifo_miss_page   varchar(50)   null,
    fifo_interrupt_n varchar(50)   null,
    fifo_text        varchar(1000) null,
    lru_total_t      varchar(50)   null,
    lru_ave_t        varchar(50)   null,
    lru_miss_page    varchar(50)   null,
    lru_interrupt_n  varchar(50)   null,
    lru_text         varchar(1000) null,
    lfu_total_t      varchar(50)   null,
    lfu_ave_t        varchar(50)   null,
    lfu_miss_page    varchar(50)   null,
    lfu_interrupt_n  varchar(50)   null,
    lfu_text         varchar(1000) null,
    opt_total_t      varchar(50)   null,
    opt_ave_t        varchar(50)   null,
    opt_miss_page    varchar(50)   null,
    opt_interrupt_n  varchar(50)   null,
    opt_text         varchar(1000) null
);
