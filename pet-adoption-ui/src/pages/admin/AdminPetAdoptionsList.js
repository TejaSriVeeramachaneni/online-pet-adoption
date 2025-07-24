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
  TextField,
} from "@mui/material";
import { useNavigate } from "react-router-dom";
import api from "../../api/api";
import { useAuth } from "../../contexts/AuthContext";
import CustomSnackbar from "../home/CustomSnackbar";

const AdminPetAdoptionsList = () => {
  const navigate = useNavigate();
  const [petAdoptionsList, setPetAdoptionsList] = useState([]);
  const { user, isAdmin } = useAuth();
  const [snackbar, setSnackbar] = useState({
    message: "",
    alertType: "",
  });
  const [openSnackbar, setOpenSnackbar] = useState(false);
  const [searchTerm, setSearchTerm] = useState("");
  const [filteredPetAdoptions, setFilteredPetAdoptions] = useState([]);

  const fetchPetAdoptionsList = async () => {
    const URL = isAdmin()
      ? `/pet-adoptions`
      : `/users/${user?.userId}/pet-adoptions`;
    try {
      const response = await api.get(URL);
      setPetAdoptionsList(response.data);
      setFilteredPetAdoptions(response.data);
    } catch (error) {
      console.error("Error fetching pet adoptions list:", error);
    }
  };
  useEffect(() => {
    fetchPetAdoptionsList();
  }, [user]);

  const handleViewPetDetails = (petId) => {
    navigate(`/pets/${petId}`);
  };

  const handleUpdateStatus = async (adoptionId, status) => {
    try {
      // Make an API call to save the booking
      await api.put(`/pet-adoptions/${adoptionId}/update-status`, {
        status,
      });

      setSnackbar({
        message: "Pet adoption approved successfully!",
        alertType: "success",
      });
      setOpenSnackbar(true);
      setTimeout(() => {
        fetchPetAdoptionsList();
      }, 1000);
    } catch (error) {
      console.error("Error while updating the adoption status :", error);
      setSnackbar({ message: error?.response.data, alertType: "error" });
      setOpenSnackbar(true);
    }
  };

  useEffect(() => {
    const filteredResults = petAdoptionsList.filter(
      (adoption) =>
        adoption.username.toLowerCase().includes(searchTerm.toLowerCase()) ||
        adoption.email.toLowerCase().includes(searchTerm.toLowerCase())
    );
    setFilteredPetAdoptions(filteredResults);
  }, [searchTerm]);

  return (
    <Box p={1}>
      <CustomSnackbar
        open={openSnackbar}
        handleClose={() => {
          setSnackbar({
            message: "",
            alertType: "",
          });
          setOpenSnackbar(false);
        }}
        {...snackbar}
      />
      <Typography variant="h5" gutterBottom>
        Pet Adoptions
        <TextField
          label="Search"
          variant="outlined"
          size="small"
          onChange={(e) => setSearchTerm(e.target.value)}
          sx={{ marginLeft: 4 }}
        />
      </Typography>
      <TableContainer component={Paper}>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell>
                <strong>Pet Id</strong>
              </TableCell>
              <TableCell>
                <strong>Applicant Name</strong>
              </TableCell>
              <TableCell>
                <strong>Email</strong>
              </TableCell>
              <TableCell>
                <strong>Application Date</strong>
              </TableCell>
              <TableCell>
                <strong>Fee</strong>
              </TableCell>
              <TableCell>
                <strong>Payment Status</strong>
              </TableCell>
              <TableCell>
                <strong>Application Status</strong>
              </TableCell>

              <TableCell>
                <strong>Actions</strong>
              </TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {filteredPetAdoptions.map((adoption) => (
              <TableRow key={adoption.adoptionId}>
                <TableCell>{adoption.petDto?.petId}</TableCell>
                <TableCell>{adoption.username}</TableCell>
                <TableCell>{adoption.email}</TableCell>
                <TableCell>{adoption.applicationDate}</TableCell>
                <TableCell>${adoption.totalPrice}</TableCell>
                <TableCell>{adoption.paymentStatus}</TableCell>
                <TableCell>{adoption.status}</TableCell>
                <>
                  <TableCell>
                    <Box display={"flex"} flexDirection={"row"}>
                      {adoption.status === "Pending" && (
                        <>
                          <Button
                            variant="outlined"
                            color="success"
                            onClick={() =>
                              handleUpdateStatus(
                                adoption.adoptionId,
                                "Approved"
                              )
                            }
                            sx={{ textTransform: "none", marginRight: 2 }}
                          >
                            Approve
                          </Button>
                          <Button
                            variant="outlined"
                            color="error"
                            onClick={() =>
                              handleUpdateStatus(
                                adoption.adoptionId,
                                "Rejected"
                              )
                            }
                            sx={{ textTransform: "none" }}
                          >
                            Reject
                          </Button>
                        </>
                      )}
                      <Button
                        variant="outlined"
                        color="primary"
                        onClick={() =>
                          handleViewPetDetails(adoption.petDto.petId)
                        }
                        sx={{
                          textTransform: "none",
                          marginRight: 2,
                          marginLeft: 2,
                        }}
                      >
                        View
                      </Button>
                    </Box>
                  </TableCell>
                  <TableCell></TableCell>
                  <TableCell></TableCell>
                </>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
    </Box>
  );
};

export default AdminPetAdoptionsList;
