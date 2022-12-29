package com.lr.algurithm;

import com.lr.frame.Page;
import sun.awt.Mutex;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OPT extends Thread {

	private int opt_i = 0;
	public int opt_lackpage=0;//缺页数
	public double opt_ave_time=0;//平均时间
	public double opt_total_time=0;//总时间
	public List<Integer> opt_memory_page = new ArrayList<>();//内存现有页数存放
	// public int[] opt_memory_page = new int[config.taskSize];
	public List<Integer> opt_quick_table_page = new ArrayList<>();//快表现有页数存放
	public Mutex pause = new Mutex();
	public String snumber = new String();
	public String interruption = new String();
	public String totalTime = new String();
	public String avrTime = new String();
	public String missPage = new String();

	public double start(int a, double per_time) {
		if (!config.is_check) { //无快表模式
			if (!opt_memory_page.contains(a)) { //不在内存，产生缺页中断
				opt_lackpage ++ ;
				if (opt_memory_page.size() < config.memory_num) {
					opt_memory_page.add(a);
				} else { //遍历之后所有页号，与内存内页号相同则移到尾部，最后删除头部
					Map<Integer, Boolean> has_move = new HashMap<>();//移动过的不能再移动
					int cnt = 0;//移动次数要小于内存块数
					for (int j = 0; j < config.taskSize; j ++ ) {
						has_move.put(config.task[j], false);
					}

					for (int j = opt_i + 1; j < config.taskSize; j ++ ) { //从需要进入内存的页号的后一位开始
						if(opt_memory_page.contains(config.task[j])
								&& has_move.get(config.task[j]) != true
								&& cnt < config.memory_num - 1)
						{
							int temp = 0;
							for (int i = 0; i < opt_memory_page.size(); ++ i) {
								if (config.task[j] == opt_memory_page.get(i))
									temp = i;
							}
							opt_memory_page.remove(temp);
							opt_memory_page.add(config.task[j]);
							has_move.put(config.task[j], true);
							cnt ++ ;
						}
					}
					opt_memory_page.remove(0);
					opt_memory_page.add(a);

				}
				opt_total_time += 3 * config.memory_access + config.miss_page_interrupt;
				per_time += 3 * config.memory_access + config.miss_page_interrupt;
			} else {
				opt_total_time += 2 * config.memory_access;
				per_time += 2 * config.memory_access;
			}
		} else { //快表模式
			if(!opt_quick_table_page.contains(a)) { //不在快表
				if(!opt_memory_page.contains(a)) { //不在内存，产生缺页中断
					opt_lackpage ++ ;
					if(opt_memory_page.size() < config.memory_num) { //内存块未满,直接调入
						opt_memory_page.add(a);
					} else {
						Map<Integer, Boolean> has_move = new HashMap<>();//移动过的不能再移动
						int cnt = 0;//移动次数要小于内存块数
						for (int j = 0; j < config.taskSize; j ++ ) {
							has_move.put(config.task[j], false);
						}
						for(int j = opt_i + 1; j < config.taskSize; j ++ ) { //从需要进入内存的页号的后一位开始
							if(opt_memory_page.contains(config.task[j])
									&& has_move.get(config.task[j]) != true
									&& cnt < config.memory_num - 1) {
								int temp = 0;
								for (int i = 0; i < opt_memory_page.size(); ++ i) {
									if (config.task[j] == opt_memory_page.get(i))
										temp = i;
								}
								opt_memory_page.remove(temp);
								opt_memory_page.add(config.task[j]);
								has_move.put(config.task[j], true);
								cnt++;
							}
						}
						opt_memory_page.remove(0);
						opt_memory_page.add(a);
					}

					//快表置换用FIFO
					if(opt_quick_table_page.size() < config.quick_table_num) {
						opt_quick_table_page.add(a);
					} else {
						opt_quick_table_page.remove(0);
						opt_quick_table_page.add(a);
					}
					opt_total_time += 3 * config.memory_access + config.miss_page_interrupt;
					per_time += 3 * config.memory_access + config.miss_page_interrupt;
				} else { //在内存

					opt_total_time += 2 * config.memory_access;
					per_time += 2 * config.memory_access;
				}
			} else { //在快表
				opt_total_time += config.quick_table_access + config.memory_access;
				per_time += config.quick_table_access + config.memory_access;
			}
		}
		return per_time;
	}

	@Override
	public void run() {
		for (opt_i = 0; opt_i < config.taskSize; opt_i ++ ) {
			pause.lock();

			double per_time = 0;
			per_time = start(config.task[opt_i], per_time);
			String str1="查找";
			String s1 = String.valueOf(config.task[opt_i]);
			snumber += str1;
			snumber += s1;
			snumber += ":";

			for (int j = 0; j < config.memory_num; j ++ ) {
				if (j < opt_memory_page.size()) {
					String s = String.valueOf(opt_memory_page.get(j));
					snumber += s;
					snumber += " ";
				} else {
					snumber+=" ";
				}
			}
			pause.unlock();
			BigDecimal bd = new BigDecimal(per_time);
			bd = bd.setScale(2, RoundingMode.HALF_UP);
			snumber += "耗费时间:" + bd + "\n";
			Page.OPTtest.append(snumber);
			snumber = "";
			try {
				sleep(config.run_time);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		String s1 = String.valueOf(config.taskSize);
		s1 = String.valueOf(opt_lackpage);
		interruption = s1 + "次";
		double d = (double)opt_lackpage / config.taskSize * 100;
		BigDecimal bd = new BigDecimal(d);
		bd = bd.setScale(2, RoundingMode.HALF_UP);
		opt_ave_time = (double)opt_total_time / config.taskSize;
		totalTime = String.valueOf(opt_total_time);
		BigDecimal bda = new BigDecimal(opt_ave_time);
		bda = bda.setScale(2, RoundingMode.HALF_UP);
		avrTime = String.valueOf(bda);
		missPage = bd + "%";
		Page.opt_interrupt_label.setText(interruption);
		Page.opt_total_time_label.setText(totalTime);
		Page.opt_ave_time_label.setText(avrTime);
		Page.opt_miss_page_label.setText(missPage);
		// System.out.println("opt"+s1);
		// System.out.println("opt"+opt_total_time);
		// System.out.println("opt"+opt_ave_time);
		// System.out.println("opt"+d + "%");
	}
}
