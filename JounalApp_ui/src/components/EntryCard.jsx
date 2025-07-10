import React from 'react';
import { Link } from 'react-router-dom';
import { Calendar, Edit, Trash2 } from 'lucide-react';

const EntryCard = ({ entry, onDelete }) => {
  const formatDate = (dateString) => {
    return new Date(dateString).toLocaleDateString('en-US', {
      year: 'numeric',
      month: 'short',
      day: 'numeric',
    });
  };

  const getSentimentBadge = (sentiment) => {
    if (!sentiment) return null;

    const colors = {
      positive: 'bg-green-100 text-green-800 dark:bg-green-900 dark:text-green-200',
      negative: 'bg-red-100 text-red-800 dark:bg-red-900 dark:text-red-200',
      neutral: 'bg-gray-100 text-gray-800 dark:bg-gray-700 dark:text-gray-200',
    };

    return (
      <span className={`inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium ${colors[sentiment] || colors.neutral}`}>
        {sentiment}
      </span>
    );
  };

  return (
    <div className="bg-white dark:bg-gray-800 rounded-lg shadow-sm border border-gray-200 dark:border-gray-700 p-6 hover:shadow-md transition-shadow">
      <div className="flex items-start justify-between mb-4">
        <div className="flex-1">
          <Link to={`/entry/${entry._id}`} className="block">
            <h3 className="text-lg font-semibold text-gray-900 dark:text-white hover:text-indigo-600 dark:hover:text-indigo-400 transition-colors">
              {entry.title}
            </h3>
          </Link>
          <div className="flex items-center text-sm text-gray-500 dark:text-gray-400 mt-1">
            <Calendar className="h-4 w-4 mr-1" />
            {formatDate(entry.created_at)}
          </div>
        </div>
        {entry.sentiment && (
          <div className="ml-4">
            {getSentimentBadge(entry.sentiment)}
          </div>
        )}
      </div>

      <p className="text-gray-600 dark:text-gray-300 mb-4 line-clamp-3">
        {entry.content.length > 150 ? `${entry.content.substring(0, 150)}...` : entry.content}
      </p>

      <div className="flex items-center justify-between">
        <Link
          to={`/entry/${entry._id}`}
          className="text-indigo-600 hover:text-indigo-500 text-sm font-medium"
        >
          Read more
        </Link>
        <div className="flex items-center space-x-2">
          <Link
            to={`/entry/${entry._id}/edit`}
            className="p-2 text-gray-400 hover:text-indigo-600 dark:hover:text-indigo-400 transition-colors"
          >
            <Edit className="h-4 w-4" />
          </Link>
          <button
            onClick={() => onDelete(entry._id)}
            className="p-2 text-gray-400 hover:text-red-600 dark:hover:text-red-400 transition-colors"
          >
            <Trash2 className="h-4 w-4" />
          </button>
        </div>
      </div>
    </div>
  );
};

export default EntryCard;