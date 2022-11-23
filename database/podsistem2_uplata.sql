-- MySQL dump 10.13  Distrib 8.0.26, for Win64 (x86_64)
--
-- Host: localhost    Database: podsistem2
-- ------------------------------------------------------
-- Server version	8.0.18

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `uplata`
--

DROP TABLE IF EXISTS `uplata`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `uplata` (
  `IdTra` int(11) NOT NULL AUTO_INCREMENT,
  `Iznos` int(11) NOT NULL,
  `Datum` datetime NOT NULL,
  `Svrha` varchar(45) DEFAULT NULL,
  `IdRac` int(11) NOT NULL,
  `NazivFilijale` varchar(45) DEFAULT NULL,
  `BrTransakcije` int(11) NOT NULL,
  PRIMARY KEY (`IdTra`),
  KEY `IdRacUpl_idx` (`IdRac`),
  CONSTRAINT `IdRacUpl` FOREIGN KEY (`IdRac`) REFERENCES `racun` (`IdRac`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `uplata`
--

LOCK TABLES `uplata` WRITE;
/*!40000 ALTER TABLE `uplata` DISABLE KEYS */;
INSERT INTO `uplata` VALUES (1,110,'2022-07-07 16:09:03',NULL,1,'Ovo nisi implementiro',7),(2,110,'2022-07-07 16:09:46',NULL,1,'Ovo nisi implementiro',8),(3,2000,'2022-07-07 16:23:05','Svrha Isplate',1,'fili',12),(4,2000,'2022-07-07 16:24:21','Svrha Isplate',1,'fili',13),(5,2000,'2022-07-07 16:24:37','Svrha Isplate',1,'fili',14),(6,2000,'2022-07-07 16:25:30','Svrha Isplate',1,'F1',15),(7,2000,'2022-07-07 19:07:31','Svrha Isplate',1,'F1',19),(8,22000,'2022-07-07 19:07:34','Svrha Isplate',1,'F1',19);
/*!40000 ALTER TABLE `uplata` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-07-07 19:20:15
