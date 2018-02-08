# ScalikeJDBC SQLSyntax Extension
[![Build Status](https://travis-ci.org/roundrop/scalikejdbc-sqlsyntax-ext.svg?branch=master)](https://travis-ci.org/roundrop/scalikejdbc-sqlsyntax-ext)

ScalikeJDBC SQLSyntax Extension: eq/ne/like with ignore case, and escape clause.

### Case insensitivity

| SQLSyntax                              | SQL                                    |
|:---------------------------------------|:---------------------------------------|
| `sqls.ieq(column.title, "abc")`        | `LOWER(title) = LOWER('abc')`          |
| `sqls.ine(column.title, "abc")`        | `LOWER(title) <> LOWER('abc')`         |
| `sqls.ilike(column.title, "%abc%")`    | `LOWER(title) LIKE LOWER('%abc%')`     |
| `sqls.notILike(column.title, "%abc%")` | `LOWER(title) NOT LIKE LOWER('%abc%')` |

### Escaping operands with the LIKE predicate

| SQLSyntax                                               | SQL                                                    |
|:--------------------------------------------------------|:-------------------------------------------------------|
| `sqls.like(column.title, "%#%0D#%0A%").escape("#")`     | `title LIKE '%#%0D#%0A%' ESCAPE '#'`                   |
| `sqls.notLike(column.title, "%#%0D#%0A%").escape("#")`  | `title NOT LIKE '%#%0D#%0A%' ESCAPE '#'`               |
| `sqls.ilike(column.title, "%#%0D#%0A%").escape("#")`    | `LOWER(title) LIKE LOWER('%#%0D#%0A%') ESCAPE '#'`     |
| `sqls.notILike(column.title, "%#%0D#%0A%").escape("#")` | `LOWER(title) NOT LIKE LOWER('%#%0D#%0A%') ESCAPE '#'` |

## Examples

```scala
import com.github.roundrop.scalikejdbcext.sqlsyntax._

// eq with ignore case
val name = "alice"
val alice: Option[Member] = withSQL {
  select.from(Member as m).where(sqls.ieq(m.name, name))
}.map(rs => Member(rs)).single.apply()
// --> select m.id as i_on_m, ... from members m where LOWER(m.name) = LOWER('alice');

// like with ignore case
val s = "b"
val q = scalikejdbc.LikeConditionEscapeUtil.contains(s) // "b" => "%b%"
val b: Seq[Member] = withSQL {
  select.from(Member as m).where(sqls.ilike(m.name, q))
}.map(rs => Member(rs)).list.apply()
// --> select m.id as i_on_m, ... from members m where LOWER(m.name) like LOWER('%b%');

// use escape clause
val escapeChar = "#"
val util = scalikejdbc.LikeConditionEscapeUtil(escapeChar)
val s = "_100%"
val q = util.endsWith(s)  // "_100%" => "%#_100#%"
val x: Seq[Member] = withSQL {
  select.from(Member as m).where(sqls.like(m.name, q).escape(escapeChar))
}.map(rs => Member(rs)).list.apply()
// --> select m.id as i_on_m, ... from members m where m.name like '%#_100#%' ESCAPE '#';
```

## Setup

#### Sbt

```scala
libraryDependencies += "com.github.roundrop" %% "scalikejdbc-sqlsyntax-ext" % "1.2.+"
```

#### Maven

```xml
<dependency>
    <groupId>com.github.roundrop</groupId>
    <artifactId>scalikejdbc-sqlsyntax-ext_2.12</artifactId>
    <version>[1.2,)</version>
</dependency>
```
