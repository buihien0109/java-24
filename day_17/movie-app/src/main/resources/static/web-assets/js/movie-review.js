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

const reviewListEl = document.querySelector(".review-list");
const formReviewEl = document.getElementById("form-review")
const contentEl = document.getElementById("review-content");
const modalReviewEl = document.getElementById("modalReview");
const modalReviewInstance = new bootstrap.Modal('#modalReview', {
    keyboard: false
})

function formatDate(dateString) {
    const date = new Date(dateString);

    const day = ("0" + date.getDate()).slice(-2); // 09 -> 09 , 020 -> 20
    const month = ("0" + (date.getMonth() + 1)).slice(-2);
    const year = date.getFullYear();

    return `${day}/${month}/${year}`;
}

const renderReviews = (reviews) => {
    let html = "";
    reviews.forEach((review) => {
        html += `
            <div class="review-item d-flex mb-4">
                <div class="review-avatar">
                    <img src=${review.user.avatar} alt=${review.user.name}>
                </div>
                <div class="review-info ps-3">
                    <p class="mb-0">
                        <span class="fw-bold">${review.user.name}</span>
                        <span class="fst-italic text-muted">
                            (${formatDate(review.createdAt)})
                        </span>
                    </p>
                    <p class="mb-0 fw-bold">
                        ${review.rating}
                        <span class="text-warning"><i class="fa fa-star"></i></span>
                    </p>
                    <p class="mb-0">${review.content}</p>
                </div>
            </div>
        `
    });
    reviewListEl.innerHTML = html;
}

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
            toastr.success("Review created successfully!");

            reviews.unshift(response.data);
            renderReviews(reviews);

            modalReviewInstance.hide();
        })
        .catch(error => {
            console.error(error);
            toastr.error("There was an error creating the review.");
        });
});
