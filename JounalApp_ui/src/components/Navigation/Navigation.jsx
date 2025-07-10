import React from 'react';
import { Link, useLocation } from 'react-router-dom';

export const Navigation = () => {
  const location = useLocation();

  return (
    <nav className="fixed top-0 left-0 right-0 z-50 bg-white/90 backdrop-blur-md shadow-lg">
      <div className="container mx-auto px-4 py-4">
        <div className="flex justify-center space-x-8">
          <Link
            to="/"
            className={`
              px-6 py-3 rounded-full font-semibold transition-all duration-300
              ${location.pathname === '/'
                ? 'bg-purple-600 text-white shadow-lg transform scale-105'
                : 'text-gray-700 hover:bg-purple-100 hover:text-purple-600'
              }
            `}
          >
            First Page
          </Link>
          <Link
            to="/second"
            className={`
              px-6 py-3 rounded-full font-semibold transition-all duration-300
              ${location.pathname === '/second'
                ? 'bg-indigo-600 text-white shadow-lg transform scale-105'
                : 'text-gray-700 hover:bg-indigo-100 hover:text-indigo-600'
              }
            `}
          >
            Second Page
          </Link>
        </div>
      </div>
    </nav>
  );
};