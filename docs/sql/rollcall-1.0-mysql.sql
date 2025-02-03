-- auto-generated definition
create table example_table
(
    id          int auto_increment
        primary key,
    column_name varchar(255)                          not null,
    created_at  timestamp default current_timestamp() null
);

-- Tabelle attendants erstellen
CREATE TABLE attendants
(
    id        BIGINT AUTO_INCREMENT PRIMARY KEY,
    firstname VARCHAR(100),
    lastname  VARCHAR(100)
);

-- Tabelle attendance erstellen
CREATE TABLE attendance
(
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_id      BIGINT    NOT NULL,
    course_id       BIGINT    NOT NULL,
    attendance_time TIMESTAMP NOT NULL,
    status          VARCHAR(50) DEFAULT 'present'
);

-- Beispieldaten für attendants einfügen
INSERT INTO attendants (id, firstname, lastname)
VALUES (1, 'Lisa', 'Tester'),
       (2, 'Markus', 'Muster'),
       (3, 'Jakob', 'Maier'),
       (4, 'Nils', 'Rest');

-- Beispieldaten für attendance einfügen
INSERT INTO attendance (student_id, course_id, attendance_time, status)
VALUES (1, 101, '2025-02-01 08:30:00', 'present'),
       (2, 101, '2025-02-01 08:35:00', 'late'),
       (3, 101, '2025-02-01 08:40:00', 'present'),
       (1, 102, '2025-02-02 10:00:00', 'present'),
       (2, 102, '2025-02-02 10:10:00', 'present'),
       (3, 102, '2025-02-02 10:15:00', 'absent');

