CREATE TABLE Executions(
    buyer_name TEXT,
    seller_name TEXT,
    prod_name TEXT,
    status NUMERIC,
    rating NUMERIC,
    Stime NUMERIC,
    primary key (buyer_name, seller_name, Stime),
    FOREIGN KEY (buyer_name) REFERENCES Agents(name),    
    FOREIGN KEY (seller_name) REFERENCES Agents(name),
    FOREIGN KEY (prod_name) REFERENCES Products(name)    
)