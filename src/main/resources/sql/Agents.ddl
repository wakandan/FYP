CREATE TABLE Agents(
    sessionId TEXT, 
    name TEXT,  
    balance NUMERIC, 
    aType NUMERIC,
    PRIMARY KEY(sessionId, name)
);