INSERT INTO user (name, github_id, image, role_key)
VALUES ('Jack', 'guswns1659',
        'https://avatars3.githubusercontent.com/u/55608425?s=400&u=bb7394a04f87d003a70fe15c3d88cdc370171e5b&v=4',
        'GUEST');

INSERT INTO user (name, github_id, image, role_key)
VALUES ('Sigrid Jin', 'jypthemiracle',
        'https://avatars3.githubusercontent.com/u/55608425?s=400&u=bb7394a04f87d003a70fe15c3d88cdc370171e5b&v=4',
        'GUEST');

INSERT INTO milestone (title, due_date, description)
VALUES ('1차 배포', '2020-06-28', '1차 배포');

INSERT INTO issue (title, user_id, milestone_id)
VALUES ('SQL 작성', 1, 1);

INSERT INTO issue (title, user_id, milestone_id)
VALUES ('스키마 작성', 1, 1);


INSERT INTO comment (content, created_at, updated_at, user_id, issue_id)
VALUES ('아하하 어렵네요.', '2020-06-25', '2020-06-26', 1, 1);

INSERT INTO comment (content, created_at, updated_at, user_id, issue_id)
VALUES ('아하하 쉽네요.', '2020-06-25', '2020-06-26', 1, 1);

INSERT INTO comment (content, created_at, updated_at, user_id, issue_id)
VALUES ('아하하 그저 그렇네요.', '2020-06-25', '2020-06-26', 1, 2);

INSERT INTO label (title, color, description)
VALUES ('BE-배포', '#FF5733', '백엔드 배포 라벨');

INSERT INTO photo (url, comment_id)
VALUES ('https://user-images.githubusercontent.com/41055141/84121797-8efa8d80-aa72-11ea-8a2b-d8fd96d91372.png', 1);

INSERT INTO emoji (name, comment_id)
VALUES ('THUMBS_UP', 1);

INSERT INTO emoji (name, comment_id)
VALUES ('THUMBS_DOWN', 2);

INSERT INTO emoji (name, comment_id)
VALUES ('LAUGH', 2);

INSERT INTO assignee (issue_id, user_id)
VALUES (1, 1);

INSERT INTO label_has_issue (label_id, issue_id)
VALUES (1, 1);

INSERT INTO role (role_key, user_id)
VALUES ('GUEST', 1);