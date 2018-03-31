
CREATE SCHEMA ISSUE_TRACKER;

CREATE TABLE ISSUE_TRACKER.USER (
  ID BIGINT GENERATED BY DEFAULT AS IDENTITY,
  USERNAME varchar(50) not null
);

CREATE TABLE ISSUE_TRACKER.ISSUE (
  ID BIGINT GENERATED BY DEFAULT AS IDENTITY,
  TITLE varchar(255) not null,
  DESCRIPTION varchar(255) not null,
  STATUS  varchar(20) not null,
  REPORTER  BIGINT not null,
  ASSIGNEE BIGINT,
  CREATED DATE NOT NULL,
  COMPLETED DATE
);

ALTER TABLE ISSUE_TRACKER.ISSUE
ADD FOREIGN KEY (REPORTER)
REFERENCES ISSUE_TRACKER.USER(ID);

ALTER TABLE ISSUE_TRACKER.ISSUE
ADD FOREIGN KEY (ASSIGNEE)
REFERENCES ISSUE_TRACKER.USER(ID);

INSERT INTO ISSUE_TRACKER.USER (USERNAME) VALUES ('j.jones');
INSERT INTO ISSUE_TRACKER.USER (USERNAME) VALUES ('h.humble');

INSERT INTO ISSUE_TRACKER.ISSUE (TITLE, DESCRIPTION, STATUS, REPORTER, CREATED)
VALUES (
    'Issue number one',
    'This is issue number one',
    'backlog',
    (select ID from ISSUE_TRACKER.USER where USER.USERNAME = 'j.jones'),
    '2017-08-01'
);

INSERT INTO ISSUE_TRACKER.ISSUE (TITLE, DESCRIPTION, STATUS, REPORTER, CREATED)
VALUES (
  'Issue number two',
  'This is issue number two',
  'backlog',
  (select ID from ISSUE_TRACKER.USER where USER.USERNAME = 'h.humble'),
  '2017-08-01'
);

INSERT INTO ISSUE_TRACKER.ISSUE (TITLE, DESCRIPTION, STATUS, REPORTER, ASSIGNEE, CREATED, COMPLETED)
VALUES (
  'Issue number three',
  'This is issue number three',
  'done',
  (select ID from ISSUE_TRACKER.USER where USER.USERNAME = 'h.humble'),
  (select ID from ISSUE_TRACKER.USER where USER.USERNAME = 'h.humble'),
  '2017-07-22',
  '2017-08-02'
);

INSERT INTO ISSUE_TRACKER.ISSUE (TITLE, DESCRIPTION, STATUS, REPORTER, ASSIGNEE, CREATED, COMPLETED)
VALUES (
  'Issue number four',
  'This is issue number four',
  'done',
  (select ID from ISSUE_TRACKER.USER where USER.USERNAME = 'h.humble'),
  (select ID from ISSUE_TRACKER.USER where USER.USERNAME = 'j.jones'),
  '2017-08-02',
  '2017-08-02'
);

INSERT INTO ISSUE_TRACKER.ISSUE (TITLE, DESCRIPTION, STATUS, REPORTER, ASSIGNEE, CREATED)
VALUES (
  'Issue number five',
  'This is issue number five',
  'in_progress',
  (select ID from ISSUE_TRACKER.USER where USER.USERNAME = 'j.jones'),
  (select ID from ISSUE_TRACKER.USER where USER.USERNAME = 'j.jones'),
  '2017-08-02'
);