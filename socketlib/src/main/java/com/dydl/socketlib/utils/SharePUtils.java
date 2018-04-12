package com.dydl.socketlib.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build;

/**
 * 注:如果不关心返回 SharedPreferences 的返回值，edit 之后，用 apply()，不要用 commit():
 * http://stackoverflow
 * .com/questions/5960678/whats-the-difference-between-commit-and
 * -apply-in-shared-preference
 *
 * @author YueCK
 */
public class SharePUtils {
	private static Context mContext = null;
	private static SharedPreferences sharedPreferences = null;
	private static Editor editor = null;// 获取编辑器
	private static String name = "SGCONFIG";
	private static boolean useApply;

	/**
	 * 初始化
	 *
	 * @param con
	 */
	public static void init(Context con) {
		try {
			if (mContext == null) {
				mContext = con;
				sharedPreferences = mContext.getSharedPreferences(name,
						Context.MODE_MULTI_PROCESS);
				editor = sharedPreferences.edit();
				useApply = Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static Context getContext() {
		return mContext;
	}

	/**
	 * 是否存在当前的key
	 *
	 * @param key
	 *
	 * @return boolean
	 */
	public static boolean isContains(String key) {
		if (key == null || key.length() <= 0) {
			return false;
		}
		return sharedPreferences.contains(key);
	}

	/**
	 * 保存String
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	public static void putString(String key, String value) {
		editor.putString(key, value);
		commit();
	}

	/**
	 * 保存boolean
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	public static void putBoolean(String key, boolean value) {
		editor.putBoolean(key, value);
		commit();
	}

	/**
	 * 保存int
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	public static void putInt(String key, int value) {
		editor.putInt(key, value);
		commit();
	}

	/**
	 * 保存 long
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	public static void putLong(String key, long value) {
		editor.putLong(key, value);
		commit();
	}

	/**
	 * 读取String
	 *
	 * @param key
	 * @return
	 */
	public static String getString(String key) {
		if (null == sharedPreferences) {
			sharedPreferences = mContext.getSharedPreferences(name,
					Context.MODE_PRIVATE);
		}
		String value = sharedPreferences.getString(key, "");
		return value;
	}

	/**
	 * 读取boolean
	 *
	 * @param key
	 * @return
	 */
	public static boolean getBoolean(String key) {
		if (null == sharedPreferences) {
			sharedPreferences = mContext.getSharedPreferences(name,
					Context.MODE_PRIVATE);
		}
		boolean value = sharedPreferences.getBoolean(key, false);
		return value;
	}

	/**
	 * 读取boolean
	 *
	 * @param key
	 * @return
	 */
	public static boolean getBoolean(String key, boolean def) {
		if (null == sharedPreferences) {
			sharedPreferences = mContext.getSharedPreferences(name,
					Context.MODE_PRIVATE);
		}
		boolean value = sharedPreferences.getBoolean(key, def);
		return value;
	}

	/**
	 * 读取int
	 *
	 * @param key
	 * @return
	 */
	public static int getInt(String key) {
		if (null == sharedPreferences) {
			sharedPreferences = mContext.getSharedPreferences(name,
					Context.MODE_PRIVATE);
		}
		int value = sharedPreferences.getInt(key, 0);
		return value;
	}

	/**
	 * 读取int
	 *
	 * @param key
	 * @return
	 */
	public static int getInt(String key, int defaultValue) {
		if (null == sharedPreferences) {
			sharedPreferences = mContext.getSharedPreferences(name,
					Context.MODE_PRIVATE);
		}
		int value = sharedPreferences.getInt(key, defaultValue);
		return value;
	}

	/**
	 * 读取 long
	 *
	 * @param key
	 * @return
	 */
	public static long getLong(String key, long defaultValue) {
		if (null == sharedPreferences) {
			sharedPreferences = mContext.getSharedPreferences(name,
					Context.MODE_PRIVATE);
		}
		return sharedPreferences.getLong(key, defaultValue);
	}

	/**
	 * 清空所有数据
	 */

	public static void clearAll() {
		if (null != editor) {
			editor.clear();
			commit();
		}
	}

	/**
	 * 存一个值<br>
	 * value的类型仅支持int/Integer,boolean/Boolean,String,long/Long,float/Float
	 *
	 * @param key
	 * @param value
	 */
	public static void write(String key, Object value) {
		if (key == null || value == null) {
			return;
		}
		try {
			writeToEditor(key, value);
			commit();
		} catch (Throwable e) {
		}
	}

	public static void commit() {
		if (useApply) {
			// Since API Level 9, apply() is provided for asynchronous
			// operations
			editor.apply();
		} else {
			// Fallback to syncrhonous if not available
			editor.commit();
		}
	}

	/**
	 * 从SharedPreferences中移除某项数据
	 *
	 * @param key
	 */
	public static void remove(String key) {
		if (key == null || key.length() <= 0) {
			return;
		}
		editor.remove(key);
		commit();
	}

	/**
	 * 清空xml下所有值
	 */
	public void removeAll() {
		editor.clear();
		commit();
	}

	private static void writeToEditor(String key, Object value) {
		Class<?> clazz = value.getClass();
		if (clazz == Integer.class || clazz == int.class) {
			editor.putInt(key, (Integer) value);
		} else if (clazz == String.class) {
			editor.putString(key, (String) value);
		} else if (clazz == Boolean.class || clazz == boolean.class) {
			editor.putBoolean(key, (Boolean) value);
		} else if (clazz == Long.class || clazz == long.class) {
			editor.putLong(key, (Long) value);
		} else if (clazz == Float.class || clazz == float.class) {
			editor.putFloat(key, (Float) value);
		} else {
		}
	}
}
