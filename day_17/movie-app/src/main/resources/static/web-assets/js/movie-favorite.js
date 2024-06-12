const btnFavorite = document.getElementById('btn-favorite');

btnFavorite.addEventListener('click', function () {
    if (isFavorite) {
        // Xóa khỏi danh sách yêu thích
        removeFromFavorite(movie.id);
    } else {
        // Thêm vào danh sách yêu thích
        addToFavorite(movie.id);
    }
})

const addToFavorite = (movieId) => {
    axios.post(`/api/favorites?movieId=${movieId}`)
        .then(response => {
            toastr.success('Đã thêm vào danh sách yêu thích')
            isFavorite = true;
            btnFavorite.innerText = "Xóa khỏi yêu thích"
        })
        .catch(error => {
            console.log(error)
            toastr.error('Đã có lỗi xảy ra')
        })
}

const removeFromFavorite = (movieId) => {
    axios.delete(`/api/favorites?movieId=${movieId}`)
        .then(response => {
            toastr.success('Đã xóa khỏi danh sách yêu thích')
            isFavorite = false;
            btnFavorite.innerText = "Thêm vào yêu thích"
        })
        .catch(error => {
            console.log(error)
            toastr.error('Đã có lỗi xảy ra')
        })
}