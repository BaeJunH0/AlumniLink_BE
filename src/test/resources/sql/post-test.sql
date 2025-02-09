INSERT INTO member (id, admin, employed, password, email, git_link, nickname, resume_link)
VALUES
    (DEFAULT, TRUE, TRUE, RANDOM_UUID(), 'admin@example.com', 'https://github.com/admin', 'admin_user', 'https://resume.com/admin'),

    (DEFAULT, FALSE, TRUE, RANDOM_UUID(), 'user1@example.com', 'https://github.com/user1', 'user_one', 'https://resume.com/user1'),

    (DEFAULT, FALSE, FALSE, RANDOM_UUID(), 'user2@example.com', 'https://github.com/user2', 'user_two', 'https://resume.com/user2'),

    (DEFAULT, FALSE, TRUE, RANDOM_UUID(), 'user3@example.com', NULL, 'user_three', NULL),

    (DEFAULT, FALSE, FALSE, RANDOM_UUID(), 'user4@example.com', 'https://github.com/user4', 'user_four', NULL);


INSERT INTO post (id, at_created, at_modified, member_id, body, description, title, tag)
VALUES
    (DEFAULT, '2024-02-01 10:00:00.000000', '2024-02-02 12:00:00.000000', 1, 'Spring Boot 프로젝트를 진행하며 겪은 문제 해결 방법을 공유합니다.', 'Spring Boot 프로젝트 경험', 'Spring Boot 프로젝트 후기', 'PROJECT'),

    (DEFAULT, '2024-02-03 14:30:00.000000', '2024-02-04 16:00:00.000000', 2, '이력서 작성 시 강조해야 할 핵심 포인트를 정리했습니다.', '이력서 작성 팁', '이력서 작성 가이드', 'TIP'),

    (DEFAULT, '2024-02-05 09:15:00.000000', '2024-02-06 11:20:00.000000', 3, 'Java의 Optional을 활용하여 NullPointerException을 방지하는 방법', 'Java Optional 활용법', 'Java Optional 제대로 활용하기', 'TIP'),

    (DEFAULT, '2024-02-07 08:45:00.000000', '2024-02-08 10:30:00.000000', 4, 'Docker를 활용하여 개발 환경을 구축하는 방법을 정리했습니다.', 'Docker 환경 설정', 'Docker를 이용한 개발 환경 구축', 'PROJECT'),

    (DEFAULT, '2024-02-09 15:20:00.000000', '2024-02-10 18:00:00.000000', 5, '네트워크 관련 CS 지식을 정리한 노트 공유', '네트워크 기본 개념', '네트워크 CS 핵심 정리', 'TIP'),

    (DEFAULT, '2024-02-11 07:10:00.000000', '2024-02-12 09:45:00.000000', 1, '포트폴리오를 구성할 때 어떤 프로젝트를 포함해야 할까?', '포트폴리오 구성 전략', '좋은 포트폴리오 만들기', 'TIP'),

    (DEFAULT, '2024-02-13 13:05:00.000000', '2024-02-14 15:10:00.000000', 2, 'Spring Security를 사용하여 로그인 기능을 구현하는 방법', 'Spring Security 로그인 구현', 'Spring Security 기본 적용법', 'PROJECT'),

    (DEFAULT, '2024-02-15 11:45:00.000000', '2024-02-16 13:20:00.000000', 3, 'GitHub 프로필을 효과적으로 꾸미는 방법을 소개합니다.', 'GitHub 프로필 관리', '눈에 띄는 GitHub 프로필 만들기', 'TIP'),

    (DEFAULT, '2024-02-17 10:30:00.000000', '2024-02-18 12:15:00.000000', 4, '면접 준비 시 자주 묻는 질문과 답변 예시 정리', '면접 예상 질문', '기술 면접 대비 가이드', 'TIP'),

    (DEFAULT, '2024-02-19 16:00:00.000000', '2024-02-20 17:50:00.000000', 5, 'AWS EC2를 활용하여 웹 애플리케이션을 배포하는 방법', 'AWS 배포 가이드', 'AWS EC2를 활용한 배포', 'PROJECT');
