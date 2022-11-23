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
-- Table structure for table `isplata`
--

DROP TABLE IF EXISTS `isplata`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `isplata` (
  `IdTra` int(11) NOT NULL AUTO_INCREMENT,
  `Iznos` int(11) NOT NULL,
  `Datum` datetime NOT NULL,
  `Svrha` varchar(45) DEFAULT NULL,
  `IdRac` int(11) NOT NULL,
  `NazivFilijale` varchar(45) DEFAULT NULL,
  `BrTransakcije` int(11) NOT NULL,
  PRIMARY KEY (`IdTra`),
  KEY `IdRacIspl_idx` (`IdRac`),
  CONSTRAINT `IdRacIspl` FOREIGN KEY (`IdRac`) REFERENCES `racun` (`IdRac`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `isplata`
--

LOCK TABLES `isplata` WRITE;
/*!40000 ALTER TABLE `isplata` DISABLE KEYS */;
INSERT INTO `isplata` VALUES (1,110,'2022-07-07 16:13:13',NULL,1,'Nisi ovo implementiro',9),(2,20010,'2022-07-07 16:13:37',NULL,1,'Nisi ovo implementiro',10),(3,2000,'2022-07-07 16:13:57',NULL,1,'Nisi ovo implementiro',11),(4,2000,'2022-07-07 16:14:02',NULL,1,'Nisi ovo implementiro',10),(5,2000,'2022-07-07 16:14:15',NULL,1,'Nisi ovo implementiro',11),(6,2000,'2022-07-07 16:26:04','Svrha Isplate',1,'Nisi ovo implementiro',16),(7,2000,'2022-07-07 16:27:52','Svrha Isplate',1,'F1',17),(8,21000,'2022-07-07 16:28:17','Svrha Isplate',1,'F1',18),(9,21000,'2022-07-07 19:08:09','Svrha Isplate',1,'F1',20);
/*!40000 ALTER TABLE `isplata` ENABLE KEYS */;
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
