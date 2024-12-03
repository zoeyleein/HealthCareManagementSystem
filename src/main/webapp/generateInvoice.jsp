<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Generate Invoice</title>
    <script>
        // Function to search for patients based on user input
        function searchPatient() {
            const searchQuery = document.getElementById("patientSearch").value;
            // Trigger search if input length is at least 2 characters
            if (searchQuery.length >= 2) {
                const xhr = new XMLHttpRequest();
                // Send GET request to server with the search query
                xhr.open("GET", "searchPatient?query=" + encodeURIComponent(searchQuery), true);
                xhr.onreadystatechange = function () {
                    // Check if request is complete and response is successful
                    if (xhr.readyState === 4 && xhr.status === 200) {
                        const suggestions = JSON.parse(xhr.responseText); // Parse JSON response
                        const suggestionBox = document.getElementById("suggestions");
                        suggestionBox.innerHTML = ""; // Clear previous suggestions

                        // Create suggestion elements for each patient
                        suggestions.forEach(patient => {
                            const option = document.createElement("div");
                            option.textContent = patient.name; // Display patient name
                            option.dataset.id = patient.id; // Store patient ID in a data attribute
                            option.onclick = function () {
                                selectPatient(patient.id, patient.name); // Set patient info on click
                            };
                            suggestionBox.appendChild(option); // Add option to suggestions box
                        });
                    }
                };
                xhr.send(); // Send the request
            } else {
                // Clear suggestions if input is too short
                document.getElementById("suggestions").innerHTML = "";
            }
        }

        // Function to select a patient and populate the input fields
        function selectPatient(id, name) {
            document.getElementById("patientID").value = id; // Set hidden input for patient ID
            document.getElementById("patientSearch").value = name; // Update search box with patient name
            document.getElementById("suggestions").innerHTML = ""; // Clear suggestions
        }

        // Form validation to ensure a patient is selected
        function validateForm() {
            const patientID = document.getElementById("patientID").value;
            if (!patientID) { // Check if patient ID is empty
                alert("Please select a valid patient from the suggestions."); // Alert user
                return false; // Prevent form submission
            }
            return true; // Allow form submission if valid
        }
    </script>
    <style>
        /* Style for suggestions box */
        #suggestions {
            border: 1px solid #ccc; /* Add border to suggestions box */
            max-height: 200px; /* Limit the height of the suggestions box */
            overflow-y: auto; /* Add scroll if suggestions exceed max height */
            position: absolute; /* Position the suggestions box correctly */
            background-color: white; /* Ensure a white background */
            z-index: 1000; /* Ensure it appears above other elements */
        }

        /* Style for individual suggestion items */
        #suggestions div {
            padding: 5px; /* Add padding inside suggestion box */
            cursor: pointer; /* Change cursor to pointer */
        }

        /* Highlight suggestion on hover */
        #suggestions div:hover {
            background-color: #f0f0f0; /* Light gray background on hover */
        }
    </style>
</head>
<body>
    <h1>Generate Invoice</h1> <!-- Page title -->
    <form action="invoicing" method="post" onsubmit="return validateForm()"> <!-- Form submission to invoicing endpoint -->
        <div style="position: relative;"> <!-- Wrapper to position suggestions relative to input -->
            <label for="patientSearch">Search Patient:</label> <!-- Label for patient search -->
            <input type="text" id="patientSearch" onkeyup="searchPatient()" placeholder="Type to search..." required> <!-- Input for patient search -->
            <input type="hidden" id="patientID" name="patientID"> <!-- Hidden field to store selected patient ID -->
            <div id="suggestions"></div> <!-- Suggestions container -->
        </div>
        <br>
        <label for="totalAmount">Total Amount:</label> <!-- Label for total amount -->
        <input type="text" id="totalAmount" name="totalAmount" required> <!-- Input for total amount -->
        <br>
        <label for="isPaid">Mark as Paid:</label> <!-- Label for marking invoice as paid -->
        <input type="checkbox" id="isPaid" name="isPaid"> <!-- Checkbox to mark invoice as paid -->
        <br>
        <button type="submit">Submit</button> <!-- Submit button -->
    </form>
    
    <!-- Button to return to previous menu -->
    <button onclick="window.location.href='InvoiceMain.jsp'">Back to Invoice Main</button>
</body>
</html>




