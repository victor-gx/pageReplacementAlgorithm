package com.lr.algurithm;

import com.lr.frame.Page;
import com.lr.tool.Node;
import sun.awt.Mutex;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class LFU extends Thread {

	private int lfu_i = 0;
	public int lfu_lackpage=0;//缺页数
	public double lfu_ave_time=0;//平均时间
	public double lfu_total_time=0;//总时间
	public List<Node> lfu_memory_page = new ArrayList<>();//内存现有页数存放
	public List<Integer> lfu_quick_table_page = new ArrayList<>();//快表现有页数存放
	public Mutex pause = new Mutex();
	public String snumber = new String();
	public String interruption = new String();
	public String totalTime = new String();
	public String avrTime = new String();
	public String missPage = new String();

	public double start(int a, double per_time) {
		if(!config.is_check) { //无快表模式
			boolean flag = true;
			int index = 0;
			for (Node map:lfu_memory_page) {
				if(map.key == a) {
					flag = false;
					break;
				}
				index ++ ;
			}

			if (flag) { //不在内存
				lfu_lackpage ++ ;
				//内存满，删去访问频率最小的那个，相等的频率按fifo删除
				if(lfu_memory_page.size() >= config.memory_num) {
					Node min_obj;
					min_obj = lfu_memory_page.get(0);
					for (Node pair : lfu_memory_page) {
						if (pair.value < min_obj.value) {
							min_obj = pair;
						}
					}
					lfu_memory_page.remove(min_obj);
				}
				lfu_memory_page.add(new Node(a));
				lfu_total_time += 3 * config.memory_access + config.miss_page_interrupt;
				per_time += 3 * config.memory_access + config.miss_page_interrupt;
			} else {

				lfu_memory_page.get(index).value++;
				lfu_total_time += 2 * config.memory_access;
				per_time += 2 * config.memory_access;
			}
		} else { //快表模式
			if(!lfu_quick_table_page.contains(a))//不在快表
			{
				boolean flag = true;
				int index = 0;
				for (Node map : lfu_memory_page) {
					if (map.key == a) {
						flag = false;
						break;
					}
					index ++ ;
				}

				if (flag) { //不在内存
					lfu_lackpage ++ ;
					//内存满，删去访问频率最小的那个，相等的频率按lfu删除
					if (lfu_memory_page.size() >= config.memory_num) {
						Node min_obj;
						min_obj = lfu_memory_page.get(0);
						for (Node map : lfu_memory_page) {
							if(map.value < min_obj.value) {
								min_obj = map;
							}
						}
						lfu_memory_page.remove(min_obj);
					}

					lfu_memory_page.add(new Node(a));

					//快表置换用FIFO
					if (lfu_quick_table_page.size() < config.quick_table_num) {
						lfu_quick_table_page.add(a);
					} else {
						lfu_quick_table_page.remove(0);
						lfu_quick_table_page.add(a);
					}

					lfu_total_time += 3 * config.memory_access + config.miss_page_interrupt;
					per_time += 3 * config.memory_access + config.miss_page_interrupt;
				} else { //在内存
					lfu_memory_page.get(index).value++;

					lfu_total_time += 2 * config.memory_access;
					per_time += 2 * config.memory_access;
				}
			} else { //在快表
				int index = 0;
				for (Node map : lfu_memory_page) {
					if(map.key == a) {
						lfu_memory_page.get(index).value++;
						break;
					}
					index++;
				}
				lfu_total_time += config.quick_table_access + config.memory_access;
				per_time += config.quick_table_access + config.memory_access;
			}
		}
		return per_time;
	}

	@Override
	public void run() {
		for(lfu_i = 0; lfu_i < config.taskSize; lfu_i ++ ) {
			pause.lock();
			double per_time = 0;
			per_time = start(config.task[lfu_i], per_time);
			String str1 = "查找";
			String s1 = String.valueOf(config.task[lfu_i]);
			snumber += str1;
			snumber += s1;
			snumber += ":";

			for (int j = 0; j < config.memory_num; j ++) {
				if (j < lfu_memory_page.size()) {
					String s = String.valueOf(lfu_memory_page.get(j).key);
					snumber+=s;
					snumber+=" ";
				} else {
					snumber+=" ";
				}
			}
			pause.unlock();
			BigDecimal bd = new BigDecimal(per_time);
			bd = bd.setScale(2, RoundingMode.HALF_UP);
			snumber += "耗费时间:" + bd + "\n";
			Page.LFUtest.append(snumber);
			snumber = "";
			try {
				sleep(config.run_time);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		String s1 = String.valueOf(config.taskSize);
		s1 = String.valueOf(lfu_lackpage);
		interruption = s1 + "次";
		double d = (double)lfu_lackpage / config.taskSize * 100;
		BigDecimal bd = new BigDecimal(d);
		bd = bd.setScale(2, RoundingMode.HALF_UP);
		lfu_ave_time = (double)lfu_total_time / config.taskSize;
		totalTime = String.valueOf(lfu_total_time);
		BigDecimal bda = new BigDecimal(lfu_ave_time);
		bda = bda.setScale(2, RoundingMode.HALF_UP);
		avrTime = String.valueOf(bda);
		missPage = bd + "%";
		Page.lfu_interrupt_label.setText(interruption);
		Page.lfu_total_time_label.setText(totalTime);
		Page.lfu_ave_time_label.setText(avrTime);
		Page.lfu_miss_page_label.setText(missPage);
		// System.out.println("lfu"+s1);
		// System.out.println("lfu"+lfu_total_time);
		// System.out.println("lfu"+lfu_ave_time);
		// System.out.println("lfu"+d + "%");
	}
}
