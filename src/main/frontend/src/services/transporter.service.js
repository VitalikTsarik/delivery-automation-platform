import axios from "axios";
import authHeader from "./auth-header";

const API_URL = "http://localhost:8080/api/transporter/";

class TransporterService {
  getFreePackages() {
    return axios.get(API_URL + "packages/free", {headers: authHeader()})
      .then(response => response.data);
  }

  createTrip(trip) {
    return axios.post(API_URL + "trip", {...trip}, {headers: authHeader()});
  }

  addPackage(tripId, cargoId) {
    return axios.put(API_URL + `trip/package?packageId=${cargoId}&tripId=${tripId}`, null, {headers: authHeader()});
  }

  removePackage(tripId, cargoId) {
    return axios.delete(API_URL + `trip/package?packageId=${cargoId}&tripId=${tripId}`, {headers: authHeader()});
  }

  getTrips() {
    return axios.get(API_URL + "trips", {headers: authHeader()})
      .then(response => response.data);
  }

  updateCurrentCity(order, tripId) {
    return axios.put(API_URL + `trip/current-city?order=${order}&tripId=${tripId}`, null, {headers: authHeader()});
  }

  assignRoute(route, tripId) {
    return axios.put(API_URL + "trip/route", {route: route, tripId: tripId}, {headers: authHeader()});
  }

  startTrip(id) {
    return axios.post(API_URL + `trip/started?tripId=${id}`, null, {headers: authHeader()});
  }
}

export default new TransporterService();
