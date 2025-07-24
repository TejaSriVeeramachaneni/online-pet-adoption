import React, { useState, useEffect } from "react";
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

const EditVaccinationDetails = () => {
  const navigate = useNavigate();
  const { petId, vaccineId } = useParams();
  const [vaccineDetails, setVaccineDetails] = useState({
    vaccineName: "",
    vaccinationDate: "",
    nextVaccinationDate: "",
  });
  const [snackbar, setSnackbar] = useState({
    message: "",
    alertType: "",
  });
  const [openSnackbar, setOpenSnackbar] = useState(false);

  useEffect(() => {
    const fetchVaccineDetails = async () => {
      try {
        const response = await api.get(`/pet-vaccinations/${vaccineId}`);
        setVaccineDetails(response.data);
      } catch (error) {
        console.error("Error fetching vaccine details:", error);
      }
    };

    fetchVaccineDetails();
  }, [petId, vaccineId]);

  const handleUpdateVaccination = async () => {
    try {
      await api.put(`/pet-vaccinations/${vaccineId}`, {
        ...vaccineDetails,
        petId,
      });

      setSnackbar({
        message: "Vaccination details updated successfully!",
        alertType: "success",
      });
      setOpenSnackbar(true);

      setTimeout(() => {
        navigate(`/admin/pets/${petId}/vaccination`);
      }, 1000);
    } catch (error) {
      console.error("Error updating vaccination details:", error);
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
          Edit Vaccination Details
        </Typography>
        <TextField
          label="Vaccine Name"
          fullWidth
          size="small"
          margin="normal"
          value={vaccineDetails.vaccineName}
          onChange={(e) =>
            setVaccineDetails({
              ...vaccineDetails,
              vaccineName: e.target.value,
            })
          }
        />
        <TextField
          label="Vaccination Date"
          fullWidth
          margin="normal"
          size="small"
          type="date"
          value={vaccineDetails.vaccinationDate}
          inputProps={{ min: moment().format("YYYY-MM-DD") }}
          onChange={(e) =>
            setVaccineDetails({
              ...vaccineDetails,
              vaccinationDate: e.target.value,
            })
          }
          InputLabelProps={{
            shrink: true,
          }}
        />
        <TextField
          label="Next Vaccination Date"
          fullWidth
          margin="normal"
          type="date"
          size="small"
          inputProps={{ min: moment().format("YYYY-MM-DD") }}
          value={vaccineDetails.nextVaccinationDate}
          onChange={(e) =>
            setVaccineDetails({
              ...vaccineDetails,
              nextVaccinationDate: e.target.value,
            })
          }
          InputLabelProps={{
            shrink: true,
          }}
        />
        <Button
          variant="contained"
          color="primary"
          onClick={handleUpdateVaccination}
          style={{ marginTop: 16 }}
        >
          Update Vaccination Details
        </Button>
      </Box>
    </Container>
  );
};

export default EditVaccinationDetails;
