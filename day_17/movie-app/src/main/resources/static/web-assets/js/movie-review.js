const stars = document.querySelectorAll(".rating .star");
const ratingValue = document.getElementById("rating-value");

let currentRating = 0;

stars.forEach((star) => {
    star.addEventListener("mouseover", () => {
        resetStars();
        const rating = parseInt(star.getAttribute("data-rating"));
        highlightStars(rating);
    });

    star.addEventListener("mouseout", () => {
        resetStars();
        highlightStars(currentRating);
    });

    star.addEventListener("click", () => {
        currentRating = parseInt(star.getAttribute("data-rating"));
        ratingValue.textContent = `Bạn đã đánh giá ${currentRating} sao.`;
        highlightStars(currentRating);
    });
});

function resetStars() {
    stars.forEach((star) => {
        star.classList.remove("active");
    });
}

function highlightStars(rating) {
    stars.forEach((star) => {
        const starRating = parseInt(star.getAttribute("data-rating"));
        if (starRating <= rating) {
            star.classList.add("active");
        }
    });
}

const formReviewEl = document.getElementById("form-review")
const contentEl = document.getElementById("review-content");
const modalReviewEl = document.getElementById("modalReview");

formReviewEl.addEventListener("submit", function(event) {
    event.preventDefault();

    if (!contentEl.value.trim() || currentRating === 0) {
        toastr.warning("Please enter content and select a rating.");
        return;
    }

    const reviewData = {
        content: contentEl.value.trim(),
        rating: currentRating,
        movieId: movie.id
    };

    axios.post("/api/reviews", reviewData)
        .then(response => {
            console.log(response.data);
            toastr.success("Review created successfully!");
        })
        .catch(error => {
            console.error(error);
            toastr.error("There was an error creating the review.");
        });
});
