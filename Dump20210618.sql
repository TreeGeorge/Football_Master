-- MySQL dump 10.13  Distrib 8.0.21, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: footballmaster
-- ------------------------------------------------------
-- Server version	8.0.20

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
-- Table structure for table `banks`
--

DROP TABLE IF EXISTS `banks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `banks` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '은행 넘버',
  `name` varchar(30) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `banks`
--

LOCK TABLES `banks` WRITE;
/*!40000 ALTER TABLE `banks` DISABLE KEYS */;
INSERT INTO `banks` VALUES (1,'국민 은행'),(2,'기업 은행'),(3,'농협 은행'),(4,'신한 은행'),(5,'SC(스탠다드차타드) 은행'),(6,'KEB하나(구외환포함) 은행'),(7,'한국씨티(구 한미) 은행'),(8,'우리 은행'),(9,'경남 은행'),(10,'광주 은행'),(11,'대구 은행'),(12,'도이치 은행'),(13,'부산 은행'),(14,'산업 은행'),(15,'수협 은행'),(16,'전북 은행'),(17,'제주 은행'),(18,'새마을금고 은행'),(19,'신용협동조합 은행'),(20,'홍콩샹하이(HSBC) 은행'),(21,'상호저축은행중앙회 은행'),(22,'BOA(Bank of America) 은행'),(23,'제이피모간체이스 은행'),(24,'카카오뱅크'),(25,'케이뱅크'),(26,'유안타증권 은행');
/*!40000 ALTER TABLE `banks` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fields`
--

DROP TABLE IF EXISTS `fields`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fields` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '구장 번호',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '구장 이름',
  `place` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '구장 장소',
  `participation_fee` int NOT NULL DEFAULT '10000' COMMENT '참가비',
  `man_to_man_rule` enum('A','B','C','D','E') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'D' COMMENT '경기 인원 룰\nA = 3:3, B = 4:4, C = 5:5, D = 6:6, E = 7:7',
  `floor_material` enum('G','U') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'G' COMMENT '경기장 바닥\\nG = 잔디, U = 우레탄',
  `shose_rule` enum('F','E') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'F' COMMENT '신발 규칙\nF = 풋살화, E = 모든신발',
  `min_people` int NOT NULL DEFAULT '6' COMMENT '최소 모집 인원수',
  `max_people` int NOT NULL DEFAULT '21' COMMENT '최대 모집 인원수',
  `size` varchar(12) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '구장 크기',
  `shower_room` enum('Y','N') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'N' COMMENT '샤워장 유무\nY = 있음, N = 없음',
  `park` enum('Y','N') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'Y' COMMENT '주차 가능 여부\nY = 주차 가능, N = 주차 불가',
  `shose_rent` enum('Y','N') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'N' COMMENT '신발 대여 가능 여부\nY = 가능, N = 불가능',
  `clothes_rent` enum('Y','N') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'N' COMMENT '운동복 대여 가능 여부\nY = 가능, N = 불가능',
  `special_thing` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '특이사항',
  `area` enum('A','B','C','D','E','F','G','H','I','J','K','L','M') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'A' COMMENT '구장 서비스 지역\\\\nA = 서울, B = 경기, C = 인천, D = 대전,  E = 대구, F = 부산, G = 울산, H = 광주, I = 충북, J = 경북, K = 전북, L = 충남, M = 경남',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '구장 등록 시간',
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fields`
--

LOCK TABLES `fields` WRITE;
/*!40000 ALTER TABLE `fields` DISABLE KEYS */;
INSERT INTO `fields` VALUES (1,'용산 아이파크몰 (1구장/레알)','서울 용산구 한강대로23길 55',10000,'D','G','F',10,16,'40x20m','N','Y','N','N','임시','A','2021-06-10 14:42:27',NULL),(2,'인천 송도 트리플 스트리트 풋살장 A구장','인천 연수구 송도과학로16번길 33-3 C동 3F',10000,'D','G','F',10,16,'30x20m','N','Y','N','N','임시','C','2021-06-10 14:42:27',NULL),(3,'부산 팔라시오FC 해운대 풋살장 A구장','부산 해운대구 좌동순환로 395',10000,'D','G','F',10,16,'40x20m','N','Y','Y','N','임시','H','2021-06-10 14:42:27',NULL);
/*!40000 ALTER TABLE `fields` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `managers`
--

DROP TABLE IF EXISTS `managers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `managers` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '매니저 번호',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '매니저의 이름',
  `phone_number` varchar(20) NOT NULL COMMENT '매니저 핸드폰 번호',
  `birthday` date NOT NULL COMMENT '매니저의 생년월일',
  `greetings` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '자기 소개말',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `managers`
--

LOCK TABLES `managers` WRITE;
/*!40000 ALTER TABLE `managers` DISABLE KEYS */;
INSERT INTO `managers` VALUES (1,'송수영','01012345678','1997-06-07','임시','2021-06-10 15:14:14',NULL),(2,'이재록','01023456789','1998-04-13','임시','2021-06-10 15:14:14',NULL),(3,'조지환','01034567890','1994-07-20','임시','2021-06-10 15:14:14',NULL),(4,'천경태','01045678901','1995-11-24','임시','2021-06-10 15:14:14',NULL),(5,'최희재','01056789012','1996-04-01','임시','2021-06-10 15:14:14',NULL),(6,'황혜린','01067890123','2000-01-25','임시','2021-06-10 15:14:14',NULL);
/*!40000 ALTER TABLE `managers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `matches`
--

DROP TABLE IF EXISTS `matches`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `matches` (
  `id` int NOT NULL AUTO_INCREMENT,
  `match_date` datetime NOT NULL,
  `gender_rule` enum('M','F','H') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'H' COMMENT '경기 성별 룰\nM = 남성만, F = 여성만, H = 혼성',
  `level` enum('L','M','H') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'L' COMMENT '모집하는 선수의 실력\nL = 1~3, M = 2~3, H = 3',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `fields_id` int DEFAULT NULL,
  `managers_id` int DEFAULT NULL COMMENT '매니저 번호',
  PRIMARY KEY (`id`),
  KEY `fk_match_field1_idx` (`fields_id`),
  KEY `fk_match_manager1_idx` (`managers_id`),
  CONSTRAINT `fk_matches_fields_id` FOREIGN KEY (`fields_id`) REFERENCES `fields` (`id`),
  CONSTRAINT `fk_matches_managers_id` FOREIGN KEY (`managers_id`) REFERENCES `managers` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `matches`
--

LOCK TABLES `matches` WRITE;
/*!40000 ALTER TABLE `matches` DISABLE KEYS */;
INSERT INTO `matches` VALUES (1,'2021-07-05 12:00:00','F','L','2021-06-10 15:15:48','2021-06-14 13:13:51',1,1),(2,'2021-07-12 00:00:00','H','L','2021-06-10 15:15:48',NULL,2,2),(3,'2021-07-05 15:00:00','M','M','2021-06-10 15:15:48','2021-06-14 13:13:51',1,1),(4,'2021-07-08 15:00:00','F','L','2021-06-10 15:15:48','2021-06-14 13:13:51',1,1),(5,'2021-05-01 00:00:00','H','L','2021-06-18 16:36:54','2021-06-18 16:36:54',1,1);
/*!40000 ALTER TABLE `matches` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(100) NOT NULL,
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '사용자' COMMENT '유저 이름',
  `gender` enum('M','F') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'M' COMMENT '유저 성별\\nM = 남성 , F = 여성',
  `birthday` date NOT NULL DEFAULT '2000-01-01' COMMENT '유저 생일',
  `balance` int NOT NULL DEFAULT '0' COMMENT '유저의 남은 잔액',
  `fair_point` int NOT NULL DEFAULT '100' COMMENT '유저의 페어 포인트',
  `phone_number` varchar(11) NOT NULL DEFAULT '0' COMMENT '유저의 핸드폰 번호',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '유저 생성 시간',
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'asdf@naver.com','차은우','M','1993-11-25',20000,100,'1012345678','2021-06-10 14:43:27','2021-06-18 16:40:04'),(2,'zxcv@naver.com','이지은','F','2000-01-01',0,100,'0','2021-06-10 14:43:27','2021-06-16 19:17:45'),(3,'zxcv3@naver.com','사용자','M','2000-01-01',0,100,'0','2021-06-10 14:44:31',NULL),(4,'treegeorge92@gmail.com','사용자','M','2000-01-01',0,100,'0','2021-06-11 15:59:52','2021-06-11 15:59:52'),(5,'vcxzfdsa@naver.com','사용자','M','2000-01-01',0,100,'0','2021-06-16 14:40:21','2021-06-16 14:40:21'),(6,'asdfves@naver.com','사용자','M','2000-01-01',0,100,'0','2021-06-16 14:51:33','2021-06-16 14:51:33'),(7,'ufmfkjr@naver.com','사용자','M','2000-01-01',0,100,'0','2021-06-16 14:57:21','2021-06-16 14:57:21');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users_banks`
--

DROP TABLE IF EXISTS `users_banks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users_banks` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '유저와 뱅크를 이어주는 넘버',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `account_number` varchar(30) DEFAULT '0' COMMENT '유저의 계좌번호',
  `account_holder` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '예금주' COMMENT '예금주 명',
  `banks_id` int DEFAULT NULL COMMENT '은행 포링키',
  `users_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_user_bank_bank1_idx` (`banks_id`),
  KEY `fk_user_id_idx` (`users_id`),
  CONSTRAINT `fk_users_banks_banks_id` FOREIGN KEY (`users_id`) REFERENCES `users` (`id`),
  CONSTRAINT `fk_users_banks_users_id` FOREIGN KEY (`users_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users_banks`
--

LOCK TABLES `users_banks` WRITE;
/*!40000 ALTER TABLE `users_banks` DISABLE KEYS */;
INSERT INTO `users_banks` VALUES (2,'2021-06-10 15:16:14','2021-06-17 12:07:46','1111111','이지은',3,1),(3,'2021-06-10 15:16:14',NULL,'6512458210','이지은',1,2);
/*!40000 ALTER TABLE `users_banks` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users_cash`
--

DROP TABLE IF EXISTS `users_cash`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users_cash` (
  `id` int NOT NULL AUTO_INCREMENT,
  `type` enum('C','R','U','W') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'C' COMMENT '충전 타입\\\\nC = 충전, R = 환불, U = 사용, W = 경기 취소로 인한 환불',
  `price` int NOT NULL COMMENT '금액',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '결제 시간',
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `users_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_users_cash_id_idx` (`users_id`),
  CONSTRAINT `fk_users_cash_users_id` FOREIGN KEY (`users_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users_cash`
--

LOCK TABLES `users_cash` WRITE;
/*!40000 ALTER TABLE `users_cash` DISABLE KEYS */;
INSERT INTO `users_cash` VALUES (1,'C',30000,'2021-06-10 15:16:48',NULL,1),(2,'U',10000,'2021-06-10 15:16:48','2021-06-18 14:31:49',1),(3,'U',10000,'2021-06-10 15:16:48','2021-06-18 14:31:49',1),(4,'C',40000,'2021-06-10 15:16:48',NULL,2),(17,'U',10000,'2021-06-18 16:40:04','2021-06-18 16:40:04',1);
/*!40000 ALTER TABLE `users_cash` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users_matches`
--

DROP TABLE IF EXISTS `users_matches`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users_matches` (
  `id` int NOT NULL AUTO_INCREMENT,
  `matches_id` int DEFAULT NULL,
  `users_id` int DEFAULT NULL,
  `status` enum('A','C') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'A' COMMENT '유저의 매치 신청의 취소 여부 상태\\nA = 신청상태, C = 취소상태',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `fk_user_match_match1_idx` (`matches_id`),
  KEY `fk_user_id_idx` (`users_id`),
  CONSTRAINT `fk_users_matches_matches_id` FOREIGN KEY (`matches_id`) REFERENCES `matches` (`id`),
  CONSTRAINT `fk_users_matches_users_id` FOREIGN KEY (`users_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users_matches`
--

LOCK TABLES `users_matches` WRITE;
/*!40000 ALTER TABLE `users_matches` DISABLE KEYS */;
INSERT INTO `users_matches` VALUES (1,1,1,'C','2021-06-10 15:16:39','2021-06-18 16:05:49'),(2,2,1,'A','2021-06-10 15:16:39',NULL),(3,5,1,'A','2021-06-18 16:37:39','2021-06-18 16:42:21');
/*!40000 ALTER TABLE `users_matches` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-06-18 17:01:12
