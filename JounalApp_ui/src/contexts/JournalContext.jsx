import React, { createContext, useContext, useState,useCallback } from 'react';
import api from '../utils/api';


const JournalContext = createContext();

export const useJournal = () => {
  const context = useContext(JournalContext);
  if (context === undefined) {
    throw new Error('use Journal must be used within a JournalProvider');
  }
  return context;
};

export const JournalProvider = ({ children }) => {
  const [entries, setEntries] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

const fetchEntries =   useCallback (async () => {
  setLoading(true);
  setError(null);

  const token = localStorage.getItem('token');
  if (!token) {
    setError('Authentication required');
    setLoading(false);
    return;
  }
  api.defaults.headers.common['Authorization'] = `Bearer ${token}`;
  try {
    const response = await api.get("/entry/all-entry");

    const entries = response.data;
    console.log("Your ALL Entries   : " , entries);
    setEntries(entries);
    setError(entries.length === 0 ? 'No entries found' : null);

  } catch (err) {

    console.error('Fetch error:', err);
     console.error('Response data:', err.response?.data);
    setEntries([]);
   setError(
         err.response?.data?.message ||
         err.response?.data ||
         `Error ${err.response?.status}: ${err.message}`

       );
  } finally {
    setLoading(false);
  }
} , []);

  const createEntry = async (title, content) => {
       // 1) grab and verify token
        const token = localStorage.getItem('token');
        if (!token) {
          throw new Error('You must be logged in to create an entry');
        }
        api.defaults.headers.common['Authorization'] = `Bearer ${token}`;

    try {
      const response = await api.post('/entry/create-entry', { title, content });
       console.log("created entry :" ,response.data);
      setEntries(prev => [response.data, ...prev]);

      return response.data;
    } catch (err) {
        console.error('createEntry error:', err);
      const msg = err.response?.data?.message
                   || err.response?.data?.error
                   || err.message
                   || 'Failed to create entry';
         throw new Error(msg);
    }
  };

  const updateEntry = async (id, title, content) => {
    try {
        const stringId = entry._id;
      const response = await api.put(`/entry/id/${stringId}`, { title, content });
      setEntries(prev => prev.map(entry =>
        entry._id === id ? response.data : entry
      ));
      return response.data;
    } catch (error) {
      throw new Error(error.response?.data?.message || 'Failed to update entry');
    }
  };

  const deleteEntry = async (id) => {
    try {
        const stringId  = entry._id;
      await api.delete(`/entry/id/${stringId}`);
      setEntries(prev => prev.filter(entry => entry._id !== id));
    } catch (error) {
      throw new Error(error.response?.data?.message || 'Failed to delete entry');
    }
  };

  const getEntryById = async (id) => {
    try {
          const stringId = entry._id;
      const response = await api.get(`/entry/id/${stringId}`);
      return response.data;
    } catch (error) {
      throw new Error(error.response?.data?.message || 'Failed to fetch entry');
    }
  };

  const value = {
    entries,
    loading,
    error,
    fetchEntries,
    createEntry,
    updateEntry,
    deleteEntry,
    getEntryById,
  };

  return <JournalContext.Provider value={value}>{children}</JournalContext.Provider>;
};