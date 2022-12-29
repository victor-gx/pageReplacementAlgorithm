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