import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { useJournal } from '../contexts/JournalContext';
import EntryCard from '../components/EntryCard';
import WeatherWidget from '../components/WeatherWidget';
import { Plus, BookOpen, AlertCircle } from 'lucide-react';

const Dashboard = () => {
  const { entries, loading, error, fetchEntries, deleteEntry } = useJournal();
  const [deleteLoading, setDeleteLoading] = useState(null);

  useEffect(() => {
    fetchEntries();
  }, [fetchEntries]);

  const handleDelete = async (entryId) => {
    if (!window.confirm('Are you sure you want to delete this entry?')) return;

    setDeleteLoading(entryId);
    try {
      await deleteEntry(entryId);
    } catch (error) {
      console.error('Failed to delete entry:', error);
    } finally {
      setDeleteLoading(null);
    }
  };

  if (loading) {
    return (
      <div className="min-h-screen flex items-center justify-center bg-gray-50 dark:bg-gray-900">
        <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-indigo-600"></div>
      </div>
    );
  }

  return (
    <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
      <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
        <div className="lg:col-span-2">
          <div className="flex items-center justify-between mb-6">
            <div className="flex items-center">
              <BookOpen className="h-6 w-6 text-indigo-600 mr-2" />
              <h1 className="text-2xl font-bold text-gray-900 dark:text-white">Your Journal</h1>
            </div>
            <Link
              to="/create"
              className="inline-flex items-center px-4 py-2 bg-indigo-600 text-white rounded-md hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-offset-2 transition-colors"
            >
              <Plus className="h-4 w-4 mr-1" />
              New Entry
            </Link>
          </div>

          {error && (
            <div className="mb-6 p-4 bg-red-50 dark:bg-red-900/20 border border-red-200 dark:border-red-800 rounded-md">
              <div className="flex items-center">
                <AlertCircle className="h-5 w-5 text-red-600 dark:text-red-400 mr-2" />
                <p className="text-sm text-red-600 dark:text-red-400">{error}</p>
              </div>
            </div>
          )}

          {entries.length === 0 ? (
            <div className="text-center py-12">
              <BookOpen className="h-12 w-12 text-gray-400 mx-auto mb-4" />
              <h3 className="text-lg font-medium text-gray-900 dark:text-white mb-2">No entries yet</h3>
              <p className="text-gray-500 dark:text-gray-400 mb-4">
                Start your journaling journey by creating your first entry.
              </p>
              <Link
                to="/create"
                className="inline-flex items-center px-4 py-2 bg-indigo-600 text-white rounded-md hover:bg-indigo-700 transition-colors"
              >
                <Plus className="h-4 w-4 mr-1" />
                Create First Entry
              </Link>
            </div>
          ) : (
            <div className="grid gap-6">
              {entries.map((entry) => (
                <EntryCard
                  key={entry._id}
                  entry={entry}
                  onDelete={handleDelete}
                  loading={deleteLoading === entry._id}
                />
              ))}
            </div>
          )}
        </div>

        <div className="lg:col-span-1">
          <WeatherWidget />
        </div>
      </div>
    </div>
  );
};

export default Dashboard;