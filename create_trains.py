import datetime
from random import randint, random, randrange, shuffle

MAX_PLACE = 250
COLUMN = [
    "id",
    "name",
    "departure_station",
    "departure_city",
    "arrival_station",
    "arrival_city",
    "departure_date",
    "arrival_date",
    "nb_ticket_premier",
    "nb_ticket_standard",
    "nb_ticket_eco",
    "\n",
]
TRAINS = [
    "TGV City",
    "TGV Rupo",
    "TGV Cime",
    "TGV Lome",
    "TGV Luyo",
    "TGV Rope",
    "TGV Rayu",
]
STATIONS = [
    ("Gare de Lyon", "Paris"),
    ("Gare Montparnasse", "Paris"),
    ("Gare Saint Lazar", "Paris"),
    ("Gare du Nord", "Paris"),
    ("Marseille St Charles", "Marseilles"),
    ("Bordeux St Jean", "Bordeaux"),
    ("Lyon Part de Dieu", "Lyon"),
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


with open("fake_trains.csv", "w") as f:
    f.write(";".join(COLUMN))
    for i, v in enumerate(range(100)):
        shuffle(STATIONS)
        shuffle(TRAINS)

        data = [i, TRAINS[0]]

        departure = STATIONS[0]
        arrival = STATIONS[1]

        data.extend(list(departure + arrival))
        date_departure = rand_date()
        date_arrival = date_departure + datetime.timedelta(hours=8)
        data.append(date_departure)
        data.append(date_arrival)

        data.append(20)
        data.append(randint(0, MAX_PLACE - data[-1]))
        data.append(MAX_PLACE - data[-1] - data[-2])

        f.write(";".join([str(d) for d in data]))
        f.write("\n")
