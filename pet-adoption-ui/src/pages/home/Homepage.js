import React from "react";
import { Link } from "react-router-dom";
import {
  Button,
  Typography,
  Container,
  Grid,
  Card,
  CardMedia,
} from "@mui/material";
import petImage1 from "../../images/home-image2.jpeg";
import petImage2 from "../../images/home-image3.jpeg";
import adoptionIcon from "../../images/home-image1.jpeg";

const HomePage = () => {
  return (
    <Container maxWidth="md" style={{ marginTop: "50px" }}>
      <Grid container spacing={3}>
        <Grid item xs={12} sm={6}>
          <Typography variant="h2" align="left" gutterBottom>
            Find Your Perfect Companion
          </Typography>
          <Typography variant="body1" align="left" paragraph>
            Welcome to our Pet Adoption Hub, where you can discover and adopt
            your new furry friend. Find joy and companionship by giving a loving
            home to a pet in need.
          </Typography>
          <Button
            variant="contained"
            color="primary"
            component={Link}
            to="/pets"
            style={{ marginTop: "10px" }}
          >
            Browse Pets
          </Button>
        </Grid>

        <Grid item xs={12} sm={6}>
          <Card>
            <CardMedia
              component="img"
              alt="Adoption Icon"
              height="200"
              image={adoptionIcon}
              style={{ borderRadius: "8px" }}
            />
          </Card>
        </Grid>
      </Grid>

      <Grid
        container
        spacing={2}
        justifyContent="center"
        style={{ marginTop: "30px" }}
      >
        <Grid item xs={12} sm={6} marginBottom={3}>
          <Card>
            <CardMedia
              component="img"
              alt="Pet 1"
              height="250"
              image={petImage1}
              style={{ borderRadius: "8px" }}
            />
          </Card>
        </Grid>
        <Grid item xs={12} sm={6}>
          <Card>
            <CardMedia
              component="img"
              alt="Pet 2"
              height="250"
              image={petImage2}
              style={{ borderRadius: "8px" }}
            />
          </Card>
        </Grid>
      </Grid>
    </Container>
  );
};

export default HomePage;
