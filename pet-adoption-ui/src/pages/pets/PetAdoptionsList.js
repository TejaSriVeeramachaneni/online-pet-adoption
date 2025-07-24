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
  Typography,
} from "@mui/material";
import { useNavigate } from "react-router-dom";
import api from "../../api/api";
import { useAuth } from "../../contexts/AuthContext";

const PetAdoptionsList = () => {
  const navigate = useNavigate();
  const [petAdoptionsList, setPetAdoptionsList] = useState([]);
  const { user, isAdmin } = useAuth();

  useEffect(() => {
    const fetchPetAdoptionsList = async () => {
      try {
        const response = await api.get(`/users/${user?.userId}/pet-adoptions`);
        setPetAdoptionsList(response.data);
      } catch (error) {
        console.error("Error fetching pet adoptions list:", error);
      }
    };

    fetchPetAdoptionsList();
  }, [user]);

  const handleViewPetDetails = (petId) => {
    navigate(`/pets/${petId}`);
  };

  const handlePayFee = (petId, adoptionId) => {
    // navigate(`/pets/${petId}/adopt`, {
    //   state: { data: { petData } },
    // });
    navigate(`/pets/${petId}/adopt/${adoptionId}/pay-fee`);
  };

  return (
    <Box p={3}>
      <Typography variant="h5" gutterBottom>
        Pet Adoptions
      </Typography>
      <TableContainer component={Paper}>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell>
                <strong>Pet Name</strong>
              </TableCell>
              <TableCell>
                <strong>Gender</strong>
              </TableCell>
              <TableCell>
                <strong>Color</strong>
              </TableCell>
              <TableCell>
                <strong>Application Date</strong>
              </TableCell>
              <TableCell>
                <strong> Fee</strong>
              </TableCell>
              <TableCell>
                <strong> Payment status</strong>
              </TableCell>
              <TableCell>
                <strong>Application Status</strong>
              </TableCell>
              <TableCell>
                <strong>Action</strong>
              </TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {petAdoptionsList.map((adoption) => (
              <TableRow key={adoption.adoptionId}>
                <TableCell>{adoption.petDto?.name}</TableCell>
                <TableCell>{adoption.petDto?.gender}</TableCell>
                <TableCell>{adoption.petDto?.color}</TableCell>
                <TableCell>{adoption.applicationDate}</TableCell>
                <TableCell>${adoption.totalPrice}</TableCell>
                <TableCell>{adoption.paymentStatus}</TableCell>
                <TableCell>{adoption.status}</TableCell>
                <TableCell>
                  <Box display={"flex"} flexDirection={"row"}>
                    {(!adoption.paymentStatus ||
                      adoption.paymentStatus !== "Paid") && (
                      <Button
                        variant="contained"
                        color="primary"
                        onClick={() =>
                          handlePayFee(
                            adoption.petDto.petId,
                            adoption.adoptionId
                          )
                        }
                        sx={{ textTransform: "none", marginRight: 2 }}
                      >
                        Pay Fee
                      </Button>
                    )}
                    <Button
                      variant="outlined"
                      color="primary"
                      onClick={() =>
                        handleViewPetDetails(adoption.petDto.petId)
                      }
                      sx={{ textTransform: "none", marginRight: 2 }}
                    >
                      View Pet Details
                    </Button>
                  </Box>
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
    </Box>
  );
};

export default PetAdoptionsList;
