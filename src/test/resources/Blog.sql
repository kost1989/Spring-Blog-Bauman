DROP TABLE IF EXISTS comment;
DROP TABLE IF EXISTS post_tag;
DROP TABLE IF EXISTS tag;
DROP TABLE IF EXISTS post;
DROP TABLE IF EXISTS user_role;
DROP TABLE IF EXISTS role;
DROP TABLE IF EXISTS "user";

CREATE TABLE "user" (
  user_id BIGSERIAL PRIMARY KEY,
  username VARCHAR(50) NOT NULL UNIQUE,
  password VARCHAR(100) NOT NULL,
  dt_created TIMESTAMP DEFAULT now() NOT NULL,
  is_active BOOLEAN
);

CREATE TABLE role (
  role_id BIGSERIAL PRIMARY KEY,
  name VARCHAR(50) NOT NULL
);

CREATE TABLE user_role (
  user_id BIGINT REFERENCES "user" (user_id),
  role_id BIGINT REFERENCES role(role_id),
  PRIMARY KEY (user_id, role_id)
);


CREATE TABLE post (
    post_id BIGSERIAL PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    content VARCHAR,
    user_id BIGINT REFERENCES "user"(user_id),
    dt_created TIMESTAMP DEFAULT now() NOT NULL,
    dt_updated TIMESTAMP
);

CREATE TABLE tag (
    tag_id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE post_tag (
    post_id BIGINT REFERENCES post(post_id) ON DELETE CASCADE,
    tag_id BIGINT REFERENCES tag(tag_id) ON DELETE CASCADE,
    PRIMARY KEY (post_id, tag_id)
);

CREATE TABLE comment (
    comment_id BIGSERIAL PRIMARY KEY,
    post_id BIGINT REFERENCES post(post_id) ON DELETE CASCADE,
    content VARCHAR,
    user_id BIGINT REFERENCES "user"(user_id),
    dt_created TIMESTAMP DEFAULT now() NOT NULL
);

-- Insert

insert into role(name) values ('ADMIN');
insert into role(name) values ('USER');


insert into "user" (username, password, is_active)
    values ('user1', '$2a$10$UzY2k/M2QeYjbHL.N8W.oOFNmBnrei9LdkuYZ8oFqiousDyOWCgKe', true);

insert into "user" (username, password, is_active)
    values ('user2', '$2a$10$0WxgQe/cMLBAfUDZgtpJLe/Anj15KdjwjNh9xYo769QibZtwdO5Um', true);

insert into "user" (username, password, is_active)
    values ('admin', '$2a$10$Ux6TtamVQnVRdSAr1UblauvJv0xibg97.3JqAzX4QqN5vceO2QtEq', true);

insert into user_role values (3, 1);
insert into user_role values (1, 2);
insert into user_role values (2, 2);


insert into post (title, content, user_id, dt_created, dt_updated)
	values ('Day 1', 'It''s all good!', 1, '2021-04-03 14:16:00'::timestamp, null);
insert into post (title, content, user_id, dt_created, dt_updated)
	values ('Day 2', 'It''s all ok!', 1, '2021-04-03 14:16:05'::timestamp, null);
insert into post (title, content, user_id, dt_created, dt_updated)
	values ('Day 3', 'It''s all bad!', 2, '2021-04-03 14:16:10'::timestamp, null);

insert into comment (user_id, post_id, content, dt_created)
    values (2, 2, 'Nice!', current_timestamp);
insert into comment (user_id, post_id, content, dt_created)
    values (2, 1, 'Awesome!', current_timestamp);
insert into comment (user_id, post_id, content, dt_created)
    values (2, 1, 'Excellent!', current_timestamp);
insert into comment (user_id, post_id, content, dt_created)
    values (1, 1, 'Wonderful!', current_timestamp);
insert into comment (user_id, post_id, content, dt_created)
    values (2, 3, 'Disgusting!', current_timestamp);
insert into comment (user_id, post_id, content, dt_created)
    values (2, 3, 'Atrocious!', current_timestamp);

insert into tag (name) values ('news');
insert into tag (name) values ('life');
insert into tag (name) values ('day');
insert into tag (name) values ('mood');
insert into tag (name) values ('ideas');
insert into tag (name) values ('thoughts');

insert into post_tag(post_id, tag_id) values (1, 1);
insert into post_tag(post_id, tag_id) values (1, 2);
insert into post_tag(post_id, tag_id) values (2, 3);
insert into post_tag(post_id, tag_id) values (2, 2);
insert into post_tag(post_id, tag_id) values (2, 1);
insert into post_tag(post_id, tag_id) values (2,5);
insert into post_tag(post_id, tag_id) values (3, 3);
insert into post_tag(post_id, tag_id) values (3, 2);
insert into post_tag(post_id, tag_id) values (3, 6);
