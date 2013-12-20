package com.frame.easyandroid.db.dao.impl;

import android.content.Context;

import com.frame.easyandroid.bean.Book;
import com.frame.easyandroid.db.dao.BookDao;

public class BookDaoImpl extends DAOImpl<Book> implements BookDao {

	public BookDaoImpl(Context context) {
		super(context);
	}

}
