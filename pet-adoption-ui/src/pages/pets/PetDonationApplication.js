import React, { useEffect, useState } from "react";
import {
  Box,
  Button,
  TextField,
  Typography,
  Container,
  FormControl,
  InputLabel,
  Select,
  MenuItem,
} from "@mui/material";
import { useNavigate } from "react-router-dom";
import api from "../../api/api";
import CustomSnackbar from "../home/CustomSnackbar";
import styled from "styled-components";
import { useAuth } from "../../contexts/AuthContext";
import CloudUploadIcon from "@mui/icons-material/CloudUpload";

const StyledFormControl = styled(FormControl)`
  width: 100%;
  margin-bottom: 20px;
`;

export const VisuallyHiddenInput = styled("input")({
  clip: "rect(0 0 0 0)",
  clipPath: "inset(50%)",
  height: 1,
  overflow: "hidden",
  position: "absolute",
  bottom: 0,
  left: 0,
  whiteSpace: "nowrap",
  width: 1,
});

const PetDonationApplication = () => {
  const navigate = useNavigate();
  const [petData, setPetData] = useState({
    name: "",
    breedId: "",
    categoryId: "",
    age: "",
    gender: "",
    size: "",
    color: "",
    temperament: "",
    specialRequirements: "",
  });
  const [snackbar, setSnackbar] = useState({
    message: "",
    alertType: "",
  });
  const [openSnackbar, setOpenSnackbar] = useState(false);
  const [categories, setCategories] = useState([]);
  const [breeds, setBreeds] = useState([]);
  const { isAdmin, user } = useAuth();
  const [image, setImage] = useState(null);
  const handleImageChange = (e) => {
    const file = e.target.files[0];
    setImage(file);
  };

  const handleChange = (e) => {
    setPetData({
      ...petData,
      [e.target.name]:
        e.target.type === "checkbox" ? e.target.checked : e.target.value,
    });
  };

  useEffect(() => {
    const fetchOnLoadData = async () => {
      try {
        const responseCategories = await api.get("/categories");
        setCategories(responseCategories.data);

        const responseBreeds = await api.get("/breeds");
        setBreeds(responseBreeds.data);
      } catch (error) {
        console.error("Error fetching pets list:", error);
      }
    };

    fetchOnLoadData();
  }, []);

  const handleDonatePet = async () => {
    const { name, breedId, categoryId, age, gender, size, color } = petData;

    if (
      !name ||
      !breedId ||
      !categoryId ||
      !age ||
      !gender ||
      !size ||
      !color ||
      !image
    ) {
      setSnackbar({
        message: "All fields marked with * are required!",
        alertType: "error",
      });
      setOpenSnackbar(true);
      return;
    }

    const formData = new FormData();
    formData.append("image", image);
    formData.append(
      "petDonationDto",
      JSON.stringify({
        status: "Pending",
        userId: user?.userId,
        username: user?.firstName + " " + user?.lastName,
        petDto: {
          ...petData,
          availableForAdoption: false,
          adoptionStatus: "Not Available",
          status: "Pending",
        },
      })
    );

    try {
      await api.post("/pet-donations", formData);
      setSnackbar({
        message: "Pet donated successfully!",
        alertType: "success",
      });
      setOpenSnackbar(true);
      setTimeout(() => {
        navigate("/user/pet-donations");
      }, 1000);
    } catch (error) {
      setSnackbar({ message: error?.response.data, alertType: "error" });
      setOpenSnackbar(true);
      console.error("Error donating pet:", error);
    }
  };

  const breedsList = breeds.filter(
    (breed) => breed.categoryId === petData.categoryId
  );

  return (
    <Container
      style={{
        display: "flex",
        flexDirection: "column",
        alignItems: "center",
        justifyContent: "center",
        marginTop: 40,
      }}
    >
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
      <Box
        style={{
          display: "flex",
          flexDirection: "column",
          alignItems: "center",
          padding: "32px",
          backgroundColor: "#ffffff",
          borderRadius: "8px",
          boxShadow: "0 3px 10px rgba(0, 0, 0, 0.2)",
          width: "100%",
        }}
      >
        <Typography style={{ marginRight: 10 }} variant="h5" gutterBottom>
          Donate Pet
        </Typography>
        <form>
          <Box display={"flex"} flexDirection={"row"} flexWrap={"wrap"}>
            <Box width={"44%"}>
              <TextField
                label="Pet Name *"
                variant="standard"
                fullWidth
                size="small"
                margin="normal"
                InputLabelProps={{ shrink: true }}
                name="name"
                value={petData.name}
                onChange={handleChange}
              />
              <TextField
                label="Age *"
                variant="standard"
                fullWidth
                size="small"
                margin="normal"
                InputLabelProps={{ shrink: true }}
                name="age"
                type="number"
                value={petData.age}
                onChange={handleChange}
              />
              <StyledFormControl sx={{ marginTop: 2 }} size="small">
                <InputLabel>Category *</InputLabel>
                <Select
                  value={petData.categoryId}
                  onChange={handleChange}
                  label="Category *"
                  name="categoryId"
                >
                  {categories.map((category) => (
                    <MenuItem
                      key={category.categoryId}
                      value={category.categoryId}
                    >
                      {category.name}
                    </MenuItem>
                  ))}
                </Select>
              </StyledFormControl>
              <StyledFormControl
                sx={{ marginTop: 2 }}
                disabled={!Boolean(petData.categoryId)}
                size="small"
              >
                <InputLabel>Breed *</InputLabel>
                <Select
                  value={petData.breedId}
                  onChange={handleChange}
                  label="Breed *"
                  name="breedId"
                >
                  {breedsList.map((breed) => (
                    <MenuItem key={breed.breedId} value={breed.breedId}>
                      {breed.name}
                    </MenuItem>
                  ))}
                </Select>
              </StyledFormControl>

              <StyledFormControl sx={{ marginTop: 2 }} size="small">
                <InputLabel>Gender *</InputLabel>
                <Select
                  value={petData.gender}
                  onChange={handleChange}
                  label="Gender *"
                  name="gender"
                >
                  <MenuItem value="male">Male</MenuItem>
                  <MenuItem value="female">Female</MenuItem>
                </Select>
              </StyledFormControl>
            </Box>
            <Box width={"44%"} marginLeft={8}>
              <TextField
                label="Size *"
                variant="standard"
                fullWidth
                size="small"
                margin="normal"
                InputLabelProps={{ shrink: true }}
                name="size"
                value={petData.size}
                onChange={handleChange}
              />
              <TextField
                label="Color *"
                variant="standard"
                fullWidth
                size="small"
                margin="normal"
                InputLabelProps={{ shrink: true }}
                name="color"
                value={petData.color}
                onChange={handleChange}
              />
              <TextField
                label="Temperament"
                variant="standard"
                fullWidth
                size="small"
                margin="normal"
                InputLabelProps={{ shrink: true }}
                name="temperament"
                value={petData.temperament}
                onChange={handleChange}
              />
              <TextField
                label="Special Requirements"
                variant="standard"
                fullWidth
                size="small"
                margin="normal"
                InputLabelProps={{ shrink: true }}
                name="specialRequirements"
                value={petData.specialRequirements}
                onChange={handleChange}
              />
              <Box mt={2} sx={{ display: "flex", alignSelf: "flex-start" }}>
                <Button
                  component="label"
                  variant="contained"
                  startIcon={<CloudUploadIcon />}
                  sx={{ textTransform: "none" }}
                >
                  {image?.name
                    ? image.name.length > 30
                      ? `${image.name.slice(0, 30)}...`
                      : image.name
                    : "Upload Pet image"}

                  <VisuallyHiddenInput
                    type="file"
                    accept="image/*"
                    onChange={handleImageChange}
                  />
                </Button>
              </Box>
            </Box>
          </Box>

          <Button
            variant="contained"
            color="primary"
            onClick={handleDonatePet}
            fullWidth
            style={{ borderRadius: 20, marginTop: 25 }}
            sx={{ width: "40%" }}
          >
            Donate Pet
          </Button>
        </form>
      </Box>
    </Container>
  );
};

export default PetDonationApplication;
