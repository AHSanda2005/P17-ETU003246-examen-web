CREATE TABLE utilisateur (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE credit (
    id INT AUTO_INCREMENT PRIMARY KEY,
    libele VARCHAR(255) NOT NULL,
    montant INT,
    datedebut Date,
    datefin Date
);

CREATE TABLE depense (
    id INT AUTO_INCREMENT PRIMARY KEY,
    libele VARCHAR(255) NOT NULL,
    montant INT,
    date Date
)