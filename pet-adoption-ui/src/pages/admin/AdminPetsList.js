import React, { useState, useEffect } from "react";
import {
  Box,
  Button,
  Paper,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
} from "@mui/material";
import { useNavigate } from "react-router-dom";
import api from "../../api/api";

const AdminPetsList = () => {
  const navigate = useNavigate();
  const [petsList, setPetsList] = useState([]);

  useEffect(() => {
    const fetchPetsList = async () => {
      try {
        const response = await api.get("/pets");
        setPetsList(response.data);
      } catch (error) {
        console.error("Error fetching pets list:", error);
      }
    };

    fetchPetsList();
  }, []);

  const handleCreatePet = () => {
    // Redirect to the Create Pet page
    navigate("/admin/pets/create-new");
  };

  const handleEditPet = (petId) => {
    // Redirect to the Edit Pet page with the petId
    navigate(`/admin/pets/${petId}/update`);
  };

  const handleAddVaccination = (petId) => {
    // Redirect to the Edit Pet page with the petId
    navigate(`/admin/pets/${petId}/vaccination`);
  };

  // const handleDeletePet = async (petId) => {
  //   try {
  //     await api.delete(`/pets/${petId}`);
  //     // Update the pets list after deleting the pet
  //     setPetsList((prevPetsList) =>
  //       prevPetsList.filter((pet) => pet.petId !== petId)
  //     );
  //   } catch (error) {
  //     console.error("Error deleting pet:", error);
  //   }
  // };

  return (
    <Box p={3}>
      <Button
        variant="contained"
        color="primary"
        onClick={handleCreatePet}
        style={{ marginBottom: 16, float: "right" }}
        sx={{ textTransform: "none" }}
      >
        Create Pet
      </Button>
      <TableContainer component={Paper}>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell>
                <strong>Pet Id</strong>
              </TableCell>
              <TableCell>
                <strong>Pet Name</strong>
              </TableCell>
              <TableCell>
                <strong>Age</strong>
              </TableCell>
              <TableCell>
                <strong>Gender</strong>
              </TableCell>
              <TableCell>
                <strong>Color</strong>
              </TableCell>
              <TableCell>
                <strong>Status</strong>
              </TableCell>
              <TableCell>
                <strong>Adoption Status</strong>
              </TableCell>
              <TableCell>
                <strong>Action</strong>
              </TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {petsList
              .filter((pet) => pet.status !== "Rejected")
              .map((pet) => (
                <TableRow key={pet.petId}>
                  <TableCell>{pet.petId}</TableCell>
                  <TableCell>{pet.name}</TableCell>
                  <TableCell>{pet.age}</TableCell>
                  <TableCell>{pet.gender}</TableCell>
                  <TableCell>{pet.color}</TableCell>
                  <TableCell>{pet.status}</TableCell>
                  <TableCell>{pet.adoptionStatus}</TableCell>
                  <TableCell>
                    <Button
                      variant="outlined"
                      color="primary"
                      onClick={() => handleEditPet(pet.petId)}
                      sx={{ textTransform: "none", marginRight: 2 }}
                    >
                      Edit
                    </Button>
                    <Button
                      variant="outlined"
                      color="primary"
                      onClick={() => handleAddVaccination(pet.petId)}
                      sx={{ textTransform: "none" }}
                    >
                      Vaccination
                    </Button>
                  </TableCell>
                </TableRow>
              ))}
          </TableBody>
        </Table>
      </TableContainer>
    </Box>
  );
};

export default AdminPetsList;
