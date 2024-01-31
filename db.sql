/*
SQLyog Community v13.0.1 (64 bit)
MySQL - 5.5.20-log : Database - turf
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`turf` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `turf`;

/*Table structure for table `bank` */

DROP TABLE IF EXISTS `bank`;

CREATE TABLE `bank` (
  `bank_id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) DEFAULT NULL,
  `bname` varchar(50) DEFAULT NULL,
  `accno` decimal(10,0) DEFAULT NULL,
  `ifsc` varchar(15) DEFAULT NULL,
  `phno` decimal(10,0) DEFAULT NULL,
  `balance` int(11) DEFAULT NULL,
  PRIMARY KEY (`bank_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `bank` */

/*Table structure for table `booking` */

DROP TABLE IF EXISTS `booking`;

CREATE TABLE `booking` (
  `bid` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) DEFAULT NULL,
  `tid` int(11) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `bdate` varchar(45) DEFAULT NULL,
  `slot` varchar(45) DEFAULT NULL,
  `status` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`bid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `booking` */

/*Table structure for table `facilities` */

DROP TABLE IF EXISTS `facilities`;

CREATE TABLE `facilities` (
  `fid` int(11) NOT NULL AUTO_INCREMENT,
  `tid` int(11) DEFAULT NULL,
  `facility` varchar(50) DEFAULT NULL,
  `description` varchar(50) DEFAULT NULL,
  `image` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`fid`),
  UNIQUE KEY `facility` (`facility`,`description`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `facilities` */

/*Table structure for table `fee_details` */

DROP TABLE IF EXISTS `fee_details`;

CREATE TABLE `fee_details` (
  `fid` int(11) NOT NULL AUTO_INCREMENT,
  `tid` int(11) DEFAULT NULL,
  `amount` float DEFAULT NULL,
  PRIMARY KEY (`fid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `fee_details` */

/*Table structure for table `feedback` */

DROP TABLE IF EXISTS `feedback`;

CREATE TABLE `feedback` (
  `fid` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) DEFAULT NULL,
  `feedback` varchar(30) DEFAULT NULL,
  `rating` varchar(50) DEFAULT NULL,
  `date` date DEFAULT NULL,
  PRIMARY KEY (`fid`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `feedback` */

insert  into `feedback`(`fid`,`uid`,`feedback`,`rating`,`date`) values 
(2,1,NULL,NULL,NULL);

/*Table structure for table `login` */

DROP TABLE IF EXISTS `login`;

CREATE TABLE `login` (
  `lid` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) DEFAULT NULL,
  `password` varchar(20) DEFAULT NULL,
  `type` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`lid`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;

/*Data for the table `login` */

insert  into `login`(`lid`,`username`,`password`,`type`) values 
(1,'admin','admin','admin'),
(5,'turf','turf','turf'),
(6,'user','user','user'),
(10,'turf2','turt2','turf'),
(11,'aa','aa','turf');

/*Table structure for table `payment` */

DROP TABLE IF EXISTS `payment`;

CREATE TABLE `payment` (
  `pid` int(11) NOT NULL AUTO_INCREMENT,
  `tid` int(11) DEFAULT NULL,
  `amount` float DEFAULT NULL,
  `date` date DEFAULT NULL,
  `status` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`pid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `payment` */

/*Table structure for table `sloat_status` */

DROP TABLE IF EXISTS `sloat_status`;

CREATE TABLE `sloat_status` (
  `ssid` int(11) NOT NULL AUTO_INCREMENT,
  `tid` int(11) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `sloat` varchar(50) DEFAULT NULL,
  `status` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`ssid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `sloat_status` */

insert  into `sloat_status`(`ssid`,`tid`,`date`,`sloat`,`status`) values 
(1,1,'2002-03-06','12 to 13','booked');

/*Table structure for table `turf_registration` */

DROP TABLE IF EXISTS `turf_registration`;

CREATE TABLE `turf_registration` (
  `tid` int(11) NOT NULL AUTO_INCREMENT,
  `lid` int(11) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  `place` varchar(20) DEFAULT NULL,
  `landmark` varchar(20) DEFAULT NULL,
  `phno` varchar(20) DEFAULT NULL,
  `mail_id` varchar(100) DEFAULT NULL,
  `image` varchar(50) DEFAULT NULL,
  `latti` varchar(15) DEFAULT NULL,
  `longi` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`tid`),
  UNIQUE KEY `phno` (`phno`,`mail_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*Data for the table `turf_registration` */

insert  into `turf_registration`(`tid`,`lid`,`name`,`place`,`landmark`,`phno`,`mail_id`,`image`,`latti`,`longi`) values 
(1,10,'turf','kozhikode','landmark 1','9632587412','turf@gmail.com','20230114_102734.jpg','11.2772','75.12262'),
(2,5,'turf3','koyilandy','landamrk3','8523697412','turf3@gmail.com','20230114_105753.jpg','6','2'),
(3,11,'aa','aa','aaa','9876543210','aa@gmail.com','20230114_105753.jpg','11.234','75.2345');

/*Table structure for table `turfrating` */

DROP TABLE IF EXISTS `turfrating`;

CREATE TABLE `turfrating` (
  `rid` int(11) NOT NULL AUTO_INCREMENT,
  `tid` int(11) DEFAULT NULL,
  `uid` int(11) DEFAULT NULL,
  `rating` varchar(45) DEFAULT NULL,
  `feedback` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`rid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `turfrating` */

insert  into `turfrating`(`rid`,`tid`,`uid`,`rating`,`feedback`) values 
(1,2,0,'5','nn');

/*Table structure for table `user_reg` */

DROP TABLE IF EXISTS `user_reg`;

CREATE TABLE `user_reg` (
  `uid` int(11) NOT NULL AUTO_INCREMENT,
  `lid` int(11) DEFAULT NULL,
  `fname` varchar(20) DEFAULT NULL,
  `mname` varchar(20) DEFAULT NULL,
  `lname` varchar(20) DEFAULT NULL,
  `phno` varchar(20) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`uid`),
  UNIQUE KEY `phno` (`phno`,`email`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*Data for the table `user_reg` */

insert  into `user_reg`(`uid`,`lid`,`fname`,`mname`,`lname`,`phno`,`email`) values 
(1,NULL,NULL,NULL,NULL,NULL,NULL);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
