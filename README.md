# ScalikeJDBC SQLSyntax Extension

ScalikeJDBC SQLSyntax Extension: eq/ne/like with ignore case, and more.

### case insensitivity

| SQLSyntax                                       | SQL                                    |
|:------------------------------------------------|:---------------------------------------|
| `sqls.eqIgnoreCase(column.title, "abc")`        | `LOWER(title) = LOWER('abc')`          |
| `sqls.neIgnoreCase(column.title, "abc")`        | `LOWER(title) <> LOWER('abc')`         |
| `sqls.likeIgnoreCase(column.title, "%abc%")`    | `LOWER(title) LIKE LOWER('%abc%')`     |
| `sqls.notLikeIgnoreCase(column.title, "%abc%")` | `LOWER(title) NOT LIKE LOWER('%abc%')` |

### shortcut methods using the LIKE predicate

| SQLSyntax                                       | SQL                                    |
|:------------------------------------------------|:---------------------------------------|
| `sqls.contains(column.title, "abc")`            | `title LIKE '%' || 'abc' || '%'`       |
| `sqls.startsWith(column.title, "abc")`          | `title LIKE 'abc' || '%'`              |
| `sqls.endsWith(column.title, "abc")`            | `title LIKE '%' || 'abc'`              |

## Examples

```scala
import com.github.roundrop.scalikejdbcext.sqlsyntax._

// eq with ignore case
Tag.where(sqls.eqIgnoreCase(column.name, name))...

// ne with ignore case
Tag.where(sqls.neIgnoreCase(column.name, name))...

// like with ignore case
Article.countBy(sqls.likeIgnoreCase(column.title, "%abc%").or.likeIgnoreCase(column.body, "%xyz%"))...
```

## Setup

#### Sbt

```scala
libraryDependencies += "com.github.roundrop" %% "scalikejdbc-sqlsyntax-ext" % "1.1.+"
```

#### Maven

```xml
<dependency>
    <groupId>com.github.roundrop</groupId>
    <artifactId>scalikejdbc-sqlsyntax-ext_2.12</artifactId>
    <version>[1.1,)</version>
</dependency>
```
