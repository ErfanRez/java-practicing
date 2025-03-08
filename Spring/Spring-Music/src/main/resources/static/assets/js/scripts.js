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
    const addSongButton = document.getElementById("addSongButton")
    if (submitButton) {
        submitButton.disabled = true;
        submitButton.innerText = 'Saving...';
    } else {
        console.error("Submit button not found!");
    }
    if(addSongButton){
        addSongButton.disabled = true;
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