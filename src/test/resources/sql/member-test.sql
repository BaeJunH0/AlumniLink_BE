INSERT INTO member (id, admin, employed, password, email, git_link, nickname, resume_link)
VALUES
    (DEFAULT, TRUE, TRUE, RANDOM_UUID(), 'admin@example.com', 'https://github.com/admin', 'admin_user', 'https://resume.com/admin'),

    (DEFAULT, FALSE, TRUE, RANDOM_UUID(), 'user1@example.com', 'https://github.com/user1', 'user_one', 'https://resume.com/user1'),

    (DEFAULT, FALSE, FALSE, RANDOM_UUID(), 'user2@example.com', 'https://github.com/user2', 'user_two', 'https://resume.com/user2'),

    (DEFAULT, FALSE, TRUE, RANDOM_UUID(), 'user3@example.com', NULL, 'user_three', NULL),

    (DEFAULT, FALSE, FALSE, RANDOM_UUID(), 'user4@example.com', 'https://github.com/user4', 'user_four', NULL);
