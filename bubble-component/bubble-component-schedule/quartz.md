地址：http://blog.csdn.net/u010648555/article/details/54863144
1. 介绍 
Quartz是OpenSymphony开源组织在Job scheduling领域又一个开源项目，是完全由java开发的一个开源的任务日程管理系统，“任务进度管理器”就是一个在预先确定（被纳入日程）的时间到达时，负责执行（或者通知）其他软件组件的系统。 
Quartz用一个小Java库发布文件（.jar文件），这个库文件包含了所有Quartz核心功能。这些功能的主要接口(API)是Scheduler接口。它提供了简单的操作，例如：将任务纳入日程或者从日程中取消，开始/停止/暂停日程进度。 
2. 定时器种类 
Quartz 中五种类型的 Trigger：SimpleTrigger，CronTirgger，DateIntervalTrigger，NthIncludedDayTrigger和Calendar 类（ org.quartz.Calendar）。 
最常用的： 
SimpleTrigger：用来触发只需执行一次或者在给定时间触发并且重复N次且每次执行延迟一定时间的任务。 
CronTrigger：按照日历触发，例如“每个周五”，每个月10日中午或者10：15分。 
3. 存储方式 
RAMJobStore和JDBCJobStore 
对比：

类型	优点	缺点
RAMJobStore	不要外部数据库，配置容易，运行速度快	因为调度程序信息是存储在被分配给JVM的内存里面，所以，当应用程序停止运行时，所有调度信息将被丢失。另外因为存储到JVM内存里面，所以可以存储多少个Job和Trigger将会受到限制
JDBCJobStore	支持集群，因为所有的任务信息都会保存到数据库中，可以控制事物，还有就是如果应用服务器关闭或者重启，任务信息都不会丢失，并且可以恢复因服务器关闭或者重启而导致执行失败的任务	运行速度的快慢取决与连接数据库的快慢
4. 表关系和解释

表关系

这里写图片描述

解释

表名称	说明
qrtz_blob_triggers	Trigger作为Blob类型存储(用于Quartz用户用JDBC创建他们自己定制的Trigger类型，JobStore 并不知道如何存储实例的时候)
qrtz_calendars	以Blob类型存储Quartz的Calendar日历信息， quartz可配置一个日历来指定一个时间范围
qrtz_cron_triggers	存储Cron Trigger，包括Cron表达式和时区信息。
qrtz_fired_triggers	存储与已触发的Trigger相关的状态信息，以及相联Job的执行信息
qrtz_job_details	存储每一个已配置的Job的详细信息
qrtz_locks	存储程序的非观锁的信息(假如使用了悲观锁)
qrtz_paused_trigger_graps	存储已暂停的Trigger组的信息
qrtz_scheduler_state	存储少量的有关 Scheduler的状态信息，和别的 Scheduler 实例(假如是用于一个集群中)
qrtz_simple_triggers	存储简单的 Trigger，包括重复次数，间隔，以及已触的次数
qrtz_triggers	存储已配置的 Trigger的信息
qrzt_simprop_triggers	
5. 核心类和关系

核心类 
（1）核心类 
QuartzSchedulerThread ：负责执行向QuartzScheduler注册的触发Trigger的工作的线程。 
ThreadPool：Scheduler使用一个线程池作为任务运行的基础设施，任务通过共享线程池中的线程提供运行效率。 
QuartzSchedulerResources：包含创建QuartzScheduler实例所需的所有资源（JobStore，ThreadPool等）。 
SchedulerFactory ：提供用于获取调度程序实例的客户端可用句柄的机制。 
JobStore： 通过类实现的接口，这些类要为org.quartz.core.QuartzScheduler的使用提供一个org.quartz.Job和org.quartz.Trigger存储机制。作业和触发器的存储应该以其名称和组的组合为唯一性。 
QuartzScheduler ：这是Quartz的核心，它是org.quartz.Scheduler接口的间接实现，包含调度org.quartz.Jobs，注册org.quartz.JobListener实例等的方法。 
Scheduler ：这是Quartz Scheduler的主要接口，代表一个独立运行容器。调度程序维护JobDetails和触发器的注册表。 一旦注册，调度程序负责执行作业，当他们的相关联的触发器触发（当他们的预定时间到达时）。 
Trigger ：具有所有触发器通用属性的基本接口，描述了job执行的时间出发规则。 - 使用TriggerBuilder实例化实际触发器。 
JobDetail ：传递给定作业实例的详细信息属性。 JobDetails将使用JobBuilder创建/定义。 
Job：要由表示要执行的“作业”的类实现的接口。只有一个方法 void execute(jobExecutionContext context) 
(jobExecutionContext 提供调度上下文各种信息，运行时数据保存在jobDataMap中) 
Job有个子接口StatefulJob ,代表有状态任务。 
有状态任务不可并发，前次任务没有执行完，后面任务处于阻塞等到。
关系-自己理解
这里写图片描述

一个job可以被多个Trigger 绑定，但是一个Trigger只能绑定一个job！

6. 配置文件 
quartz.properties 
//调度标识名 集群中每一个实例都必须使用相同的名称 （区分特定的调度器实例） 
org.quartz.scheduler.instanceName：DefaultQuartzScheduler 
//ID设置为自动获取 每一个必须不同 （所有调度器实例中是唯一的） 
org.quartz.scheduler.instanceId ：AUTO 
//数据保存方式为持久化 
org.quartz.jobStore.class ：org.quartz.impl.jdbcjobstore.JobStoreTX 
//表的前缀 
org.quartz.jobStore.tablePrefix ： QRTZ_ 
//设置为TRUE不会出现序列化非字符串类到 BLOB 时产生的类版本问题 
//org.quartz.jobStore.useProperties ： true 
//加入集群 true 为集群 false不是集群 
org.quartz.jobStore.isClustered ： false 
//调度实例失效的检查时间间隔 
org.quartz.jobStore.clusterCheckinInterval：20000 
//容许的最大作业延长时间 
org.quartz.jobStore.misfireThreshold ：60000 
//ThreadPool 实现的类名 
org.quartz.threadPool.class：org.quartz.simpl.SimpleThreadPool 
//线程数量 
org.quartz.threadPool.threadCount ： 10 
//线程优先级 
org.quartz.threadPool.threadPriority ： 5（threadPriority 属性的最大值是常量 java.lang.Thread.MAX_PRIORITY，等于10。最小值为常量 java.lang.Thread.MIN_PRIORITY，为1） 
//自创建父线程 
//org.quartz.threadPool.threadsInheritContextClassLoaderOfInitializingThread： true 
//数据库别名 
org.quartz.jobStore.dataSource ： qzDS 
//设置数据源 
org.quartz.dataSource.qzDS.driver:com.mysql.jdbc.Driver 
org.quartz.dataSource.qzDS.URL:jdbc:mysql://localhost:3306/quartz 
org.quartz.dataSource.qzDS.user:root 
org.quartz.dataSource.qzDS.password:123456 
org.quartz.dataSource.qzDS.maxConnection:10

7.JDBC插入表顺序

主要的JDBC操作类，执行sql顺序。 
Simple_trigger ：插入顺序 
qrtz_job_details —> qrtz_triggers —> qrtz_simple_triggers 
qrtz_fired_triggers 
Cron_Trigger：插入顺序 
qrtz_job_details —> qrtz_triggers —> qrtz_cron_triggers 
qrtz_fired_triggers

8.参考文章

官网： http://www.quartz-scheduler.org/ 
Quartz任务调度快速入门 ：http://sishuok.com/forum/posts/list/405.html 
深入解读Quartz的原理 ：http://lavasoft.blog.51cto.com/62575/181907/ 
基于 Quartz 开发企业级任务调度应用 ：http://www.ibm.com/developerworks/cn/opensource/os-cn-quartz/ 
quartz 数据库表含义解释 ：http://blog.csdn.net/tengdazhang770960436/article/details/51019291 
Quartz源码分析： https://my.oschina.net/chengxiaoyuan/blog/664833 
http://blog.csdn.net/u010648555/article/category/6601767 
Quartz系列：http://blog.csdn.net/Evankaka/article/category/3155529


## quartz加载方式
###通过ScheduleConfig文件加载
###listener加载
###spring配置文件加载
## 监控job
### JobRunShell
### TriggerListener(推荐)
## 防止同步执行
在Quartz中，通过注解@DisallowConcurrentExecution实现，当Trigger在完成retrieveJob后，将检查Job的注解，如果Job已经在运行，将在循环中continue跳过此Job
## 定制历史记录插件
### 1. Quartz的Plugin系统
插件系统可以充分利用各种Listener，在Trigger或者Job执行生命周期中实现AOP定制，比如下面就是在Trigger中加日志的插件。在配置文件中加入如下后，当Tigger完成后将自动打上Log。

```
org.quartz.plugin.his.class=org.quartz.plugins.history.LoggingTriggerHistoryPlugin
org.quartz.plugin.his.triggerFiredMessage=Trigger [{1}.{0}] fired job ...
org.quartz.plugin.his.triggerCompleteMessage=Trigger [{1}.{0}] completed ...
org.quartz.plugin.his.triggerMisfiredMessage=Trigger [{1}.{0}] misfired job ...
```

插件的实现原理很简单，它没有依赖Spring注入，而是在Factory初始化读取Prop时通过Class.newInstance生成对象实例，并读取prop(比如上面的triggerFiredMessage)调用反射setTriggerFiredMessage()注入属性

```
// 通过Class.newInstance，并反射`setProp`注入参数
org.quartz.impl.StdSchedulerFactory#instantiate();
// 以上图为例，首先截取掉前面的Group，然后实例化
def listenerClass = "org.quartz.plugins.history.LoggingTriggerHistoryPlugin"
def listener = loadHelper.loadClass(listenerClass).newInstance();
def m = listener.getClass().getMethod("set" + "TriggerFiredMessage", [String.class]);
m.invoke(listener,"Trigger [{1}.{0}] fired job ...")
```

### 2. 历史执行记录的Plugin定制

定制历史记录主要是方便日后有据可查(有锅不背)，以减少黑盒问题。下文只提供思路，不提供源码。

2.1. 定制Listener
定制JobListener与SchedulerPlugin接口，并在jobWasExecuted中进行记录操作，此处可以参考LoggingJobHistoryPlugin，并通过context获取Fire相关，Map相关以及Result相关

public interface JobListener {
  	//重写如下方法，并写入日志
    void jobWasExecuted(JobExecutionContext context,
            JobExecutionException jobException);
}
除了cron等基本信息外，这里最重要的就是JobExecutionContext.getResult()还有任务中的dataMap，需要结合具体业务进行反序列化，这里也就是为什么网上基本看不到开源实现的原因，因为都是与业务强绑定的。

2.2. 定制DBAppender
写入可以参考Logback中DBAppender的实现方式，不借助ORM框架实现高性能写入日志，拼装与commit操作，由于我删除了Event，因此没有加锁（本身没有加锁场景），可以参考这里，伪代码如下

String getInsertSQL(){
  	//此部分使用了SEQ，代替了默认的Trigger，只在Oracle下测试通过
    return "INSERT INTO JOB_HIS (ID, ....) VALUES (TASK_SQL.nextval, ?, ?, ?, ?...)";
}

protected appendDBlog(T t){
    Connection connection = null;
    PreparedStatement insertStatement = null;
    try{
        connection = getConnection();
        connection.setAutoCommit(false);
        insertStatement = connection.prepareStatement(getInsertSQL(), new String[] { EVENT_ID_COL_NAME });
      	prepareStatement.setString(1,..);
      	....
        connection.commit();
    }catch(Exception e){
        //各种异常
    }finally{
        //关闭DB流
    }
}
此处主要难点在移植DBAppeder，并复用Quartz的DataSource。

2.3. 定制日志DDL
DDL可以参考Logback的SQL，基本上可以复用，但是在Logback中，默认是使用的Trigger，由于这个Trigger权限有时候不好搞到，我们可以用SEQ代替。

当日志快要满时，我们可以手动或者Trigger删除旧的日志

delete from JOB_HIS where FIRE_DATE < sysdate - 90;
## 记一次Quartz重复调度(任务重复执行)的问题排查
https://segmentfault.com/a/1190000015492260