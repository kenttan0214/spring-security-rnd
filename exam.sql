-- phpMyAdmin SQL Dump
-- version 4.0.10.12
-- http://www.phpmyadmin.net
--
-- Host: 127.3.125.130:3306
-- Generation Time: Nov 27, 2016 at 07:07 AM
-- Server version: 5.5.52
-- PHP Version: 5.3.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `exam`
--

-- --------------------------------------------------------

--
-- Table structure for table `tbl_authorities`
--

CREATE TABLE IF NOT EXISTS `tbl_authorities` (
  `AUTHORITY_ID` int(11) NOT NULL AUTO_INCREMENT,
  `AUTHORITY_DESC` varchar(10) NOT NULL,
  PRIMARY KEY (`AUTHORITY_ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `tbl_authorities`
--

INSERT INTO `tbl_authorities` (`AUTHORITY_ID`, `AUTHORITY_DESC`) VALUES
(1, 'ADMIN'),
(2, 'USER');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_user`
--

CREATE TABLE IF NOT EXISTS `tbl_user` (
  `USER_ID` int(11) NOT NULL AUTO_INCREMENT,
  `USER_NAME` varchar(25) NOT NULL,
  `USER_EMAIL` varchar(256) NOT NULL,
  `USER_PASSWORD` varchar(256) NOT NULL,
  `ACCOUNT_EXPIRED_DATE` date NOT NULL,
  `ACCOUNT_LOCKED` tinyint(1) NOT NULL DEFAULT '0',
  `PASSWORD_EXPIRED` tinyint(1) NOT NULL DEFAULT '0',
  `ACCOUNT_ENABLE` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`USER_ID`),
  UNIQUE KEY `USER_NAME` (`USER_NAME`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `tbl_user`
--

INSERT INTO `tbl_user` (`USER_ID`, `USER_NAME`, `USER_EMAIL`, `USER_PASSWORD`, `ACCOUNT_EXPIRED_DATE`, `ACCOUNT_LOCKED`, `PASSWORD_EXPIRED`, `ACCOUNT_ENABLE`) VALUES
(1, 'Kent', 'kent.tan0214@gmail.com', '123123123', '2099-02-14', 0, 0, 1);

-- --------------------------------------------------------

--
-- Table structure for table `tbl_user_authorities`
--

CREATE TABLE IF NOT EXISTS `tbl_user_authorities` (
  `USER_ID` int(11) NOT NULL,
  `AUTHORITY_ID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tbl_user_authorities`
--

INSERT INTO `tbl_user_authorities` (`USER_ID`, `AUTHORITY_ID`) VALUES
(1, 1),
(1, 2);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
