package com.github.roundrop

import _root_.scalikejdbc._

package object scalikejdbcext {

  /*
   * Convenience SQLSyntax
   *
   * - case insensitivity
   * sqls.eqIgnoreCase(column.title, "abc")         =>  LOWER(title) = LOWER('abc')
   * sqls.neIgnoreCase(column.title, "abc")         =>  LOWER(title) <> LOWER('abc')
   *
   * sqls.likeIgnoreCase(column.title, "%abc%")     =>  LOWER(title) LIKE LOWER('%abc%')
   * sqls.notLikeIgnoreCase(column.title, "%abc%")  =>  LOWER(title) NOT LIKE LOWER('%abc%')
   *
   * - shortcut methods using the LIKE predicate
   * sqls.contains(column.title, "abc")             =>  title LIKE '%' || 'abc' || '%'
   * sqls.startsWith(column.title, "abc")           =>  title LIKE 'abc' || '%'
   * sqls.endsWith(column.title, "abc")             =>  title LIKE '%' || 'abc'
   *
   */
  implicit class ConvenienceSQLSyntax(val underlying: SQLSyntax) extends AnyVal {
    def eqIgnoreCase[A](column: SQLSyntax, value: A)(implicit ev: ParameterBinderFactory[A]): SQLSyntax = {
      value match {
        case null | None => sqls"${underlying} LOWER(${column}) is null"
        case _ => sqls"${underlying} LOWER(${column}) = LOWER(${ev(value)})"
      }
    }
    def neIgnoreCase[A](column: SQLSyntax, value: A)(implicit ev: ParameterBinderFactory[A]): SQLSyntax = {
      value match {
        case null | None => sqls"${underlying} LOWER(${column}) is not null"
        case _ => sqls"${underlying} LOWER(${column}) <> LOWER(${ev(value)})"
      }
    }
    def likeIgnoreCase(column: SQLSyntax, value: String): SQLSyntax = sqls"${underlying} LOWER(${column}) like LOWER(${value})"
    def notLikeIgnoreCase(column: SQLSyntax, value: String): SQLSyntax = sqls"${underlying} LOWER(${column}) not like LOWER(${value})"
    def contains(column: SQLSyntax, value: String): SQLSyntax = sqls"${underlying} ${column} like '%' || ${value} || '%'"
    def startsWith(column: SQLSyntax, value: String): SQLSyntax = sqls"${underlying} ${column} like ${value} || '%'"
    def endsWith(column: SQLSyntax, value: String): SQLSyntax = sqls"${underlying} ${column} like '%' || ${value}"
  }
  implicit class ConvenienceSQLSyntax_(s: SQLSyntax.type) {
    def eqIgnoreCase[A: ParameterBinderFactory](column: SQLSyntax, value: A): SQLSyntax = SQLSyntax.empty.eqIgnoreCase(column, value)
    def neIgnoreCase[A: ParameterBinderFactory](column: SQLSyntax, value: A): SQLSyntax = SQLSyntax.empty.neIgnoreCase(column, value)
    def likeIgnoreCase(column: SQLSyntax, value: String): SQLSyntax = SQLSyntax.empty.likeIgnoreCase(column, value)
    def notLikeIgnoreCase(column: SQLSyntax, value: String): SQLSyntax = SQLSyntax.empty.notLikeIgnoreCase(column, value)
    def contains(column: SQLSyntax, value: String): SQLSyntax = SQLSyntax.empty.contains(column, value)
    def startsWith(column: SQLSyntax, value: String): SQLSyntax = SQLSyntax.empty.startsWith(column, value)
    def endsWith(column: SQLSyntax, value: String): SQLSyntax = SQLSyntax.empty.endsWith(column, value)
  }

}
