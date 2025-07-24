import React, { useState, useEffect } from "react";
import {
  Box,
  Card,
  CardContent,
  CardMedia,
  Typography,
  Button,
  Grid,
} from "@mui/material";
import { useParams, useNavigate } from "react-router-dom";
import api from "../../api/api";
import CustomSnackbar from "../home/CustomSnackbar";
import { useAuth } from "../../contexts/AuthContext";

const PetDetails = () => {
  const { petId } = useParams();
  const navigate = useNavigate();
  const [petData, setPetData] = useState({});
  const { isAuthenticated, user } = useAuth();

  const [snackbar, setSnackbar] = useState({
    message: "",
    alertType: "",
  });
  const [openSnackbar, setOpenSnackbar] = useState(false);
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

  const handleAdopt = async () => {
    if (isAuthenticated()) {
      try {
        await api.post("/pet-adoptions", {
          userId: user?.userId,
          username: user?.firstName + " " + user?.lastName,
          petId: petData?.petId,
          totalPrice: petData?.adoptionFee,
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
    } else {
      setSnackbar({
        message: "You must login first",
        alertType: "error",
      });
      setOpenSnackbar(true);
      setTimeout(() => {
        navigate("/user/login");
      }, 1000);
    }
  };

  const handlePaymentSubmit = async () => {};

  return (
    <Box marginTop={"20px"}>
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
      <Card>
        <Grid container spacing={2}>
          <Grid item xs={12} sm={6}>
            <CardMedia
              component="img"
              alt={petData.name}
              height="460"
              image={
                petData.imageUrl
                  ? require(`../../images/pet-images/${petData.imageUrl}`)
                  : require(`../../images/no-pet-image.jpeg`)
              }
            />
          </Grid>
          <Grid item xs={12} sm={6}>
            <CardContent>
              <Typography variant="h5" gutterBottom>
                {petData.name}
              </Typography>
              <Typography
                variant="body2"
                color="text.secondary"
                sx={{ marginBottom: 2 }}
              >
                {`Age: ${petData.age} - Gender: ${petData.gender}`}
              </Typography>
              <Typography
                variant="body2"
                color="text.secondary"
                sx={{ marginBottom: 2 }}
              >
                {`Size: ${petData.size} - Color: ${petData.color}`}
              </Typography>
              <Typography
                variant="body2"
                color="text.secondary"
                sx={{ marginBottom: 2 }}
              >
                {`Status: ${petData.status}`}
              </Typography>
              <Typography
                variant="body2"
                color="text.secondary"
                sx={{ marginBottom: 2 }}
              >
                {`Available for Adoption: ${
                  petData.availableForAdoption ? "Yes" : "No"
                }`}
              </Typography>
              <Typography
                variant="body2"
                color="text.secondary"
                sx={{ marginBottom: 2 }}
              >
                {`Adoption Fee: $${petData.adoptionFee}`}
              </Typography>
              <Typography
                variant="body2"
                color="text.secondary"
                sx={{ marginBottom: 2 }}
              >
                {`Category: ${categoryName}`}
              </Typography>
              <Typography
                variant="body2"
                color="text.secondary"
                sx={{ marginBottom: 2 }}
              >
                {`Breed: ${breedName}`}
              </Typography>
              <Typography
                variant="body2"
                color="text.secondary"
                sx={{ marginBottom: 2 }}
              >
                {`Temperament: ${petData.temperament}`}
              </Typography>
              <Typography
                variant="body2"
                color="text.secondary"
                sx={{ marginBottom: 2 }}
              >
                {`Special Requirements: ${petData.specialRequirements}`}
              </Typography>
              {Boolean(petData.availableForAdoption) && (
                <Button
                  variant="contained"
                  color="primary"
                  onClick={handleAdopt}
                  style={{ marginTop: 16 }}
                >
                  Adopt {petData.name}
                </Button>
              )}
            </CardContent>
          </Grid>
        </Grid>
      </Card>
    </Box>
  );
};

export default PetDetails;
