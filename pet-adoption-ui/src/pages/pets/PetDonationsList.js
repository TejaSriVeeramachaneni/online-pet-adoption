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

const PetDonationsList = () => {
  const navigate = useNavigate();
  const [petDonationsList, setPetDonationsList] = useState([]);
  const { user, isAdmin } = useAuth();

  useEffect(() => {
    const fetchPetDonationsList = async () => {
      try {
        const response = await api.get(`/users/${user?.userId}/pet-donations`);
        setPetDonationsList(response.data);
      } catch (error) {
        console.error("Error fetching pet donations list:", error);
      }
    };

    fetchPetDonationsList();
  }, [user]);

  const handleViewPetDetails = (petId) => {
    navigate(`/pets/${petId}`);
  };

  return (
    <Box p={3}>
      <Button
        variant="contained"
        color="primary"
        onClick={() => navigate("/user/pet-donations/create-new")}
        style={{ marginBottom: 16, float: "right" }}
        sx={{ textTransform: "none" }}
      >
        Donate Pet
      </Button>
      <Typography variant="h5" gutterBottom>
        Pet Donations
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
                <strong>Age</strong>
              </TableCell>
              <TableCell>
                <strong>Application Date</strong>
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
            {petDonationsList.map((donation) => (
              <TableRow key={donation.donationId}>
                <TableCell>{donation.petDto?.name}</TableCell>
                <TableCell>{donation.petDto?.gender}</TableCell>
                <TableCell>{donation.petDto?.color}</TableCell>
                <TableCell>{donation.petDto?.age}</TableCell>
                <TableCell>{donation.submissionDate}</TableCell>
                <TableCell>{donation.status}</TableCell>
                <TableCell>
                  <Button
                    variant="outlined"
                    color="primary"
                    onClick={() => handleViewPetDetails(donation.petDto.petId)}
                    sx={{ textTransform: "none", marginRight: 2 }}
                  >
                    View Pet Details
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

export default PetDonationsList;
