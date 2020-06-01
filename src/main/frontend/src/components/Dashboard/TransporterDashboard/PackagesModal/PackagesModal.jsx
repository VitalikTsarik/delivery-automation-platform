import React, { useEffect, useState } from "react";
import Modal from "react-bootstrap/Modal";
import Button from "react-bootstrap/Button";
import Table from "react-bootstrap/Table";
import FormCheck from "react-bootstrap/FormCheck";
import ButtonGroup from "react-bootstrap/ButtonGroup";

import TransporterService from "../../../../services/transporter.service";
import CargoModal from "../../../common/CargoModal/CargoModal";

const PackagesModal = ({trip, ...props}) => {
  const [packages, setPackages] = useState([]);
  const [selectedPackages, setSelectedPackages] = useState([]);
  const [modalShow, setModalShow] = useState(false);
  const [modalItem, setModalItem] = useState({});

  useEffect(() => {
    (async () => {
      setPackages(await TransporterService.getFreePackages());
    })();
  }, [trip]);
  useEffect(() => {
    if (trip) {
      setSelectedPackages(trip.packageList);
    }
  }, [trip]);

  const handleDetail = (id) => {
    const item = packages.find(item => item.id === id);
    setModalItem(item);
    setModalShow(true);
  };
  const handleSelect = async (id) => {
    const selected = selectedPackages.find(item => item.id === id);
    const item = packages.find(item => item.id === id);
    if (selected) {
      await TransporterService.removePackage(trip.id, id);
      setSelectedPackages((prev) => prev.filter((item) => item.id !== id));
    } else {
      await TransporterService.addPackage(trip.id, id);
      setSelectedPackages((prev) => [...prev, item]);
    }
  };
  return (
    <Modal
      {...props}
      size="lg"
      centered
    >
      <Modal.Header closeButton>
        <Modal.Title>Available packages</Modal.Title>
      </Modal.Header>
      <Modal.Body className="content">
        <Table>
          <thead>
          <tr>
            <th/>
            <th>Name</th>
            <th>Initial Location</th>
            <th>Target Location</th>
            <th>Cost</th>
            <th/>
          </tr>
          </thead>
          <tbody>
          {Boolean(packages.length) && packages.map((item) => (
            <tr key={item.id}>
              <td>
                <FormCheck
                  checked={selectedPackages.find((value => value.id === item.id))}
                  onChange={() => handleSelect(item.id)}
                />
              </td>
              <td>{item.name}</td>
              <td>{item.initialLocation.name}</td>
              <td>{item.targetLocation.name}</td>
              <td>${item.cost}</td>
              <td>
                <ButtonGroup>
                  <Button
                    variant="primary"
                    onClick={() => handleDetail(item.id)}
                  >
                    Detail
                  </Button>
                </ButtonGroup>
              </td>
            </tr>
          ))}
          </tbody>
        </Table>
        {(packages.length === 0) && <span>There are no packages available</span>}
      </Modal.Body>
      <CargoModal
        show={modalShow}
        item={modalItem}
        disabled={true}
        onHide={() => setModalShow(false)}
      />
    </Modal>
  );
};

export default PackagesModal;