-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 24, 2024 at 04:24 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `petshop`
--

-- --------------------------------------------------------

--
-- Table structure for table `cashiers`
--

CREATE TABLE `cashiers` (
  `id` int(11) NOT NULL,
  `email` varchar(255) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `username` varchar(255) NOT NULL,
  `role` enum('CASHIER','MANAGER') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `cashiers`
--

INSERT INTO `cashiers` (`id`, `email`, `name`, `password`, `phone`, `username`, `role`) VALUES
(4, 'johnsanip@gmail.com', 'Muhammad John a/l Sanip', 'johnsanip123', '0123344556', 'John Daus Sanip ', 'CASHIER'),
(5, 'bot123@gmail.com', 'Afiq Osman', 'afiqosman123', '0123344555', 'bot123', 'CASHIER');

-- --------------------------------------------------------

--
-- Table structure for table `managers`
--

CREATE TABLE `managers` (
  `id` int(11) NOT NULL,
  `email` varchar(255) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `username` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `managers`
--

INSERT INTO `managers` (`id`, `email`, `name`, `password`, `phone`, `role`, `username`) VALUES
(1, 'johnsmith@gmail.com', 'John Smith', 'johnsmith123', '0123456789', 'Manager', 'johnsmith');

-- --------------------------------------------------------

--
-- Table structure for table `products`
--

CREATE TABLE `products` (
  `id` int(11) NOT NULL,
  `brand` varchar(255) DEFAULT NULL,
  `category` varchar(255) DEFAULT NULL,
  `description` text DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `price` double NOT NULL,
  `quantity` int(11) NOT NULL,
  `img` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `products`
--

INSERT INTO `products` (`id`, `brand`, `category`, `description`, `name`, `price`, `quantity`, `img`) VALUES
(13, 'Whiskey', 'Cat Food', 'adadadadadadadad', 'Whiskey Tuna', 20, 13, '1732413922362_1.jpg'),
(14, 'Royal Canin', 'Cat Food', 't20 food for t20 cat', 'Royal Canin Mother & Babycat', 50, 12, '1732414443406_2.png'),
(16, 'belif', 'Cat Food', 'only for bengal cat', 'belif bengal', 34, 5, '1732417406018_3.jpg'),
(17, 'IAMS', 'Cat Food', 'Good for cat health', 'IAMS Proactive Health', 39, 2, '1732417516516_4.jpg'),
(19, 'IAMS', 'Dog Food', 'Good for dog health and you', 'IAMS Proactive Health Dog', 34, 4, '1732417660884_8.jpg'),
(21, 'IAMS', 'Dog food', 'Schmoll dog food', 'IAMS Proactive Shmoll Dog', 32, 4, '1732461588486_9.jpg');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `cashiers`
--
ALTER TABLE `cashiers`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK7cyuvx4fr1ryt9jaa0sjebh9v` (`email`),
  ADD UNIQUE KEY `UK95v170u8i5fajrdpcb5kqvevd` (`username`);

--
-- Indexes for table `managers`
--
ALTER TABLE `managers`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK9t1pttj95csyjl4it8lxgl7jj` (`email`),
  ADD UNIQUE KEY `UK47i207jqaocudxi77kquurcr4` (`username`);

--
-- Indexes for table `products`
--
ALTER TABLE `products`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `cashiers`
--
ALTER TABLE `cashiers`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `managers`
--
ALTER TABLE `managers`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `products`
--
ALTER TABLE `products`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
