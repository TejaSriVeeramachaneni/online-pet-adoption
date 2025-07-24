import React from "react";
import { Navigate } from "react-router-dom";
import { useAuth } from "../contexts/AuthContext";

const adminUrls = [
  "/admin/dashboard",
  "/admin/users",
  "/admin/users/:id",
  "/admin/pets/:petId/update",
  "/admin/pets/create-new",
  "/admin/pets",
  "/admin/pet-adoptions",
  "/admin/pet-donations",
  "/admin/pets/:petId/vaccination",
  "/admin/pets/:petId/vaccination/:vaccineId/update",
  "/admin/pets/:petId/vaccination/new",
];

const userUrls = [
  "/user/dashboard",
  "/pets/:petId/adopt",
  "/pets/:petId/adopt/:adoptionId/pay-fee",
  "/user/pet-adoptions",
  "/user/pet-donations",
  "/user/pet-donations/create-new",
];

function PrivateRoute({ path, element: Component }) {
  const { isAuthenticated, isAdmin, isUser } = useAuth();
  let redirectUrl = "/access-denied";
  let hasAccess = false;

  if (adminUrls.includes(path)) {
    hasAccess = isAuthenticated() && isAdmin();
  } else if (userUrls.includes(path)) {
    hasAccess = isAuthenticated() && isUser();
  }

  return <>{hasAccess ? <Component /> : <Navigate to={redirectUrl} />}</>;
}

export default PrivateRoute;
