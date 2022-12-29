package com.lr.algurithm;

import com.lr.frame.Page;
import sun.awt.Mutex;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class FIFO extends Thread{

	private int fifo_i = 0;
	public int fifo_lackpage=0;//缺页数
	public double fifo_ave_time=0;//平均时间
	public double fifo_total_time=0;//总时间
	public List<Integer> fifo_memory_page = new ArrayList<>();//内存现有页数存放
	public List<Integer> fifo_quick_table_page = new ArrayList<>();//快表现有页数存放
	public Mutex pause = new Mutex();
	public String snumber = new String();
	public String interruption = new String();
	public String totalTime = new String();
	public String avrTime = new String();
	public String missPage = new String();

	public double start(int a, double per_time) {
		if (!config.is_check) { //无快表模式
			if (!fifo_memory_page.contains(a)) { //不在内存，产生缺页中断
				fifo_lackpage ++ ;
				if (fifo_memory_page.size() < config.memory_num)
					fifo_memory_page.add(a);
				else {
					fifo_memory_page.remove(0);
					fifo_memory_page.add(a);
				}
				fifo_total_time += 3 * config.memory_access + config.miss_page_interrupt;
				per_time += 3 * config.memory_access + config.miss_page_interrupt;
			} else {
				fifo_total_time += 2 * config.memory_access;
				per_time += 2 * config.memory_access;
			}
		}
		else { //快表模式
			if (!fifo_quick_table_page.contains(a)) { //不在快表
				if (!fifo_memory_page.contains(a)) { //不在内存，产生缺页中断
					fifo_lackpage ++ ;
					if (fifo_memory_page.size() < config.memory_num) //内存块未满,直接调入
						fifo_memory_page.add(a);
					else {
						fifo_memory_page.remove(0);
						fifo_memory_page.add(a);
					}

					//快表置换用FIFO
					if (fifo_quick_table_page.size() < config.quick_table_num)
						fifo_quick_table_page.add(a);
					else {
						fifo_quick_table_page.remove(0);
						fifo_quick_table_page.add(a);
					}
					fifo_total_time += 3 * config.memory_access + config.miss_page_interrupt;
					per_time += 3 * config.memory_access + config.miss_page_interrupt;
				} else { //在内存
					fifo_total_time += 2 * config.memory_access;
					per_time += 2 * config.memory_access;
				}
			}  else { //在快表
				fifo_total_time += config.quick_table_access + config.memory_access;
				per_time += config.quick_table_access + config.memory_access;;
			}
		}
		return per_time;
	}

	@Override
	public void run() {
		super.run();
		for(fifo_i = 0; fifo_i < config.taskSize; fifo_i ++ ) {
			pause.lock();

			double per_time = 0;
			per_time = start(config.task[fifo_i], per_time);
			String str1="查找";
			String s1 = String.valueOf(config.task[fifo_i]);
			snumber += str1;
			snumber += s1;
			snumber += ":";

			for (int j = 0; j < config.memory_num; j ++ ) {
				if (j < fifo_memory_page.size()) {
					String s = String.valueOf(fifo_memory_page.get(j));
					snumber += s;
					snumber += " ";
				}
				else {
					snumber += " ";
				}
			}
			pause.unlock();
			BigDecimal bd = new BigDecimal(per_time);
			bd = bd.setScale(2, RoundingMode.HALF_UP);
			snumber += "耗费时间:" + bd + "\n";
			Page.FIFOtest.append(snumber);
			snumber = "";
			try {
				sleep(config.run_time);//睡眠300ms
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		String s1 = String.valueOf(config.taskSize);
		s1 = String.valueOf(fifo_lackpage);
		interruption = s1 + "次";

		// emit fifo_updata(s1+"次","interrupt_label");
		double d = (double)fifo_lackpage / config.taskSize * 100;
		BigDecimal bd = new BigDecimal(d);
		bd = bd.setScale(2, RoundingMode.HALF_UP);
		totalTime = String.valueOf(fifo_total_time);
		fifo_ave_time = fifo_total_time / config.taskSize;
		BigDecimal bda = new BigDecimal(fifo_ave_time);
		bda = bda.setScale(2, RoundingMode.HALF_UP);
		avrTime = String.valueOf(bda);
		missPage = bd + "%";
		Page.fifo_interrupt_label.setText(interruption);
		Page.fifo_total_time_label.setText(totalTime);
		Page.fifo_ave_time_label.setText(avrTime);
		Page.fifo_miss_page_label.setText(missPage);
		// System.out.println("fifo:"+s1);
		// System.out.println("fifo:"+fifo_total_time);
		// System.out.println("fifo:"+fifo_ave_time);
		// System.out.println("fifo:"+d + "%");

	}
}
