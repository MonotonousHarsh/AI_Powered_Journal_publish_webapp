import React, { useEffect, useRef } from 'react';
import barba from '@barba/core';
import { useLocation } from 'react-router-dom';

export const BarbaWrapper = ({ children }) => {
  const wrapperRef = useRef(null);
  const location = useLocation();

  useEffect(() => {
    // Initialize Barba.js
    barba.init({
      transitions: [
        {
          name: 'fade-slide',
          leave(data) {
            return new Promise((resolve) => {
              const timeline = [
                {
                  targets: data.current.container,
                  opacity: [1, 0],
                  translateY: [0, -50],
                  duration: 600,
                  easing: 'easeInOutQuart'
                }
              ];

              // Animate cards out
              const cards = data.current.container.querySelectorAll('.mood-card');
              cards.forEach((card, index) => {
                setTimeout(() => {
                  card.style.transform = 'translateY(-100px) rotate(15deg) scale(0.8)';
                  card.style.opacity = '0';
                }, index * 100);
              });

              setTimeout(resolve, 800);
            });
          },
          enter(data) {
            return new Promise((resolve) => {
              // Set initial state
              data.next.container.style.opacity = '0';
              data.next.container.style.transform = 'translateY(50px)';

              // Animate in
              setTimeout(() => {
                data.next.container.style.transition = 'all 0.8s cubic-bezier(0.25, 0.46, 0.45, 0.94)';
                data.next.container.style.opacity = '1';
                data.next.container.style.transform = 'translateY(0)';

                // Animate cards in
                const cards = data.next.container.querySelectorAll('.mood-card');
                cards.forEach((card, index) => {
                  card.style.opacity = '0';
                  card.style.transform = 'translateY(100px) scale(0.8)';

                  setTimeout(() => {
                    card.style.transition = 'all 0.6s cubic-bezier(0.25, 0.46, 0.45, 0.94)';
                    card.style.opacity = '1';
                    card.style.transform = 'translateY(0) scale(1)';
                  }, index * 150 + 200);
                });

                setTimeout(resolve, 1200);
              }, 100);
            });
          }
        },
        {
          name: 'slide-left',
          from: { namespace: 'home' },
          to: { namespace: 'second' },
          leave(data) {
            return new Promise((resolve) => {
              data.current.container.style.transition = 'transform 0.8s cubic-bezier(0.25, 0.46, 0.45, 0.94)';
              data.current.container.style.transform = 'translateX(-100%)';
              setTimeout(resolve, 800);
            });
          },
          enter(data) {
            return new Promise((resolve) => {
              data.next.container.style.transform = 'translateX(100%)';
              data.next.container.style.transition = 'transform 0.8s cubic-bezier(0.25, 0.46, 0.45, 0.94)';

              setTimeout(() => {
                data.next.container.style.transform = 'translateX(0)';
                setTimeout(resolve, 800);
              }, 50);
            });
          }
        },
        {
          name: 'slide-right',
          from: { namespace: 'second' },
          to: { namespace: 'home' },
          leave(data) {
            return new Promise((resolve) => {
              data.current.container.style.transition = 'transform 0.8s cubic-bezier(0.25, 0.46, 0.45, 0.94)';
              data.current.container.style.transform = 'translateX(100%)';
              setTimeout(resolve, 800);
            });
          },
          enter(data) {
            return new Promise((resolve) => {
              data.next.container.style.transform = 'translateX(-100%)';
              data.next.container.style.transition = 'transform 0.8s cubic-bezier(0.25, 0.46, 0.45, 0.94)';

              setTimeout(() => {
                data.next.container.style.transform = 'translateX(0)';
                setTimeout(resolve, 800);
              }, 50);
            });
          }
        }
      ],
      views: [
        {
          namespace: 'home',
          beforeEnter() {
            // Reinitialize home page animations
            const cards = document.querySelectorAll('.animate-float');
            cards.forEach((card, index) => {
              card.style.animationDelay = `${index * 0.5}s`;
            });
          }
        },
        {
          namespace: 'second',
          beforeEnter() {
            // Reinitialize second page animations
            const cards = document.querySelectorAll('.animate-float');
            cards.forEach((card, index) => {
              card.style.animationDelay = `${(index + 4) * 0.5}s`;
            });
          }
        }
      ]
    });

    return () => {
      barba.destroy();
    };
  }, []);

  // Handle React Router navigation
  useEffect(() => {
    if (barba.transitions) {
      barba.go(location.pathname);
    }
  }, [location]);

  return (
    <div ref={wrapperRef} data-barba="wrapper">
      {children}
    </div>
  );
};