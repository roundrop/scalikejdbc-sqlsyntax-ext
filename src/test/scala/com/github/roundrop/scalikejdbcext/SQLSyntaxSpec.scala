package com.github.roundrop.scalikejdbcext

import org.scalatest._
import scalikejdbc.interpolation._

class SQLSyntaxSpec extends FlatSpec with Matchers {

  import Implicits._


  /* eqIgnoreCase */

  it should "have #eqIgnoreCase" in {
    val s = SQLSyntax.eqIgnoreCase(sqls"title", "abc")
    s.value should equal(" LOWER(title) = LOWER(?)")
    s.parameters should equal(Seq("abc"))
  }

  it should "have #eqIgnoreCase for null values" in {
    val s = SQLSyntax.eqIgnoreCase(sqls"title", null)
    s.value should equal(" LOWER(title) is null")
    s.parameters should equal(Nil)
  }

  it should "have #eqIgnoreCase for None values" in {
    val s = SQLSyntax.eqIgnoreCase(sqls"title", None)
    s.value should equal(" LOWER(title) is null")
    s.parameters should equal(Nil)
  }

  it should "have eqIgnoreCase" in {
    val s = SQLSyntax.eq(sqls"no", 123).and.eqIgnoreCase(sqls"title", "abc")
    s.value should equal(" no = ? and LOWER(title) = LOWER(?)")
    s.parameters should equal(Seq(123, "abc"))
  }


  /* neIgnoreCase */

  it should "have #neIgnoreCase" in {
    val s = SQLSyntax.neIgnoreCase(sqls"title", "abc")
    s.value should equal(" LOWER(title) <> LOWER(?)")
    s.parameters should equal(Seq("abc"))
  }

  it should "have #neIgnoreCase for null values" in {
    val s = SQLSyntax.neIgnoreCase(sqls"title", null)
    s.value should equal(" LOWER(title) is not null")
    s.parameters should equal(Nil)
  }

  it should "have #neIgnoreCase for None values" in {
    val s = SQLSyntax.neIgnoreCase(sqls"title", None)
    s.value should equal(" LOWER(title) is not null")
    s.parameters should equal(Nil)
  }

  it should "have neIgnoreCase" in {
    val s = SQLSyntax.eq(sqls"no", 123).and.neIgnoreCase(sqls"title", "abc")
    s.value should equal(" no = ? and LOWER(title) <> LOWER(?)")
    s.parameters should equal(Seq(123, "abc"))
  }


  /* likeIgnoreCase */

  it should "have #likeIgnoreCase" in {
    val s = SQLSyntax.likeIgnoreCase(sqls"title", "%abc%")
    s.value should equal(" LOWER(title) like LOWER(?)")
    s.parameters should equal(Seq("%abc%"))
  }

  it should "have likeIgnoreCase" in {
    val s = SQLSyntax.eq(sqls"no", 123).and.likeIgnoreCase(sqls"title", "%abc%")
    s.value should equal(" no = ? and LOWER(title) like LOWER(?)")
    s.parameters should equal(Seq(123, "%abc%"))
  }

  /* notLikeIgnoreCase */

  it should "have #notLikeIgnoreCase" in {
    val s = SQLSyntax.notLikeIgnoreCase(sqls"title", "%abc%")
    s.value should equal(" LOWER(title) not like LOWER(?)")
    s.parameters should equal(Seq("%abc%"))
  }

  it should "have notLikeIgnoreCase" in {
    val s = SQLSyntax.eq(sqls"no", 123).and.notLikeIgnoreCase(sqls"title", "%abc%")
    s.value should equal(" no = ? and LOWER(title) not like LOWER(?)")
    s.parameters should equal(Seq(123, "%abc%"))
  }

  /* contains */

  it should "have #contains" in {
    val s = SQLSyntax.contains(sqls"title", "abc")
    s.value should equal(" title like '%' || ? || '%'")
    s.parameters should equal(Seq("abc"))
  }

  it should "have contains" in {
    val s = SQLSyntax.eq(sqls"no", 123).and.contains(sqls"title", "abc")
    s.value should equal(" no = ? and title like '%' || ? || '%'")
    s.parameters should equal(Seq(123, "abc"))
  }

  /* startsWith */

  it should "have #startsWith" in {
    val s = SQLSyntax.startsWith(sqls"title", "abc")
    s.value should equal(" title like ? || '%'")
    s.parameters should equal(Seq("abc"))
  }

  it should "have startsWith" in {
    val s = SQLSyntax.eq(sqls"no", 123).and.startsWith(sqls"title", "abc")
    s.value should equal(" no = ? and title like ? || '%'")
    s.parameters should equal(Seq(123, "abc"))
  }

  /* endsWith */

  it should "have #endsWith" in {
    val s = SQLSyntax.endsWith(sqls"title", "abc")
    s.value should equal(" title like '%' || ?")
    s.parameters should equal(Seq("abc"))
  }

  it should "have endsWith" in {
    val s = SQLSyntax.eq(sqls"no", 123).and.endsWith(sqls"title", "abc")
    s.value should equal(" no = ? and title like '%' || ?")
    s.parameters should equal(Seq(123, "abc"))
  }

}
