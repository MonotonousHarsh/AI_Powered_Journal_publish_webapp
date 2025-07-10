import React, { useState } from 'react';
import { MoodCard } from './MoodCard';
import { Calendar, FileText } from 'lucide-react';

export const MoodSelector = () => {
  const [selectedMood, setSelectedMood] = useState(null);

  const moods = [
    {
      id: 1,
      title: "MISTY WOODS",
      subtitle: "Misty Woods",
      image: "https://images.pexels.com/photos/1496373/pexels-photo-1496373.jpeg?auto=compress&cs=tinysrgb&w=600",
      color: "from-green-400/80 to-emerald-600/80"
    },
    {
      id: 2,
      title: "ZEN FOCUS",
      subtitle: "Zen Focus",
      image: "https://images.pexels.com/photos/1051838/pexels-photo-1051838.jpeg?auto=compress&cs=tinysrgb&w=600",
      color: "from-orange-400/80 to-amber-600/80"
    },
    {
      id: 3,
      title: "COSMIC WHISPER",
      subtitle: "Cosmic Whisper",
      image: "https://images.pexels.com/photos/1252890/pexels-photo-1252890.jpeg?auto=compress&cs=tinysrgb&w=600",
      color: "from-blue-400/80 to-indigo-600/80"
    },
    {
      id: 4,
      title: "SERENE FLOW",
      subtitle: "Serene Flow",
      image: "https://images.pexels.com/photos/1738986/pexels-photo-1738986.jpeg?auto=compress&cs=tinysrgb&w=600",
      color: "from-slate-400/80 to-gray-600/80"
    }
  ];

  const handleMoodSelect = (moodId) => {
    setSelectedMood(moodId);
  };

  return (
    <div className="bg-white rounded-3xl shadow-2xl p-8 relative overflow-hidden">
      {/* Background Icons */}
      <div className="absolute top-6 left-6 opacity-20">
        <Calendar className="w-8 h-8 text-purple-400" />
      </div>
      <div className="absolute top-6 right-6 opacity-20">
        <FileText className="w-8 h-8 text-purple-400" />
      </div>

      {/* Header */}
      <div className="text-center mb-12">
        <h1 className="text-4xl md:text-5xl font-bold text-gray-800 mb-4">
          What is ur{' '}
          <span className="text-transparent bg-clip-text bg-gradient-to-r from-purple-600 via-purple-500 to-indigo-600">
            MOOD
          </span>{' '}
          today?
        </h1>
        <div className="w-full h-px bg-gradient-to-r from-transparent via-purple-300 to-transparent"></div>
      </div>

      {/* Mood Cards Grid */}
      <div className="grid grid-cols-2 md:grid-cols-4 gap-6 mb-8">
        {moods.map((mood, index) => (
          <MoodCard
            key={mood.id}
            mood={mood}
            index={index}
            isSelected={selectedMood === mood.id}
            onSelect={() => handleMoodSelect(mood.id)}
          />
        ))}
      </div>

      {/* Selection Indicator */}
      {selectedMood && (
        <div className="text-center">
          <div className="inline-flex items-center px-6 py-3 bg-gradient-to-r from-purple-500 to-indigo-500 text-white rounded-full shadow-lg animate-pulse">
            <span className="font-semibold">
              {moods.find(m => m.id === selectedMood)?.title} selected
            </span>
          </div>
        </div>
      )}
    </div>
  );
};