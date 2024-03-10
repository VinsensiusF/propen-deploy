
INSERT INTO role (name, created_at, modified_at, deleted_at) VALUES ('STUDENT', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL);
INSERT INTO role (name, created_at, modified_at, deleted_at) VALUES ('ADMIN', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL);
INSERT INTO role (name, created_at, modified_at, deleted_at) VALUES ('TEACHER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL);
INSERT INTO role (name, created_at, modified_at, deleted_at) VALUES ('TOP_LEVEL', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL);
INSERT INTO role (name, created_at, modified_at, deleted_at) VALUES ('CUSTOMER_SERVICE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL);

INSERT INTO account (email, password, name, profile_picture, status, role_id, created_at, modified_at, deleted_at)
VALUES ('admin@gmail.com', 'admin', 'admin', NULL, 'VERIFIED', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL);

INSERT INTO account (email, password, name, profile_picture, status, role_id, created_at, modified_at, deleted_at)
VALUES ('toplevel@gmail.com', 'toplevel', 'toplevel', NULL, 'VERIFIED', 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL);

INSERT INTO account (email, password, name, profile_picture, status, role_id, created_at, modified_at, deleted_at)
VALUES ('teacher@gmail.com', 'teacher', 'teacher', NULL, 'VERIFIED', 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL);

INSERT INTO account (email, password, name, profile_picture, status, role_id, created_at, modified_at, deleted_at)
VALUES ('cs@gmail.com', 'cs', 'cs', NULL, 'VERIFIED', 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL);
