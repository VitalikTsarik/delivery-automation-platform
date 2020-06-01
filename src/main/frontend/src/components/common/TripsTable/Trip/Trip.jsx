import React from "react";
import ButtonGroup from "react-bootstrap/ButtonGroup";
import Button from "react-bootstrap/Button";

const Trip = ({trip, actions}) => {
  const packages = trip.packageList.map((item, index) => (
    <span key={item.id}>{item.name}{(index !== trip.packageList.length - 1) && ", "}</span>
  ));
  const routeList = trip.routeList;
  const route = routeList.map((item, index) => (
    <span key={index}>{item}{(index !== routeList.length - 1) && " ⇨ "}</span>
  ));
  const started = trip.currentLocation !== -1;
  const first = trip.currentLocation === 0;
  const finished = trip.currentLocation === trip.routeList.length - 1;
  return (
    <tr>
      <td>{trip.car}</td>
      <td>{packages}</td>
      <td>{route}</td>
      <td>{started ? trip.routeList[trip.currentLocation] : "Not started"}</td>
      {actions && (
        <td>
          {started ? (
            <ButtonGroup>
              <Button
                variant="primary"
                onClick={() => actions.onNext(trip.currentLocation - 1)}
                disabled={first}
              >
                Previous
              </Button>
              <Button
                variant="primary"
                onClick={() => actions.onNext(trip.currentLocation + 1)}
                disabled={finished}
              >
                Next
              </Button>
            </ButtonGroup>
          ) : (
            <ButtonGroup>
              <Button
                variant="primary"
                onClick={actions.onPackages}
              >
                Packages
              </Button>
              <Button
                variant="primary"
                onClick={actions.onRoute}
              >
                Route
              </Button>
              <Button
                variant="success"
                onClick={actions.onStart}
                disabled={trip.packageList.length === 0 || routeList.length === 0}
              >
                Start
              </Button>
            </ButtonGroup>
          )}
        </td>
      )}
    </tr>
  );
};

export default Trip;