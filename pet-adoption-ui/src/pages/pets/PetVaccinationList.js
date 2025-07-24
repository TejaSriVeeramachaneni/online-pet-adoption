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
import api from "../../api/api";
import { useNavigate, useParams } from "react-router-dom";

const PetVaccinationList = () => {
  const { petId } = useParams();
  const navigate = useNavigate();
  const [vaccinationList, setVaccinationList] = useState([]);

  useEffect(() => {
    const fetchVaccinationList = async () => {
      try {
        const response = await api.get(`/pet-vaccinations?petId=${petId}`);
        setVaccinationList(response.data);
      } catch (error) {
        console.error("Error fetching vaccination list:", error);
      }
    };

    fetchVaccinationList();
  }, [petId]);

  const handleAddVaccination = () => {
    navigate(`/admin/pets/${petId}/vaccination/new`);
  };

  return (
    <Box p={3}>
      <Button
        variant="contained"
        color="primary"
        onClick={handleAddVaccination}
        sx={{ textTransform: "none", marginBottom: 2, float: "right" }}
      >
        Add Vaccination
      </Button>
      <Typography variant="h5" gutterBottom>
        Pet Vaccination Details
      </Typography>
      <TableContainer component={Paper}>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell>
                <strong>Vaccine Name</strong>
              </TableCell>
              <TableCell>
                <strong>Vaccination Date</strong>
              </TableCell>
              <TableCell>
                <strong>Next Vaccination Date</strong>
              </TableCell>
              <TableCell>
                <strong>Action</strong>
              </TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {vaccinationList.map((vaccination) => (
              <TableRow key={vaccination.vaccinationId}>
                <TableCell>{vaccination.vaccineName}</TableCell>
                <TableCell>{vaccination.vaccinationDate}</TableCell>
                <TableCell>{vaccination.nextVaccinationDate}</TableCell>
                <TableCell>
                  <Button
                    variant="contained"
                    color="primary"
                    onClick={() =>
                      navigate(
                        `/admin/pets/${petId}/vaccination/${vaccination.vaccinationId}/update`
                      )
                    }
                    sx={{
                      textTransform: "none",
                      marginBottom: 2,
                    }}
                  >
                    Edit
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

export default PetVaccinationList;
