package com.frame.easyandroid.db.dao.impl;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.frame.easyandroid.db.dao.DAO;
import com.frame.easyandroid.db.dao.DBHelper;
import com.frame.easyandroid.db.dao.aonntation.Column;
import com.frame.easyandroid.db.dao.aonntation.ID;
import com.frame.easyandroid.db.dao.aonntation.Table;

/**
 * 数据库的基本实现类
 * @author liuzhao
 *
 * @param <T>
 */
@SuppressWarnings("unused")
public abstract class DAOImpl<T> implements DAO<T> {
	private static final String TAG = "DAOImpl";
	protected DBHelper dbHelper;
	protected SQLiteDatabase db;

	public DAOImpl(Context context) {
		dbHelper = new DBHelper(context);
		db = dbHelper.getWritableDatabase();
	}

	@Override
	public int delete(Serializable id) {
		// 问题一：获取到表名
		// TABLE_ID_COL
		db.delete(getTableName(), DBHelper.TABLE_ID_COL + "=?",
				new String[] { id.toString() });
		return 0;
	}

	@Override
	public long insert(T t) {
		// 问题一：需要明确实体t（封装的信息）对应是数据库某张表中那个字段
		// 问题二：获取到表名

		ContentValues values = new ContentValues();
		// 将t里封装的信息设置给ContentValues
		fillContentValues(t, values);

		long insert = db.insert(getTableName(), null, values);
		return insert;
	}

	/**
	 * 将t里封装的信息设置给ContentValues
	 * 
	 * @param t
	 * @param values
	 */
	private void fillContentValues(T t, ContentValues values) {
		// values.put(DBHelper.TABLE_NEW_TITLE_COL, news.getTitle());

		Field[] declaredFields = t.getClass().getDeclaredFields();

		for (Field item : declaredFields) {
			item.setAccessible(true);
			Column column = item.getAnnotation(Column.class);
			if (column != null) {
				String key = column.value();
				// This reproduces the effect of object.fieldName
				String value = "";
				try {
					value = item.get(t).toString();// 执行结果：news.title
				} catch (Exception e) {
					e.printStackTrace();
				}

				ID id = item.getAnnotation(ID.class);
				if (id != null) {
					// 主键
					if (id.autoIncreament()) {
						// 主键是自增，才不设置它的值
					} else {
						values.put(key, value);
					}

				} else {
					// 非主键
					values.put(key, value);

				}
			}
		}

	}

	@Override
	public List<T> queryAll() {
		// 问题一：获取到表名
		// 问题二：需要明确实体t（封装的信息）对应是数据库某张表中那个字段
		// 问题三：解决T的实例的创建
		Cursor query = null;
		List<T> list = new ArrayList<T>();
		try {
			query = db
					.query(getTableName(), null, null, null, null, null, null);

			while (query.moveToNext()) {

				// query里面封装是当前行所有信息
				/**
				 * int columnIndex =
				 * query.getColumnIndex(DBHelper.TABLE_NEW_TITLE_COL); String
				 * title = query.getString(columnIndex);
				 */
				T t = getInstence();
				fillInstance(query, t);
				list.add(t);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (query != null)
				query.close();
		}
		return list;
	}

	/**
	 * 从数据库中保存信息到具体实例信息的封装
	 * 
	 * @param query
	 * @param t
	 */
	private void fillInstance(Cursor query, T t) {
		/**
		 * int columnIndex = query.getColumnIndex(DBHelper.TABLE_NEW_TITLE_COL);
		 * String title = query.getString(columnIndex);
		 */
		Field[] fields = t.getClass().getDeclaredFields();

		for (Field item : fields) {
			String key = "";

			Column column = item.getAnnotation(Column.class);
			if (column != null) {
				key = column.value();
				int columnIndex = query.getColumnIndex(key);
				String value = query.getString(columnIndex);
				try {
					item.set(t, value);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}

	}

	@Override
	public int update(T t) {
		// 问题一：需要明确实体t（封装的信息）对应是数据库某张表中那个字段
		// 问题二：获取表名
		// 问题三：获取到t实例里封装的主键的信息
		ContentValues values = new ContentValues();
		fillContentValues(t, values);

		db.update(getTableName(), values, DBHelper.TABLE_ID_COL + "=?",
				new String[] { getId(t) });

		return 0;
	}

	/**
	 * 获取实体里封装的主键信息
	 * 
	 * @param t
	 * @return
	 */
	private String getId(T t) {
		Field[] fields = t.getClass().getDeclaredFields();

		for (Field item : fields) {
			ID id = item.getAnnotation(ID.class);
			if (id != null) {
				try {
					item.setAccessible(true);
					String value = item.get(t) + "";
					return value;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	/**
	 * 获取表名
	 * 
	 * @return
	 */
	private String getTableName() {
		T t = getInstence();
		Table table = t.getClass().getAnnotation(Table.class);
		if (table != null) {
			return table.value();
		}
		return null;
	}

	// 明确实体与表的列的对应关系

	/**
	 * 创建T的实例
	 * 
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public T getInstence() {
		// 问题：获取子类，拿到它的父类，获取到泛型里传递的信息
		// 获取子类
		Type superclass = this.getClass().getGenericSuperclass();

		ParameterizedType pt = (ParameterizedType) superclass;
		Type[] actualTypeArguments = pt.getActualTypeArguments();
		Type type = actualTypeArguments[0];

		try {
			return (T) ((Class) type).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Log.i(TAG, super.getClass().getSimpleName());
		return null;
	}

}
