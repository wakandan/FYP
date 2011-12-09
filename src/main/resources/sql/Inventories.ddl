CREATE TABLE Inventories(
    agent_name TEXT,
    prod_name TEXT,
    quantity NUMERIC,
    primary key(agent_name, prod_name),
    foreign key(agent_name) references agents(name),
    foreign key(prod_name) references products(name)
)