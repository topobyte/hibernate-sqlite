// Copyright 2021 Sebastian Kuerten
//
// This file is part of hibernate-sqlite.
//
// hibernate-sqlite is free software: you can redistribute it and/or modify
// it under the terms of the GNU Lesser General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// hibernate-sqlite is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public License
// along with hibernate-sqlite. If not, see <http://www.gnu.org/licenses/>.

/*
 * Original copyright notice from https://gist.github.com/virasak/54436:
 * 
 * The author disclaims copyright to this source code. In place of
 * a legal notice, here is a blessing:
 * 
 * May you do good and not evil.
 * May you find forgiveness for yourself and forgive others.
 * May you share freely, never taking more than you give.
 */
package org.hibernate.dialect;

import java.sql.Types;

import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.dialect.function.VarArgsSQLFunction;
import org.hibernate.type.StandardBasicTypes;

public class SQLiteDialect extends Dialect
{

	public SQLiteDialect()
	{
		super();
		registerColumnType(Types.BIT, "integer");
		registerColumnType(Types.TINYINT, "tinyint");
		registerColumnType(Types.SMALLINT, "smallint");
		registerColumnType(Types.INTEGER, "integer");
		registerColumnType(Types.BIGINT, "bigint");
		registerColumnType(Types.FLOAT, "float");
		registerColumnType(Types.REAL, "real");
		registerColumnType(Types.DOUBLE, "double");
		registerColumnType(Types.NUMERIC, "numeric");
		registerColumnType(Types.DECIMAL, "decimal");
		registerColumnType(Types.CHAR, "char");
		registerColumnType(Types.VARCHAR, "varchar");
		registerColumnType(Types.LONGVARCHAR, "longvarchar");
		registerColumnType(Types.DATE, "date");
		registerColumnType(Types.TIME, "time");
		registerColumnType(Types.TIMESTAMP, "timestamp");
		registerColumnType(Types.BINARY, "blob");
		registerColumnType(Types.VARBINARY, "blob");
		registerColumnType(Types.LONGVARBINARY, "blob");
		// registerColumnType(Types.NULL, "null");
		registerColumnType(Types.BLOB, "blob");
		registerColumnType(Types.CLOB, "clob");
		registerColumnType(Types.BOOLEAN, "integer");

		registerFunction("concat", new VarArgsSQLFunction(
				StandardBasicTypes.STRING, "", "||", ""));
		registerFunction("mod",
				new SQLFunctionTemplate(StandardBasicTypes.INTEGER, "?1 % ?2"));
		registerFunction("substr",
				new StandardSQLFunction("substr", StandardBasicTypes.STRING));
		registerFunction("substring",
				new StandardSQLFunction("substr", StandardBasicTypes.STRING));
	}

	@Override
	public boolean supportsIdentityColumns()
	{
		return true;
	}

	/*
	 * public boolean supportsInsertSelectIdentity() { return true; // As
	 * specify in NHibernate dialect }
	 */

	@Override
	public boolean hasDataTypeInIdentityColumn()
	{
		return false; // As specify in NHibernate dialect
	}

	/*
	 * public String appendIdentitySelectToInsert(String insertString) { return
	 * new StringBuffer(insertString.length()+30). // As specify in NHibernate
	 * dialect append(insertString).
	 * append("; ").append(getIdentitySelectString()). toString(); }
	 */

	@Override
	public String getIdentityColumnString()
	{
		// return "integer primary key autoincrement";
		return "integer";
	}

	@Override
	public String getIdentitySelectString()
	{
		return "select last_insert_rowid()";
	}

	@Override
	public boolean supportsLimit()
	{
		return true;
	}

	@Override
	public String getLimitString(String query, boolean hasOffset)
	{
		return new StringBuffer(query.length() + 20).append(query)
				.append(hasOffset ? " limit ? offset ?" : " limit ?")
				.toString();
	}

	@Override
	public boolean supportsTemporaryTables()
	{
		return true;
	}

	@Override
	public String getCreateTemporaryTableString()
	{
		return "create temporary table if not exists";
	}

	@Override
	public boolean dropTemporaryTableAfterUse()
	{
		return false;
	}

	@Override
	public boolean supportsCurrentTimestampSelection()
	{
		return true;
	}

	@Override
	public boolean isCurrentTimestampSelectStringCallable()
	{
		return false;
	}

	@Override
	public String getCurrentTimestampSelectString()
	{
		return "select current_timestamp";
	}

	@Override
	public boolean supportsUnionAll()
	{
		return true;
	}

	@Override
	public boolean hasAlterTable()
	{
		return false; // As specify in NHibernate dialect
	}

	@Override
	public boolean dropConstraints()
	{
		return false;
	}

	@Override
	public String getAddColumnString()
	{
		return "add column";
	}

	@Override
	public String getForUpdateString()
	{
		return "";
	}

	@Override
	public boolean supportsOuterJoinForUpdate()
	{
		return false;
	}

	@Override
	public String getDropForeignKeyString()
	{
		throw new UnsupportedOperationException(
				"No drop foreign key syntax supported by SQLiteDialect");
	}

	@Override
	public String getAddForeignKeyConstraintString(String constraintName,
			String[] foreignKey, String referencedTable, String[] primaryKey,
			boolean referencesPrimaryKey)
	{
		throw new UnsupportedOperationException(
				"No add foreign key syntax supported by SQLiteDialect");
	}

	@Override
	public String getAddPrimaryKeyConstraintString(String constraintName)
	{
		throw new UnsupportedOperationException(
				"No add primary key syntax supported by SQLiteDialect");
	}

	@Override
	public boolean supportsIfExistsBeforeTableName()
	{
		return true;
	}

	@Override
	public boolean supportsCascadeDelete()
	{
		return false;
	}

}