package com.kissme.mimo.infrastructure.persist;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import com.kissme.mimo.domain.article.Article.ArticleStatus;

/**
 * 
 * @author loudyn
 * 
 */
public class MybatisArticleStatusTypeHandler extends BaseTypeHandler<ArticleStatus> {

	@Override
	public void setNonNullParameter(PreparedStatement ps, int index, ArticleStatus parameter, JdbcType jdbcType) throws SQLException {
		ps.setString(index, parameter.toString());
	}

	@Override
	public ArticleStatus getNullableResult(ResultSet rs, String columnName) throws SQLException {
		String status = rs.getString(columnName);
		return Enum.valueOf(ArticleStatus.class, status);
	}

	@Override
	public ArticleStatus getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		String status = cs.getString(columnIndex);
		return Enum.valueOf(ArticleStatus.class, status);
	}
}
