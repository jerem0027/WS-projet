import datetime
from random import randint, randrange, shuffle


MAX_PLACE = 250

TRAINS = [
    "TGV City",
    "TGV Rupo",
    "TGV Cime",
    "TGV Lome",
    "TGV Luyo",
    "TGV Rope",
    "TGV Rayu",
    "TGV Fast",
    "OuiGo Hess",
    "OuiGo Keba",
    "OuiGo Loom",
    "OuiGo LoKante",
    "OuiGo Tamtam",
    "OuiGo ThamCity",
    "OuiGo Wiggle",
]
STATIONS = [
    ("Gare de Lyon", "Paris"),
    ("Gare Montparnasse", "Paris"),
    ("Gare Saint Lazar", "Paris"),
    ("Gare du Nord", "Paris"),
    ("Marseille St Charles", "Marseilles"),
    ("Bordeux St Jean", "Bordeaux"),
    ("Lyon Part de Dieu", "Lyon"),
    ("Bourg Palette", "Kanto"),
    ("ODonut", "Springfield"),
    ("Victoria", "South Park"),
    ("Union Station", "Toronto"),
    ("Centrale Station", "Gotham City"),
    ("Village de Noël", "Pôle-Nord"),
    ("Aqua Boulevard", "Atlantide"),
    ("Placard Station", "Narnia"),
    ("External Park", "Old York"),
]

STARTS = datetime.datetime(2021, 9, 1)
ENDS = datetime.datetime(2023, 9, 1)

def rand_date():
    time_between = ENDS - STARTS
    days = time_between.days
    rand_days = randrange(days)
    rand_hours = randint(0, 24)
    rand_min = randint(0, 60)
    rand_date = STARTS + datetime.timedelta(
        days=rand_days, hours=rand_hours, minutes=rand_min
    )
    return rand_date

class RandomTrain:
    @staticmethod
    def get_random_train():
        shuffle(TRAINS)
        shuffle(STATIONS)
        data = {
            "name": TRAINS[0],
            "departure_station": STATIONS[0][0],
            "departure_city": STATIONS[0][1],
            "arrival_station": STATIONS[1][0],
            "arrival_city": STATIONS[1][1],
            "departure_date": rand_date(),
            "nb_ticket_premiere": 20
        }
        
        data["arrival_date"] = data["departure_date"] + datetime.timedelta(hours=randint(2, 8))
        data["nb_ticket_standard"] = randint(0, MAX_PLACE - data["nb_ticket_premiere"])
        data["nb_ticket_eco"] = MAX_PLACE - data["nb_ticket_premiere"] - data["nb_ticket_standard"]

        return data
