## 基于spring boot、以Redis为消息队列的邮件发送系统
  -  分为邮件生产端和消费端。中间以消息队列的方式将两个系统解耦。
  -  以允许多次生产、单次消费（保证同一消息只发送一次）的方式来邮件保证业务的可达性和幂等性。
  -  注意事项:需要自行搭建Mysql或MariaDB主从数据库，也可单数据库模式，只要把slave的数据库信息和master一致即可。
