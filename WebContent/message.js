// Function to open the dialog
function openDialog() {
    var dialog = document.getElementById('dialog');
    dialog.style.display = 'block';
}

// Function to close the dialog
function closeDialog() {
    var dialog = document.getElementById('dialog');
    dialog.style.display = 'none';
}

// Event listener for the button to open the dialog
document.getElementById('openDialogBtn').addEventListener('click', openDialog);
 