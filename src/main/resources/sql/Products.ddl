CREATE TABLE Products(
    sessionId TEXT,  
    quantity NUMERIC,
    name TEXT,
    category NUMERIC,
    priceMin NUMERIC, 
    priceMax NUMERIC,
    PRIMARY KEY(sessionId, name)
);