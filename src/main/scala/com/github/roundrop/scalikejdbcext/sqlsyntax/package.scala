package com.github.roundrop.scalikejdbcext

import _root_.scalikejdbc._
import scalikejdbc.interpolation.SQLSyntax

package object sqlsyntax {

  /*
   * ScalikeJDBC SQLSyntax Extension
   *
   * - Case insensitivity
   *   sqls.ieq(column.title, "abc")         =>  LOWER(title) = LOWER('abc')
   *   sqls.ine(column.title, "abc")         =>  LOWER(title) <> LOWER('abc')
   *   sqls.ilike(column.title, "%abc%")     =>  LOWER(title) LIKE LOWER('%abc%')
   *   sqls.notILike(column.title, "%abc%")  =>  LOWER(title) NOT LIKE LOWER('%abc%')
   *
   * - Escaping operands with the LIKE predicate
   * Often, your pattern may contain any of the wildcard characters "_" and "%", in case of which you may want to escape them.
   * ScalikeJDBC does not automatically escape patterns in like() and notLike() methods. Instead, you can explicitly define an escape character as such:
   *
   *   sqls.like(column.title, "%#%0D#%0A%").escape("#")     => title LIKE '%#%0D#%0A%' ESCAPE '#'
   *   sqls.notLike(column.title, "%#%0D#%0A%").escape("#")  => title NOT LIKE '%#%0D#%0A%' ESCAPE '#'
   *   sqls.ilike(column.title, "%#%0D#%0A%").escape("#")    => LOWER(title) LIKE LOWER('%#%0D#%0A%') ESCAPE '#'
   *   sqls.notILike(column.title, "%#%0D#%0A%").escape("#") => LOWER(title) NOT LIKE LOWER('%#%0D#%0A%') ESCAPE '#'
   *
   */
  implicit class ConvenienceSQLSyntax(val underlying: SQLSyntax) extends AnyVal {
    def ieq[A](column: SQLSyntax, value: A)(implicit ev: ParameterBinderFactory[A]): SQLSyntax = {
      value match {
        case null | None => sqls"$underlying LOWER($column) is null"
        case _ => sqls"$underlying LOWER($column) = LOWER(${ev(value)})"
      }
    }
    def ine[A](column: SQLSyntax, value: A)(implicit ev: ParameterBinderFactory[A]): SQLSyntax = {
      value match {
        case null | None => sqls"$underlying LOWER($column) is not null"
        case _ => sqls"$underlying LOWER($column) <> LOWER(${ev(value)})"
      }
    }

    def ilike(column: SQLSyntax, value: String): SQLSyntax = sqls"$underlying LOWER($column) like LOWER($value)"
    def notILike(column: SQLSyntax, value: String): SQLSyntax = sqls"$underlying LOWER($column) not like LOWER($value)"

    def escape(escapeChar: String): SQLSyntax = sqls"$underlying ESCAPE $escapeChar"
  }

  implicit class ConvenienceSQLSyntax_(private val s: SQLSyntax.type) extends AnyVal {
    def ieq[A: ParameterBinderFactory](column: SQLSyntax, value: A): SQLSyntax = SQLSyntax.empty.ieq(column, value)
    def ine[A: ParameterBinderFactory](column: SQLSyntax, value: A): SQLSyntax = SQLSyntax.empty.ine(column, value)

    def ilike(column: SQLSyntax, value: String): SQLSyntax = SQLSyntax.empty.ilike(column, value)
    def notILike(column: SQLSyntax, value: String): SQLSyntax = SQLSyntax.empty.notILike(column, value)

    def escape(escapeChar: String): SQLSyntax = SQLSyntax.empty.escape(escapeChar)
  }

}
