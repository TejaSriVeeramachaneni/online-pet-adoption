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
  TextField,
  Typography,
} from "@mui/material";
import { useNavigate } from "react-router-dom";
import api from "../../api/api";
import { useAuth } from "../../contexts/AuthContext";
import CustomSnackbar from "../home/CustomSnackbar";

const AdminPetDonationsList = () => {
  const navigate = useNavigate();
  const [petDonationsList, setPetDonationsList] = useState([]);
  const [searchTerm, setSearchTerm] = useState("");
  const [filteredPetDonations, setFilteredPetDonations] = useState([]);
  const [snackbar, setSnackbar] = useState({
    message: "",
    alertType: "",
  });
  const [openSnackbar, setOpenSnackbar] = useState(false);
  const fetchPetDonationsList = async () => {
    try {
      const response = await api.get(`/pet-donations`);
      setPetDonationsList(response.data);
      const filteredResults = searchTerm
        ? response.data?.filter(
            (donation) =>
              donation.username
                .toLowerCase()
                .includes(searchTerm.toLowerCase()) ||
              donation.email.toLowerCase().includes(searchTerm.toLowerCase())
          )
        : response.data;
      setFilteredPetDonations(filteredResults);
    } catch (error) {
      console.error("Error fetching pet donations list:", error);
    }
  };

  useEffect(() => {
    fetchPetDonationsList();
  }, []);

  const handleViewPetDetails = (petId) => {
    navigate(`/pets/${petId}`);
  };

  const handleUpdateStatus = async (donationId, status) => {
    try {
      // Make an API call to save the booking
      await api.put(`/pet-donations/${donationId}/update-status`, {
        status,
      });

      setSnackbar({
        message: "Pet donation approved successfully!",
        alertType: "success",
      });
      setOpenSnackbar(true);
      setTimeout(() => {
        fetchPetDonationsList();
      }, 1000);
    } catch (error) {
      console.error("Error while updating the donation status :", error);
      setSnackbar({ message: error?.response.data, alertType: "error" });
      setOpenSnackbar(true);
    }
  };

  useEffect(() => {
    const filteredResults = searchTerm
      ? petDonationsList.filter(
          (donation) =>
            donation.username
              ?.toLowerCase()
              .includes(searchTerm.toLowerCase()) ||
            donation.email?.toLowerCase().includes(searchTerm.toLowerCase())
        )
      : petDonationsList;
    setFilteredPetDonations(filteredResults);
  }, [searchTerm]);

  return (
    <Box p={3}>
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
        Pet Donations
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
                <strong>Donation Id</strong>
              </TableCell>
              <TableCell>
                <strong>Pet Name</strong>
              </TableCell>
              <TableCell>
                <strong>Gender</strong>
              </TableCell>
              <TableCell>
                <strong>Applicant Name</strong>
              </TableCell>
              <TableCell>
                <strong>Applicant Email</strong>
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
            {filteredPetDonations.map((donation) => (
              <TableRow key={donation.donationId}>
                <TableCell>{donation.donationId}</TableCell>
                <TableCell>{donation.petDto?.name}</TableCell>
                <TableCell>{donation.petDto?.gender}</TableCell>
                <TableCell>{donation.username}</TableCell>
                <TableCell>{donation.email}</TableCell>
                <TableCell>{donation.submissionDate}</TableCell>
                <TableCell>{donation.status}</TableCell>
                <TableCell>
                  <>
                    <Box display={"flex"} flexDirection={"row"}>
                      {donation.status === "Pending" && (
                        <>
                          <Button
                            variant="outlined"
                            color="success"
                            onClick={() =>
                              handleUpdateStatus(
                                donation.donationId,
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
                                donation.donationId,
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
                          handleViewPetDetails(donation.petDto.petId)
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
                  </>
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
    </Box>
  );
};

export default AdminPetDonationsList;
