sp_help tableName;
sp_columns tableName;
复制表结构

游标定义语法
DECLARE cursor_name CURSOR
　　[ LOCAL  ：局部游标，仅在当前会话有效
     | GLOBAL  ： 全局游标，全局有效，可以
    ]
     [ FORWARD_ONLY ：只能向前游标，读取游标时只能使用 Next 谓词
       | SCROLL  ：滚动游标，FIRST、LAST、PRIOR、NEXT、RELATIVE、ABSOLUTE 都可以使用
    ]
     [ STATIC ：定义一个游标，以创建将由该游标使用的数据的临时复本。对游标的所有请求都从 tempdb 中的这一临时表中得到应答；
       | KEYSET ：对基表中的非键值所做的更改（由游标所有者更改或由其他用户提交）可以在用户滚动游标时看到。其他用户执行的插入是不可见的（不能通过 Transact-SQL 服务器游标执行插入）。如果删除某一行，则在尝试提取该行时返回值为 -2 的 @@FETCH_STATUS。
       | DYNAMIC ：定义一个游标，以反映在滚动游标时对结果集内的各行所做的所有数据更改。行的数据值、顺序和成员身份在每次提取时都会更改。动态游标不支持 ABSOLUTE 提取选项
       | FAST_FORWARD ：指定启用了性能优化的 FORWARD_ONLY、READ_ONLY 游标。如果指定了 SCROLL 或 FOR_UPDATE，则不能也指定 FAST_FORWARD
    ]
     [ READ_ONLY ：只读游标，不能对游标内容进行更改，不能使用 where current of 语句
       | SCROLL_LOCKS ：指定通过游标进行的定位更新或删除一定会成功。将行读入游标时 SQL Server 将锁定这些行，以确保随后可对它们进行修改。
       | OPTIMISTIC  ：指定如果行自读入游标以来已得到更新，则通过游标进行的定位更新或定位删除不成功。当将行读入游标时，SQL Server 不锁定行

备份表数据
select * into claims_archive.catalog_audit1  from claims_archive.catalog_audit

执行存储过程权限授予
REVOKE EXECUTE ON a.archive_inventory TO claimsdevread;
GRANT EXECUTE ON a.remove_inventory TO claimsdevread;
