import React, { createContext, useState, useContext, useEffect } from "react";
import { apiClient } from "@/pages/api/ApiClient";
import { signOut } from "firebase/auth";
import { auth } from "@/firebase";
import { useRouter } from "next/router";

export const AuthContext = createContext();

export const useAuth = () => useContext(AuthContext);

export const AuthProvider = ({ children }) => {
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const [username, setUsername] = useState(null);
  const [fullName, setFullName] = useState(null);
  const router = useRouter();

  useEffect(() => {
    if (localStorage.getItem("token")) {
      login(localStorage.getItem("username"), localStorage.getItem("token"))
        .then((response) => {
          apiClient.interceptors.request.use((config) => {
            config.headers.Authorization =
              "Bearer " + localStorage.getItem("token");
            return config;
          });
        })
        .catch((error) => {
          logout();
        });
    } else {
      logout();
    }
  }, []);

  /**
   * Logs in a user by setting the authorization header and making a request to the API to retrieve user data.
   *
   * @param {string} username - The username of the user.
   * @param {string} token - The authentication token.
   * @return {Promise<boolean>} A promise that resolves to true if the login is successful, false otherwise.
   */
  const login = async (username, token) => {
    apiClient.interceptors.request.use((config) => {
      config.headers.Authorization = "Bearer " + token;
      return config;
    });

    apiClient
      .get(`/users/${username}`)
      .then((response) => {
        if (response.status === 200) {
          setIsAuthenticated(true);
          setUsername(response.data.username);
          setFullName(response.data.fullName);
          localStorage.setItem("isAuth", true);
          localStorage.setItem("username", response.data.username);
          localStorage.setItem("fullName", response.data.fullName);
          return true;
        }
        return false;
      })
      .catch((error) => {
        logout();
        return false;
      });
  };

  const logout = () => {
    // Perform logout logic, set isAuthenticated to false
    signOut(auth)
      .then(() => {
        setIsAuthenticated(false);
        setUsername(null);
        setFullName(null);
        localStorage.removeItem("isAuth");
        localStorage.removeItem("username");
        localStorage.removeItem("fullName");
        localStorage.removeItem("token");
        router.push("/login");
      })
      .catch((error) => {
        // console.log(error);
      });
  };

  return (
    <AuthContext.Provider
      value={{ isAuthenticated, username, fullName, login, logout }}
    >
      {children}
    </AuthContext.Provider>
  );
};
