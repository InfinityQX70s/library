SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

DROP SCHEMA IF EXISTS `library` ;
CREATE SCHEMA IF NOT EXISTS `library` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `library` ;

-- -----------------------------------------------------
-- Table `library`.`User`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `library`.`User` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(255) NOT NULL,
  `password` VARCHAR(32) NOT NULL,
  `firstName` VARCHAR(45) NOT NULL,
  `lastName` VARCHAR(45) NOT NULL,
  `isLibrarian` BIT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `library`.`Genre`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `library`.`Genre` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `library`.`Author`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `library`.`Author` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `alias` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `library`.`Book`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `library`.`Book` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `count` INT NOT NULL,
  `year` INT NOT NULL,
  `authorId` INT NOT NULL,
  `genreId` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Book_Author_idx` (`authorId` ASC),
  INDEX `fk_Book_Genre1_idx` (`genreId` ASC),
  CONSTRAINT `fk_Book_Author`
    FOREIGN KEY (`authorId`)
    REFERENCES `library`.`Author` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Book_Genre1`
    FOREIGN KEY (`genreId`)
    REFERENCES `library`.`Genre` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `library`.`Status`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `library`.`Status` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `library`.`BookOrder`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `library`.`BookOrder` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `userId` INT NOT NULL,
  `bookId` INT NOT NULL,
  `statusId` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_BookOrder_User1_idx` (`userId` ASC),
  INDEX `fk_BookOrder_Book1_idx` (`bookId` ASC),
  INDEX `fk_BookOrder_Status1_idx` (`statusId` ASC),
  CONSTRAINT `fk_BookOrder_User1`
    FOREIGN KEY (`userId`)
    REFERENCES `library`.`User` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_BookOrder_Book1`
    FOREIGN KEY (`bookId`)
    REFERENCES `library`.`Book` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_BookOrder_Status1`
    FOREIGN KEY (`statusId`)
    REFERENCES `library`.`Status` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
