package com.robotemplates.cookbook.database;

import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.robotemplates.cookbook.CookbookApplication;
import com.robotemplates.cookbook.CookbookConfig;
import com.robotemplates.cookbook.database.model.CategoryModel;
import com.robotemplates.cookbook.database.model.IngredientModel;
import com.robotemplates.cookbook.database.model.RecipeModel;

import org.alfonz.utility.Logcat;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
	private static final String DATABASE_NAME = CookbookConfig.DATABASE_NAME;
	private static final String DATABASE_PATH = CookbookApplication.getContext().getDatabasePath(CookbookConfig.DATABASE_NAME).getPath();
	private static final int DATABASE_VERSION = CookbookConfig.DATABASE_VERSION;

	private static DatabaseHelper sInstance;

	private Dao<CategoryModel, Long> mCategoryDao = null;
	private Dao<RecipeModel, Long> mRecipeDao = null;
	private Dao<IngredientModel, Long> mIngredientDao = null;
	private boolean mMigration = false;

	private DatabaseHelper() {
		super(CookbookApplication.getContext(), DATABASE_PATH, null, DATABASE_VERSION);

		boolean exists = DatabaseMigrationUtility.fileExists(DATABASE_PATH);
		boolean update = DATABASE_VERSION > DatabaseMigrationUtility.getVersion();

		if (exists && update) {
			mMigration = true;
		}

		if (!exists || update) {
			synchronized (this) {
				boolean success = DatabaseMigrationUtility.copyPrepopulatedDatabase(DATABASE_NAME, DATABASE_PATH);
				if (success) {
					DatabaseMigrationUtility.setVersion(DATABASE_VERSION);
				}
			}
		}
	}

	// singleton
	public static synchronized DatabaseHelper getInstance() {
		if (sInstance == null) sInstance = new DatabaseHelper();
		return sInstance;
	}

	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
		try {
			Logcat.d("");

			//TableUtils.createTable(connectionSource, CategoryModel.class);
			//TableUtils.createTable(connectionSource, RecipeModel.class);
			//TableUtils.createTable(connectionSource, IngredientModel.class);

			if (mMigration) {
				DatabaseMigrationUtility.restoreFavorites();
				mMigration = false;
			}
		} catch (android.database.SQLException e) {
			Logcat.e(e, "can't create database");
			e.printStackTrace();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
		try {
			Logcat.d("");
		} catch (android.database.SQLException e) {
			Logcat.e(e, "can't upgrade database");
			e.printStackTrace();
		}
	}

	@Override
	public void close() {
		super.close();
		mCategoryDao = null;
		mRecipeDao = null;
		mIngredientDao = null;
	}

	public synchronized void clearDatabase() {
		try {
			Logcat.d("");

			TableUtils.dropTable(getConnectionSource(), CategoryModel.class, true);
			TableUtils.dropTable(getConnectionSource(), RecipeModel.class, true);
			TableUtils.dropTable(getConnectionSource(), IngredientModel.class, true);

			TableUtils.createTable(getConnectionSource(), CategoryModel.class);
			TableUtils.createTable(getConnectionSource(), RecipeModel.class);
			TableUtils.createTable(getConnectionSource(), IngredientModel.class);
		} catch (android.database.SQLException | java.sql.SQLException e) {
			Logcat.e(e, "can't clear database");
			e.printStackTrace();
		}
	}

	public synchronized Dao<CategoryModel, Long> getCategoryDao() throws java.sql.SQLException {
		if (mCategoryDao == null) {
			mCategoryDao = getDao(CategoryModel.class);
		}
		return mCategoryDao;
	}

	public synchronized Dao<RecipeModel, Long> getRecipeDao() throws java.sql.SQLException {
		if (mRecipeDao == null) {
			mRecipeDao = getDao(RecipeModel.class);
		}
		return mRecipeDao;
	}

	public synchronized Dao<IngredientModel, Long> getIngredientDao() throws java.sql.SQLException {
		if (mIngredientDao == null) {
			mIngredientDao = getDao(IngredientModel.class);
		}
		return mIngredientDao;
	}
}
