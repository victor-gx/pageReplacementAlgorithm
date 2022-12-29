package com.lr.algurithm;

import com.lr.frame.Page;
import sun.awt.Mutex;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class LRU extends Thread {

	private int lru_i = 0;
	public int lru_lackpage=0;//缺页数
	public double lru_ave_time=0;//平均时间
	public double lru_total_time=0;//总时间
	public List<Integer> lru_memory_page = new ArrayList<>();//内存现有页数存放
	public List<Integer> lru_quick_table_page = new ArrayList<>();//快表现有页数存放
	public Mutex pause = new Mutex();
	public String snumber = new String();
	public String interruption = new String();
	public String totalTime = new String();
	public String avrTime = new String();
	public String missPage = new String();

	public double start(int a, double per_time) {
		if(!config.is_check) { //无快表模式

			if(!lru_memory_page.contains(a)) { //不在内存，产生缺页中断
				lru_lackpage ++ ;
				if(lru_memory_page.size() < config.memory_num) {
					lru_memory_page.add(a);
				} else {
					lru_memory_page.remove(0);
					lru_memory_page.add(a);
				}
				lru_total_time += 3 * config.memory_access + config.miss_page_interrupt;
				per_time += 3 * config.memory_access + config.miss_page_interrupt;
			}
			else { //在内存中则找出这个元素并删除，在末尾添加这个元素，保证最近访问的置于末尾
				// if (a < lru_memory_page.size())
				int temp = 0;
				for (int i = 0; i < lru_memory_page.size(); ++ i) {
					if (a == lru_memory_page.get(i))
						temp = i;
				}
				lru_memory_page.remove(temp);
				// System.out.println(a);
				lru_memory_page.add(a);
				lru_total_time += 2 * config.memory_access;
				per_time += 2 * config.memory_access;
			}
		}
		else { //快表模式
			if(!lru_quick_table_page.contains(a)) { //不在快表
				if(!lru_memory_page.contains(a)) { //不在内存，产生缺页中断
					lru_lackpage ++ ;
					if(lru_memory_page.size() < config.memory_num) { //内存块未满,直接调入
						lru_memory_page.add(a);
					} else {
						lru_memory_page.remove(0);
						lru_memory_page.add(a);
					}
					//快表置换用FIFO
					if(lru_quick_table_page.size() < config.quick_table_num) {
						lru_quick_table_page.add(a);
					} else {
						lru_quick_table_page.remove(0);
						lru_quick_table_page.add(a);
					}
					lru_total_time += 3 * config.memory_access + config.miss_page_interrupt;
					per_time += 3 * config.memory_access + config.miss_page_interrupt;
				}
				else { //在内存
					int temp = 0;
					for (int i = 0; i < lru_memory_page.size(); ++ i) {
						if (a == lru_memory_page.get(i))
							temp = i;
					}
					lru_memory_page.remove(temp);
					lru_memory_page.add(a);
					lru_total_time += 2 * config.memory_access;
					per_time += 2 * config.memory_access;
				}
			} else { //在快表
				int temp = 0;
				for (int i = 0; i < lru_memory_page.size(); ++ i) {
					if (a == lru_memory_page.get(i))
						temp = i;
				}
				lru_memory_page.remove(temp);
				lru_memory_page.add(a);
				lru_total_time += config.quick_table_access + config.memory_access;
				per_time += config.quick_table_access + config.memory_access;
			}
		}
		return per_time;
	}

	@Override
	public void run() {
		for (lru_i = 0; lru_i < config.taskSize; lru_i ++ ) {
			pause.lock();

			double per_time = 0;
			per_time = start(config.task[lru_i],per_time);
			String str1="查找";
			String s1 = String.valueOf(config.task[lru_i]);
			snumber += str1;
			snumber += s1;
			snumber += ":";

			for (int j = 0; j < config.memory_num; j ++ ) {
				if (j < lru_memory_page.size()) {
					String s = String.valueOf(lru_memory_page.get(j));
					snumber+=s;
					snumber+=" ";
				} else {
					snumber+=" ";
				}
			}
			pause.unlock();
			BigDecimal bd = new BigDecimal(per_time);
			bd = bd.setScale(2, RoundingMode.HALF_UP);
			snumber += "耗费时间:" + bd.toString() + "\n";
			Page.LRUtest.append(snumber);
			snumber = "";
			try {
				sleep(config.run_time);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		String s1 = String.valueOf(config.taskSize);
		s1 = String.valueOf(lru_lackpage);
		interruption = s1 + "次";
		double d = (double)lru_lackpage / config.taskSize * 100;
		BigDecimal bd = new BigDecimal(d);
		bd = bd.setScale(2, RoundingMode.HALF_UP);
		lru_ave_time = (double)lru_total_time / config.taskSize;
		totalTime = String.valueOf(lru_total_time);
		BigDecimal bda = new BigDecimal(lru_ave_time);
		bda = bda.setScale(2, RoundingMode.HALF_UP);
		avrTime = String.valueOf(bda);
		missPage = bd + "%";
		Page.lru_interrupt_label.setText(interruption);
		Page.lru_total_time_label.setText(totalTime);
		Page.lru_ave_time_label.setText(avrTime);
		Page.lru_miss_page_label.setText(missPage);
		// System.out.println("lru"+s1);
		// System.out.println("lru"+lru_total_time);
		// System.out.println("lru"+lru_ave_time);
		// System.out.println("lru"+d + "%");
	}
}
