// Cập nhật thông tin phim
$('#form-update-movie').validate({
    rules: {
        name: {
            required: true
        },
        trailerUrl: {
            required: true
        },
        description: {
            required: true
        },
        releaseYear: {
            required: true
        },
        type: {
            required: true
        },
        status: {
            required: true
        },
        country: {
            required: true
        }
    },
    messages: {
        name: {
            required: "Tên không được để trống"
        },
        trailerUrl: {
            required: "Trailer URL không được để trống"
        },
        description: {
            required: "Mô tả không được để trống"
        },
        releaseYear: {
            required: "Năm phát hành không được để trống"
        },
        type: {
            required: "Loại phim không được để trống"
        },
        status: {
            required: "Trạng thái không được để trống"
        },
        country: {
            required: "Quốc gia không được để trống"
        }
    },
    errorElement: 'span',
    errorPlacement: function (error, element) {
        error.addClass('invalid-feedback');
        element.closest('.form-group').append(error);
    },
    highlight: function (element, errorClass, validClass) {
        $(element).addClass('is-invalid');
    },
    unhighlight: function (element, errorClass, validClass) {
        $(element).removeClass('is-invalid');
    }
});

const btnUpdate = document.getElementById('btn-update');
btnUpdate.addEventListener('click', () => {
    if (!$('#form-update-movie').valid()) return;

    const data = {
        name: document.getElementById('name').value,
        trailerUrl: document.getElementById('trailerUrl').value,
        description: document.getElementById('description').value,
        genreIds: $('#genre').val().map(e => Number(e)), // get value of select2 using jquery syntax
        directorIds: $('#director').val().map(e => Number(e)),
        actorIds: $('#actor').val().map(e => Number(e)),
        releaseYear: Number(document.getElementById('releaseYear').value),
        type: document.getElementById('type').value,
        status: document.getElementById('status').value === 'true',
        countryId: Number(document.getElementById('country').value)
    }

    axios.put(`/api/admin/movies/${movie.id}`, data)
        .then(res => {
            toastr.success("Cập nhật thông tin phim thành công");
        })
        .catch(err => {
            toastr.error(err.response.data.message);
        })
})

// Xóa movie
const btnDelete = document.getElementById('btn-delete');
btnDelete.addEventListener('click', () => {
    const isConfirm = window.confirm("Bạn có chắc chắn muốn xóa phim này không?");
    if (!isConfirm) return;

    axios.delete(`/api/admin/movies/${movie.id}`)
        .then(res => {
            toastr.success("Xóa phim thành công");
            setTimeout(() => {
                window.location.href = '/admin/movies';
            }, 1500);
        })
        .catch(err => {
            toastr.error(err.response.data.message);
        })
})

// Show trailer preview
const showEpisodePreview = (episodeId) => {
    const episode = episodes.find(e => e.id === episodeId);
    $('#modalPreview').modal('show');

    const video = document.querySelector('#modalPreview video');
    video.src = episode.videoUrl;
}

$('#modalPreview').on('hidden.bs.modal', function (event) {
    const video = document.querySelector('#modalPreview video');
    video.src = "";
})

// Upload poster
const inputPoster = document.getElementById('input-poster');
const thumbnail = document.getElementById('thumbnail');

inputPoster.addEventListener("change", (e) => {
    const file = e.target.files[0];

    const formData = new FormData();
    formData.append('file', file);

    axios.post(`/api/admin/movies/${movie.id}/upload-poster`, formData)
        .then(res => {
            thumbnail.src = res.data;
            toastr.success("Upload hình ảnh thành công");
        })
        .catch(err => {
            toastr.error(err.response.data.message);
        })
})

// render episodes
const tableBodyEl = document.querySelector("#table-episode tbody");
const renderEpisodes = (episodes) => {
    let html = "";
    episodes.forEach(episode => {
        html += `
            <tr>
                <td>${episode.displayOrder}</td>
                <td>${episode.name}</td>
                <td class="videoUrl">${episode.videoUrl ?? "Chưa cập nhật"}</td>
                <td class="duration">${episode.duration ?? "Chưa cập nhật"}</td>
                <td>${episode.status ? 'Công khai' : 'Ẩn'}</td>
                <td>
                    <label class="btn btn-warning btn-sm mb-0" for="input-video-${episode.id}">
                        <i class="fas fa-upload"></i>
                    </label>
                    <input type="file" id="input-video-${episode.id}" class="d-none" onchange="uploadEpisodeVideo(${episode.id}, event)">
                    <button class="btn btn-primary btn-sm" onclick="showEpisodePreview(${episode.id})">
                        <i class="fas fa-play"></i>
                    </button>
                    <button class="btn btn-success btn-sm" onclick="openModalUpdateEpisode(${episode.id})"><i class="fas fa-pencil-alt"></i></button>
                    <button class="btn btn-danger btn-sm" onclick="deleteEpisode(${episode.id})"><i class="fas fa-trash-alt"></i></button>
                </td>
            </tr>
        `;
    })

    tableBodyEl.innerHTML = html;
}

// Upload episode video
const uploadEpisodeVideo = (episodeId, event) => {
    const file = event.target.files[0];

    const formData = new FormData();
    formData.append('file', file);

    axios.post(`/api/admin/episodes/${episodeId}/upload-video`, formData)
        .then(res => {
            const index = episodes.findIndex(e => e.id === Number(episodeId));
            episodes[index] = res.data;
            renderEpisodes(episodes);

            toastr.success("Upload video thành công");
        })
        .catch(err => {
            console.log(err)
            toastr.error(err.response.data.message);
        })
}

$('#form-episode').validate({
    rules: {
        name: {
            required: true
        },
        displayOrder: {
            required: true
        },
        status: {
            required: true
        },
    },
    messages: {
        name: {
            required: "Tên không được để trống"
        },
        displayOrder: {
            required: "Thứ tự tập phim không được để trống"
        },
        status: {
            required: "Trạng thái không được để trống"
        }
    },
    errorElement: 'span',
    errorPlacement: function (error, element) {
        error.addClass('invalid-feedback');
        element.closest('.form-group').append(error);
    },
    highlight: function (element, errorClass, validClass) {
        $(element).addClass('is-invalid');
    },
    unhighlight: function (element, errorClass, validClass) {
        $(element).removeClass('is-invalid');
    }
});

let idEpisodeUpdate = null;
const modalEpisodeTitleEl = document.querySelector("#modalEpisode .modal-title");
const openModalCreateEpisode = () => {
    $('#modalEpisode').modal('show');
    modalEpisodeTitleEl.innerHTML = "Tạo tập phim";
}

// Submit form
const formEpisodeEl = document.getElementById("form-episode");
const episodeNameEl = document.getElementById("episodeName");
const episodeDisplayOrderEl = document.getElementById("episodeDisplayOrder");
const episodeStatusEl = document.getElementById("episodeStatus");

$('#modalEpisode').on('hidden.bs.modal', function (event) {
    modalEpisodeTitleEl.innerHTML = "Tạo tập phim";
    episodeNameEl.value = "";
    episodeDisplayOrderEl.value = "";
    episodeStatusEl.value = "false";
    idEpisodeUpdate = null;
})

formEpisodeEl.addEventListener("submit", (e) => {
    e.preventDefault();
    if (idEpisodeUpdate) {
        updateEpisode();
    } else {
        createEpisode();
    }
});

// create episode
const createEpisode = () => {
    if (!$('#form-episode').valid()) return;
    const data = {
        name: episodeNameEl.value,
        displayOrder: episodeDisplayOrderEl.value,
        status: episodeStatusEl.value === 'true',
        movieId: movie.id
    }

    axios.post(`/api/admin/episodes`, data)
        .then(res => {
            episodes.push(res.data);
            renderEpisodes(episodes);
            toastr.success("Tạo tập phim thành công");
            $('#modalEpisode').modal('hide');
        })
        .catch(err => {
            toastr.error(err.response.data.message);
        })
}

// update episode
const openModalUpdateEpisode = (episodeId) => {
    const episode = episodes.find(e => e.id === episodeId);

    $('#modalEpisode').modal('show');
    modalEpisodeTitleEl.innerHTML = "Cập nhật tập phim";
    episodeNameEl.value = episode.name;
    episodeDisplayOrderEl.value = episode.displayOrder;
    episodeStatusEl.value = episode.status ? "true" : "false";

    idEpisodeUpdate = episodeId;
};

const updateEpisode = () => {
    if (!$('#form-episode').valid()) return;

    const data = {
        name: episodeNameEl.value,
        displayOrder: episodeDisplayOrderEl.value,
        status: episodeStatusEl.value === 'true',
    }

    axios.put(`/api/admin/episodes/${idEpisodeUpdate}`, data)
        .then(res => {
            const index = episodes.findIndex(e => e.id === Number(idEpisodeUpdate));
            episodes[index] = res.data;
            renderEpisodes(episodes);

            toastr.success("Cập nhật tập phim thành công");
            $('#modalEpisode').modal('hide');
        })
        .catch(err => {
            toastr.error(err.response.data.message);
        })
}

// delete episode
const deleteEpisode = (id) => {
    const isConfirm = window.confirm("Bạn có chắc chắn muốn xóa tập phim này không?");
    if (!isConfirm) return;

    axios.delete(`/api/admin/episodes/${id}`)
        .then(res => {
            episodes = episodes.filter(e => e.id !== id);
            renderEpisodes(episodes);
            toastr.success("Xóa tập phim thành công");
        })
        .catch(err => {
            toastr.error(err.response.data.message);
        })
}