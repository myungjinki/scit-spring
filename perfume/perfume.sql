use springJpa;

CREATE TABLE perfume (
     no INT AUTO_INCREMENT PRIMARY KEY,
     name VARCHAR(30) NOT NULL,
     gender VARCHAR(10) NOT NULL,
     age INT NOT NULL,
     favorite_scent VARCHAR(50),
     favorite_brand VARCHAR(50),
     usage_frequency VARCHAR(50),
     purchase_budget VARCHAR(50),
     comments VARCHAR(200),
     completion_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
     CONSTRAINT chk_gender CHECK (gender IN ('남성', '여성'))
);

select * from perfume;

