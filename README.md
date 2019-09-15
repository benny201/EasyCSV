# EasyCSV
基于JAVA的CSV导入导出
A library for import &amp; export CSV in JAVA, Easy to use!


## Quick Start
### 1.Export 导出
```java

1) add **CSVColumn** annotation to exported attributes
@CSVColumn(name = "test1", index = 0)

* index is not nessassary. but without declared index, this column will be appended to the tail.


2) call **ExportCSVUitls**
CSVFileInfo csvFileInfo = ExportCSVUtils.export(input, Demo.class, "test.csv");

```

### 2. Import 导入

```
see you soon...
```

### 3. Contact Me
Email me: zhibinwu1993@outlook.com