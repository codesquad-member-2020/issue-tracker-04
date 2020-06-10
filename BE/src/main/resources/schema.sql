DROP TABLE IF EXISTS emoji ;

DROP TABLE IF EXISTS photo ;

DROP TABLE IF EXISTS comment ;

DROP TABLE IF EXISTS assignee ;

DROP TABLE IF EXISTS milestone ;

DROP TABLE IF EXISTS label ;

DROP TABLE IF EXISTS user ;

DROP TABLE IF EXISTS issue ;


CREATE TABLE IF NOT EXISTS user (
  id INT NOT NULL,
  name VARCHAR(45) NULL,
  github_id VARCHAR(100) NULL,
  image VARCHAR(500) NULL,
  PRIMARY KEY (id))

CREATE TABLE IF NOT EXISTS milestone (
  id INT NOT NULL,
  title VARCHAR(500) NULL,
  due_date DATETIME NULL,
  description VARCHAR(500) NULL,
  PRIMARY KEY (id))

CREATE TABLE IF NOT EXISTS label (
  id INT NOT NULL,
  name VARCHAR(45) NULL,
  color VARCHAR(100) NULL,
  description VARCHAR(500) NULL,
  issue_id INT NOT NULL,
  PRIMARY KEY (id),
  INDEX fk_label_issue1_idx (issue_id ASC) VISIBLE,
  CONSTRAINT fk_label_issue1
    FOREIGN KEY (issue_id)
    REFERENCES issue (id)
    )

CREATE TABLE IF NOT EXISTS issue (
  id INT NOT NULL,
  title VARCHAR(100) NULL,
  github_id VARCHAR(100) NULL,
  milestone_id INT NOT NULL,
  label_id INT NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT fk_issue_milestone1
    FOREIGN KEY (milestone_id)
    REFERENCES milestone (id)
    ,
  CONSTRAINT fk_issue_label1
    FOREIGN KEY (label_id)
    REFERENCES label (id)
    )



CREATE TABLE IF NOT EXISTS comment (
  id INT NOT NULL,
  content VARCHAR(500) NULL,
  created_at DATETIME NULL,
  updated_at DATETIME NULL,
  github_id VARCHAR(100) NULL,
  issue_id INT NOT NULL,
  github_id VARCHAR(100) NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT fk_comment_issue1
    FOREIGN KEY (issue_id)
    REFERENCES issue (id)
    
    ,
  CONSTRAINT fk_comment_user1
    FOREIGN KEY (github_id)
    REFERENCES user (id)
    
    )

CREATE TABLE IF NOT EXISTS emoji (
  name VARCHAR(100) NULL,
  comment_id INT NOT NULL,
  CONSTRAINT fk_emoji_comment
    FOREIGN KEY (comment_id)
    REFERENCES comment (id)
    
    )

CREATE TABLE IF NOT EXISTS photo (
  url VARCHAR(500) NULL,
  github_id VARCHAR(100) NULL,
  comment_id INT NOT NULL,
  CONSTRAINT fk_photo_comment1
    FOREIGN KEY (comment_id)
    REFERENCES comment (id)
    
    )

CREATE TABLE IF NOT EXISTS assignee (
  user_id INT NOT NULL,
  issue_id INT NOT NULL,
  CONSTRAINT fk_assignee_user1
    FOREIGN KEY (user_id)
    REFERENCES user (id)
    ,
  CONSTRAINT fk_assignee_issue1
    FOREIGN KEY (issue_id)
    REFERENCES issue (id)
    )

