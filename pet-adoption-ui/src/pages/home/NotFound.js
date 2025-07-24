import React from "react";
import { Link, useNavigate } from "react-router-dom";
import { Button, Typography, Container, Grid } from "@mui/material";

const PageNotFound = () => {
  const navigate = useNavigate();

  return (
    <Container
      maxWidth="md"
      style={{ textAlign: "center", marginTop: "100px" }}
    >
      <Grid container spacing={3} justifyContent="center" alignItems="center">
        <Grid item xs={12}>
          <Typography variant="h1" gutterBottom>
            Oops!
          </Typography>
        </Grid>
        <Grid item xs={12}>
          <Typography variant="h3" gutterBottom>
            We can't seem to find the page you're looking for.
          </Typography>
        </Grid>
        <Grid item xs={12}>
          <Button
            component={Link}
            to="/"
            variant="contained"
            color="primary"
            size="large"
            sx={{ mr: 2 }}
          >
            Go to Home
          </Button>
          <Button onClick={() => navigate(-1)} variant="outlined" size="large">
            Go Back
          </Button>
        </Grid>
      </Grid>
    </Container>
  );
};

export default PageNotFound;
