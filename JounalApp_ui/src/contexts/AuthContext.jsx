import React, { createContext, useContext, useState, useEffect } from 'react';
import api from '../utils/api';

const AuthContext = createContext();

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (context === undefined) {
    throw new Error('useAuth must be used within an AuthProvider');
  }
  return context;
};

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null);
  const [token, setToken] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const initializeAuth = async () => {
      const storedToken = localStorage.getItem('token');
      const storedUser = localStorage.getItem('user');

      if (storedToken && storedUser) {
        try {
          setToken(storedToken);
          setUser(JSON.parse(storedUser));
        } catch (error) {
          console.error('Failed to parse stored user data:', error);
          localStorage.removeItem('token');
          localStorage.removeItem('user');
        }
      }
      setLoading(false);
    };

    initializeAuth();
  }, []);

 const login = async (username, password) => {
   try {
     const response = await api.post('/public/login', { username, password });
     console.log('🛠 RAW LOGIN RESPONSE:', response.data);
     console.log("typeof response.data:", typeof response.data);
     console.log("response.data.token:", response.data.token);
     console.log("response.data.user:", response.data.user);
    const { token: newToken, userDetails: userData } = response.data;

// TEMP DEBUG LOG:
    console.log("Parsed token:", newToken);
    console.log("Parsed userData:", userData);

     // Validate incoming data
     if (!newToken || typeof newToken !== 'string') {
       throw new Error('Login response did not include a valid token');
     }
     if (!userData || typeof userData !== 'object') {
       throw new Error('Login response did not include valid user data');
     }

     // Update state
     setToken(newToken);
     setUser(userData);

     // Persist to localStorage
     localStorage.setItem('token', newToken);
     localStorage.setItem('user', JSON.stringify(userData));
   } catch (error) {
     // On any failure, clean up stale storage
     localStorage.removeItem('token');
     localStorage.removeItem('user');
     // Forward a clear error message
     throw new Error(
       error.response?.data?.message
         ? error.response.data.message
         : error.message || 'Login failed'
     );
   }
 };


  const signup = async (username, email, password) => {
    try {
      await api.post('/public/signup', { username, email, password });
    } catch (error) {
      throw new Error(error.response?.data?.message || 'Signup failed');
    }
  };

  const updateProfile = async (userData) => {
    try {
      const response = await api.put('/user', userData);
      setUser(response.data);
      localStorage.setItem('user', JSON.stringify(response.data));
    } catch (error) {
      throw new Error(error.response?.data?.message || 'Profile update failed');
    }
  };

  const deleteAccount = async () => {
    try {
      await api.delete('/user');
      logout();
    } catch (error) {
      throw new Error(error.response?.data?.message || 'Account deletion failed');
    }
  };

  const logout = () => {
    setUser(null);
    setToken(null);
    localStorage.removeItem('token');
    localStorage.removeItem('user');
  };

  const isAuthenticated = !!token && !!user;
  const isAdmin = user?.roles?.includes('ADMIN') || false;

  const value = {
    user,
    token,
    login,
    signup,
    updateProfile,
    deleteAccount,
    logout,
    isAuthenticated,
    isAdmin,
    loading,
  };

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
};