import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { City } from 'src/app/classes/City';
import { Client } from 'src/app/classes/Client';
import { Country } from 'src/app/classes/Country';
import { ClientService } from 'src/app/services/client.service';
import { LocationService } from 'src/app/services/location.service';

@Component({
  selector: 'app-update-client',
  templateUrl: './update-client.component.html',
  styleUrls: ['./update-client.component.scss'],
})
export class UpdateClientComponent implements OnInit {
  @Input()
  public client!: Client;
  @Output() exit = new EventEmitter();
  @Output() update = new EventEmitter<Client>();

  countries: Country[] = [];
  salutations: string[] = [];
  cities: City[] = [];

  constructor(
    private locationService: LocationService,
    private clientService: ClientService
  ) {}

  ngOnInit(): void {
    let countriesPromise = this.locationService.getAllCountries();
    let salutationsPromise = this.clientService.getSalutations();
    let cityPromise = this.locationService.getAllCities(
      this.client.city.country.id
    );

    countriesPromise.then(
      (data: Country[]) => {
        this.countries = data;
      },
      (reason) => {
        console.log(reason);
      }
    );
    salutationsPromise.then(
      (data: string[]) => {
        this.salutations = data;
      },
      (reason) => {
        console.log(reason);
      }
    );
    cityPromise.then(
      (data: City[]) => {
        this.cities = data;
      },
      (reason) => {
        console.log(reason);
      }
    );
  }

  updateCountry(event: Event | string) {
    let newId: string;

    if (event instanceof Event) {
      newId = (event.target as HTMLInputElement).value;
    } else {
      newId = event;
    }

    let emptyCountry: Country = { id: '-1', name: '' };

    if (newId != '-1') {
      let c = this.countries.find((c) => c.id == newId);
      if (c) {
        emptyCountry = c;
      }
    }

    this.client.city = {
      city: '',
      postalCode: '',
      country: emptyCountry,
    };
  }

  filteredCityNames(): City[] {
    let filteredCities = this.cities.filter((c) =>
      c.postalCode.startsWith(this.client.city.postalCode)
    );

    return filteredCities;
  }

  updateCity($event: KeyboardEvent) {
    let cities = this.filteredCityNames();
    console.log($event);

    if (cities.length == 1) {
      console.log(cities[0]);

      this.client.city.city = cities[0].city;
      this.client.city.postalCode = cities[0].postalCode;
      this.client.city.country = cities[0].country;
    } else if (cities.length == 0) {
      this.client.city.city = '';
    }
  }

  updatePostalCode() {
    let filteredCities = this.cities.filter(
      (c) => c.city == this.client.city.city
    );

    if (filteredCities.length == 1) {
      console.log(filteredCities[0]);

      this.client.city.city = filteredCities[0].city;
      this.client.city.postalCode = filteredCities[0].postalCode;
      this.client.city.country = filteredCities[0].country;
    }
  }
}
