package com.robotemplates.cookbook.database.query;

import com.robotemplates.cookbook.database.dao.CategoryDAO;
import com.robotemplates.cookbook.database.data.Data;

import java.sql.SQLException;

public class CategoryDeleteAllQuery extends Query {
	public CategoryDeleteAllQuery() {
	}

	@Override
	public Data<Integer> processData() throws SQLException {
		Data<Integer> data = new Data<>();
		data.setDataObject(CategoryDAO.deleteAll());
		return data;
	}
}
