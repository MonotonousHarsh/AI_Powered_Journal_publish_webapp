import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useJournal } from '../contexts/JournalContext';
import EntryForm from '../components/EntryForm';
import { BookOpen } from 'lucide-react';

const CreateEntry = () => {
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');
  const { createEntry } = useJournal();
  const navigate = useNavigate();

  const handleSubmit = async (formData) => {
    setLoading(true);
    setError('');

    try {
      const entry = await createEntry(formData.title, formData.content);
      navigate(`/entry/${entry._id}`);
    } catch (error) {
      setError(error.message);
    } finally {
      setLoading(false);
    }
  };

  const handleCancel = () => {
    navigate('/dashboard');
  };

  return (
    <div className="max-w-4xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
      <div className="mb-6">
        <div className="flex items-center mb-2">
          <BookOpen className="h-6 w-6 text-indigo-600 mr-2" />
          <h1 className="text-2xl font-bold text-gray-900 dark:text-white">Create New Entry</h1>
        </div>
        <p className="text-gray-600 dark:text-gray-400">
          Share your thoughts and experiences in a new journal entry.
        </p>
      </div>

      {error && (
        <div className="mb-6 p-4 bg-red-50 dark:bg-red-900/20 border border-red-200 dark:border-red-800 rounded-md">
          <p className="text-sm text-red-600 dark:text-red-400">{error}</p>
        </div>
      )}

      <EntryForm
        onSubmit={handleSubmit}
        onCancel={handleCancel}
        loading={loading}
      />
    </div>
  );
};

export default CreateEntry;