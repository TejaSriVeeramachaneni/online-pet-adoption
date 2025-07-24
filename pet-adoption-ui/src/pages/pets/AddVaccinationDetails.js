import React, { useState } from "react";
import {
  Box,
  Button,
  TextField,
  Typography,
  Snackbar,
  Container,
} from "@mui/material";
import { useNavigate, useParams } from "react-router-dom";
import api from "../../api/api";
import CustomSnackbar from "../home/CustomSnackbar";
import moment from "moment";

const AddVaccinationDetails = () => {
  const navigate = useNavigate();
  const { petId } = useParams();
  const [vaccineName, setVaccineName] = useState("");
  const [vaccinationDate, setVaccinationDate] = useState("");
  const [nextVaccinationDate, setNextVaccinationDate] = useState("");
  const [snackbar, setSnackbar] = useState({
    message: "",
    alertType: "",
  });
  const [openSnackbar, setOpenSnackbar] = useState(false);

  const handleSaveVaccination = async () => {
    if (!vaccineName || !vaccinationDate || !nextVaccinationDate) {
      setSnackbar({
        message: "All fields are required!",
        alertType: "error",
      });
      setOpenSnackbar(true);
      return;
    }
    try {
      await api.post(`/pet-vaccinations`, {
        vaccineName,
        vaccinationDate,
        nextVaccinationDate,
        petId,
      });

      setSnackbar({
        message: "Vaccination details added successfully!",
        alertType: "success",
      });
      setOpenSnackbar(true);
      setTimeout(() => {
        navigate(`/admin/pets/${petId}/vaccination`);
      }, 1000);
    } catch (error) {
      console.error("Error adding vaccination details:", error);
      setSnackbar({ message: error?.response.data, alertType: "error" });
      setOpenSnackbar(true);
    }
  };

  const handleCloseSnackbar = () => {
    setSnackbar({
      message: "",
      alertType: "",
    });
    setOpenSnackbar(false);
  };

  return (
    <Container
      style={{
        display: "flex",
        flexDirection: "column",
        alignItems: "center",
        justifyContent: "center",
      }}
    >
      <Box
        style={{
          display: "flex",
          flexDirection: "column",
          alignItems: "center",
          justifyContent: "center",
          padding: "32px",
          backgroundColor: "#ffffff",
          borderRadius: "8px",
          boxShadow: "0 3px 10px rgba(0, 0, 0, 0.2)",
          width: "100%",
          maxWidth: "350px",
          marginTop: 40,
        }}
      >
        <CustomSnackbar
          open={openSnackbar}
          onClose={handleCloseSnackbar}
          {...snackbar}
        />

        <Typography variant="h5" gutterBottom>
          Add Vaccination Details
        </Typography>
        <TextField
          label="Vaccine Name"
          fullWidth
          margin="normal"
          size="small"
          value={vaccineName}
          onChange={(e) => setVaccineName(e.target.value)}
        />
        <TextField
          label="Vaccination Date"
          fullWidth
          margin="normal"
          type="date"
          size="small"
          value={vaccinationDate}
          onChange={(e) => setVaccinationDate(e.target.value)}
          InputLabelProps={{
            shrink: true,
          }}
          inputProps={{ min: moment().format("YYYY-MM-DD") }}
        />
        <TextField
          label="Next Vaccination Date"
          fullWidth
          margin="normal"
          type="date"
          size="small"
          value={nextVaccinationDate}
          onChange={(e) => setNextVaccinationDate(e.target.value)}
          InputLabelProps={{
            shrink: true,
          }}
          inputProps={{ min: moment().format("YYYY-MM-DD") }}
        />
        <Button
          variant="contained"
          color="primary"
          onClick={handleSaveVaccination}
          style={{ marginTop: 16 }}
          sx={{ textTransform: "none" }}
        >
          Save Vaccination Details
        </Button>
      </Box>
    </Container>
  );
};

export default AddVaccinationDetails;
