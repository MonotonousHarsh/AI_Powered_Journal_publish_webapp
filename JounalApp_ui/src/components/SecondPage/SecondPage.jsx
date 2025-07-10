import React from 'react';
import { MoodCard } from '../MoodSelector/MoodCard';

export const SecondPageContent = () => {
  const additionalMoods = [
    {
      id: 5,
      title: "OCEAN BREEZE",
      image: "https://images.pexels.com/photos/1032650/pexels-photo-1032650.jpeg?auto=compress&cs=tinysrgb&w=400",
      color: "from-cyan-400 to-blue-600"
    },
    {
      id: 6,
      title: "GOLDEN HOUR",
      image: "https://images.pexels.com/photos/1181677/pexels-photo-1181677.jpeg?auto=compress&cs=tinysrgb&w=400",
      color: "from-yellow-400 to-orange-500"
    },
    {
      id: 7,
      title: "MYSTIC NIGHT",
      image: "https://images.pexels.com/photos/1624496/pexels-photo-1624496.jpeg?auto=compress&cs=tinysrgb&w=400",
      color: "from-indigo-500 to-purple-700"
    }
  ];

  return (
    <div className="max-w-6xl mx-auto">
      <div className="text-center mb-12">
        <h2 className="text-5xl font-bold mb-4">
          Or maybe this <span className="text-indigo-600">VIBE</span>?
        </h2>
        <div className="w-32 h-1 bg-gradient-to-r from-indigo-400 to-purple-500 mx-auto rounded-full"></div>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-3 gap-8 justify-items-center">
        {additionalMoods.map((mood, index) => (
          <MoodCard
            key={mood.id}
            mood={mood}
            index={index + 4} // Continue animation delay from first page
          />
        ))}
      </div>
    </div>
  );
};