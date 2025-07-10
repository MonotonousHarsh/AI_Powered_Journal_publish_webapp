import React, { useState } from 'react';

export const MoodCard = ({ mood, index, isSelected, onSelect }) => {
  const [isHovered, setIsHovered] = useState(false);

  return (
    <div
      className="relative group cursor-pointer"
      onMouseEnter={() => setIsHovered(true)}
      onMouseLeave={() => setIsHovered(false)}
      onClick={onSelect}
      style={{
        animationDelay: `${index * 0.2}s`
      }}
    >
      <div className={`
        relative w-full aspect-[3/4] rounded-2xl overflow-hidden
        transform transition-all duration-500 ease-out
        animate-fade-in-up
        ${isHovered ? 'scale-105 -rotate-1' : 'scale-100 rotate-0'}
        ${isSelected ? 'ring-4 ring-purple-400 ring-offset-4' : ''}
        ${isHovered ? 'shadow-2xl shadow-purple-500/30' : 'shadow-lg shadow-black/20'}
      `}>
        {/* Background Image */}
        <div
          className="absolute inset-0 bg-cover bg-center transition-transform duration-700 group-hover:scale-110"
          style={{
            backgroundImage: `url(${mood.image})`
          }}
        />

        {/* Gradient Overlay */}
        <div className={`
          absolute inset-0 bg-gradient-to-t ${mood.color}
          transition-opacity duration-500
          ${isHovered ? 'opacity-60' : 'opacity-70'}
        `} />

        {/* Content */}
        <div className="absolute inset-0 flex flex-col justify-end p-4">
          <div className={`
            text-center transform transition-all duration-500
            ${isHovered ? 'translate-y-0 scale-105' : 'translate-y-1'}
          `}>
            <h3 className="text-white font-bold text-sm md:text-base mb-1 tracking-wider">
              {mood.title}
            </h3>
            <div className="w-8 h-px bg-white/60 mx-auto"></div>
          </div>
        </div>

        {/* Hover Glow Effect */}
        <div className={`
          absolute inset-0 rounded-2xl
          transition-all duration-500
          ${isHovered ? 'bg-white/10' : 'bg-transparent'}
        `} />

        {/* Selection Indicator */}
        {isSelected && (
          <div className="absolute top-3 right-3">
            <div className="w-6 h-6 bg-purple-500 rounded-full flex items-center justify-center animate-pulse">
              <div className="w-2 h-2 bg-white rounded-full"></div>
            </div>
          </div>
        )}
      </div>

      {/* Floating Animation Particles */}
      {isHovered && (
        <div className="absolute inset-0 pointer-events-none">
          <div className="absolute top-4 left-4 w-1 h-1 bg-white/80 rounded-full animate-ping" />
          <div className="absolute top-8 right-6 w-1.5 h-1.5 bg-purple-300/80 rounded-full animate-pulse" />
          <div className="absolute bottom-12 left-8 w-1 h-1 bg-pink-300/70 rounded-full animate-bounce" />
        </div>
      )}
    </div>
  );
};