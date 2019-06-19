package com.idcard.hnd.idcardjchl.sqlite;

import android.content.Context;
import android.text.TextUtils;

import com.idcard.hnd.idcardjchl.AppContext;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * DbUtils
 * Created by yhw on 2016/6/26.
 */
public class DataBaseUtilssss {

    private static DbUtils db;

    public static void init(AppContext app) {
        db = app.getDb();
    }

    public static DbUtils getDb() {
        return db;
    }

    /**
     * 检测表是否存在
     *
     * @param entity
     * @return
     * @throws DbException
     */
    public static boolean tableIsExist(Class entity) throws DbException {
        return db.tableIsExist(entity);
    }

    public static boolean datasIsExist(Class entity, String column, String value) throws DbException {
        boolean flag = false;
        List list = queryDbWhere(entity, column, value);
        if (list != null && list.size() > 0) {
            flag = true;
        }
        return flag;
    }

    /**
     * 插入一条数据
     *
     * @param obj
     * @throws DbException
     */
    public static void saveItem(Object obj) throws DbException {
        db.save(obj);
    }

    /**
     * 插入一个集合
     *
     * @param list
     * @throws DbException
     */
    public static void saveList(List<?> list) throws DbException {
        db.saveAll(list);
    }

    /**
     * 查询集合 -- 查询表中所有数据
     *
     * @param entity
     * @return
     * @throws DbException
     */
    public static List<?> queryDbAll(Class entity) throws DbException {
        return db.findAll(entity);
    }

    /**
     * 查询集合 -- 查询单个条件：“=”
     *
     * @param entity
     * @param column
     * @param value
     * @return
     * @throws DbException
     */
    public static List<?> queryDbWhere(Class entity, String column, String value) throws DbException {
        return db.findAll(Selector.from(entity).where(column, "=", value));
    }


    /**
     * 查询集合 -- 查询单个条件：“=”
     *
     * @param entity
     * @param column
     * @param value
     * @return
     * @throws DbException
     */
    public static List<?> queryDbWhereAndOrder(Class entity, String column, String value, String column1, boolean flag) throws DbException {
        return db.findAll(Selector.from(entity).where(column, "=", value).orderBy(column1, flag));
    }

    /**
     * 查询数据库 排序
     *
     * @param entity 数据库表.class
     * @param column 根据哪个字段排序
     * @param desc   降序 ase 升序
     * @return 返回List
     * @throws DbException
     */
    public static List<?> queryDbSort(Class entity, String column, boolean desc) throws DbException {
        return db.findAll(Selector.from(entity).orderBy(column, desc));
    }

    /**
     * 查询集合 -- 两个条件 1：“like%”， 2：“=”
     *
     * @param entity
     * @param column
     * @param key
     * @param column1
     * @param value1
     * @return
     * @throws DbException
     */
    public static List<?> queryDbItemLikeWhereTwoList(Class entity, String column, String key, String column1, String value1) throws DbException {
        return db.findAll(Selector.from(entity).where(column, "like", key + "%").and(column1, "=", value1));
    }

    /**
     * 查询集合 -- 两个条件 1：“=” ，2：“in”
     *
     * @param entity
     * @param column
     * @param value
     * @param column1
     * @param arr
     * @return
     * @throws DbException
     */
    public static List<?> queryDbWhereDl(Class entity, String column, String value, String column1, String[] arr) throws DbException {
        return db.findAll(Selector.from(entity).where(column, "=", value).and(column1, "in", arr));
    }


    /**
     * 查询集合 -- 一个条件 ：“in”
     *
     * @param entity
     * @param column1
     * @param arr
     * @return
     * @throws DbException
     */
    public static List<?> queryDbWhereDl(Class entity, String column1, String[] arr) throws DbException {
        return db.findAll(Selector.from(entity).where(column1, "in", arr));
    }


    /**
     * 查询集合 -- 两个条件 1:“=” ，2：“%like%”
     *
     * @param entity
     * @param column
     * @param value
     * @param column1
     * @param key1
     * @return
     * @throws DbException
     */
    public static List<?> queryDbWhereDl(Class entity, String column, String value, String column1, String key1) throws DbException {
        return db.findAll(Selector.from(entity).where(column, "=", value).and(column1, "like", "%" + key1 + "%"));
    }

    /**
     * 查询集合 -- 一个条件 :“%kile%”
     *
     * @param entity
     * @param column
     * @param key
     * @return
     * @throws DbException
     */
    public static List<?> queryDbLike(Class entity, String column, String key) throws DbException {
        return db.findAll(Selector.from(entity).where(column, "like", "%" + key + "%"));
    }

    /**
     * 查询集合 -- 两个条件1：“%like%” 2：“in”
     *
     * @param entity
     * @param column
     * @param key
     * @param column1
     * @param arr
     * @return
     * @throws DbException
     */
    public static List<?> queryDbLikeDl(Class entity, String column, String key, String column1, String[] arr) throws DbException {
        return db.findAll(Selector.from(entity).where(column, "like", "%" + key + "%").and(column1, "in", arr));
    }

    public static List<?> queryDbLikeDl(Class entity, String column, String key, String column1, String key1) throws DbException {
        return db.findAll(Selector.from(entity).where(column, "like", "%" + key + "%").and(column1, "like", "%" + key1 + "%"));
    }

    public static List<?> queryDbLikeForm(Class entity, String column, String key) throws DbException {
        return db.findAll(Selector.from(entity).where(column, "like", key + "%"));
    }

    public static List<?> queryDbAllMoreJudge(Class entity, String column, String key, String column1, String value1) throws DbException {
        return db.findAll(Selector.from(entity).where(column, "like", "%" + key + "%").and(column1, "=", value1));
    }

    public static List<?> queryDbAllLikeFrom(Class entity, String column, String key, String column1, String value1) throws DbException {
        return db.findAll(Selector.from(entity).where(column, "like", key + "%").and(column1, "=", value1));
    }

    public static List<?> queryDbLikeFormDl(Class entity, String column, String key, String column1, String[] arr) throws DbException {
        return db.findAll(Selector.from(entity).where(column, "like", key + "%").and(column1, "in", arr));
    }

    public static List<?> queryDbLikeFormDl(Class entity, String column, String key, String column1, String key1) throws DbException {
        return db.findAll(Selector.from(entity).where(column, "like", key + "%").and(column1, "like", "%" + key1 + "%"));
    }

    public static List<?> queryDbAll(Class entity, String column, String[] arr) throws DbException {
        return db.findAll(Selector.from(entity).where(column, "in", arr));
    }

    public static List<?> queryDbAll(Class entity, String column, String[] arr, String column1, String key1) throws DbException {
        return db.findAll(Selector.from(entity).where(column, "in", arr).and(column1, "like", "%" + key1 + "%"));
    }

    public static List<?> queryDbAllMoreJudge1(Class entity, String column, String value, String column1, String value1) throws DbException {
        return db.findAll(Selector.from(entity).where(column, "=", value).and(column1, "=", value1));
    }

    public static List<?> queryDbAllMoreJudge1AndOrder(Class entity, String column, String value, String column1, String value1, String column2, boolean flag) throws DbException {
        return db.findAll(Selector.from(entity).where(column, "=", value).and(column1, "=", value1).orderBy(column2, flag));
    }

    public static List<?> queryDbAllMoreJudge2(Class entity, String column, String value, String column1, String value1, String column2, String value2) throws DbException {
        return db.findAll(Selector.from(entity).where(column, "=", value).and(column1, "=", value1).and(column2, "=", value2));
    }

    public static Object queryDbItemLikeMoreJudge(Class entity, String column, String key, String column1, String value1) throws DbException {
        return db.findFirst(Selector.from(entity).where(column, "like", "%" + key + "%").and(column1, "=", value1));
    }

    public static Object queryDbItemLike(Class entity, String column, String key) throws DbException {
        return db.findFirst(Selector.from(entity).where(column, "like", "%" + key + "%"));
    }

    public static Object queryDbItemMoreJudge(Class entity, String column, String value, String column1, String value1) throws DbException {
        return db.findFirst(Selector.from(entity).where(column, "=", value).and(column1, "=", value1));
    }

    public static Object queryDbItemMoreJudge2(Class entity, String column, String value, String column1, String value1) throws DbException {
        return db.findFirst(Selector.from(entity).where(column, "=", value).and(column1, "like", "%" + value1 + "%"));
    }

    public static Object queryDbItemMoreJudgeThree(Class entity, String column, String value, String column1, String value1, String column2, String value2) throws DbException {
        return db.findFirst(Selector.from(entity).where(column, "=", value).and(column1, "=", value1).and(column2, "=", value2));
    }

    public static Object queryDbItemMoreJudgeThreeOrder(Class entity, String column, String value, String column1, String value1, String column2, String value2, String column3, boolean desc) throws DbException {
        return db.findFirst(Selector.from(entity).where(column, "=", value).and(column1, "=", value1).and(column2, "=", value2).orderBy(column3, desc));
    }

    public static Object queryDbItemMoreJudgeThree2(Class entity, String column, String value, String column1, String value1, String column2, String value2) throws DbException {
        return db.findFirst(Selector.from(entity).where(column, "=", value).and(column1, "like", "%" + value1 + "%").and(column2, "=", value2));
    }

    public static Object queryDbItem(Class entity, String column, String value) throws DbException {
        return db.findFirst(Selector.from(entity).where(column, "=", value));
    }

    public static Object queryDbItemLikeWhereMoreJudge(Class entity, String column, String key, String column1, String value1) throws DbException {
        return db.findFirst(Selector.from(entity).where(column, "like", key + "%").and(column1, "=", value1));
    }

    public static void deleteDbItem(Class entity, String column, Object value, String column1, Object value1) throws DbException {
        db.delete(entity, WhereBuilder.b(column, "=", value).and(column1, "=", value1));
    }

    public static void deleteDbItem(Class entity, String column, Object value) throws DbException {
        db.delete(entity, WhereBuilder.b(column, "=", value));
    }

    public static void deleteDbItem(Object obj) throws DbException {
        db.delete(obj);
    }

    public static void updateItem(Object object) throws DbException {
        db.update(object);
    }

    public static void updateItem(Object object, String... updateColumnNames) throws DbException {
        db.update(object, updateColumnNames);
    }

    public static void updateItemColumn(Object object, String column) throws DbException {
        db.update(object, column);
    }

    public static void updateItem(Object object, String column, Object value) throws DbException {
        db.update(object, WhereBuilder.b(column, "=", value));
    }

    public static void execNonQuery(String sql) throws DbException {
        db.execNonQuery(sql);
    }

    public static void getAssetsToDb(Context context, String fileName) {
        InputStreamReader inputReader = null;
        try {
            inputReader = new InputStreamReader(context.getResources().getAssets().open(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            String result = "";
            while ((line = bufReader.readLine()) != null) {
                result = line;
                db.execNonQuery(result);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DbException e) {
            e.printStackTrace();
        }
    }


    /**
     * 判断是否存在，选择保存和更新
     *
     * @param entity
     * @param obj
     * @param column
     * @param value
     * @param column1
     * @param value1
     * @param column2
     * @param value2
     * @throws DbException
     */
    public static void saveOrUpdate(Class entity, Object obj, String column, String value, String column1, String value1, String column2, String value2) throws DbException {
        Object old = null;
        if (!TextUtils.isEmpty(column) && !TextUtils.isEmpty(column1) && !TextUtils.isEmpty(column2)) {
            old = db.findFirst(Selector.from(entity).where(column, "=", value).and(column1, "=", value1).and(column2, "=", value2));
        } else if (!TextUtils.isEmpty(column) && !TextUtils.isEmpty(column1)) {
            old = db.findFirst(Selector.from(entity).where(column, "=", value).and(column1, "=", value1));
        } else if (!TextUtils.isEmpty(column)) {
            old = db.findFirst(Selector.from(entity).where(column, "=", value));
        }
        if (old == null) {
            saveItem(obj);
        } else {
            deleteDbItem(old);
            saveItem(obj);
        }
    }

    public static void saveOrUpdateAll(List<?> list) throws DbException {
        db.saveOrUpdateAll(list);
    }



}
