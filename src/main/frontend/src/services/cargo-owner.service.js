import axios from "axios";
import authHeader from "./auth-header";

const API_URL = "http://localhost:8080/api/cargo-owner/";

class CargoOwnerService {
  getPackages() {
    return axios.get(API_URL + "packages", {headers: authHeader()})
      .then(response => response.data);
  }

  createPackage(item) {
    return axios.post(API_URL + "package", {...item}, {headers: authHeader()});
  }

  getPackage(id) {
    return axios.get(API_URL + `package/${id}`, {headers: authHeader()})
      .then(response => response.data);
  }

  editPackage({id, ...item}) {
    return axios.put(API_URL + `package/${id}`, {...item}, {headers: authHeader()});
  }

  removePackage(id) {
    return axios.delete(API_URL + `package/${id}`, {headers: authHeader()});
  }

  getTrips() {
    return axios.get(API_URL + "trips", {headers: authHeader()})
      .then(response => response.data);
  }
}

export default new CargoOwnerService();
