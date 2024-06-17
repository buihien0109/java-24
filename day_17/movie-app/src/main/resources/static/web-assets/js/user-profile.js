// Xử lý validate form
$('#form-update-user').validate({
    rules: {
        name: {
            required: true
        },
    },
    messages: {
        name: {
            required: "Tên user không được để trống"
        },
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

// Xử lý submit form
const btnUpdate = document.getElementById('btn-update');
const nameEl = document.getElementById('name');
btnUpdate.addEventListener('click', () => {
    if (!$('#form-update-user').valid()) return;

    const data = {
        name: nameEl.value,
    }
    axios.put("/api/users/update-profile", data)
        .then(res => {
            toastr.success("Cập nhật thông tin thành công");
        })
        .catch(err => {
            toastr.error("Cập nhật thông tin thất bại");
        })
})

// Xử lý đổi mật khẩu
$('#form-update-password').validate({
    rules: {
        oldPassword: {
            required: true,
        },
        newPassword: {
            required: true,
        },
        confirmPassword: {
            required: true,
            equalTo: "#newPassword"
        }
    },
    messages: {
        oldPassword: {
            required: "Mật khẩu cũ không được để trống",
        },
        newPassword: {
            required: "Mật khẩu mới không được để trống",
        },
        confirmPassword: {
            required: "Mật khẩu xác nhận không được để trống",
            equalTo: "Mật khẩu không khớp"
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

// Xử lý submit form
const btnUpdatePassword = document.getElementById('btn-update-password');
const oldPasswordEl = document.getElementById('oldPassword');
const newPasswordEl = document.getElementById('newPassword');
const confirmPasswordEl = document.getElementById('confirmPassword');
btnUpdatePassword.addEventListener('click', () => {
    if (!$('#form-update-password').valid()) return;

    const data = {
        oldPassword: oldPasswordEl.value,
        newPassword: newPasswordEl.value,
        confirmPassword: confirmPasswordEl.value,
    }
    axios.put("/api/users/update-password", data)
        .then(res => {
            toastr.success("Cập nhật mật khẩu thành công");
        })
        .catch(err => {
            toastr.error("Cập nhật mật khẩu thất bại");
        })
})

// Ẩn hiện password
const btns = document.querySelectorAll('#form-update-password span.input-group-text');
const passwordEls = document.querySelectorAll('#form-update-password input[type="password"]');
btns.forEach((btn, index) => {
    btn.addEventListener("click", () => {
        const passwordEl = passwordEls[index];
        if(passwordEl.type === "password") {
            passwordEl.type = "text";
            btn.innerHTML = '<i class="fa-regular fa-eye-slash"></i>';
        } else {
            passwordEl.type = "password";
            btn.innerHTML = '<i class="fa-regular fa-eye"></i>';
        }
    })
})