//Swiper Config

// console.log("swiper loaded")
// const swiper = new Swiper('.swiper', {
//     loop: true, // Enable infinite looping
//     slidesPerView: 'auto', // Show as many slides as fit in the container
//     spaceBetween: 16, // Space between slides
//     navigation: {
//         nextEl: '.swiper-button-next',
//         prevEl: '.swiper-button-prev',
//     },
// });

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