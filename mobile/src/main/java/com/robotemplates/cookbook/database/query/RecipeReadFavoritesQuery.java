package com.robotemplates.cookbook.database.query;

import com.robotemplates.cookbook.database.dao.RecipeDAO;
import com.robotemplates.cookbook.database.data.Data;
import com.robotemplates.cookbook.database.model.RecipeModel;

import java.sql.SQLException;
import java.util.List;

public class RecipeReadFavoritesQuery extends Query {
	private long mSkip = -1L;
	private long mTake = -1L;

	public RecipeReadFavoritesQuery() {
	}

	public RecipeReadFavoritesQuery(long skip, long take) {
		mSkip = skip;
		mTake = take;
	}

	@Override
	public Data<List<RecipeModel>> processData() throws SQLException {
		Data<List<RecipeModel>> data = new Data<>();
		data.setDataObject(RecipeDAO.readFavorites(mSkip, mTake));
		return data;
	}
}
