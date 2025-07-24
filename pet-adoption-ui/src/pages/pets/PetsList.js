import React, { useState, useEffect } from "react";
import {
  Box,
  Card,
  CardContent,
  CardMedia,
  Typography,
  Grid,
} from "@mui/material";
import api from "../../api/api";
import { useNavigate } from "react-router-dom";

const PetsList = () => {
  const [petsList, setPetsList] = useState([]);
  const navigate = useNavigate();

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

  return (
    <Grid container spacing={3} marginTop={1}>
      {petsList
        .filter((pet) => pet.status === "Approved")
        .map((pet) => (
          <Grid key={pet.petId} item xs={12} sm={6} md={4} lg={3}>
            <Card
              onClick={() => {
                navigate(`/pets/${pet.petId}`);
              }}
              style={{ cursor: "pointer", borderRadius: 16 }}
            >
              <CardMedia
                component="img"
                alt={pet.name}
                height="180"
                image={
                  pet.imageUrl
                    ? require(`../../images//pet-images/${pet.imageUrl}`)
                    : require(`../../images/no-pet-image.jpeg`)
                }
              />
              <CardContent>
                <Typography variant="h6" gutterBottom>
                  {pet.name}
                </Typography>
                <Typography variant="body2" color="text.secondary">
                  {`Age: ${pet.age} - Gender: ${pet.gender}`}
                </Typography>
                <Typography variant="body2" color="text.secondary">
                  {`Size: ${pet.size} - Color: ${pet.color}`}
                </Typography>
                <Typography variant="body2" color="text.secondary">
                  {`Available for Adoption: ${
                    pet.availableForAdoption ? "Yes" : "No"
                  }`}
                </Typography>
                <Typography variant="body2" color="text.secondary">
                  {`Adoption Fee: $${pet.adoptionFee}`}
                </Typography>
              </CardContent>
            </Card>
          </Grid>
        ))}
    </Grid>
  );
};

export default PetsList;
