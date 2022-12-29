package com.lr.db;

import java.sql.*;

import com.lr.frame.Page;
import com.lr.algurithm.config;

import javax.swing.*;

public class Save {

	private static String url = null;
	private static String username = null;
	private static String password = null;

	private static Connection conn = null;
	private static Statement st = null;

	public Save() {
		try {
			saveRecord();
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (st != null) {
				try {
					st.close();
				} catch (SQLException throwables) {
					throwables.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException throwables) {
					throwables.printStackTrace();
				}
			}
		}
	}


	public void saveRecord() throws SQLException, ClassNotFoundException {
		//1.加载驱动
		//DriverManager.registerDriver(new com.mysql.jdbc.Driver());
		Class.forName("com.mysql.jdbc.Driver");//固定写法

		//2.用户信息和
		url = "jdbc:mysql://127.0.0.1:3306/os?useUnicode=true&characterEncoding=utf8&useSSL=true";
		username = "root";
		password = "123456";

		//3.连接成功，数据库对象  Connection  代表数据库
		conn = DriverManager.getConnection(url, username, password);
		//4.获得SQL的执行对象
		st = conn.createStatement();

		String sql = "INSERT INTO test(memory_n,page_n,page_list,memory_t,quick_table_t," +
				"interrupt_t,is_check,fifo_total_t,fifo_ave_t,fifo_miss_page,fifo_interrupt_n," +
				"fifo_text,lru_total_t,lru_ave_t,lru_miss_page,lru_interrupt_n,lru_text," +
				"lfu_total_t,lfu_ave_t,lfu_miss_page,lfu_interrupt_n,lfu_text,opt_total_t," +
				"opt_ave_t,opt_miss_page,opt_interrupt_n,opt_text)VALUES('" + config.memory_num + "','" +
				config.taskSize + "','" + Page.task_edit.getText() + "','" + config.memory_access + "','" +
				config.quick_table_access + "','" + config.miss_page_interrupt + "','" +
				config.is_check + "','" + Page.fifo_total_time_label.getText() +  "','" +
				Page.fifo_ave_time_label.getText() +  "','" + Page.fifo_miss_page_label.getText() +  "','" +
				Page.fifo_interrupt_label.getText() +  "','" + Page.FIFOtest.getText() + "','" +
				Page.lru_total_time_label.getText() +  "','" + Page.lru_ave_time_label.getText() +  "','" +
				Page.lru_miss_page_label.getText() +  "','" + Page.lru_interrupt_label.getText() +  "','" +
				Page.LRUtest.getText() + "','" + Page.lfu_total_time_label.getText() +  "','" +
				Page.lfu_ave_time_label.getText() +  "','" + Page.lfu_miss_page_label.getText() +  "','" +
				Page.lfu_interrupt_label.getText() +  "','" + Page.LFUtest.getText() + "','" +
				Page.opt_total_time_label.getText() +  "','" + Page.opt_ave_time_label.getText() +  "','" +
				Page.opt_miss_page_label.getText() +  "','" + Page.opt_interrupt_label.getText() +  "','" +
				Page.OPTtest.getText() + "')";
		int i = st.executeUpdate(sql);
		if (i > 0) {
			JOptionPane.showMessageDialog(null, "保存成功", "提交信息", 1);
		} else {
			JOptionPane.showMessageDialog(null, "保存失败", "warning", JOptionPane.ERROR_MESSAGE);
		}

	}

	public static void main(String[] args) {
		new Save();
	}
}
