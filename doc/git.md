$ git init : 初始化仓库
$ git add xx : 添加xx到本地版本仓库
$ git commit -m "xxx" 添加提交信息
$ git push : 推送到远程仓库

#query current git account 
$ git config -user.name

#switch account
$ git config --global user.name xxx

基本操作

$ git init : 初始化仓库
$ git add xx : 添加xx到本地版本仓库
$ git commit -m "xxx" 添加提交信息
$ git push : 推送到远程仓库
常用命令

$ git status : 仓库当前的状态
$ git diff : 查看difference
版本回滚

$ git log : 显示从最近到最远的提交日志
$ git reset --hard commitID : 回滚到某个版本
$ git reflog : 记录你的每一次命令
$ git push -f -u origin master : 提交回滚后的版本
工作区与暂存区


管理修改

第一次修改 -> git add -> 第二次修改 -> git add -> git commit
撤销更改

$ git checkout -- file : 可以丢弃工作区的修改
$ git reset HEAD file : 可以把暂存区的修改撤销掉（unstage）
生成密钥

$ ssh-keygen -t rsa -C "youremail@example.com" : 生成相应邮箱的密钥
远程仓库

$ git remote add origin xx : 指定远程仓库地址
$ git push origin (-u) master : 推送到远程仓库
$ git clone xx : 下载远程仓库(含有.git)
$ git pull : 下拉仓库
分支

$ git branch : 列出所有分支
$ git branch <name> : 创建分支
$ git checkout <name> : 切换分支
$ git checkout -b xx : 创建并且切换到xx分支
$ git merge <name> : 合并到当前分支
$ git branch -d <name> : 删除分支

$ git log --graph : 查看分支合并图

$ 合并分支时，加上--no-ff参数就可以用普通模式合并，合并后的历史有分支，能看出来曾经做过合并，而fast forward合并就看不出来曾经做过合并
Bug分支

$ git stash : 把当前工作现场“储藏”起来
$ git stash list : 查看工作现场列表
$ git stash apply : 恢复-> 恢复后-> stash内容并不删除
$ git stash drop : 删除工作现场的内容
$ git stash pop : 恢复的同时把stash内容也删了
Feature分支

$ 如果要丢弃一个没有被合并过的分支，可以通过git branch -D <name>强行删除。
Tag标签

$ git tag <name> : 用于新建一个标签
$ git tag -a <tagname> -m xxx : 可以指定标签信息
$ git tag -s <tagname> -m xxx : 可以用PGP签名标签
$ git tag : 查看所有标签
$ git push origin <tagname> : 推送一个本地标签
$ git push origin --tags : 推送全部未推送过的本地标签
$ git tag -d <tagname> : 可以删除一个本地标签
$ git push origin :refs/tags/<tagname> : 可以删除一个远程标签