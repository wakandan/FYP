CREATE table Identities(
    original TEXT,
    changed  TEXT,
    primary key (original, changed),
    foreign key (changed) references Agents(name)
)