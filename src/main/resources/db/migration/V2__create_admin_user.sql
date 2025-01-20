INSERT INTO users (id, name, email, password, cpf, admission_date, role, active, department_id)
VALUES (
           1,
           'Admin',
           'admin@example.com',
           '$2a$10$e6GnNkgqL7B.eJcayFQCauiz9mBG5G6hEeSHzQTTJ7q2L6itkrq4W',
           '000.000.000-00',
           NOW(),
           'ADMIN',
           TRUE,
           NULL
       )
ON CONFLICT (email) DO NOTHING; 