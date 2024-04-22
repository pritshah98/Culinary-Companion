import { useState } from "react";
import { FaStar, FaStarHalfAlt } from "react-icons/fa";

const RecipeRating = ({ initialRating = 0, onRatingChange }) => {
  const [rating, setRating] = useState(initialRating);

  const handleClick = (value) => {
    // Round the value to the nearest half star
    const roundedValue = Math.round(value * 2) / 2;
    setRating(roundedValue);
    if (onRatingChange) {
      onRatingChange(roundedValue);
    }
  };

  return (
    <div className="flex items-center">
      {[...Array(5)].map((_, index) => {
        const starValue = index + 1;
        const filledStars = Math.floor(rating);
        const hasHalfStar = rating % 1 !== 0 && starValue === filledStars + 1;

        return (
          <span
            key={index}
            onClick={() => handleClick(starValue)}
            style={{ cursor: "pointer" }}
            role="button"
            aria-label={starValue <= filledStars ? "Filled Star" : "Empty Star"}
            aria-checked={starValue <= filledStars ? "true" : "false"}
            tabIndex="0"
          >
            {starValue <= filledStars ? (
              <FaStar color="#ffc107" />
            ) : (
              <FaStar color="#e4e5e9" />
            )}
            {hasHalfStar && <FaStarHalfAlt color="#ffc107" />}
          </span>
        );
      })}
    </div>
  );
};

export default RecipeRating;
