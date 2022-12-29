package com.lr.algurithm;

public class config {

	public static int page_low = 0;
	public static int page_high = 0;
	public static int page_list_low = 0;
	public static int page_list_high = 0;

	public static int run_time = 0;

	public static int quick_table_num = 0;//快表大小
	public static int memory_num = 0;//内存块数
	public static double memory_access = 0;//内存存取时间
	public static double quick_table_access = 0;//快表存取时间
	public static double miss_page_interrupt = 0;//缺页中断处理时间
	public static int taskSize = 0;//页面序列数量
	public static int[] task = new int[taskSize];//页面序列存放
	public static boolean is_check=false;//快表启用判断

}
