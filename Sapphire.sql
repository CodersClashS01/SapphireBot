-- --------------------------------------------------------
-- Host:                         devcorpstudios.de
-- Server Version:               5.7.22-0ubuntu18.04.1 - (Ubuntu)
-- Server Betriebssystem:        Linux
-- HeidiSQL Version:             9.4.0.5125
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Exportiere Datenbank Struktur für sapphirebot
CREATE DATABASE IF NOT EXISTS `sapphirebot` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `sapphirebot`;

-- Exportiere Struktur von Tabelle sapphirebot.jack_players
CREATE TABLE IF NOT EXISTS `jack_players` (
  `guild` varchar(50) DEFAULT NULL,
  `userid` varchar(50) DEFAULT NULL,
  `session_id` varchar(50) DEFAULT 'null',
  `number` int(11) DEFAULT '0',
  `cards` varchar(50) DEFAULT 'null',
  `ever_cards` varchar(50) DEFAULT 'null'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Daten Export vom Benutzer nicht ausgewählt
-- Exportiere Struktur von Tabelle sapphirebot.jack_sessions
CREATE TABLE IF NOT EXISTS `jack_sessions` (
  `guild` varchar(50) DEFAULT NULL,
  `creator` varchar(50) DEFAULT NULL,
  `session_id` varchar(50) DEFAULT 'null',
  `players` varchar(60000) DEFAULT NULL,
  `started` varchar(50) DEFAULT 'false',
  `gaming` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Daten Export vom Benutzer nicht ausgewählt
-- Exportiere Struktur von Tabelle sapphirebot.reports
CREATE TABLE IF NOT EXISTS `reports` (
  `guild` text NOT NULL,
  `victim` text NOT NULL,
  `executor` text NOT NULL,
  `reason` text NOT NULL,
  `date` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Daten Export vom Benutzer nicht ausgewählt
-- Exportiere Struktur von Tabelle sapphirebot.users
CREATE TABLE IF NOT EXISTS `users` (
  `id` varchar(50) NOT NULL,
  `bio` varchar(50) DEFAULT 'Ziemlich leer hier...',
  `xp` bigint(20) DEFAULT '0',
  `rep` bigint(20) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Daten Export vom Benutzer nicht ausgewählt
-- Exportiere Struktur von Tabelle sapphirebot.user_reports
CREATE TABLE IF NOT EXISTS `user_reports` (
  `guild` varchar(50) DEFAULT NULL,
  `use_text` varchar(50) DEFAULT 'false',
  `text_channel_id` varchar(50) DEFAULT NULL,
  `no_notify` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Daten Export vom Benutzer nicht ausgewählt
-- Exportiere Struktur von Tabelle sapphirebot.votes
CREATE TABLE IF NOT EXISTS `votes` (
  `id` text,
  `msgid` text,
  `question` text,
  `options` text,
  `votes` text
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Daten Export vom Benutzer nicht ausgewählt
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
