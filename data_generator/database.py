from sqlalchemy import create_engine
from sqlalchemy.inspection import inspect
from sqlalchemy import Column, Integer, String, DateTime
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy.sql.expression import null
from sqlalchemy.sql.schema import ForeignKey
from sqlalchemy.orm import sessionmaker
from sqlalchemy.sql.sqltypes import Boolean

from random_train import RandomTrain

Base = declarative_base()
DBSession = sessionmaker()

class Train(Base):
    __tablename__= "Train"
    
    id = Column(Integer, primary_key=True)
    name = Column(String, nullable=False)
    departure_station = Column(String, nullable=False)
    departure_city = Column(String, nullable=False)
    arrival_station = Column(String, nullable=False)
    arrival_city = Column(String, nullable=False)
    departure_date = Column(DateTime, nullable=False)
    arrival_date = Column(DateTime, nullable=False)
    nb_ticket_premiere = Column(Integer, nullable=False)
    nb_ticket_standard = Column(Integer, nullable=False)
    nb_ticket_eco = Column(Integer, nullable=False)
    
class User(Base):
    __tablename__= "User"
    
    id = Column(Integer, primary_key=True)
    name = Column(String, nullable=False)
    password = Column(String, nullable=False)

class Ticket(Base):
    __tablename__= "Ticket"
    
    id = Column(Integer, primary_key=True)
    user_id = Column(Integer, ForeignKey('User.id'))
    train_id = Column(Integer, ForeignKey('Train.id'))
    flexible = Column(Boolean, nullable=False)


class Database:
    def __init__(self):
        engine = create_engine("sqlite:///ti_ji_wi.db")
        inspector = inspect(engine)
        
        TABLES = [
            Train, User, Ticket
        ]
        for instance in TABLES:
            table = instance.metadata.tables[instance.__tablename__]
            table.create(engine)
            
            instance.metadata.bind = engine
            
        DBSession.bind = engine
        
        self.session = DBSession()
        self.session.bind = engine
        self.engine = engine

    def generate_trains(self, size=100):
        for _ in range(size):
            rand_train = RandomTrain.get_random_train()
            train = Train(**rand_train)
            self.session.add(train)
        self.session.commit()

    def generate_users(self):
        names = ["Alice", "Bob", "Charly", "David"]
        for name in names: 
            user = User(name=name, password="demo")
            self.session.add(user)
        self.session.commit()

if __name__ == "__main__":
    database = Database()
    database.generate_users()
    database.generate_trains()