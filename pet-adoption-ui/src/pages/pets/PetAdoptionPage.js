import React, { useEffect, useState } from "react";
import { Box, Grid, Paper, Typography, Button, TextField } from "@mui/material";
import PaymentForm from "./PaymentForm";
import { useLocation, useNavigate, useParams } from "react-router-dom";
import CustomSnackbar from "../home/CustomSnackbar";
import { useAuth } from "../../contexts/AuthContext";
import api from "../../api/api";

const PetAdoptionPage = () => {
  const location = useLocation();
  const { petId, adoptionId } = useParams();
  const [snackbar, setSnackbar] = useState({
    message: "",
    alertType: "",
  });
  const [openSnackbar, setOpenSnackbar] = useState(false);
  const navigate = useNavigate();
  const { user } = useAuth();
  const [petData, setPetData] = useState({});
  const [categoryName, setCategoryName] = useState("");
  const [breedName, setBreedName] = useState("");

  useEffect(() => {
    const fetchPetDetails = async () => {
      try {
        const response = await api.get(`/pets/${petId}`);
        setPetData(response.data);
        const categoriesRes = await api.get(`/categories`);

        const breedsRes = await api.get(`/breeds`);
        const categoryName = categoriesRes.data.find(
          (category) => category.categoryId === response.data.categoryId
        );
        setCategoryName(categoryName?.name);

        const breed = breedsRes.data.find(
          (breed) => breed.breedId === response.data.breedId
        );
        setBreedName(breed.name);
      } catch (error) {
        console.error("Error fetching pet details:", error);
      }
    };

    fetchPetDetails();
  }, [petId]);

  const handlePaymentSubmit = async ({
    cardNumber,
    cardName,
    expiryMonth,
    expiryYear,
    cvv,
  }) => {
    const PAID = "Paid";
    const CARD = "Card";

    const paymentDetails = {
      paymentType: CARD,
      totalAmount: petData?.adoptionFee,
      cardNumber,
      cardName,
      expiryMonth,
      expiryYear,
      cvv,
      status: PAID,
    };

    try {
      // Make an API call to save the booking
      await api.post(`/pet-adoptions/${petId}/pay-fee`, {
        userId: user?.userId,
        username: user?.firstName + " " + user?.lastName,
        petId: petData?.petId,
        adoptionId,
        totalPrice: petData?.adoptionFee,
        paymentDetails,
        status: "Pending",
      });

      setSnackbar({
        message: "Pet adoption application submitted successfully!",
        alertType: "success",
      });
      setOpenSnackbar(true);
      setTimeout(() => {
        navigate("/user/pet-adoptions");
      }, 1000);
    } catch (error) {
      console.error("Error while adopting the pet:", error);
      setSnackbar({ message: error?.response.data, alertType: "error" });
      setOpenSnackbar(true);
    }
  };

  return (
    <Box p={4}>
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
      <Typography variant="h4" gutterBottom>
        Pet Adoption and Payment
      </Typography>
      <Grid container spacing={4}>
        <Grid item xs={12} md={6}>
          <Paper elevation={3} style={{ padding: "16px" }}>
            <Typography variant="h6" gutterBottom>
              Pet Details
            </Typography>
            <Typography variant="body1">
              <strong>Name:</strong> {petData.name}
            </Typography>
            <Typography variant="body1">
              <strong>Age:</strong> {petData.age}
            </Typography>
            <Typography variant="body1">
              <strong>Gender:</strong> {petData.gender}
            </Typography>
            <Typography variant="body1">
              <strong>Category:</strong> {categoryName}
            </Typography>
            <Typography variant="body1">
              <strong>Breed:</strong> {breedName}
            </Typography>
            <Typography variant="body1">
              <strong>Size:</strong> {petData.size}
            </Typography>
            <Typography variant="body1">
              <strong>Adoption Fee:</strong> ${petData.adoptionFee}
            </Typography>
          </Paper>
        </Grid>

        <Grid item xs={12} md={6}>
          <PaymentForm
            totalPrice={petData.adoptionFee}
            handlePayment={handlePaymentSubmit}
          />
        </Grid>
      </Grid>
    </Box>
  );
};

export default PetAdoptionPage;
