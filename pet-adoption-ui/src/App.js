import React from "react";
import CssBaseline from "@mui/material/CssBaseline";
import { ThemeProvider, createTheme } from "@mui/material/styles";
import moment from "moment";
import "moment-timezone";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import PrivateRoute from "./components/PrivateRoute";
import Layout from "./components/layout/Layout";
import { AuthProvider } from "./contexts/AuthContext";
import PageNotFound from "./pages/home/NotFound";
import Logout from "./pages/login/Logout";
import HomePage from "./pages/home/Homepage";
import UserLogin from "./pages/user/UserLogin";
import AdminLogin from "./pages/admin/AdminLogin";
import UserRegister from "./pages/user/UserRegister";
import UserDashboard from "./pages/user/UserDashboard";
import AdminDashboard from "./pages/admin/AdminDashboard";
import AdminPetsList from "./pages/admin/AdminPetsList";
import CreatePet from "./pages/admin/CreatePet";
import EditPet from "./pages/admin/EditPet";
import PetsList from "./pages/pets/PetsList";
import PetDetails from "./pages/pets/PetDetails";
import PetAdoptionPage from "./pages/pets/PetAdoptionPage";
import PetAdoptionsList from "./pages/pets/PetAdoptionsList";
import AdminPetAdoptionsList from "./pages/admin/AdminPetAdoptionsList";
import PetDonationApplication from "./pages/pets/PetDonationApplication";
import PetDonationsList from "./pages/pets/PetDonationsList";
import AdminPetDonationsList from "./pages/admin/AdminPetDonationsList";
import PetVaccinationList from "./pages/pets/PetVaccinationList";
import AddVaccinationDetails from "./pages/pets/AddVaccinationDetails";
import EditVaccinationDetails from "./pages/pets/EditVaccinationDetails";

moment.tz.setDefault("UTC");

// Create a theme with the "Open Sans" font family
const theme = createTheme({
  typography: {
    fontFamily: [
      "Open Sans", // Add any other fallback fonts here
      "-apple-system",
      "BlinkMacSystemFont",
      '"Segoe UI"',
      "Roboto",
      '"Helvetica Neue"',
      "Arial",
      "sans-serif",
    ].join(","),
  },
  palette: {
    primary: {
      main: "#6504b5",
    },
    secondary: {
      main: "#D51F1F",
    },
  },
});

function App() {
  return (
    <AuthProvider>
      <div
        style={{
          minHeight: "100vh",
          display: "flex",
          flexDirection: "column",
          backgroundColor: "#E6E6FA", // Transparent light gray color
        }}
      >
        <ThemeProvider theme={theme}>
          <CssBaseline />
          <Router>
            <Layout style={{ flex: 1 }}>
              <Routes>
                <Route exact path="/" element={<HomePage />} />
                <Route exact path="/logout" element={<Logout />} />
                <Route path="*" element={<PageNotFound />} />
                <Route exact path="/user/login" element={<UserLogin />} />
                <Route exact path="/user/register" element={<UserRegister />} />

                <Route exact path="/pets" element={<PetsList />} />
                <Route exact path="/pets/:petId" element={<PetDetails />} />

                <Route
                  exact
                  path="/pets/:petId/adopt/:adoptionId/pay-fee"
                  element={
                    <PrivateRoute
                      path="/pets/:petId/adopt/:adoptionId/pay-fee"
                      element={PetAdoptionPage}
                    />
                  }
                />

                <Route
                  exact
                  path="/user/dashboard"
                  element={
                    <PrivateRoute
                      path="/user/dashboard"
                      element={UserDashboard}
                    />
                  }
                />

                <Route
                  exact
                  path="/user/pet-adoptions"
                  element={
                    <PrivateRoute
                      path="/user/pet-adoptions"
                      element={PetAdoptionsList}
                    />
                  }
                />

                <Route
                  exact
                  path="/user/pet-donations/create-new"
                  element={
                    <PrivateRoute
                      path="/user/pet-donations/create-new"
                      element={PetDonationApplication}
                    />
                  }
                />
                <Route
                  exact
                  path="/user/pet-donations"
                  element={
                    <PrivateRoute
                      path="/user/pet-donations"
                      element={PetDonationsList}
                    />
                  }
                />

                <Route
                  exact
                  path="/admin/dashboard"
                  element={
                    <PrivateRoute
                      path="/admin/dashboard"
                      element={AdminDashboard}
                    />
                  }
                />
                <Route
                  exact
                  path="/admin/pet-adoptions"
                  element={
                    <PrivateRoute
                      path="/admin/pet-adoptions"
                      element={AdminPetAdoptionsList}
                    />
                  }
                />
                <Route
                  exact
                  path="/admin/pet-donations"
                  element={
                    <PrivateRoute
                      path="/admin/pet-donations"
                      element={AdminPetDonationsList}
                    />
                  }
                />
                <Route
                  exact
                  path="/admin/pets"
                  element={
                    <PrivateRoute path="/admin/pets" element={AdminPetsList} />
                  }
                />
                <Route
                  exact
                  path="/admin/pets/create-new"
                  element={
                    <PrivateRoute
                      path="/admin/pets/create-new"
                      element={CreatePet}
                    />
                  }
                />
                <Route
                  exact
                  path="/admin/pets/:petId/update"
                  element={
                    <PrivateRoute
                      path="/admin/pets/:petId/update"
                      element={EditPet}
                    />
                  }
                />
                <Route
                  exact
                  path="/admin/pets/:petId/vaccination"
                  element={
                    <PrivateRoute
                      path="/admin/pets/:petId/vaccination"
                      element={PetVaccinationList}
                    />
                  }
                />
                <Route
                  exact
                  path="/admin/pets/:petId/vaccination/new"
                  element={
                    <PrivateRoute
                      path="/admin/pets/:petId/vaccination/new"
                      element={AddVaccinationDetails}
                    />
                  }
                />
                <Route
                  exact
                  path="/admin/pets/:petId/vaccination/:vaccineId/update"
                  element={
                    <PrivateRoute
                      path="/admin/pets/:petId/vaccination/:vaccineId/update"
                      element={EditVaccinationDetails}
                    />
                  }
                />
                <Route exact path="/admin/login" element={<AdminLogin />} />
              </Routes>
            </Layout>
          </Router>
        </ThemeProvider>
      </div>
    </AuthProvider>
  );
}

export default App;
