INSERT INTO users (ldap_id, first_name, last_name, email, created_at) VALUES('ben', 'ben', 'Johnson', 'ben.johnson@gmail.com', now());
INSERT INTO users (ldap_id, first_name, last_name, email, created_at) VALUES('bob', 'bob', 'Alex', 'bob.alex@gmail.com', now());
INSERT INTO users (ldap_id, first_name, last_name, email, created_at) VALUES('alex', 'Alex', 'Smith', 'alex.smith@email.com', now());
INSERT INTO users (ldap_id, first_name, last_name, email, created_at) VALUES('joe', 'Joe', 'Johnson', 'joe.johnson@email.com', now());
INSERT INTO users (ldap_id, first_name, last_name, email, created_at) VALUES('jerry', 'Jerry', 'Williams', 'jerry.williams@email.com', now());
INSERT INTO tweet (user_id, "content", created_at) VALUES(1, 'test-1', now());
INSERT INTO tweet (user_id, "content", created_at) VALUES(2, 'test-2', now());
INSERT INTO tweet (user_id, "content", created_at) VALUES (2, 'test-2', '2024-01-01');
INSERT INTO tweet (user_id, "content", created_at) VALUES (2, 'test-3', '2024-01-05');
INSERT INTO tweet (user_id, "content", created_at) VALUES (2, 'test-4', '2024-01-10');
INSERT INTO tweet (user_id, "content", created_at) VALUES (2, 'test-5', '2024-01-15');
INSERT INTO tweet (user_id, "content", created_at) VALUES (2, 'test-6', '2024-01-20');
INSERT INTO tweet (user_id, "content", created_at) VALUES (2, 'test-7', '2024-01-25');
INSERT INTO tweet (user_id, "content", created_at) VALUES (2, 'test-8', '2024-02-01');
INSERT INTO tweet (user_id, "content", created_at) VALUES (2, 'test-9', '2024-02-05');
INSERT INTO tweet (user_id, "content", created_at) VALUES (2, 'test-10', '2024-02-10');
INSERT INTO tweet (user_id, "content", created_at) VALUES (2, 'test-11', '2024-02-15');
INSERT INTO following (follower_id, following_id, created_at) VALUES(1, 2, now());
INSERT INTO following (follower_id, following_id, created_at) VALUES(2, 4, now());

