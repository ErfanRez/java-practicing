//! Data Structures, Alireza Nikian, Fall 1403
//! The following code is written by Erfan Rezaei
//! Islamic Azad University of Najafabad

//! Iterative method
function iterativeSeparator(s) {
  if (s.startsWith("0") || s.length === 0 || !/^\d+$/.test(s))
    return "Invalid number!";
  if (s.length <= 3) return s;

  let result = "";
  let counter = 0;
  let j = s.length;

  for (let i = s.length - 1; i >= 0; i--) {
    counter++;
    if (counter === 3 && i !== 0) {
      result = "," + s.substring(i, j) + result;
      j = i;
      counter = 0;
    } else if (i === 0) {
      result = s.substring(0, j) + result;
    }
  }

  return result;
}

//! Recursive method
function recursiveSeparator(s) {
  if (s.startsWith("0") || s === "" || !/^\d+$/.test(s)) {
    return "Invalid number!";
  }

  if (s.length <= 3) {
    return s;
  }

  return (
    recursiveSeparator(s.substring(0, s.length - 3)) +
    "," +
    s.substring(s.length - 3)
  );
}

function updateSeparator() {
  const input = document.getElementById("numberInput").value;
  const result = recursiveSeparator(input);
  document.getElementById("resultText").textContent = result;
}
