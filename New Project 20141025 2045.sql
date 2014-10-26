-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	5.5.23


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


--
-- Create schema hospital
--

CREATE DATABASE IF NOT EXISTS hospital;
USE hospital;

--
-- Definition of table `card`
--

DROP TABLE IF EXISTS `card`;
CREATE TABLE `card` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `age` smallint(5) unsigned NOT NULL DEFAULT '0',
  `sex` enum('male','female') NOT NULL,
  `note` text,
  `isAgain` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `card`
--

/*!40000 ALTER TABLE `card` DISABLE KEYS */;
INSERT INTO `card` (`id`,`name`,`age`,`sex`,`note`,`isAgain`) VALUES 
 (22,'Card0',30,'female',NULL,0x00),
 (23,'Card1',31,'male',NULL,0x00),
 (24,'Card2',32,'female',NULL,0x00),
 (25,'Card3',33,'male',NULL,0x00),
 (26,'Card4',34,'female',NULL,0x00),
 (27,'Card5',35,'male',NULL,0x00),
 (28,'Card6',36,'female',NULL,0x00);
/*!40000 ALTER TABLE `card` ENABLE KEYS */;


--
-- Definition of table `diagnosis`
--

DROP TABLE IF EXISTS `diagnosis`;
CREATE TABLE `diagnosis` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(100) NOT NULL,
  `group_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `Diagnosis-GroupOwner_idx` (`group_id`),
  CONSTRAINT `Diagnosis-GroupOwner` FOREIGN KEY (`group_id`) REFERENCES `groups` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `diagnosis`
--

/*!40000 ALTER TABLE `diagnosis` DISABLE KEYS */;
/*!40000 ALTER TABLE `diagnosis` ENABLE KEYS */;


--
-- Definition of table `groups`
--

DROP TABLE IF EXISTS `groups`;
CREATE TABLE `groups` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `group_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `GroupOwner_idx` (`group_id`),
  CONSTRAINT `GroupOwner` FOREIGN KEY (`group_id`) REFERENCES `groups` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `groups`
--

/*!40000 ALTER TABLE `groups` DISABLE KEYS */;
/*!40000 ALTER TABLE `groups` ENABLE KEYS */;


--
-- Definition of table `notes`
--

DROP TABLE IF EXISTS `notes`;
CREATE TABLE `notes` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `user_id` int(10) NOT NULL,
  `card_id` int(10) NOT NULL,
  `hide` bit(1) NOT NULL DEFAULT b'1',
  `date` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `hidden_note` text,
  PRIMARY KEY (`id`),
  KEY `Notes-Users_idx` (`user_id`),
  KEY `Notes-Cars_idx` (`card_id`),
  CONSTRAINT `Notes-Cars` FOREIGN KEY (`card_id`) REFERENCES `card` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `Notes-Users` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `notes`
--

/*!40000 ALTER TABLE `notes` DISABLE KEYS */;
/*!40000 ALTER TABLE `notes` ENABLE KEYS */;


--
-- Definition of table `session`
--

DROP TABLE IF EXISTS `session`;
CREATE TABLE `session` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `card_id` int(11) NOT NULL,
  `result` bit(1) NOT NULL DEFAULT b'0',
  `diagnosis_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `Sessions-Cards_idx` (`card_id`),
  KEY `Sessions-Diagnosis_idx` (`diagnosis_id`),
  CONSTRAINT `Sessions-Cards` FOREIGN KEY (`card_id`) REFERENCES `card` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `Sessions-Diagnosis` FOREIGN KEY (`diagnosis_id`) REFERENCES `diagnosis` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `session`
--

/*!40000 ALTER TABLE `session` DISABLE KEYS */;
/*!40000 ALTER TABLE `session` ENABLE KEYS */;


--
-- Definition of table `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `access_level` smallint(6) NOT NULL DEFAULT '-1',
  `phone` varchar(45) DEFAULT NULL,
  `pwd` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `users`
--

/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` (`id`,`name`,`access_level`,`phone`,`pwd`) VALUES 
 (1,'Ivanov Ivan',24,NULL,'21'),
 (23,'Porky1',0,'134321223','1111'),
 (24,'Porky2',1,'132135223','0000'),
 (25,'Porky3',2,'134633432','jsfn'),
 (26,'Porky4',2,'435446333','sdfg'),
 (27,'Porky5',1,'123456789','dsgd'),
 (28,'Porky6',0,'444325252','dsfd'),
 (29,'Porky7',0,'657345434','dsfs');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;




/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
