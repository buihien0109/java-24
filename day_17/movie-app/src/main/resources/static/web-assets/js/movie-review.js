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
let updatedId = null;

modalReviewEl.addEventListener('show.bs.modal', event => {
    const modalTitle = document.querySelector("#modalReview .modal-title");
    const formBtn = document.querySelector("#form-review button[type='submit']");

    if (updatedId) {
        modalTitle.textContent = "Cập nhật bình luận";
        formBtn.textContent = "Cập nhật";
    } else {
        modalTitle.textContent = "Tạo bình luận";
        formBtn.textContent = "Tạo bình luận";
    }
})

modalReviewEl.addEventListener('hidden.bs.modal', event => {
    currentRating = 0;
    resetStars();
    ratingValue.textContent = "Vui lòng chọn đánh giá";
    contentEl.value = "";
    updatedId = null;
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
                    <div>
                        <button 
                            onclick="openModalReviewUpdate(${review.id})"
                            class="text-primary border-0 bg-transparent text-decoration-underline me-1">Sửa</button>
                        <button
                            onclick="deleteReview(${review.id})"
                            class="text-danger border-0 bg-transparent text-decoration-underline me-1">Xóa
                        </button>
                    </div>
                </div>
            </div>
        `
    });
    reviewListEl.innerHTML = html;
}

const render = reviews => {
    $('#pagination').pagination({
        dataSource: reviews,
        pageSize: 5,
        hideOnlyOnePage: true,
        className: 'paginationjs-big',
        callback: function (data, pagination) {
            renderReviews(data);
        }
    })
}

formReviewEl.addEventListener("submit", function (event) {
    event.preventDefault();

    if (!contentEl.value.trim() || currentRating === 0) {
        toastr.warning("Please enter content and select a rating.");
        return;
    }

    if (updatedId) {
        updateReview();
    } else {
        createReview();
    }
});

const createReview = () => {
    const reviewData = {
        content: contentEl.value.trim(),
        rating: currentRating,
        movieId: movie.id
    };

    axios.post("/api/reviews", reviewData)
        .then(response => {
            toastr.success("Tạo mới đánh giá thành công!");

            reviews.unshift(response.data);
            render(reviews);

            modalReviewInstance.hide();
        })
        .catch(error => {
            console.error(error);
            toastr.error("There was an error creating the review.");
        });
}

const updateReview = () => {
    const reviewData = {
        content: contentEl.value.trim(),
        rating: currentRating
    };

    axios.put(`/api/reviews/${updatedId}`, reviewData)
        .then(response => {
            toastr.success("Cập nhật đánh giá thành công!");

            // Tìm kiếm thông tin review cần cập nhật
            const review = reviews.find(review => review.id === updatedId);
            review.content = response.data.content;
            review.rating = response.data.rating;

            render(reviews);

            modalReviewInstance.hide();
        })
        .catch(error => {
            console.error(error);
            toastr.error("There was an error creating the review.");
        });
}

const deleteReview = (id) => {
    const isConfirm = window.confirm("Bạn có chắc chắn muốn xóa đánh giá này không?");
    if (!isConfirm) return;

    axios.delete(`/api/reviews/${id}`)
        .then(res => {
            toastr.success("Xóa đánh giá thành công!");

            // Xóa trong mảng ban đầu
            const index = reviews.findIndex(review => review.id === id);
            reviews.splice(index, 1);

            // Render lại giao diện
            render(reviews);
        })
        .catch(err => {
            console.error(err);
            toastr.error(err.response.data.message);
        })
}

const openModalReviewUpdate = (id) => {
    updatedId = id; // Lưu lại id review cần cập nhật
    modalReviewInstance.show(); // Hiển thị modal

    // Tìm kiếm thông tin review cần cập nhật
    const review = reviews.find(review => review.id === id);

    // Hiển thị thông tin review lên form
    currentRating = review.rating;
    ratingValue.textContent = `Bạn đã đánh giá ${currentRating} sao.`;
    highlightStars(currentRating);
    contentEl.value = review.content;
}

render(reviews);