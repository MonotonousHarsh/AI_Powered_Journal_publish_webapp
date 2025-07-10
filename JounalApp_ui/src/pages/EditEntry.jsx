import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { useJournal } from '../contexts/JournalContext';
import EntryForm from '../components/EntryForm';
import { BookOpen } from 'lucide-react';

const EditEntry = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const { getEntryById, updateEntry } = useJournal();
  const [entry, setEntry] = useState(null);
  const [loading, setLoading] = useState(true);
  const [updateLoading, setUpdateLoading] = useState(false);
  const [error, setError] = useState('');

  useEffect(() => {
    const fetchEntry = async () => {
      setLoading(true);
      setError('');

      try {
        const entryData = await getEntryById(parseInt(id));
        setEntry(entryData);
      } catch (error) {
        setError(error.message);
      } finally {
        setLoading(false);
      }
    };

    fetchEntry();
  }, [id, getEntryById]);

  const handleSubmit = async (formData) => {
    setUpdateLoading(true);
    setError('');

    try {
      await updateEntry(parseInt(id), formData.title, formData.content);
      navigate(`/entry/${id}`);
    } catch (error) {
      setError(error.message);
    } finally {
      setUpdateLoading(false);
    }
  };

  const handleCancel = () => {
    navigate(`/entry/${id}`);
  };

  if (loading) {
    return (
      <div className="min-h-screen flex items-center justify-center bg-gray-50 dark:bg-gray-900">
        <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-indigo-600"></div>
      </div>
    );
  }

  if (!entry) {
    return (
      <div className="max-w-4xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
        <div className="text-center">
          <p className="text-gray-500 dark:text-gray-400">Entry not found</p>
        </div>
      </div>
    );
  }

  return (
    <div className="max-w-4xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
      <div className="mb-6">
        <div className="flex items-center mb-2">
          <BookOpen className="h-6 w-6 text-indigo-600 mr-2" />
          <h1 className="text-2xl font-bold text-gray-900 dark:text-white">Edit Entry</h1>
        </div>
        <p className="text-gray-600 dark:text-gray-400">
          Update your journal entry with new thoughts and experiences.
        </p>
      </div>

      {error && (
        <div className="mb-6 p-4 bg-red-50 dark:bg-red-900/20 border border-red-200 dark:border-red-800 rounded-md">
          <p className="text-sm text-red-600 dark:text-red-400">{error}</p>
        </div>
      )}

      <EntryForm
        initialData={entry}
        onSubmit={handleSubmit}
        onCancel={handleCancel}
        loading={updateLoading}
      />
    </div>
  );
};

export default EditEntry;