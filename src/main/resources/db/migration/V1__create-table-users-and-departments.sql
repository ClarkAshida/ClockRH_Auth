CREATE TABLE department (
                            id SERIAL PRIMARY KEY,
                            name VARCHAR(100) NOT NULL
);

CREATE TABLE users (
                      id SERIAL PRIMARY KEY,
                      name VARCHAR(100) NOT NULL,
                      email VARCHAR(150) NOT NULL UNIQUE,
                      password VARCHAR(255) NOT NULL,
                      cpf VARCHAR(14) NOT NULL UNIQUE,
                      admission_date TIMESTAMP,
                      department_id INT,
                      role VARCHAR(50) NOT NULL,
                      active BOOLEAN NOT NULL DEFAULT TRUE,
                      FOREIGN KEY (department_id) REFERENCES department(id) ON DELETE SET NULL
);
