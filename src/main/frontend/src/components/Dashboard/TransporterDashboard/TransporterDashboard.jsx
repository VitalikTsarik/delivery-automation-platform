import React, { useEffect, useState } from "react";

import TransporterService from "../../../services/transporter.service";
import Table from "react-bootstrap/Table";
import Trip from "./Trip/Trip";
import PackagesModal from "./PackagesModal/PackagesModal";
import Button from "react-bootstrap/Button";
import TripModal from "./TripModal/TripModal";
import RouteModal from "./RouteModal/RouteModal";

const TransporterDashboard = () => {
  const [trips, setTrips] = useState([]);

  const [modalTrip, setModalTrip] = useState(false);
  const [currentTrip, setCurrentTrip] = useState(null);
  const [modalPackages, setModalPackages] = useState(false);
  const [modalRoute, setModalRoute] = useState(false);

  useEffect(() => {
    (async () => {
      setTrips(await TransporterService.getTrips());
    })();
  }, []);
  const handleNext = async (order, id) => {
    await TransporterService.updateCurrentCity(order, id);
    setTrips(await TransporterService.getTrips());
  };
  const handlePackages = (id) => {
    setCurrentTrip(trips.find((trip) => trip.id === id));
    setModalPackages(true);
  };
  const handleRoute = (id) => {
    setCurrentTrip(trips.find((trip) => trip.id === id));
    setModalRoute(true);
  };
  const handleStart = async (id) => {
    await TransporterService.startTrip(id);
    setTrips(await TransporterService.getTrips());
  };
  const handleAdd = () => {
    setModalTrip(true);
  };
  const handleModalTripHide = async () => {
    setTrips(await TransporterService.getTrips());
    setModalTrip(false);
  };
  const handleModalPackagesHide = async () => {
    setTrips(await TransporterService.getTrips());
    setModalPackages(false);
  };
  const handleModalRouteHide = async () => {
    setTrips(await TransporterService.getTrips());
    setModalRoute(false);
  };
  return (
    <>
      <h2>Your trips</h2>
      <div className="content">
        <Table striped bordered hover>
          <thead>
          <tr>
            <th>Car</th>
            <th>Route</th>
            <th>Packages</th>
            <th>Current location</th>
            <th>Actions</th>
          </tr>
          </thead>
          <tbody>
          {trips.map((trip) => (
            <Trip
              key={trip.id}
              trip={trip}
              onNext={(order) => handleNext(order, trip.id)}
              onPackages={() => handlePackages(trip.id)}
              onRoute={() => handleRoute(trip.id)}
              onStart={() => handleStart(trip.id)}
            />
          ))}
          </tbody>
        </Table>
        {(trips.length === 0) && <span>Your don't have any trips. Try creating one </span>}
        <div className="add">
          <Button onClick={handleAdd}>Add</Button>
        </div>
      </div>

      <TripModal
        show={modalTrip}
        onHide={handleModalTripHide}
      />
      <PackagesModal
        show={modalPackages}
        trip={currentTrip}
        onHide={handleModalPackagesHide}
      />
      <RouteModal
        show={modalRoute}
        trip={currentTrip}
        onHide={handleModalRouteHide}
      />
    </>
  );
};

export default TransporterDashboard;