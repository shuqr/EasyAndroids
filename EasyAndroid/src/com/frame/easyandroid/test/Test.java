package com.frame.easyandroid.test;

import java.util.List;

import android.test.AndroidTestCase;

import com.frame.easyandroid.bean.Book;
import com.frame.easyandroid.db.dao.BookDao;
import com.frame.easyandroid.db.dao.impl.BookDaoImpl;
import com.frame.easyandroid.util.Logger;

/**
 * 测试框架，ManiFest中已经注册，直接使用
 * 此处测试的是数据库的操作！
 * @author liuzhao
 * 
 */
public class Test extends AndroidTestCase {

	private static final String tag = "Test";

	public void bookInsert() {
		BookDao dao = new BookDaoImpl(getContext());
		Book book = new Book();
		book.setId(1);
		book.setName("Android 2");
		book.setSummary("这是一本Android方面的很好的教材！");
		dao.insert(book);
	}

	public void bookUpdate() {
		BookDao dao = new BookDaoImpl(getContext());
		Book book = new Book();
		book.setId(1);
		book.setName("Android 2 update");
		dao.update(book);
	}

	public void bookQuery() {
		BookDao dao = new BookDaoImpl(getContext());

		List<Book> books = dao.queryAll();
		Logger.i(tag, books.size()+"");
	}

	public void bookDelete() {
		BookDao dao = new BookDaoImpl(getContext());
		dao.delete(2);
	}
}
