import React from 'react';
import WeatherWidget from '../components/WeatherWidget';
import { Cloud } from 'lucide-react';

const Weather = () => {
  return (
    <div className="max-w-4xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
      <div className="mb-6">
        <div className="flex items-center mb-2">
          <Cloud className="h-6 w-6 text-indigo-600 mr-2" />
          <h1 className="text-2xl font-bold text-gray-900 dark:text-white">Weather</h1>
        </div>
        <p className="text-gray-600 dark:text-gray-400">
          Check the weather in any city to add context to your journal entries.
        </p>
      </div>

      <WeatherWidget />
    </div>
  );
};

export default Weather;