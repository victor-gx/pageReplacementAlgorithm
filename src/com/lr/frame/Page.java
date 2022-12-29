package com.lr.frame;

import com.lr.algurithm.FIFO;
import com.lr.algurithm.LFU;
import com.lr.algurithm.LRU;
import com.lr.algurithm.OPT;
import com.lr.algurithm.config;
import com.lr.db.History;
import com.lr.db.Save;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class Page {
	public JFrame frame;
	private JPanel panel;
	public static TextArea FIFOtest;
	public static TextArea LRUtest;
	public static TextArea LFUtest;
	public static TextArea OPTtest;
	public static JTextField task_edit;
	public static JTextField memory_num_edit;
	public static JTextField taskSize_edit;
	public static JTextField taskSize_low_edit;
	public static JTextField taskSize_high_edit;
	public static JTextField task_low_edit;
	public static JTextField task_high_edit;
	public static JCheckBox quick_table_check;
	public static JTextField memory_access_edit;
	public static JTextField quick_table_access_edit;
	public static JTextField miss_page_interrupt_edit;
	public static JTextField run_time_edit;
	public static JLabel fifo_interrupt_label;
	public static JLabel fifo_miss_page_label;
	public static JLabel fifo_total_time_label;
	public static JLabel fifo_ave_time_label;
	public static JLabel lru_interrupt_label;
	public static JLabel lru_miss_page_label;
	public static JLabel lru_total_time_label;
	public static JLabel lru_ave_time_label;
	public static JLabel lfu_interrupt_label;
	public static JLabel lfu_miss_page_label;
	public static JLabel lfu_total_time_label;
	public static JLabel lfu_ave_time_label;
	public static JLabel opt_interrupt_label;
	public static JLabel opt_miss_page_label;
	public static JLabel opt_total_time_label;
	public static JLabel opt_ave_time_label;
	public static JTextField quick_table_num_edit;

	FIFO fifo = new FIFO();
	LRU lru = new LRU();
	LFU lfu = new LFU();
	OPT opt = new OPT();


	public Page() {
		frame = new JFrame();
		frame.setTitle("页面置换算法");
		//获取与系统相关的默认工具类对象
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		//获取屏幕分辨率
		Dimension d = toolkit.getScreenSize();
		frame.setBounds((int)(d.getWidth() - 1185) / 2,(int)(d.getHeight() - 895) / 2,
				1185, 895);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // 退出应用程序默认窗口关闭
		frame.getContentPane().setLayout(null);
		// frame.setVisible(true);

		panel = new JPanel();
		panel.setBounds(10, 10, 1165, 890);
		frame.getContentPane().add(panel);
		panel.setLayout(null);


		JLabel label_1 = new JLabel("内存块数:");
		label_1.setBounds(35, 20, 100, 20);
		label_1.setFont(new Font("宋体", Font.PLAIN, 20));
		// frame.getContentPane().add(label_1);
		panel.add(label_1);

		memory_num_edit = new JTextField();
		memory_num_edit.setBounds(145, 20, 180, 20);
		// frame.getContentPane().add(memory_num_edit);
		panel.add(memory_num_edit);
		// memory_num_edit.setColumns(10);

		JLabel label_2 = new JLabel("页面数量：");
		label_2.setBounds(35, 60, 100, 20);
		label_2.setFont(new Font("宋体", Font.PLAIN, 20));
		// frame.getContentPane().add(label_2);
		panel.add(label_2);

		taskSize_edit = new JTextField();
		taskSize_edit.setBounds(145, 60, 180, 20);
		// frame.getContentPane().add(taskSize_edit);
		panel.add(taskSize_edit);

		taskSize_low_edit = new JTextField();
		taskSize_low_edit.setBounds(480, 60, 40, 20);
		// frame.getContentPane().add(taskSize_low_edit);
		panel.add(taskSize_low_edit);

		JLabel label_32 = new JLabel("-");
		label_32.setBounds(525, 60, 10, 20);
		label_32.setFont(new Font("宋体", Font.PLAIN, 20));
		// frame.getContentPane().add(label_32);
		panel.add(label_32);

		taskSize_high_edit = new JTextField();
		taskSize_high_edit.setBounds(540, 60, 40, 20);
		// frame.getContentPane().add(taskSize_high_edit);
		panel.add(taskSize_high_edit);

		JButton taskSize_btn = new JButton("随机生成");
		taskSize_btn.setBounds(350, 55, 120, 30);
		taskSize_btn.setFont(new Font("宋体", Font.PLAIN, 20));
		// frame.getContentPane().add(taskSize_btn);
		panel.add(taskSize_btn);
		taskSize_btn.addActionListener(e -> {
			if (!taskSize_low_edit.getText().isEmpty()) {
				config.page_low = Integer.parseInt(taskSize_low_edit.getText());
			} else {
				config.page_low = 1;
			}
			if (!taskSize_high_edit.getText().isEmpty())
				config.page_high = Integer.parseInt(taskSize_high_edit.getText());
			else
				config.page_high = 9;
			// int r = (int) Math.random();
			config.taskSize = (int) (config.page_low + Math.random() * (config.page_high - config.page_low + 1));
			taskSize_edit.setText(String.valueOf(config.taskSize));
		});

		JLabel label_3 = new JLabel("页面序号：");
		label_3.setBounds(35, 100, 100, 20);
		label_3.setFont(new Font("宋体", Font.PLAIN, 20));
		// frame.getContentPane().add(label_3);
		panel.add(label_3);

		task_low_edit = new JTextField();
		task_low_edit.setBounds(480, 100, 40, 20);
		// frame.getContentPane().add(task_low_edit);
		panel.add(task_low_edit);

		JLabel label_33 = new JLabel("-");
		label_33.setBounds(525, 100, 10, 20);
		label_33.setFont(new Font("宋体", Font.PLAIN, 20));
		// frame.getContentPane().add(label_33);
		panel.add(label_33);

		task_high_edit = new JTextField();
		task_high_edit.setBounds(540, 100, 40, 20);
		// frame.getContentPane().add(task_high_edit);
		panel.add(task_high_edit);

		task_edit = new JTextField();
		task_edit.setBounds(145, 100, 180, 20);
		// frame.getContentPane().add(task_edit);
		panel.add(task_edit);

		JButton task_btn = new JButton("随机生成");
		task_btn.setBounds(350, 95, 120, 30);
		task_btn.setFont(new Font("宋体", Font.PLAIN, 20));
		// frame.getContentPane().add(task_btn);
		panel.add(task_btn);
		task_btn.addActionListener(e -> {
			if (!task_low_edit.getText().isEmpty()) {
				config.page_list_low = Integer.parseInt(task_low_edit.getText());
			} else {
				config.page_list_low = 1;
			}
			if (!task_high_edit.getText().isEmpty())
				config.page_list_high = Integer.parseInt(task_high_edit.getText());
			else
				config.page_list_high = 9;
			config.taskSize = Integer.parseInt(taskSize_edit.getText());
			String list = "";
			config.task = new int[config.taskSize];
			int i = 0;
			for (; i < config.taskSize - 1; i ++ ) {
				config.task[i] = (int) (config.page_list_low + Math.random() * (config.page_list_high - config.page_list_low + 1));
				list += config.task[i] + ",";
			}
			config.task[i] = (int) (config.page_list_low + Math.random() * (config.page_list_high - config.page_list_low + 1));
			list += config.task[i];
			task_edit.setText(list);
		});

		JLabel label_15 = new JLabel("快表大小：");
		label_15.setBounds(35, 145, 100, 20);
		label_15.setFont(new Font("宋体", Font.PLAIN, 20));
		// frame.getContentPane().add(label_15);
		panel.add(label_15);

		quick_table_num_edit = new JTextField();
		quick_table_num_edit.setBounds(145, 145, 180, 20);
		// frame.getContentPane().add(quick_table_num_edit);
		panel.add(quick_table_num_edit);
		quick_table_num_edit.addActionListener(e -> {
			config.quick_table_num = Integer.parseInt(quick_table_num_edit.getText());
		});
		quick_table_num_edit.setEnabled(false);

		quick_table_check = new JCheckBox("启用快表");
		quick_table_check.setBounds(380, 145, 130, 30);
		quick_table_check.setFont(new Font("宋体", Font.PLAIN, 20));
		// frame.getContentPane().add(quick_table_check);
		panel.add(quick_table_check);
		quick_table_check.addActionListener(e -> {
			on_quick_table_check_clicked(quick_table_check.isSelected());
		});


		JLabel label_4 = new JLabel("内存存取时间：");
		label_4.setBounds(700, 20, 140, 20);
		label_4.setFont(new Font("宋体", Font.PLAIN, 20));
		// frame.getContentPane().add(label_4);
		panel.add(label_4);

		memory_access_edit = new JTextField();
		memory_access_edit.setBounds(845, 20, 50, 20);
		// frame.getContentPane().add(memory_access_edit);
		panel.add(memory_access_edit);

		JLabel label_34 = new JLabel("ms");
		label_34.setBounds(900, 20, 20, 20);
		label_34.setFont(new Font("宋体", Font.PLAIN, 20));
		// frame.getContentPane().add(label_34);
		panel.add(label_34);

		JLabel label_6 = new JLabel("快表存取时间：");
		label_6.setBounds(700, 60, 140, 20);
		label_6.setFont(new Font("宋体", Font.PLAIN, 20));
		// frame.getContentPane().add(label_6);
		panel.add(label_6);

		quick_table_access_edit = new JTextField();
		quick_table_access_edit.setBounds(845, 60, 50, 20);
		// frame.getContentPane().add(quick_table_access_edit);
		panel.add(quick_table_access_edit);

		JLabel label_35 = new JLabel("ms");
		label_35.setBounds(900, 60, 20, 20);
		label_35.setFont(new Font("宋体", Font.PLAIN, 20));
		// frame.getContentPane().add(label_35);
		panel.add(label_35);

		JLabel label_5 = new JLabel("缺页中断时间：");
		label_5.setBounds(700, 100, 140, 20);
		label_5.setFont(new Font("宋体", Font.PLAIN, 20));
		// frame.getContentPane().add(label_5);
		panel.add(label_5);

		miss_page_interrupt_edit = new JTextField();
		miss_page_interrupt_edit.setBounds(845, 100, 50, 20);
		// frame.getContentPane().add(miss_page_interrupt_edit);
		panel.add(miss_page_interrupt_edit);

		JLabel label_36 = new JLabel("ms");
		label_36.setBounds(900, 100, 20, 20);
		label_36.setFont(new Font("宋体", Font.PLAIN, 20));
		// frame.getContentPane().add(label_36);
		panel.add(label_36);

		JLabel label_16 = new JLabel("执行时间控制：");
		label_16.setBounds(700, 140, 140, 20);
		label_16.setFont(new Font("宋体", Font.PLAIN, 20));
		// frame.getContentPane().add(label_16);
		panel.add(label_16);

		run_time_edit = new JTextField();
		run_time_edit.setBounds(845, 140, 50, 20);
		// frame.getContentPane().add(run_time_edit);
		panel.add(run_time_edit);

		JLabel label_17 = new JLabel("ms");
		label_17.setBounds(900, 140, 20, 20);
		label_17.setFont(new Font("宋体", Font.PLAIN, 20));
		// frame.getContentPane().add(label_17);
		panel.add(label_17);

		JButton start_btn = new JButton("开始执行");
		start_btn.setBounds(200, 185, 120, 30);
		start_btn.setFont(new Font("宋体", Font.PLAIN, 20));
		// frame.getContentPane().add(start_btn);
		panel.add(start_btn);
		start_btn.addActionListener(e -> {
			on_clear_btn_clicked();
			config.memory_num = Integer.parseInt(memory_num_edit.getText());
			if (quick_table_check.isSelected()) {
				config.quick_table_num = Integer.parseInt(quick_table_num_edit.getText());
				if (quick_table_check.isSelected()  && config.quick_table_num >= config.memory_num) {
					JOptionPane.showMessageDialog(null, "快表大小应该小于内存大小", "warning", JOptionPane.ERROR_MESSAGE);
					return;
				}
			}

			config.memory_access = Double.parseDouble(memory_access_edit.getText());
			config.quick_table_access = Double.parseDouble(quick_table_access_edit.getText());
			config.miss_page_interrupt = Double.parseDouble(miss_page_interrupt_edit.getText());
			config.is_check = Boolean.parseBoolean(String.valueOf(quick_table_check.isSelected()));
			String str = task_edit.getText();
			List<String> lst = Arrays.asList(str.split(","));
			if (run_time_edit.getText().isEmpty()) {
				run_time_edit.setText("300");
			}
			config.run_time = Integer.parseInt(run_time_edit.getText());
			config.taskSize = lst.size();
			config.task = new int[config.taskSize];
			for (int i = 0; i < config.taskSize; ++ i) {
				str = lst.get(i);
				config.task[i] = Integer.parseInt(str);
			}
			new Thread(() -> fifo.run()).start();
			new Thread(() -> lru.run()).start();
			new Thread(() -> lfu.run()).start();
			new Thread(() -> opt.run()).start();

		});

		JButton stop_continue_btn = new JButton("暂停执行");
		stop_continue_btn.setBounds(500, 185, 120, 30);
		stop_continue_btn.setFont(new Font("宋体", Font.PLAIN, 20));
		// frame.getContentPane().add(stop_continue_btn);
		panel.add(stop_continue_btn);
		stop_continue_btn.addActionListener(e -> {
			if (stop_continue_btn.getText() == "暂停执行") {
				fifo.pause.lock();
				lru.pause.lock();
				lfu.pause.lock();
				opt.pause.lock();
				stop_continue_btn.setText("继续执行");
			} else {
				fifo.pause.unlock();
				lru.pause.unlock();
				lfu.pause.unlock();
				opt.pause.unlock();
				stop_continue_btn.setText("暂停执行");
			}
		});

		JButton stop_btn = new JButton("结束执行");
		stop_btn.setBounds(800, 185, 120, 30);
		stop_btn.setFont(new Font("宋体", Font.PLAIN, 20));
		// frame.getContentPane().add(stop_btn);
		panel.add(stop_btn);
		stop_btn.addActionListener(e -> {
			System.exit(0);
		});

		// FIFO
		JLabel lblFifo = new JLabel("FIFO");
		title(lblFifo, 20, 250, 230, 50, 15);

		FIFOtest = new TextArea();
		textBox(FIFOtest, 20, 180, 250, 200, 250);

		// JScrollPane scroll_fifo = new JScrollPane(FIFOtest);
		// scroll(scroll_fifo, 180, 250, 200, 250);

		// LRU
		JLabel lblLru = new JLabel("LRU");
		title(lblLru, 20, 490, 230, 50, 15);

		LRUtest = new TextArea();
		textBox(LRUtest, 20, 420, 250, 200, 250);

		// JScrollPane scroll_lru = new JScrollPane(LRUtest);
		// scroll(scroll_lru, 420, 250, 200, 250);

		// LFU
		JLabel lblLfu = new JLabel("LFU");
		title(lblLfu, 20, 730, 230, 50, 15);

		LFUtest = new TextArea();
		textBox(LFUtest, 20, 660, 250, 200, 250);

		// JScrollPane scroll_lfu = new JScrollPane(LFUtest);
		// scroll(scroll_lfu, 660, 250, 200, 250);

		//OPT
		JLabel lblOpt = new JLabel("OPT");
		title(lblOpt, 20, 970, 230, 50, 15);

		OPTtest = new TextArea();
		textBox(OPTtest, 20, 900, 250, 200, 250);

		// JScrollPane scroll_opt= new JScrollPane(OPTtest);
		// scroll(scroll_opt, 900, 250, 200, 250);

		JLabel label_11 = new JLabel("中断次数：");
		label_11.setBounds(35, 550, 100, 20);
		label_11.setFont(new Font("宋体", Font.PLAIN, 20));
		// frame.getContentPane().add(label_11);
		panel.add(label_11);

		fifo_interrupt_label = new JLabel("");
		fifo_interrupt_label.setBounds(220, 550, 100, 20);
		fifo_interrupt_label.setFont(new Font("宋体", Font.PLAIN, 20));
		// frame.getContentPane().add(fifo_interrupt_label);
		panel.add(fifo_interrupt_label);

		lru_interrupt_label = new JLabel("");
		lru_interrupt_label.setBounds(460, 550, 100, 20);
		lru_interrupt_label.setFont(new Font("宋体", Font.PLAIN, 20));
		// frame.getContentPane().add(lru_interrupt_label);
		panel.add(lru_interrupt_label);

		lfu_interrupt_label = new JLabel("");
		lfu_interrupt_label.setBounds(700, 550, 100, 20);
		lfu_interrupt_label.setFont(new Font("宋体", Font.PLAIN, 20));
		// frame.getContentPane().add(lfu_interrupt_label);
		panel.add(lfu_interrupt_label);

		opt_interrupt_label = new JLabel("");
		opt_interrupt_label.setBounds(940, 550, 100, 20);
		opt_interrupt_label.setFont(new Font("宋体", Font.PLAIN, 20));
		// frame.getContentPane().add(opt_interrupt_label);
		panel.add(opt_interrupt_label);

		JLabel label_12 = new JLabel("总时间：");
		label_12.setBounds(35, 600, 100, 20);
		label_12.setFont(new Font("宋体", Font.PLAIN, 20));
		// frame.getContentPane().add(label_12);
		panel.add(label_12);

		fifo_total_time_label = new JLabel("");
		fifo_total_time_label.setBounds(220, 600, 100, 20);
		fifo_total_time_label.setFont(new Font("宋体", Font.PLAIN, 20));
		// frame.getContentPane().add(fifo_total_time_label);
		panel.add(fifo_total_time_label);

		lru_total_time_label = new JLabel("");
		lru_total_time_label.setBounds(460, 600, 100, 20);
		lru_total_time_label.setFont(new Font("宋体", Font.PLAIN, 20));
		// frame.getContentPane().add(lru_total_time_label);
		panel.add(lru_total_time_label);

		lfu_total_time_label = new JLabel("");
		lfu_total_time_label.setBounds(700, 600, 100, 20);
		lfu_total_time_label.setFont(new Font("宋体", Font.PLAIN, 20));
		// frame.getContentPane().add(lfu_total_time_label);
		panel.add(lfu_total_time_label);

		opt_total_time_label = new JLabel("");
		opt_total_time_label.setBounds(940, 600, 100, 20);
		opt_total_time_label.setFont(new Font("宋体", Font.PLAIN, 20));
		// frame.getContentPane().add(opt_total_time_label);
		panel.add(opt_total_time_label);

		JLabel label_13 = new JLabel("平均时间：");
		label_13.setBounds(35, 650, 100, 20);
		label_13.setFont(new Font("宋体", Font.PLAIN, 20));
		// frame.getContentPane().add(label_13);
		panel.add(label_13);

		fifo_ave_time_label = new JLabel("");
		fifo_ave_time_label.setBounds(220, 650, 100, 20);
		fifo_ave_time_label.setFont(new Font("宋体", Font.PLAIN, 20));
		// frame.getContentPane().add(fifo_ave_time_label);
		panel.add(fifo_ave_time_label);

		lru_ave_time_label = new JLabel("");
		lru_ave_time_label.setBounds(460, 650, 100, 20);
		lru_ave_time_label.setFont(new Font("宋体", Font.PLAIN, 20));
		// frame.getContentPane().add(lru_ave_time_label);
		panel.add(lru_ave_time_label);

		lfu_ave_time_label = new JLabel("");
		lfu_ave_time_label.setBounds(700, 650, 100, 20);
		lfu_ave_time_label.setFont(new Font("宋体", Font.PLAIN, 20));
		// frame.getContentPane().add(lfu_ave_time_label);
		panel.add(lfu_ave_time_label);

		opt_ave_time_label = new JLabel("");
		opt_ave_time_label.setBounds(940, 650, 100, 20);
		opt_ave_time_label.setFont(new Font("宋体", Font.PLAIN, 20));
		// frame.getContentPane().add(opt_ave_time_label);
		panel.add(opt_ave_time_label);

		JLabel label_14 = new JLabel("缺页率：");
		label_14.setBounds(35, 700, 100, 20);
		label_14.setFont(new Font("宋体", Font.PLAIN, 20));
		// frame.getContentPane().add(label_14);
		panel.add(label_14);

		fifo_miss_page_label = new JLabel("");
		fifo_miss_page_label.setBounds(220, 700, 100, 20);
		fifo_miss_page_label.setFont(new Font("宋体", Font.PLAIN, 20));
		// frame.getContentPane().add(fifo_miss_page_label);
		panel.add(fifo_miss_page_label);

		lru_miss_page_label = new JLabel("");
		lru_miss_page_label.setBounds(460, 700, 100, 20);
		lru_miss_page_label.setFont(new Font("宋体", Font.PLAIN, 20));
		// frame.getContentPane().add(lru_miss_page_label);
		panel.add(lru_miss_page_label);

		lfu_miss_page_label = new JLabel("");
		lfu_miss_page_label.setBounds(700, 700, 100, 20);
		lfu_miss_page_label.setFont(new Font("宋体", Font.PLAIN, 20));
		// frame.getContentPane().add(lfu_miss_page_label);
		panel.add(lfu_miss_page_label);

		opt_miss_page_label = new JLabel("");
		opt_miss_page_label.setBounds(940, 700, 100, 20);
		opt_miss_page_label.setFont(new Font("宋体", Font.PLAIN, 20));
		// frame.getContentPane().add(opt_miss_page_label);
		panel.add(opt_miss_page_label);

		JButton save_btn = new JButton("保存");
		save_btn.setBounds(200, 760, 120, 30);
		save_btn.setFont(new Font("宋体", Font.PLAIN, 20));
		// frame.getContentPane().add(save_btn);
		panel.add(save_btn);
		save_btn.addActionListener(e -> {
			new Save();
		});

		JButton open_btn = new JButton("打开");
		open_btn.setBounds(500, 760, 120, 30);
		open_btn.setFont(new Font("宋体", Font.PLAIN, 20));
		// frame.getContentPane().add(open_btn);
		panel.add(open_btn);
		open_btn.addActionListener(e -> {
			new History();
		});

		JButton clear_btn = new JButton("清空");
		clear_btn.setBounds(800, 760, 120, 30);
		clear_btn.setFont(new Font("宋体", Font.PLAIN, 20));
		// frame.getContentPane().add(clear_btn);
		panel.add(clear_btn);
		clear_btn.addActionListener(e->{
			on_clear_btn_clicked();
			clear_parameter();
		});
	}

	/**
	 * 四种算法标题
	 * @param label
	 * @param size
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 */
	public void title(JLabel label, int size, int x, int y ,int w, int h) {
		label.setFont(new Font("宋体", Font.PLAIN, size));
		label.setBounds(x, y, w, h);
		// frame.getContentPane().add(label);
		panel.add(label);
	}

	/**
	 * 四种算法运行结果显示框
	 * @param textArea
	 * @param size
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 */
	public void textBox(TextArea textArea, int size, int x, int y ,int w, int h) {
		textArea.setFont(new Font("宋体", Font.PLAIN, size));
		textArea.setBackground(SystemColor.controlHighlight);
		textArea.setBounds(x, y, w, h);
		// frame.getContentPane().add(textArea);
		panel.add(textArea);
	}

	/**
	 * 分别设置水平和垂直滚动条自动出现
	 * @param scrollPane
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 */
	public void scroll(JScrollPane scrollPane,int x, int y, int w, int h) {
		scrollPane.setLocation(x, y);
		scrollPane.setSize(w, h);

		scrollPane.setHorizontalScrollBarPolicy(
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setVerticalScrollBarPolicy(
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		// frame.getContentPane().add(scrollPane);
		panel.add(scrollPane);
	}

	public void on_clear_btn_clicked() {
		// fifo.task[] = new int[fifo.taskSize];
		final int[] task = config.task;
		FIFOtest.setText("");
		fifo.fifo_lackpage = 0;
		fifo.fifo_ave_time=0;
		fifo.fifo_total_time=0;
		fifo.fifo_memory_page.clear();
		fifo.fifo_quick_table_page.clear();
		fifo.fifo_memory_page.clear();
		fifo_interrupt_label.setText("");
		fifo_miss_page_label.setText("");
		fifo_total_time_label.setText("");
		fifo_ave_time_label.setText("");
		LRUtest.setText("");
		lru.lru_lackpage = 0;
		lru.lru_ave_time=0;
		lru.lru_total_time=0;
		lru.lru_memory_page.clear();
		lru.lru_quick_table_page.clear();
		lru_interrupt_label.setText("");
		lru_miss_page_label.setText("");
		lru_total_time_label.setText("");
		lru_ave_time_label.setText("");
		LFUtest.setText("");
		lfu.lfu_lackpage = 0;
		lfu.lfu_ave_time=0;
		lfu.lfu_total_time=0;
		lfu.lfu_memory_page.clear();
		lfu.lfu_quick_table_page.clear();
		lfu_interrupt_label.setText("");
		lfu_miss_page_label.setText("");
		lfu_total_time_label.setText("");
		lfu_ave_time_label.setText("");
		OPTtest.setText("");
		opt.opt_lackpage = 0;
		opt.opt_ave_time=0;
		opt.opt_total_time=0;
		opt.opt_memory_page.clear();
		opt.opt_quick_table_page.clear();
		opt_interrupt_label.setText("");
		opt_miss_page_label.setText("");
		opt_total_time_label.setText("");
		opt_ave_time_label.setText("");

	}

	public static void on_quick_table_check_clicked(boolean checked) {
		if (checked)
			quick_table_num_edit.setEnabled(true);
		else
			quick_table_num_edit.setEnabled(false);
	}

	public static void clear_parameter() {
		memory_num_edit.setText("");
		taskSize_edit.setText("");
		taskSize_low_edit.setText("");
		taskSize_high_edit.setText("");
		task_low_edit.setText("");
		task_high_edit.setText("");
		task_edit.setText("");
		quick_table_num_edit.setText("");
		memory_access_edit.setText("");
		quick_table_access_edit.setText("");
		miss_page_interrupt_edit.setText("");
		run_time_edit.setText("");
		quick_table_check.setSelected(false);
		quick_table_num_edit.setEnabled(false);
	}

	public static void main(String[] args) {
		Page window = new Page();
		window.frame.setVisible(true);
	}
}
