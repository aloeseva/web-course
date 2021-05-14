-- -----------------------------------------------------
-- Table `hibernate_sequence`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS hibernate_sequence
(
    `next_val` BIGINT NOT NULL
)
    AUTO_INCREMENT = 1;

insert into hibernate_sequence (next_val)
values (1);

-- -----------------------------------------------------
-- Table `user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS user
(
    `id`                BIGINT       NOT NULL AUTO_INCREMENT,
    `active`            BIT(1)       NOT NULL,
    `email`             VARCHAR(255) NOT NULL,
    `first_name`        VARCHAR(255),
    `last_name`         VARCHAR(255),
    `password`          VARCHAR(255) NOT NULL,
    `registration_date` DATETIME     NOT NULL,
    `username`          VARCHAR(255) NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `user_pk` (`id` ASC)
);

-- -----------------------------------------------------
-- Table `course`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS course
(
    `id`          BIGINT       NOT NULL,
    `description` VARCHAR(255) NOT NULL,
    `image_name`  VARCHAR(255) NOT NULL,
    `name`        VARCHAR(255) NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `course_pk` (`id` ASC)

);

-- -----------------------------------------------------
-- Table `lesson`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS lesson
(
    `id`          BIGINT       NOT NULL,
    `description` VARCHAR(255) NOT NULL,
    `difficulty`  INT          NOT NULL,
    `name`        VARCHAR(255) NOT NULL,
    `course_id`   BIGINT       NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT course_fk
        FOREIGN KEY (course_id)
            REFERENCES course (`id`),
    INDEX `lesson_pk` (`id` ASC),
    INDEX `course_fk` (`course_id` ASC)
);

-- -----------------------------------------------------
-- Table `test`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS test
(
    `id`        BIGINT       NOT NULL,
    `exp_date`  VARCHAR(255),
    `name`      VARCHAR(255) NOT NULL,
    `lesson_id` BIGINT       NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT lesson_fk
        FOREIGN KEY (lesson_id)
            REFERENCES lesson (`id`),
    INDEX `test_pk` (`id` ASC),
    INDEX `lesson_fk` (`lesson_id` ASC)
);

-- -----------------------------------------------------
-- Table `question`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS question
(
    `id`      BIGINT       NOT NULL,
    `max_val` BIGINT       NOT NULL,
    `q_type`  VARCHAR(255) NOT NULL,
    `text`    VARCHAR(255) NOT NULL,
    `test_id` BIGINT       NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT test_fk
        FOREIGN KEY (test_id)
            REFERENCES test (`id`),
    INDEX `question_pk` (`id` ASC),
    INDEX `test_fk` (`test_id` ASC)
);

-- -----------------------------------------------------
-- Table `answer`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS answer
(
    `id`          BIGINT       NOT NULL,
    `a_type`      VARCHAR(255),
    `text`        VARCHAR(255) NOT NULL,
    `val`         BIGINT       NOT NULL,
    `question_id` BIGINT,
    PRIMARY KEY (`id`),
    CONSTRAINT question_fk
        FOREIGN KEY (`question_id`)
            REFERENCES question (`id`),
    INDEX `answer_pk` (`id` ASC),
    INDEX `question_fk` (`question_id` ASC)
);

-- -----------------------------------------------------
-- Table `attempt`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS attempt
(
    `id`      BIGINT NOT NULL,
    `count`   BIGINT,
    `test_id` BIGINT NOT NULL,
    `user_id` BIGINT NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT test_fk2
        FOREIGN KEY (test_id)
            REFERENCES test (`id`),
    CONSTRAINT user_fk
        FOREIGN KEY (user_id)
            REFERENCES user (`id`),
    INDEX `attempt_pk` (`id` ASC),
    INDEX `test_fk2` (`test_id` ASC),
    INDEX `user_fk` (`user_id` ASC)
);

-- -----------------------------------------------------
-- Table `material`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS material
(
    `id`        BIGINT NOT NULL,
    `extension` VARCHAR(255),
    `file_name` VARCHAR(255),
    `name`      VARCHAR(255),
    `lesson_id` BIGINT NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT lesson_fk2
        FOREIGN KEY (lesson_id)
            REFERENCES lesson (`id`),
    INDEX `material_pk` (`id` ASC),
    INDEX `lesson_fk2` (`lesson_id` ASC)
);

-- -----------------------------------------------------
-- Table `result`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS result
(
    `id`           BIGINT NOT NULL AUTO_INCREMENT,
    `attempt`      BIGINT NOT NULL,
    `result_value` BIGINT NOT NULL,
    `answer_id`    BIGINT NOT NULL,
    `user_id`      BIGINT NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT answer_fk
        FOREIGN KEY (answer_id)
            REFERENCES answer (`id`),
    CONSTRAINT user_fk2
        FOREIGN KEY (user_id)
            REFERENCES user (`id`),
    INDEX `result_pk` (`id` ASC),
    INDEX `answer_fk` (`answer_id` ASC),
    INDEX `user_fk2` (`user_id` ASC)
);

-- -----------------------------------------------------
-- Table `user_course`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS user_course
(
    `user_id`   BIGINT NOT NULL,
    `course_id` BIGINT NOT NULL,
    PRIMARY KEY (`user_id`, `course_id`),
    CONSTRAINT user_course1_fk
        FOREIGN KEY (`user_id`)
            REFERENCES user (id),
    CONSTRAINT user_course2_fk
        FOREIGN KEY (`course_id`)
            REFERENCES course (id),
    INDEX `course_fk2` (`course_id` ASC),
    INDEX `user_fk2` (`user_id` ASC)
);

-- -----------------------------------------------------
-- Table `user_create_course`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS user_create_course
(
    `user_id`   BIGINT NOT NULL,
    `course_id` BIGINT NOT NULL,
    PRIMARY KEY (`user_id`, `course_id`),
    CONSTRAINT user_create_course1_fk
        FOREIGN KEY (`user_id`)
            REFERENCES user (id),
    CONSTRAINT user_create_course2_fk
        FOREIGN KEY (`course_id`)
            REFERENCES course (id),
    INDEX `course_fk3` (`course_id` ASC),
    INDEX `user_fk3` (`user_id` ASC)
);

-- -----------------------------------------------------
-- Table `user_role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS user_role
(
    `user_id` BIGINT NOT NULL AUTO_INCREMENT,
    `roles`   VARCHAR(255),
    CONSTRAINT user_role_user_fk
        FOREIGN KEY (`user_id`)
            REFERENCES user (`id`),
    INDEX `user_fk4` (`user_id` ASC)
);
