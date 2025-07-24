import {
  AppBar,
  Avatar,
  Button,
  Menu,
  MenuItem,
  Toolbar,
  Typography,
} from "@mui/material";
import { useState } from "react";
import { Link } from "react-router-dom";
import styled from "styled-components";
import { useAuth } from "../../contexts/AuthContext";

const StyledLink = styled(Link)`
  text-decoration: none;
  color: white;
  margin-left: 20px;
  text-transform: none; /* Add this line to prevent text transformation */
`;

const Header = () => {
  const { isAdmin, isAuthenticated, isUser, user } = useAuth();
  const isLoggedIn = isAuthenticated();
  const [anchorEl, setAnchorEl] = useState(null);

  const handleMenuOpen = (event) => {
    setAnchorEl(event.currentTarget);
  };

  const handleMenuClose = () => {
    setAnchorEl(null);
  };

  const linkStyle = {
    textTransform: "none", // Prevent text transformation
  };

  return (
    <AppBar position="sticky" style={{ top: 0 }}>
      <Toolbar>
        <StyledLink to="/">
          <Typography
            variant="h6"
            component="div"
            style={{ flexGrow: 1, fontSize: "20px" }}
          >
            <b>Pet Adoption</b>
          </Typography>
        </StyledLink>

        <div style={{ marginLeft: "auto" }}>
          {isAdmin() && isLoggedIn && (
            <>
              <StyledLink to="/admin/pets">
                <Button color="inherit" style={linkStyle}>
                  Pets
                </Button>
              </StyledLink>
              <StyledLink to="/admin/pet-adoptions">
                <Button color="inherit" style={linkStyle}>
                  Pet adoptions
                </Button>
              </StyledLink>
              <StyledLink to="/admin/pet-donations">
                <Button color="inherit" style={linkStyle}>
                  Pet donations
                </Button>
              </StyledLink>
              <StyledLink to="/admin/dashboard">
                <Button color="inherit" style={linkStyle}>
                  Dashboard
                </Button>
              </StyledLink>
            </>
          )}
          {!isAdmin() && (
            <>
              <StyledLink to="/pets">
                <Button color="inherit" style={linkStyle}>
                  Pets
                </Button>
              </StyledLink>
            </>
          )}
          {isUser() && isLoggedIn && (
            <>
              <StyledLink to="/user/pet-adoptions">
                <Button color="inherit" style={linkStyle}>
                  Pet adoptions
                </Button>
              </StyledLink>
              <StyledLink to="/user/pet-donations">
                <Button color="inherit" style={linkStyle}>
                  Pet donations
                </Button>
              </StyledLink>
              <StyledLink to="/user/dashboard">
                <Button color="inherit" style={linkStyle}>
                  Dashboard
                </Button>
              </StyledLink>
            </>
          )}

          {!isLoggedIn && (
            <>
              <StyledLink to="/user/login">
                <Button color="inherit" style={linkStyle}>
                  Login
                </Button>
              </StyledLink>
              <StyledLink to="/user/register">
                <Button color="inherit" style={linkStyle}>
                  Register
                </Button>
              </StyledLink>
            </>
          )}
          {isLoggedIn && (
            <>
              <Button onClick={handleMenuOpen}>
                <Avatar
                  style={{
                    backgroundColor: "white",
                    color: "black",
                    fontSize: "16px", // Set the font size
                    width: "32px", // Set the width
                    height: "32px", // Set the height
                  }}
                >
                  {user?.firstName?.charAt(0)}
                </Avatar>
              </Button>
              <Menu
                anchorEl={anchorEl}
                open={Boolean(anchorEl)}
                onClose={handleMenuClose}
              >
                {!isAdmin() ? (
                  <Link style={{ textDecoration: "none" }} to={"/user/profile"}>
                    <MenuItem
                      style={{ color: "black" }}
                      onClick={handleMenuClose}
                    >
                      {user?.firstName}
                    </MenuItem>
                  </Link>
                ) : (
                  <MenuItem onClick={handleMenuClose}>
                    {user?.firstName}
                  </MenuItem>
                )}
                <Link style={{ textDecoration: "none" }} to="/logout">
                  <MenuItem style={{ color: "black" }}>Logout</MenuItem>
                </Link>
              </Menu>
            </>
          )}
        </div>
      </Toolbar>
    </AppBar>
  );
};

export default Header;
