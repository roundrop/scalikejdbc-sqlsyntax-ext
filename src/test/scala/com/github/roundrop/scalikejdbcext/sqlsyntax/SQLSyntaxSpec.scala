package com.github.roundrop.scalikejdbcext.sqlsyntax

import org.scalatest._
import scalikejdbc.LikeConditionEscapeUtil
import scalikejdbc.interpolation._

class SQLSyntaxSpec extends FlatSpec with Matchers {

  import Implicits._


  /* ieq */

  it should "have #ieq" in {
    val s = SQLSyntax.ieq(sqls"title", "abc")
    s.value should equal(" LOWER(title) = LOWER(?)")
    s.parameters should equal(Seq("abc"))
  }

  it should "have #ieq for null values" in {
    val s = SQLSyntax.ieq(sqls"title", null)
    s.value should equal(" LOWER(title) is null")
    s.parameters should equal(Nil)
  }

  it should "have #ieq for None values" in {
    val s = SQLSyntax.ieq(sqls"title", None)
    s.value should equal(" LOWER(title) is null")
    s.parameters should equal(Nil)
  }

  it should "have ieq" in {
    val s = SQLSyntax.eq(sqls"no", 123).and.ieq(sqls"title", "abc")
    s.value should equal(" no = ? and LOWER(title) = LOWER(?)")
    s.parameters should equal(Seq(123, "abc"))
  }


  /* ine */

  it should "have #ine" in {
    val s = SQLSyntax.ine(sqls"title", "abc")
    s.value should equal(" LOWER(title) <> LOWER(?)")
    s.parameters should equal(Seq("abc"))
  }

  it should "have #ine for null values" in {
    val s = SQLSyntax.ine(sqls"title", null)
    s.value should equal(" LOWER(title) is not null")
    s.parameters should equal(Nil)
  }

  it should "have #ine for None values" in {
    val s = SQLSyntax.ine(sqls"title", None)
    s.value should equal(" LOWER(title) is not null")
    s.parameters should equal(Nil)
  }

  it should "have ine" in {
    val s = SQLSyntax.eq(sqls"no", 123).and.ine(sqls"title", "abc")
    s.value should equal(" no = ? and LOWER(title) <> LOWER(?)")
    s.parameters should equal(Seq(123, "abc"))
  }


  /* ilike */

  it should "have #ilike" in {
    val s = SQLSyntax.ilike(sqls"title", "%abc%")
    s.value should equal(" LOWER(title) like LOWER(?)")
    s.parameters should equal(Seq("%abc%"))
  }

  it should "have ilike" in {
    val s = SQLSyntax.eq(sqls"no", 123).and.ilike(sqls"title", "%abc%")
    s.value should equal(" no = ? and LOWER(title) like LOWER(?)")
    s.parameters should equal(Seq(123, "%abc%"))
  }

  /* notILike */

  it should "have #notILike" in {
    val s = SQLSyntax.notILike(sqls"title", "%abc%")
    s.value should equal(" LOWER(title) not like LOWER(?)")
    s.parameters should equal(Seq("%abc%"))
  }

  it should "have notILike" in {
    val s = SQLSyntax.eq(sqls"no", 123).and.notILike(sqls"title", "%abc%")
    s.value should equal(" no = ? and LOWER(title) not like LOWER(?)")
    s.parameters should equal(Seq(123, "%abc%"))
  }

  /* escape */

  val escapeChar = "!"
  val util = LikeConditionEscapeUtil(escapeChar)

  it should "like with escape" in {
    val v = util.contains("foo%aa_bbb\\ccc!")
    val s = SQLSyntax.like(sqls"title", v).escape(escapeChar)
    s.value should equal(" title like ? ESCAPE ?")
    s.parameters should equal(Seq("%foo!%aa!_bbb\\ccc!!%", "!"))
  }

  it should "notLike with escape" in {
    val v = util.contains("foo%aa_bbb\\ccc!")
    val s = SQLSyntax.notLike(sqls"title", v).escape(escapeChar)
    s.value should equal(" title not like ? ESCAPE ?")
    s.parameters should equal(Seq("%foo!%aa!_bbb\\ccc!!%", "!"))
  }

  it should "ilike with escape" in {
    val v = util.contains("foo%aa_bbb\\ccc!")
    val s = SQLSyntax.ilike(sqls"title", v).escape(escapeChar)
    s.value should equal(" LOWER(title) like LOWER(?) ESCAPE ?")
    s.parameters should equal(Seq("%foo!%aa!_bbb\\ccc!!%", "!"))
  }

  it should "notILike with escape" in {
    val v = util.contains("foo%aa_bbb\\ccc!")
    val s = SQLSyntax.notILike(sqls"title", v).escape(escapeChar)
    s.value should equal(" LOWER(title) not like LOWER(?) ESCAPE ?")
    s.parameters should equal(Seq("%foo!%aa!_bbb\\ccc!!%", "!"))
  }
}
