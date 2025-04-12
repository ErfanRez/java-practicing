//Card Pagination

// Scroll left
function scrollContainerLeft(containerId) {
    const scrollableContainer = document.getElementById(containerId);
    const arrowLeft = scrollableContainer.previousElementSibling; // Left arrow
    scrollableContainer.scrollBy({ left: -600, behavior: 'smooth' });
    checkScrollPosition(scrollableContainer, arrowLeft, scrollableContainer.nextElementSibling);
}

// Scroll right
function scrollContainerRight(containerId) {
    const scrollableContainer = document.getElementById(containerId);
    const arrowRight = scrollableContainer.nextElementSibling; // Right arrow
    scrollableContainer.scrollBy({ left: 600, behavior: 'smooth' });
    checkScrollPosition(scrollableContainer, scrollableContainer.previousElementSibling, arrowRight);
}

// Check scroll position and disable/enable arrows
function checkScrollPosition(container, arrowLeft, arrowRight) {
    if (container.scrollLeft === 0) {
        arrowLeft.classList.add('disabled'); // Disable left arrow
    } else {
        arrowLeft.classList.remove('disabled'); // Enable left arrow
    }

    if (container.scrollLeft + container.clientWidth >= container.scrollWidth) {
        arrowRight.classList.add('disabled'); // Disable right arrow
    } else {
        arrowRight.classList.remove('disabled'); // Enable right arrow
    }
}

// Initial check for all containers
document.querySelectorAll('.scrollable-container').forEach(container => {
    const arrowLeft = container.previousElementSibling;
    const arrowRight = container.nextElementSibling;
    checkScrollPosition(container, arrowLeft, arrowRight);
    container.addEventListener('scroll', () => checkScrollPosition(container, arrowLeft, arrowRight));
});

/////////////////////////////////////////////////////////////////////////////////////////////

//Bootstrap error Toast

// Function to show the single error toast if there are validation errors
// function showErrorToast() {
//     const errorToast = document.getElementById('errorToast');
//     if (errorToast && errorToast.dataset.hasErrors === 'true') {
//         const toast = new bootstrap.Toast(errorToast);
//         toast.show();
//     }
// }

// // Call the function when the DOM is fully loaded
// document.addEventListener('DOMContentLoaded', showErrorToast);



// // Function to show all multiple error toasts
// function showErrorToasts() {
//     const toasts = document.querySelectorAll('.toast');
//     toasts.forEach(toast => {
//         const bootstrapToast = new bootstrap.Toast(toast);
//         bootstrapToast.show();
//     });
// }
//
// // Call the function when the DOM is fully loaded
// document.addEventListener('DOMContentLoaded', showErrorToasts);


/////////////////////////////////////////////////////////////////////////////////////////////

// Add album songs scripts

document.addEventListener('DOMContentLoaded', function () {
    const songInputsContainer = document.getElementById('songInputs');
    const addSongButton = document.getElementById('addSongButton');

    let songCount = 2; // Start counting from 3

    !!addSongButton && addSongButton.addEventListener('click', function () {
        songCount++;

        // Create new song title input
        const songTitleInput = document.createElement('div');
        songTitleInput.className = 'mb-3 song-input';
        songTitleInput.innerHTML = `
                <label for="songTitle${songCount}" class="form-label">Song Title</label>
                <input type="text" class="form-control" id="songTitle${songCount}" name="songTitles" required>
            `;

        // Create new song file input
        const songFileInput = document.createElement('div');
        songFileInput.className = 'mb-3 song-input';
        songFileInput.innerHTML = `
                <label for="songFile${songCount}" class="form-label">Song File</label>
                <input type="file" class="form-control" id="songFile${songCount}" name="songFiles" accept="audio/*" required>
                <div class="form-text">Only audio files (e.g., MP3, WAV) are allowed.</div>
            `;

        // Append new inputs to the container
        songInputsContainer.appendChild(songTitleInput);
        songInputsContainer.appendChild(songFileInput);
    });
});

/////////////////////////////////////////////////////////////////////////////////////////////

//disable submission button

function disableSubmitButton() {
    const submitButton = document.getElementById('submitButton');
    const addSongButton = document.getElementById("addSongButton");
    const loadingButton = document.getElementById("loadingButton");
    const loginButton = document.getElementById("loginButton");
    const registeringButton = document.getElementById("registeringButton");

    if (submitButton) {
        submitButton.disabled = true;
        submitButton.innerText = 'Saving...';
    } else {
        console.error("Submit button not found!");
    }

    if(addSongButton){
        addSongButton.disabled = true;
    }

    if (loadingButton) {
        loadingButton.disabled = true;
        loadingButton.innerText = 'Loading...';
    }

    if (loginButton) {
        loginButton.disabled = true;
        loginButton.innerText = 'Loading...';
    }

    if (registeringButton) {
        registeringButton.disabled = true;
        registeringButton.innerText = 'Loading...';
    }

}

/////////////////////////////////////////////////////////////////////////////////////////////

// Form submission success toast
function showToast() {
    const toastMessage = document.getElementById('toastMessage');
    if (toastMessage) {
        const toast = new bootstrap.Toast(toastMessage);
        toast.show();
    }
}

document.addEventListener('DOMContentLoaded', showToast);

/////////////////////////////////////////////////////////////////////////////////////////////

// Show modal when login button is clicked
document.getElementById('loginButton').addEventListener('click', function () {
    const loginModal = new bootstrap.Modal(document.getElementById('loginModal'));
    loginModal.show();
});

/////////////////////////////////////////////////////////////////////////////////////////////

//Change img src in artist registration form

document.addEventListener('DOMContentLoaded', function () {
    const profilePictureInput = document.getElementById('profilePicture');
    const profilePicturePreview = document.getElementById('profilePicturePreview');

    // Open file dialog when the image is clicked
    profilePicturePreview.addEventListener('click', function () {
        profilePictureInput.click();
    });

    // Update the image preview when a file is selected
    profilePictureInput.addEventListener('change', function (event) {
        const file = event.target.files[0];
        if (file) {
            const reader = new FileReader();
            reader.onload = function (e) {
                profilePicturePreview.src = e.target.result;
            };
            reader.readAsDataURL(file);
        }
    });
});

/////////////////////////////////////////////////////////////////////////////////////////////

document.addEventListener("DOMContentLoaded", () => {
    const audio = document.getElementById("audio");
    const playBtn = document.getElementById("player-play");
    const playIcon = playBtn.querySelector("i");
    const progressContainer = document.getElementById("progress-container");
    const progressFilled = document.getElementById("progress-filled");
    const currentTimeEl = document.getElementById("player-current");
    const durationEl = document.getElementById("player-duration");
    const titleEl = document.getElementById("player-title");
    const artistEl = document.getElementById("player-artist");
    const coverEl = document.getElementById("player-cover");

    // Function to format time in mm:ss
    function formatTime(seconds) {
        const mins = Math.floor(seconds / 60) || 0;
        const secs = Math.floor(seconds % 60) || 0;
        return `${mins}:${secs < 10 ? "0" : ""}${secs}`;
    }

    // Function to load a new song
    function loadSong({ audioUrl, title, artistNickname, coverUrl }) {
        // Reset audio to start
        audio.src = audioUrl;
        audio.currentTime = 0;
        audio.load();

        // Update UI elements
        titleEl.textContent = title;
        artistEl.textContent = artistNickname;
        coverEl.src = coverUrl || "/assets/images/album-cover.webp";

        // Reset progress bar
        progressFilled.style.width = "0%";
        currentTimeEl.textContent = formatTime(0);

        // Update duration when metadata is loaded
        audio.addEventListener("loadedmetadata", () => {
            durationEl.textContent = formatTime(audio.duration);
        });

        // Play the audio
        audio.play().then(() => {
            playIcon.classList.replace("fa-play", "fa-pause");
        }).catch(error => {
            console.error("Error playing audio:", error);
        });
    }

    // Play/Pause button click event
    playBtn.addEventListener("click", () => {
        if (audio.paused) {
            audio.play().then(() => {
                playIcon.classList.replace("fa-play", "fa-pause");
            }).catch(error => {
                console.error("Error playing audio:", error);
            });
        } else {
            audio.pause();
            playIcon.classList.replace("fa-pause", "fa-play");
        }
    });

    // Update progress bar as audio plays
    audio.addEventListener("timeupdate", () => {
        const percent = (audio.currentTime / audio.duration) * 100;
        progressFilled.style.width = `${percent}%`;
        currentTimeEl.textContent = formatTime(audio.currentTime);
    });

    // Seek functionality: update audio currentTime based on click position
    progressContainer.addEventListener("click", (e) => {
        const width = progressContainer.clientWidth;
        const clickX = e.offsetX;
        const duration = audio.duration;
        audio.currentTime = (clickX / width) * duration;
    });

    // Expose function globally to be called from song cards
    window.playSong = loadSong;
});










/////////////////////////////////////////////////////////////////////////////////////////////