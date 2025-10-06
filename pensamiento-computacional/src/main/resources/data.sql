-- Tabla: USER_ACCOUNT
INSERT INTO USER_ACCOUNT (id, institutional_email, password_hash, full_name, profile_photo_url, created_at, self_declared_level)
VALUES
(1, 'user1@icesi.edu.co', 'hash1', 'User Uno', NULL, CURRENT_TIMESTAMP, NULL),
(2, 'user2@icesi.edu.co', 'hash2', 'User Dos', NULL, CURRENT_TIMESTAMP, NULL),
(3, 'user3@icesi.edu.co', 'hash3', 'User Tres', NULL, CURRENT_TIMESTAMP, NULL),
(4, 'user4@icesi.edu.co', 'hash4', 'User Cuatro', NULL, CURRENT_TIMESTAMP, NULL),
(5, 'user5@icesi.edu.co', 'hash5', 'User Cinco', NULL, CURRENT_TIMESTAMP, NULL);

-- Tabla: PERMISSION
INSERT INTO PERMISSION (id, name, description)
VALUES
(1, 'VIEW_ACTIVITIES', 'Permite consultar las actividades disponibles'),
(2, 'SUBMIT_SOLUTIONS', 'Permite enviar respuestas para ejercicios'),
(3, 'GRADE_SUBMISSIONS', 'Permite evaluar y asignar puntajes a respuestas'),
(4, 'MANAGE_USERS', 'Permite administrar usuarios y roles de la plataforma');

-- Tabla: ROLE
INSERT INTO ROLE (id, name, description)
VALUES
(1, 'STUDENT', 'Rol para estudiantes inscritos en los cursos'),
(2, 'PROFESSOR', 'Rol para profesores encargados de evaluar actividades'),
(3, 'ADMIN', 'Rol administrativo con privilegios de gestión');

-- Tabla intermedia: ROLE_PERMISSION
INSERT INTO ROLE_PERMISSION (role_id, permission_id)
VALUES
(1, 1),
(1, 2),
(2, 1),
(2, 3),
(3, 1),
(3, 3),
(3, 4);

-- Tabla intermedia: USER_ACCOUNT_ROLE
INSERT INTO USER_ACCOUNT_ROLE (user_account_id, role_id)
VALUES
(1, 1),
(2, 2),
(2, 3),
(3, 1),
(4, 2),
(5, 1);

-- Tabla: LEVEL_TIER
INSERT INTO LEVEL_TIER (code)
VALUES
('BEGINNER'),
('KILLER'),
('PRO'),
('NA');

-- Tabla: ACADEMIC_TERM
INSERT INTO ACADEMIC_TERM (id, term_code, start_on, ends_on, is_active)
VALUES
(1, '2023A', '2023-01-15 00:00:00', '2023-06-15 00:00:00', true),
(2, '2023B', '2023-07-15 00:00:00', '2023-12-15 00:00:00', true),
(3, '2024A', '2024-01-15 00:00:00', '2024-06-15 00:00:00', true),
(4, '2024B', '2024-07-15 00:00:00', '2024-12-15 00:00:00', false),
(5, '2025A', '2025-01-15 00:00:00', '2025-06-15 00:00:00', false);

-- Tabla: GROUP_SECTION
INSERT INTO GROUP_SECTION (id, academic_term_id, group_code, section_title)
VALUES
(1, 1, 'G1', 'Grupo 1'),
(2, 2, 'G2', 'Grupo 2'),
(3, 3, 'G3', 'Grupo 3'),
(4, 4, 'G4', 'Grupo 4'),
(5, 5, 'G5', 'Grupo 5');

-- Tabla: EXERCISE
INSERT INTO EXERCISE (id, prompt, difficulty)
VALUES
(1, '¿Cuál es la capital de Francia?', 1),
(2, 'Resuelve la ecuación x^2 + 2x + 1 = 0', 2),
(3, 'Explica el teorema de Pitágoras', 2),
(4, '¿Qué es la fotosíntesis?', 1),
(5, 'Describe el proceso de mitosis', 3);

-- Tabla: ACTIVITY
INSERT INTO ACTIVITY (id, group_section_id, created_by_user_id, title, description, window_start_at, window_end_at)
VALUES
(1, 1, 2, 'Actividad 1', 'Descripción 1', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 2, 4, 'Actividad 2', 'Descripción 2', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 3, 2, 'Actividad 3', 'Descripción 3', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 4, 4, 'Actividad 4', 'Descripción 4', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 5, 2, 'Actividad 5', 'Descripción 5', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Tabla: ACTIVITY_EXERCISE
INSERT INTO ACTIVITY_EXERCISE (activity_id, exercise_id, display_order)
VALUES
(1, 1, 1),
(2, 2, 2),
(3, 3, 3),
(4, 4, 4),
(5, 5, 5);

-- Tabla: EXERCISE_TARGET_PROFILE
INSERT INTO EXERCISE_TARGET_PROFILE (exercise_id, profile_code)
VALUES
(1, 'BEGINNER'),
(2, 'KILLER'),
(3, 'PRO'),
(4, 'NA'),
(5, 'NA');

-- Tabla: ENROLLMENT
INSERT INTO ENROLLMENT (student_id, academic_term_id, group_section_id, enrolled_on)
VALUES
(1, 1, 1, CURRENT_TIMESTAMP),
(2, 2, 2, CURRENT_TIMESTAMP),
(3, 3, 3, CURRENT_TIMESTAMP),
(4, 4, 4, CURRENT_TIMESTAMP),
(5, 5, 5, CURRENT_TIMESTAMP);

-- Tabla: PERFORMANCE_TIER_HISTORY
INSERT INTO PERFORMANCE_TIER_HISTORY (id, student_id, academic_term_id, tier_code, computed_at, revision_no, basis_summary, method_version)
VALUES
(1, 1, 1, 'BEGINNER', CURRENT_TIMESTAMP, 1, 'Resumen 1', 'v1'),
(2, 2, 2, 'KILLER', CURRENT_TIMESTAMP, 2, 'Resumen 2', 'v1'),
(3, 3, 3, 'PRO', CURRENT_TIMESTAMP, 3, 'Resumen 3', 'v1'),
(4, 4, 4, 'NA', CURRENT_TIMESTAMP, 4, 'Resumen 4', 'v1'),
(5, 5, 5, 'NA', CURRENT_TIMESTAMP, 5, 'Resumen 5', 'v1');

-- Tabla: SCORING_EVENT
INSERT INTO SCORING_EVENT (id, student_id, activity_id, exercise_id, awarded_points, awarded_by_user_id)
VALUES
(1, 1, 1, 1, 10, 2),
(2, 2, 2, 2, 8, 4),
(3, 3, 3, 3, 9, 2),
(4, 4, 4, 4, 7, 4),
(5, 5, 5, 5, 10, 2);

-- Tabla: TEACHING_ASSIGNMENT
INSERT INTO TEACHING_ASSIGNMENT (id, professor_id, group_section_id, assigned_on)
VALUES
(1, 2, 1, CURRENT_TIMESTAMP),
(2, 4, 2, CURRENT_TIMESTAMP),
(3, 2, 3, CURRENT_TIMESTAMP),
(4, 4, 4, CURRENT_TIMESTAMP),
(5, 2, 5, CURRENT_TIMESTAMP);
