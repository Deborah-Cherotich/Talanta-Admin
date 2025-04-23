-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Mar 14, 2025 at 04:04 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `talanta`
--

-- --------------------------------------------------------

--
-- Table structure for table `mpesa_tokens`
--

CREATE TABLE `mpesa_tokens` (
  `id` bigint(20) NOT NULL,
  `token` varchar(255) NOT NULL,
  `expires_in` int(11) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `mpesa_tokens`
--

INSERT INTO `mpesa_tokens` (`id`, `token`, `expires_in`, `created_at`) VALUES
(1, 'BG6YV6fw4td6CSoCnrhm74XoN8AF', 3599, '2025-02-14 06:17:11'),
(2, '45mWhbaF2UiSG62LOlUMFfjhcS37', 3599, '2025-02-14 07:30:48'),
(3, 'MUGiyDeBXmWAqHGXbbCFq6AYYb94', 3599, '2025-02-14 08:10:18'),
(4, '7su5nNd7BkITGRRio33i9tq1Opg0', 3599, '2025-02-14 08:21:17'),
(5, 'KT6bhemOUnVM3Ni9H9PnAoWSYCjK', 3599, '2025-02-14 08:33:23'),
(6, 'YKyAMvz5Z8QQD6E6HGmNHjnyVFvb', 3599, '2025-02-14 08:37:02'),
(7, 'fQxnca7ndAhMVOVHzUkgJS1bhNTy', 3599, '2025-02-14 08:40:40'),
(8, 'MCZQMLZtXf5XLA1OnqZLi3vxiN7t', 3599, '2025-02-14 08:45:37'),
(9, '3IjHPXvjk9Pdd4a2SODM6mAstJSV', 3599, '2025-02-14 09:41:07'),
(10, 'QApHZi6CGrXIQaqAOGLRvWAD0Akl', 3599, '2025-02-14 09:44:18'),
(11, '6TCQvSNfuWmy545CWQfEWbGdQVqA', 3599, '2025-02-14 09:58:02'),
(12, 'VyeHtdxO6LIIRrVQAOFRTfJNCPRO', 3599, '2025-02-14 10:01:19'),
(13, '8fc9PJbyhi2R7D8eAZzZsdwtZgnm', 3599, '2025-02-14 10:03:50'),
(14, 'vyWb4qpEJGCElau1PUpvSqL1d3pB', 3599, '2025-02-14 10:12:07'),
(15, 'MdfLJgcaEoWE2dzX8JfwmzspWInz', 3599, '2025-02-14 10:13:55'),
(16, 'sPGzxHpm2GGPxYbgCjM8GnNo1R4G', 3599, '2025-02-14 10:15:30'),
(17, 'fYVtpt4sdTcFQtuWHiLUOHIuCepJ', 3599, '2025-02-14 10:17:06'),
(18, 'mkI6S7HTmKy082LMQCM9gWGhSdR7', 3599, '2025-02-14 10:20:57'),
(19, 'DmpoN9xOzzqgqI2vjroi1No6wb7b', 3599, '2025-02-14 10:48:06'),
(20, 'qtt9clGGY84HKXX8I0jgOtptWkAr', 3599, '2025-02-14 10:54:07'),
(21, 'j105g4beE5rwtcS1iDFKi6Zr1cZ4', 3599, '2025-02-14 11:00:11'),
(22, 'qjsFlQI2AXanwvBKVDZXSPTnszfa', 3599, '2025-02-14 11:10:18'),
(23, 'GsnjmrbWjKUur3yAdxhSoD1Ce6ZF', 3599, '2025-02-14 17:40:19'),
(24, '2a2tAylKhHAKenvnkh8oRhfAevfR', 3599, '2025-02-14 17:43:36'),
(25, 'gA16xlrO8GsuNhblR2QKKFrQG71F', 3599, '2025-02-14 19:58:03'),
(26, 'lF8A2Dzr2y4eABGwUgsE9U7MYuRl', 3599, '2025-02-15 18:39:19'),
(27, 'EGk8cmWt8rA2Ds1Mf2tvOGcJyQxG', 3599, '2025-02-15 18:49:42'),
(28, 'ZcwkeAIZtP5tbwbZh3L5ppN9eMU2', 3599, '2025-02-16 00:51:17'),
(29, 'SdYYGQXK1MsZbInGthcW8mJWR9HD', 3599, '2025-02-16 01:32:45'),
(30, '7C77OUgWGvAO1hk6KHoNxcsLyZAy', 3599, '2025-02-16 01:47:34'),
(31, 'eC78by5f2kquzGA1jUcX7gdQmvJz', 3599, '2025-02-16 02:42:18'),
(32, '7PHzgzSnCLY2NeOejZPupx4EAoqQ', 3599, '2025-02-16 04:52:24'),
(33, '8eI3RSLVLwTame53PAr6CxNUGGig', 3599, '2025-02-16 05:51:22'),
(34, 'AhQBOEFQqomXsBHC1MZK2yP29IaC', 3599, '2025-02-16 05:58:35'),
(35, 'FRHu1wZTXxOduwY6APlwN3TqTcHJ', 3599, '2025-02-16 06:18:20'),
(36, 'XwXps9BUTLZba5jPHf0H9j7J9TWT', 3599, '2025-02-16 06:51:40'),
(37, 'CQAGs5NOlegb6d1HFO24rHSS1flK', 3599, '2025-02-16 07:12:01'),
(38, 'UjivH3dJFDB7RSwjR51XwvT20P3G', 3599, '2025-02-16 07:12:06'),
(39, 'OO95L5eTvbfWYAdm42UwU2zN1fz4', 3599, '2025-02-16 07:17:29'),
(40, '7Z24v9KbcTxH9Gy5CEBdiGN3TkuQ', 3599, '2025-02-16 07:22:15'),
(41, 'OKfMUkSrTmKc4NMnOVA6jeYBvASx', 3599, '2025-02-16 07:26:48'),
(42, 'qNAyEO27GMxeAt8CazV35qXx1j8g', 3599, '2025-02-16 07:29:22'),
(43, 'LAK9LlrSWKUXDsVj8SY2Y6QdVflQ', 3599, '2025-02-16 07:35:17'),
(44, '94nrMYAf9eoNZMAToHNhA78roDBC', 3599, '2025-02-16 07:41:43'),
(45, 'pQLPoto8Z7a5OzCtqT2p04olhmtg', 3599, '2025-02-16 07:49:27'),
(46, 'LrsyHNdIIUCR8RxWTQamGTQ4s7SV', 3599, '2025-02-16 10:05:48'),
(47, '7hAzPa9y2beY3G6w8TBB4NMj0TJv', 3599, '2025-02-16 10:33:07'),
(48, 'tkwUey3MkAApjAgBEZuR80glHVY9', 3599, '2025-02-16 10:46:14'),
(49, 'PoEMA5FQo85hLVWhBLrlVkBdO8LC', 3599, '2025-02-16 11:57:54'),
(50, 'ukwoD4leA84WetgSPQrYWtaH2Q5Z', 3599, '2025-02-16 12:04:08'),
(51, 'XHbsj90mcx8Ij5kJkq4kSCrjJsLZ', 3599, '2025-02-16 12:08:10'),
(52, '0RfT9ZThEM9uexd5MgXHtVXwujcK', 3599, '2025-02-16 12:10:50'),
(53, 'M2HnuSaTtMJDtpbAzuSRMVA8FVlj', 3599, '2025-02-16 12:24:53'),
(54, 'DIpkS5okJ3WnAdnlak7G5HjN7JFM', 3599, '2025-02-16 12:50:46'),
(55, 'MuKJ7BzdlnahpMuvxwQAVaPAhNm6', 3599, '2025-02-16 13:35:47'),
(56, 'pANqWI6G9vwS8K5aIbfXoW5zh8z2', 3599, '2025-02-16 13:48:08'),
(57, 'B2A5ywADePOppmIc8wCAVgnQZc5P', 3599, '2025-02-16 13:48:24'),
(58, 'yH0t6JAqtsmeM0o1qkHtD8JbhMpf', 3599, '2025-02-16 14:22:35'),
(59, 'gdiY9Hl4TytAAPEG1J1AZxCvkUbU', 3599, '2025-02-16 14:43:57'),
(60, 'Z0GqAGGLbm6GDOnG986ZiuPyMxkl', 3599, '2025-02-16 15:01:28'),
(61, 'tCGAAVRFIZsY9TRYI8ogl9tyB3SA', 3599, '2025-02-16 15:01:28'),
(62, 'F7M1AnHIwQDR2jkOjFER1R4zyRjR', 3599, '2025-02-16 15:01:28'),
(63, 'M24VofPiyi0m3o0VxWDNH2VrsHWa', 3599, '2025-02-16 15:01:28'),
(64, 'wfRGlhR94nb0SmjjJOkvWs3SiJFU', 3599, '2025-02-16 15:15:46'),
(65, 'fQMmoAdfppPM1H0e1AG3BaEp4JBh', 3599, '2025-02-16 15:15:46'),
(66, 'DrdtoZwwGpdLhlnsDk4THjHG6AFV', 3599, '2025-02-16 15:15:52'),
(67, 'aryAJrAQcNoLIXZ8t0t2ZQCQWsA0', 3599, '2025-02-17 13:58:18'),
(68, '1wQIfsPWANEWVSoBbaAv1PLKOvY1', 3599, '2025-02-17 16:07:29'),
(69, 'GseSGjrgHyelvhgnu8qNMj2VYk1Y', 3599, '2025-02-17 17:08:13'),
(70, 'FiYA9fHAcxuINetu8N4LGzuqym3X', 3599, '2025-02-18 05:38:46'),
(71, 'yeUyggTVX9b5NdMyGVSZly8Vin2A', 3599, '2025-02-18 05:44:11'),
(72, 'oSLgGoalNAdFDX6IIAkRmSQWd8RD', 3599, '2025-02-18 05:50:37'),
(73, 'Ot2ro4MWJAoBQmhQUF0YdWu2G4AW', 3599, '2025-02-18 05:58:04'),
(74, 'ANei547caFZYAQH0td5kjU6W6fSB', 3599, '2025-02-18 06:03:28'),
(75, 'n8sXfoTCNAwqWuFQfyXY7yKqcoAQ', 3599, '2025-02-18 06:17:00'),
(76, 'U9P2kOcyAadKvHFjXJXZjYqI9V2W', 3599, '2025-02-18 06:23:29'),
(77, 'plky0SHynxhCjdIr3gitr0NHVc6w', 3599, '2025-02-18 06:28:30'),
(78, 'MC7dOswUbkO930UiNWjBqVy6Frtl', 3599, '2025-02-18 06:33:29'),
(79, 'aBUlVViZDIs2AZhoWYCEV6GQoEAk', 3599, '2025-02-18 06:46:00'),
(80, 'lPfA4BKK4Tfa380eA5YBYPRe9T6O', 3599, '2025-02-18 06:58:11'),
(81, 'WTm3G0w0jXxoeufihNx2XHScAakl', 3599, '2025-02-18 07:08:14'),
(82, 'oVdC1niFN1lZU57R4S6G6i9IYA62', 3599, '2025-02-18 08:12:06'),
(83, 'ipS6NHeiG7UmB3Fjqa0R0uWeZ0gM', 3599, '2025-02-18 08:48:37'),
(84, 'dvA6yELe2JmuxNzYrPeVVhKG3O4d', 3599, '2025-03-13 14:20:08'),
(85, '0dHuLeJp1o2FXr9RcN5Coy4iZHC3', 3599, '2025-03-13 14:32:52'),
(86, 'cL0Y6MLE9heVme7W6MiY1Fy6mnY0', 3599, '2025-03-13 15:03:40'),
(87, 'GfAPbkCV5mWK0QLVIqoDcdgBoOax', 3599, '2025-03-13 15:07:29'),
(88, 'H0UiybHuWnjAR7BvfDZKHA9fZH7L', 3599, '2025-03-13 15:16:05'),
(89, 'POEb7Ad1PRJG5A4XqLzT6kSWgJL5', 3599, '2025-03-13 19:25:08'),
(90, 'dng93InWhtpmGKWD6r4uVa6Pb2Sn', 3599, '2025-03-13 20:06:54'),
(91, 'VSmharJiq5Ee0nZVQTR0zVJ3bW43', 3599, '2025-03-13 20:22:26'),
(92, 'wHFNPA3cCtl2eClgiAzllN5cqLkr', 3599, '2025-03-13 20:44:10'),
(93, 'c8LKSdefxsuVMbQVqvqmBZc9rLPQ', 3599, '2025-03-13 21:26:10'),
(94, 'PRccNBNr4KbFqpGeILLQrPSGAmWm', 3599, '2025-03-14 12:39:58');

-- --------------------------------------------------------

--
-- Table structure for table `otp`
--

CREATE TABLE `otp` (
  `id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `otp` varchar(10) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `otp`
--

INSERT INTO `otp` (`id`, `user_id`, `otp`, `created_at`) VALUES
(81, 84, '6964', '2025-03-14 12:26:02');

-- --------------------------------------------------------

--
-- Table structure for table `stk_transactions`
--

CREATE TABLE `stk_transactions` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `phone_number` varchar(20) NOT NULL,
  `amount` decimal(10,2) NOT NULL,
  `checkout_request_id` varchar(100) DEFAULT NULL,
  `response_code` varchar(10) DEFAULT NULL,
  `transaction_date` timestamp NOT NULL DEFAULT current_timestamp(),
  `result_code` varchar(10) DEFAULT NULL,
  `result_desc` text DEFAULT NULL,
  `status` varchar(20) DEFAULT 'PENDING',
  `mpesa_receipt_number` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `stk_transactions`
--

INSERT INTO `stk_transactions` (`id`, `user_id`, `phone_number`, `amount`, `checkout_request_id`, `response_code`, `transaction_date`, `result_code`, `result_desc`, `status`, `mpesa_receipt_number`) VALUES
(95, 84, '254715185271', 1.00, 'ws_CO_14032025154253016715185271', '0', '2025-03-14 12:41:23', '0', 'The service request is processed successfully.', 'PENDING', 'TCE7NUEFBJ');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` bigint(20) NOT NULL,
  `firstName` varchar(255) NOT NULL,
  `lastName` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `phoneNumber` varchar(20) NOT NULL,
  `dob` date NOT NULL,
  `levelOfEducation` varchar(255) NOT NULL,
  `token` text NOT NULL,
  `password` varchar(255) NOT NULL,
  `isAccountVerified` tinyint(1) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `firstName`, `lastName`, `email`, `phoneNumber`, `dob`, `levelOfEducation`, `token`, `password`, `isAccountVerified`) VALUES
(84, 'Clinton', 'Tovesi', 'clintonekesa90@gmail.com', '0715185271', '2025-03-12', 'PhD', 'b0ec34b4-3a8b-4724-84d6-3042e4f0fd0c-9yI1Vv0AlP07oxmZCHjZ6z-Lzw_7yTYVp-CFlBKC_9sjCT7vw91X5rT0y5OZf4brfJV3WCBgZLv8osXn0BY2-BlhAZN21JGcD-hfBEEyIeF4wR30W0FtgdXljdJetpa40iGenD7u-yqfpcwvsS3mMr9bdT7OVQtjL72itT8CqSjJxdvfn7irUd4kNjMcNS4Nq4OueHhs9oQQZ6N0ZU5IWKiAts7-RmQVfDjK74Kg6cF7gwS2OfJhlB6QapcjuhvFHRsw5zs1lGHc5hl4LLxWe7mVu_pDKMy8wQnjqLEpMoN5I7yq---BM0OE1dO_hNcG0aD6PAL-TsckhM3QxgwILQ', '1234567', 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `mpesa_tokens`
--
ALTER TABLE `mpesa_tokens`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `otp`
--
ALTER TABLE `otp`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `stk_transactions`
--
ALTER TABLE `stk_transactions`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `email` (`email`),
  ADD UNIQUE KEY `phoneNumber` (`phoneNumber`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `mpesa_tokens`
--
ALTER TABLE `mpesa_tokens`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=95;

--
-- AUTO_INCREMENT for table `otp`
--
ALTER TABLE `otp`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=82;

--
-- AUTO_INCREMENT for table `stk_transactions`
--
ALTER TABLE `stk_transactions`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=96;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=85;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `otp`
--
ALTER TABLE `otp`
  ADD CONSTRAINT `otp_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
