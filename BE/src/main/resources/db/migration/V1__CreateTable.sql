DROP TABLE IF EXISTS label_has_issue;
DROP TABLE IF EXISTS assignee;
DROP TABLE IF EXISTS emoji;
DROP TABLE IF EXISTS photo;
DROP TABLE IF EXISTS label;
DROP TABLE IF EXISTS comment;
DROP TABLE IF EXISTS issue;
DROP TABLE IF EXISTS milestone;
DROP TABLE IF EXISTS user;

CREATE TABLE IF NOT EXISTS user
(
    id        INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    name      VARCHAR(45)                    NULL,
    github_id VARCHAR(45)                    NULL,
    image     VARCHAR(500)                   NULL
);

CREATE TABLE IF NOT EXISTS milestone
(
    id          INT PRIMARY KEY AUTO_INCREMENT,
    title       VARCHAR(45) NULL,
    due_date    DATETIME    NULL,
    description VARCHAR(45) NULL
);

CREATE TABLE IF NOT EXISTS issue
(
    id           INT PRIMARY KEY AUTO_INCREMENT,
    title        VARCHAR(45) NOT NULL,
    status       VARCHAR(45) DEFAULT 'OPEN',
    `created_on` DATETIME    NULL,
    `updated_on` DATETIME    NULL,
    user_id      INT         NULL,
    milestone_id INT         NULL REFERENCES milestone (id),
    CONSTRAINT fk_issue_user FOREIGN KEY (user_id) REFERENCES user (id)
);

CREATE TABLE IF NOT EXISTS comment
(
    id         INT PRIMARY KEY AUTO_INCREMENT,
    content    TEXT     NULL,
    created_at DATETIME NULL,
    updated_at DATETIME NULL,
    user_id    INT      NOT NULL,
    issue_id   INT      NOT NULL,
    CONSTRAINT fk_comment_user1 FOREIGN KEY (user_id) REFERENCES user (id),
    CONSTRAINT fk_comment_issue1 FOREIGN KEY (issue_id) REFERENCES issue (id)
);

CREATE TABLE IF NOT EXISTS label
(
    id          INT PRIMARY KEY AUTO_INCREMENT,
    title       VARCHAR(45)  NULL,
    color       VARCHAR(45)  NULL,
    description VARCHAR(100) NULL
);

CREATE TABLE IF NOT EXISTS photo
(
    url        VARCHAR(500) NULL,
    comment_id INT          NOT NULL,
    CONSTRAINT fk_photo_comment1 FOREIGN KEY (comment_id) REFERENCES comment (id)
);

CREATE TABLE IF NOT EXISTS emoji
(
    name       VARCHAR(45) NULL,
    comment_id INT         NOT NULL,
    CONSTRAINT fk_emoji_comment1 FOREIGN KEY (comment_id) REFERENCES comment (id)
);

CREATE TABLE IF NOT EXISTS assignee
(
    issue_id INT NOT NULL,
    user_id  INT NOT NULL,
    CONSTRAINT fk_issue_has_user_issue FOREIGN KEY (issue_id) REFERENCES issue (id),
    CONSTRAINT fk_issue_has_user_user1 FOREIGN KEY (user_id) REFERENCES user (id)
);

CREATE TABLE IF NOT EXISTS label_has_issue
(
    id       INT PRIMARY KEY AUTO_INCREMENT,
    label_id INT NOT NULL,
    issue_id INT NOT NULL
);

CREATE TABLE IF NOT EXISTS milestone_has_issue
(
    milestone_id INT NOT NULL,
    issue_id INT NOT NULL
);