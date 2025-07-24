import React from "react";
import styled from "styled-components";
import { Box, Card, CardContent, Typography } from "@mui/material";
import { useNavigate } from "react-router-dom";

const StyledBox = styled(Box)`
  padding: 32px;
  text-align: center;
  text-transform: none;
`;

const StyledItem = styled(Box)`
  margin: 20px;
`;

const StyledCard = styled(Card)`
  border-radius: 8px;
  overflow: hidden;
  background-color: #f5f5f5;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  transition: transform 0.3s ease-in-out;

  &:hover {
    transform: translateY(-5px);
  }
`;

const AdminDashboard = () => {
  const navigate = useNavigate();

  const items = [
    {
      title: "Pets",
      description: "View pets",
      link: "/admin/pets",
    },
    {
      title: "Pet donations",
      description: "View pet donations",
      link: "/admin/pet-donations",
    },
    {
      title: "Pet adoptions",
      description: "View pet adoptions",
      link: "/admin/pet-adoptions",
    },
  ];

  return (
    <StyledBox>
      {items.map((item, index) => (
        <StyledItem key={index} sx={{ cursor: "pointer" }}>
          <StyledCard onClick={() => navigate(item.link)}>
            <CardContent>
              <Typography variant="h5" gutterBottom>
                <b>{item.title}</b>
              </Typography>
              <Typography variant="body2" paragraph style={{ color: "blue" }}>
                {item.description}
              </Typography>
            </CardContent>
          </StyledCard>
        </StyledItem>
      ))}
    </StyledBox>
  );
};

export default AdminDashboard;
