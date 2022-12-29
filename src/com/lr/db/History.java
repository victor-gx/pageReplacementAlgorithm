package com.lr.db;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class History {
	private static String url=null;
	private static String username=null;
	private static String password=null;
	private JPanel panel;
	private JFrame frame;

	public History() {
		frame = new JFrame("历史记录");
		// frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // 退出应用程序默认窗口关闭
		// //获取与系统相关的默认工具类对象
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		//获取屏幕分辨率
		Dimension d = toolkit.getScreenSize();
		frame.setBounds((int)(d.getWidth() - 1000) / 2,(int)(d.getHeight() - 850) / 2,
				1000, 850);
		frame.setVisible(true);

		try {
			historyRecord();
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
			JOptionPane.showMessageDialog(null, "数据源错误", "错误", JOptionPane.ERROR_MESSAGE);
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			JOptionPane.showMessageDialog(null, "数据操作错误", "错误", JOptionPane.ERROR_MESSAGE);
		}


	}

	public void historyRecord() throws SQLException, ClassNotFoundException {
		//1.加载驱动
		//DriverManager.registerDriver(new com.mysql.jdbc.Driver());
		Class.forName("com.mysql.jdbc.Driver");//固定写法

		//2.用户信息和
		url="jdbc:mysql://127.0.0.1:3306/os?useUnicode=true&characterEncoding=utf8&useSSL=true";
		username="root";
		password="123456";

		//3.连接成功，数据库对象  Connection  代表数据库
		Connection conn=DriverManager.getConnection(url,username,password);

		//4.执行SQL的对象，去执行SQL，可能存在结果，返回结果信息
		String sql = "select * from test";
		PreparedStatement pstm = conn.prepareStatement(sql);
		ResultSet rs = pstm.executeQuery();
		// ResultSet rs=statement.executeQuery(sql);
		int count = 0;
		while (rs.next()) {
			count++;
		}
		rs = pstm.executeQuery();

		// 将查询获得的记录数据，转换成适合生成JTable的数据形式
		Object[][] info = new Object[count][27];

		Object[] title = {"内存块数", "页面大小", " 页号序列", "内存存取时间", "快表存取时间",
				"中断执行时间", "是否勾选快表", "FIFO总时间", "FIFO平均时间", "FIFO缺页率",
				"FIFO中断次数", "FIFO执行过程", "LRU总时间", "LRU平均时间", "LRU缺页率",
				"LRU中断次数", "LRU执行过程", "LFU总时间", "LFU平均时间", "LFU缺页率",
				"LFU中断次数", "LFU执行过程", "OPT总时间", "OPT平均时间", "OPT缺页率",
				"OPT中断次数", "OPT执行过程"};

		count = 0;
		while (rs.next()) {
			// info[count][0] = rs.getObject("id");
			info[count][0] = rs.getObject("memory_n");
			info[count][1] = rs.getObject("page_n");
			info[count][2] = rs.getObject("page_list");
			info[count][3] = rs.getObject("memory_t");
			info[count][4] = rs.getObject("quick_table_t");
			info[count][5] = rs.getObject("interrupt_t");
			info[count][6] = rs.getObject("is_check");
			info[count][7] = rs.getObject("fifo_total_t");
			info[count][8] = rs.getObject("fifo_ave_t");
			info[count][9] = rs.getObject("fifo_miss_page");
			info[count][10] = rs.getObject("fifo_interrupt_n");
			info[count][11] = rs.getObject("fifo_text");
			info[count][12] = rs.getObject("lru_total_t");
			info[count][13] = rs.getObject("lru_ave_t");
			info[count][14] = rs.getObject("lru_miss_page");
			info[count][15] = rs.getObject("lru_interrupt_n");
			info[count][16] = rs.getObject("lru_text");
			info[count][17] = rs.getObject("lfu_total_t");
			info[count][18] = rs.getObject("lfu_ave_t");
			info[count][19] = rs.getObject("lfu_miss_page");
			info[count][20] = rs.getObject("lfu_interrupt_n");
			info[count][21] = rs.getObject("lfu_text");
			info[count][22] = rs.getObject("opt_total_t");
			info[count][23] = rs.getObject("opt_ave_t");
			info[count][24] = rs.getObject("opt_miss_page");
			info[count][25] = rs.getObject("opt_interrupt_n");
			info[count][26] = rs.getObject("opt_text");
			count++;
		}

		JTable table = new JTable(info, title);

		// 设置表格内容颜色
		table.setForeground(Color.BLACK);                   // 字体颜色
		table.setFont(new Font(null, Font.PLAIN, 14));      // 字体样式
		table.setSelectionForeground(Color.DARK_GRAY);      // 选中后字体颜色
		table.setSelectionBackground(Color.LIGHT_GRAY);     // 选中后字体背景
		table.setGridColor(Color.GRAY);                     // 网格颜色

		// 设置表头
		table.getTableHeader().setFont(new Font(null, Font.BOLD, 14));  // 设置表头名称字体样式
		table.getTableHeader().setForeground(Color.RED);                // 设置表头名称字体颜色
		table.getTableHeader().setReorderingAllowed(false);             // 设置不允许拖动重新排序各列

		// 设置行高
		table.setRowHeight(30);

		// 第一列列宽设置为40
		table.getColumnModel().getColumn(0).setPreferredWidth(40);

		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		JScrollPane scroll = new JScrollPane(table);

		frame.add(scroll);
		frame.setVisible(true);
		frame.pack();
	}

	public static void main(String[] args) {
		new History();
	}



}
