import React, { useEffect, useState } from "react";
import Table from "react-bootstrap/Table";
import Button from "react-bootstrap/Button";
import CargoModal from "./CargoForm/CargoForm";
import ButtonGroup from "react-bootstrap/ButtonGroup";

import "./CargoOwnerDashboard.css";

import CargoOwnerService from "../../../services/cargo-owner.service";

const CargoOwnerDashboard = () => {
  const [packages, setPackages] = useState([]);
  const [modalShow, setModalShow] = useState(false);
  const [modalEdit, setModalEdit] = useState(false);
  const [modalItem, setModalItem] = useState({});

  useEffect(() => {
    (async () => {
      setPackages(await CargoOwnerService.getContent());
    })();
  }, []);

  const handleEdit = async (id) => {
    const item = await CargoOwnerService.getPackage(id);
    setModalItem(item);
    setModalEdit(true);
    setModalShow(true);
  };
  const handleRemove = async (id) => {
    await CargoOwnerService.removePackage(id);
    setPackages(prev => prev.filter(item => item.id !== id));
  };
  const handleAdd = () => {
    setModalItem({});
    setModalEdit(false);
    setModalShow(true);
  };
  const handleHide = async () => {
    setPackages(await CargoOwnerService.getContent());
    setModalShow(false);
  };
  return (
    <>
      <div className="content">
        <Table striped bordered hover>
          <thead>
          <tr>
            <th>Name</th>
            <th>Initial Location</th>
            <th>Target Location</th>
            <th>Cost</th>
            <th>Actions</th>
          </tr>
          </thead>
          <tbody>
          {Boolean(packages.length) && packages.map((item) => (
            <tr key={item.id}>
              <td>{item.name}</td>
              <td>{item.initialLocation}</td>
              <td>{item.targetLocation}</td>
              <td>${item.cost}</td>
              <td>
                <ButtonGroup>
                  <Button
                    variant="primary"
                    onClick={() => handleEdit(item.id)}
                  >
                    Edit
                  </Button>
                  <Button
                    variant="danger"
                    onClick={() => handleRemove(item.id)}
                  >
                    Remove
                  </Button>
                </ButtonGroup>
              </td>
            </tr>
          ))}
          </tbody>
        </Table>
        {(packages.length === 0) && <span>Your don't have any packages. Try creating one </span>}
        <div className="add">
          <Button onClick={handleAdd}>add</Button>
        </div>
      </div>
      <CargoModal
        show={modalShow}
        edit={modalEdit}
        item={modalItem}
        onHide={handleHide}
      />
    </>
  );
};

export default CargoOwnerDashboard;