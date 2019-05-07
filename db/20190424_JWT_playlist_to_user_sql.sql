-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema playlistgenerator
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema playlistgenerator
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `playlistgenerator` DEFAULT CHARACTER SET utf8 ;
USE `playlistgenerator` ;

-- -----------------------------------------------------
-- Table `playlistgenerator`.`albums`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `playlistgenerator`.`albums` (
  `album_id` INT(11) NOT NULL,
  `album_title` VARCHAR(250) NOT NULL,
  `album_tracklist_url` VARCHAR(250) NOT NULL,
  PRIMARY KEY (`album_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE UNIQUE INDEX `album_id_UNIQUE` ON `playlistgenerator`.`albums` (`album_id` ASC);


-- -----------------------------------------------------
-- Table `playlistgenerator`.`artists`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `playlistgenerator`.`artists` (
  `artist_id` INT(11) NOT NULL,
  `artist_name` VARCHAR(250) NOT NULL,
  `artist_tracklist_url` VARCHAR(250) NOT NULL,
  PRIMARY KEY (`artist_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE UNIQUE INDEX `artist_id_UNIQUE` ON `playlistgenerator`.`artists` (`artist_id` ASC);


-- -----------------------------------------------------
-- Table `playlistgenerator`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `playlistgenerator`.`users` (
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `enabled` INT(11) NULL DEFAULT 1,
  PRIMARY KEY (`username`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `playlistgenerator`.`authority`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `playlistgenerator`.`authority` (
  `username` VARCHAR(45) NOT NULL,
  `role_type` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`username`),
  CONSTRAINT `fk_authority_users1`
    FOREIGN KEY (`username`)
    REFERENCES `playlistgenerator`.`users` (`username`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE INDEX `fk_authority_users1_idx` ON `playlistgenerator`.`authority` (`username` ASC);


-- -----------------------------------------------------
-- Table `playlistgenerator`.`genres`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `playlistgenerator`.`genres` (
  `genre_id` INT(11) NOT NULL,
  `genre_name` VARCHAR(45) NOT NULL,
  `genre_image_url` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`genre_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE UNIQUE INDEX `genre_id_UNIQUE` ON `playlistgenerator`.`genres` (`genre_id` ASC);

CREATE UNIQUE INDEX `genre_name_UNIQUE` ON `playlistgenerator`.`genres` (`genre_name` ASC);


-- -----------------------------------------------------
-- Table `playlistgenerator`.`user_details`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `playlistgenerator`.`user_details` (
  `user_id` INT(11) NOT NULL,
  `username` VARCHAR(45) NOT NULL,
  `first_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`user_id`),
  CONSTRAINT `fk_user_details_users1`
    FOREIGN KEY (`username`)
    REFERENCES `playlistgenerator`.`users` (`username`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE UNIQUE INDEX `user_id_UNIQUE` ON `playlistgenerator`.`user_details` (`user_id` ASC);

CREATE INDEX `fk_user_details_users1_idx` ON `playlistgenerator`.`user_details` (`username` ASC);


-- -----------------------------------------------------
-- Table `playlistgenerator`.`playlists`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `playlistgenerator`.`playlists` (
  `playlist_id` INT(11) NOT NULL AUTO_INCREMENT,
  `playlist_title` VARCHAR(45) NOT NULL,
  `user_id` INT(11) NOT NULL,
  PRIMARY KEY (`playlist_id`),
  CONSTRAINT `fk_playlists_user_details1`
    FOREIGN KEY (`user_id`)
    REFERENCES `playlistgenerator`.`user_details` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = '	';

CREATE UNIQUE INDEX `playlist_id_UNIQUE` ON `playlistgenerator`.`playlists` (`playlist_id` ASC);

CREATE INDEX `fk_playlists_user_details1_idx` ON `playlistgenerator`.`playlists` (`user_id` ASC);


-- -----------------------------------------------------
-- Table `playlistgenerator`.`genre_to_playlist`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `playlistgenerator`.`genre_to_playlist` (
  `genre_to_playlist_id` INT(11) NOT NULL AUTO_INCREMENT,
  `genre_id` INT(11) NOT NULL,
  `playlist_id` INT(11) NOT NULL,
  PRIMARY KEY (`genre_to_playlist_id`),
  CONSTRAINT `fk_track_to_playlist_genres1`
    FOREIGN KEY (`genre_id`)
    REFERENCES `playlistgenerator`.`genres` (`genre_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_track_to_playlist_playlists1`
    FOREIGN KEY (`playlist_id`)
    REFERENCES `playlistgenerator`.`playlists` (`playlist_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE UNIQUE INDEX `track_to_artist_id_UNIQUE` ON `playlistgenerator`.`genre_to_playlist` (`genre_to_playlist_id` ASC);

CREATE INDEX `fk_track_to_playlist_genres1_idx` ON `playlistgenerator`.`genre_to_playlist` (`genre_id` ASC);

CREATE INDEX `fk_track_to_playlist_playlists1_idx` ON `playlistgenerator`.`genre_to_playlist` (`playlist_id` ASC);


-- -----------------------------------------------------
-- Table `playlistgenerator`.`tracks`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `playlistgenerator`.`tracks` (
  `track_id` INT(11) NOT NULL,
  `track_title` VARCHAR(250) NOT NULL,
  `track_link` VARCHAR(250) NOT NULL,
  `duration` INT(11) NOT NULL,
  `rank` INT(11) NOT NULL,
  `track_preview_url` VARCHAR(250) NULL DEFAULT NULL,
  `album_id` INT(11) NOT NULL,
  `genre_id` INT(11) NOT NULL,
  `artist_id` INT(11) NOT NULL,
  PRIMARY KEY (`track_id`),
  CONSTRAINT `fk_tracks_albums`
    FOREIGN KEY (`album_id`)
    REFERENCES `playlistgenerator`.`albums` (`album_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_tracks_artists_idx`
    FOREIGN KEY (`artist_id`)
    REFERENCES `playlistgenerator`.`artists` (`artist_id`),
  CONSTRAINT `fk_tracks_genres1`
    FOREIGN KEY (`genre_id`)
    REFERENCES `playlistgenerator`.`genres` (`genre_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE UNIQUE INDEX `track_id_UNIQUE` ON `playlistgenerator`.`tracks` (`track_id` ASC);

CREATE INDEX `fk_tracks_albums_idx` ON `playlistgenerator`.`tracks` (`album_id` ASC);

CREATE INDEX `fk_tracks_genres1_idx` ON `playlistgenerator`.`tracks` (`genre_id` ASC);

CREATE INDEX `fk_tracks_artists_idx` ON `playlistgenerator`.`tracks` (`artist_id` ASC);


-- -----------------------------------------------------
-- Table `playlistgenerator`.`track_to_playlist`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `playlistgenerator`.`track_to_playlist` (
  `track_to_playlist_id` INT(11) NOT NULL AUTO_INCREMENT,
  `track_id` INT(11) NOT NULL,
  `playlist_id` INT(11) NOT NULL,
  PRIMARY KEY (`track_to_playlist_id`),
  CONSTRAINT `fk_track_to_playlist_playlists2`
    FOREIGN KEY (`playlist_id`)
    REFERENCES `playlistgenerator`.`playlists` (`playlist_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_track_to_playlist_tracks1`
    FOREIGN KEY (`track_id`)
    REFERENCES `playlistgenerator`.`tracks` (`track_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE UNIQUE INDEX `track_to_playlist_id_UNIQUE` ON `playlistgenerator`.`track_to_playlist` (`track_to_playlist_id` ASC);

CREATE INDEX `fk_track_to_playlist_tracks1_idx` ON `playlistgenerator`.`track_to_playlist` (`track_id` ASC);

CREATE INDEX `fk_track_to_playlist_playlists2_idx` ON `playlistgenerator`.`track_to_playlist` (`playlist_id` ASC);


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
