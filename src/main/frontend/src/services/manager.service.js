import axios from "axios";

import authHeader from "./auth-header";

const API_URL = "http://localhost:8080/api/manager/";

class ManagerService {
  getPackages() {
    return axios.get(API_URL + "packages", {headers: authHeader()})
      .then(response => response.data);
  }

  getTrips() {
    return axios.get(API_URL + "trips", {headers: authHeader()})
      .then(response => response.data);
  }
}

export default new ManagerService();
