import React from "react";
import Table from "react-bootstrap/Table";

import Trip from "./Trip/Trip";

const TripsTable = ({trips, onNext, onPackages, onRoute, onStart}) => {
  return (
    <Table striped bordered hover>
      <thead>
      <tr>
        <th>Car</th>
        <th>Packages</th>
        <th>Route</th>
        <th>Current location</th>
        {onNext && <th>Actions</th>}
      </tr>
      </thead>
      <tbody>
      {trips.map((trip) => (
        <Trip
          key={trip.id}
          trip={trip}
          onNext={onNext && ((order) => onNext(order, trip.id))}
          onPackages={onPackages && (() => onPackages(trip.id))}
          onRoute={onRoute && (() => onRoute(trip.id))}
          onStart={onStart && (() => onStart(trip.id))}
        />
      ))}
      </tbody>
    </Table>
  );
};

export default TripsTable;